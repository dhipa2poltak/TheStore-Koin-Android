package com.dpfht.bookstore_koin.framework.rest.api

import com.dpfht.bookstore_koin.data.model.remote.toDomain
import com.dpfht.bookstore_koin.data.repository.AppDataSource
import com.dpfht.bookstore_koin.domain.model.DomainDataResponse
import com.dpfht.bookstore_koin.framework.rest.api.ResultWrapper.GenericError
import com.dpfht.bookstore_koin.framework.rest.api.ResultWrapper.NetworkError
import com.dpfht.bookstore_koin.framework.rest.api.ResultWrapper.Success
import com.dpfht.bookstore_koin.usecases.UseCaseResultWrapper
import kotlinx.coroutines.Dispatchers

class RemoteDataSourceImpl(private val restService: RestService): AppDataSource {

  override suspend fun getProducts(): UseCaseResultWrapper<DomainDataResponse> {
    return when (val result = safeApiCall(Dispatchers.IO) { restService.getProducts() }) {
      is Success -> {
        UseCaseResultWrapper.Success(result.value.toDomain())
      }
      is GenericError -> {
        if (result.code != null && result.error != null) {
          UseCaseResultWrapper.ErrorResult(result.error.localizedMessage ?: "")
        } else {
          UseCaseResultWrapper.ErrorResult("error in conversion")
        }
      }
      is NetworkError -> {
        UseCaseResultWrapper.ErrorResult("error in connection")
      }
    }
  }
}
