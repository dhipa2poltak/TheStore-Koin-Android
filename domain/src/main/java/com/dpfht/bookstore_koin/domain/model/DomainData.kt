package com.dpfht.bookstore_koin.domain.model

data class DomainData(
  val banner: String = "",
  val products: ArrayList<DomainProduct> = arrayListOf()
)