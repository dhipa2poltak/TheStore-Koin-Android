package com.dpfht.thestore_koin.data.model.remote

import androidx.annotation.Keep
import com.dpfht.thestore_koin.domain.entity.ProductEntity
import com.google.gson.annotations.SerializedName

@Keep
data class Product(
  @SerializedName("product_id")
  val productId: Long? = -1,
  @SerializedName("product_name")
  val productName: String? = "",
  val price: Double? = 0.0,
  val stock: Int? = 0,
  val description: String? = "",
  val images: Image? = null
)

fun Product.toDomain(): ProductEntity {
  return ProductEntity(
    productId ?: -1,
    productName ?: "",
    price ?: 0.0,
    stock ?: 0,
    description ?: "",
    images?.toDomain()
  )
}

