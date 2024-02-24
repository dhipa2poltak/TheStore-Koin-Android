package com.dpfht.thestore_koin.framework.data.datasource.remote

import com.dpfht.thestore_koin.data.datasource.AppDataSource
import com.dpfht.thestore_koin.data.model.remote.toDomain
import com.dpfht.thestore_koin.domain.entity.DataDomain
import com.dpfht.thestore_koin.domain.entity.Result
import com.dpfht.thestore_koin.domain.entity.Result.ErrorResult
import com.dpfht.thestore_koin.domain.entity.Result.Success
import com.dpfht.thestore_koin.framework.data.datasource.remote.rest.RestService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class RemoteDataSourceImpl(private val restService: RestService): AppDataSource {

  override suspend fun getProducts(): Result<DataDomain> {
    return safeApiCall(Dispatchers.IO) { restService.getProducts().toDomain() }
  }

  private suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher, apiCall: suspend () -> T): Result<T> {
    return withContext(dispatcher) {
      try {
        Success(apiCall.invoke())
      } catch (t: Throwable) {
        when (t) {
          is IOException -> ErrorResult("error in connection")
          is HttpException -> {
            //val code = t.code()
            //val errorResponse = convertErrorBody(t)
            //GenericError(code, t)
            ErrorResult(t.message ?: "")
          }
          else -> {
            ErrorResult("error in conversion")
          }
        }
      }
    }
  }

  /*
  private fun convertErrorBody(t: HttpException): ErrorResponse? {
    return try {
      t.response()?.errorBody()?.source().let {
        val json = it?.readString(Charset.defaultCharset())
        val typeToken = object : TypeToken<ErrorResponse>() {}.type
        val errorResponse = Gson().fromJson<ErrorResponse>(json, typeToken)
        errorResponse
      }
    } catch (e: Exception) {
      null
    }
  }
  */
}
