package com.dpfht.thestore_koin.domain.model

data class DomainProduct(
  val productId: Long = -1,
  val productName: String = "",
  val price: Double = 0.0,
  val stock: Int = 0,
  val description: String = "",
  val images: DomainImage? = null
)