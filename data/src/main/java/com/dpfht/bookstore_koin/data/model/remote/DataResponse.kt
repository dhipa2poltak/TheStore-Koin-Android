package com.dpfht.bookstore_koin.data.model.remote

import androidx.annotation.Keep
import com.dpfht.bookstore_koin.domain.model.DomainData
import com.dpfht.bookstore_koin.domain.model.DomainDataResponse

@Keep
data class DataResponse(
  val status: String = "",
  val message: String = "",
  val data: Data? = null
)

fun DataResponse.toDomain(): DomainDataResponse {
  return DomainDataResponse(data?.toDomain() ?: DomainData("", arrayListOf()))
}
