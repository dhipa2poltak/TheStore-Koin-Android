package com.dpfht.bookstore_koin.framework.rest.api

import com.dpfht.bookstore_koin.data.model.remote.DataResponse
import retrofit2.http.GET

interface RestService {

  //@GET("b/5ee794370e966a7aa369eafd")
  @GET("v1/8e9b0905-00a9-4458-8cc0-5d95154dadb7")
  suspend fun getProducts(): DataResponse
}
