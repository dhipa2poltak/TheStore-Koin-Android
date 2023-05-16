package com.dpfht.thestore_koin.data.model.remote

import androidx.annotation.Keep
import com.dpfht.thestore_koin.domain.entity.DataEntity
import com.dpfht.thestore_koin.domain.entity.DataDomain

@Keep
data class DataResponse(
  val status: String? = "",
  val message: String? = "",
  val data: Data? = null
)

fun DataResponse.toDomain(): DataDomain {
  return DataDomain(
    data?.toDomain() ?: DataEntity("", listOf())
  )
}
