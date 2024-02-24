package com.dpfht.thestore_koin.framework.data.datasource.remote

import com.dpfht.thestore_koin.data.datasource.AppDataSource
import com.dpfht.thestore_koin.data.model.remote.toDomain
import com.dpfht.thestore_koin.domain.entity.AppException
import com.dpfht.thestore_koin.domain.entity.DataDomain
import com.dpfht.thestore_koin.framework.data.datasource.remote.rest.RestService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class RemoteDataSourceImpl(private val restService: RestService): AppDataSource {

  override suspend fun getProducts(): DataDomain {
    return safeApiCall(Dispatchers.IO) { restService.getProducts().toDomain() }
  }

  private suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher, apiCall: suspend () -> T): T {
    return withContext(dispatcher) {
      try {
        apiCall.invoke()
      } catch (t: Throwable) {
        throw when (t) {
          is IOException -> AppException("error in connection")
          is HttpException -> {
            //val code = t.code()
            //val errorResponse = convertErrorBody(t)
            //GenericError(code, t)
            AppException(t.message ?: "")
          }
          else -> {
            AppException("error in conversion")
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
