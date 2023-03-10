package com.dpfht.thestore_koin.data.model.remote

import androidx.annotation.Keep
import com.dpfht.thestore_koin.domain.model.DomainData

@Keep
data class Data(
  val banner: String = "",
  val products: List<Product> = arrayListOf()
)

fun Data.toDomain(): DomainData {
  return DomainData(banner, ArrayList(products.map { it.toDomain() }))
}
