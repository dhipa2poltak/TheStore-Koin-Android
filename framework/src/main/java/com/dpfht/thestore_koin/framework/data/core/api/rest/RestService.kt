package com.dpfht.thestore_koin.framework.data.core.api.rest

import com.dpfht.thestore_koin.data.model.remote.DataResponse
import retrofit2.http.GET

interface RestService {

  @GET("v1/8e9b0905-00a9-4458-8cc0-5d95154dadb7")
  suspend fun getProducts(): DataResponse
}
