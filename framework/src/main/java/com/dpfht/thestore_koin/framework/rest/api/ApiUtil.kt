package com.dpfht.thestore_koin.framework.rest.api

import com.dpfht.thestore_koin.framework.rest.api.ResultWrapper.GenericError
import com.dpfht.thestore_koin.framework.rest.api.ResultWrapper.NetworkError
import com.dpfht.thestore_koin.framework.rest.api.ResultWrapper.Success
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher, apiCall: suspend () -> T): ResultWrapper<T> {
  return withContext(dispatcher) {
    try {
      Success(apiCall.invoke())
    } catch (t: Throwable) {
      when (t) {
        is IOException -> NetworkError
        is HttpException -> {
          val code = t.code()
          //val errorResponse = convertErrorBody(t)
          GenericError(code, t)
        }
        else -> {
          GenericError(null, null)
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