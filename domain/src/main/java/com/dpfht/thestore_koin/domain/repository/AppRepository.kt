package com.dpfht.thestore_koin.domain.repository

import com.dpfht.thestore_koin.domain.entity.DataDomain

interface AppRepository {

  suspend fun getProducts(): DataDomain
}
