package com.dpfht.thestore_koin.data.model.remote

import androidx.annotation.Keep
import com.dpfht.thestore_koin.domain.entity.DataEntity

@Keep
data class Data(
  val banner: String? = "",
  val products: List<Product>? = arrayListOf()
)

fun Data.toDomain(): DataEntity {
  return DataEntity(
    banner ?: "",
    ArrayList(products?.map { it.toDomain() } ?: arrayListOf())
  )
}
