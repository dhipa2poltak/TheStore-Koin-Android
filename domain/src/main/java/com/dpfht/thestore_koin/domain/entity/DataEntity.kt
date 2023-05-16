package com.dpfht.thestore_koin.domain.entity

data class DataEntity(
  val banner: String = "",
  val products: List<ProductEntity> = listOf()
)
