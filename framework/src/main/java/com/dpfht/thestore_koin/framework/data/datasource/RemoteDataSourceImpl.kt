package com.dpfht.thestore_koin.framework.data.datasource

import com.dpfht.thestore_koin.data.datasource.AppDataSource
import com.dpfht.thestore_koin.data.model.remote.toDomain
import com.dpfht.thestore_koin.domain.entity.DataDomain
import com.dpfht.thestore_koin.framework.data.core.api.rest.RestService
import com.dpfht.thestore_koin.framework.data.core.api.rest.safeApiCall
import com.dpfht.thestore_koin.domain.entity.Result
import kotlinx.coroutines.Dispatchers

class RemoteDataSourceImpl(private val restService: RestService): AppDataSource {

  override suspend fun getProducts(): Result<DataDomain> {
    return safeApiCall(Dispatchers.IO) { restService.getProducts().toDomain() }
  }
}
