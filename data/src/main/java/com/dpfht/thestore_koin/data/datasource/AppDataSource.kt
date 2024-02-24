package com.dpfht.thestore_koin.data.datasource

import com.dpfht.thestore_koin.domain.entity.DataDomain

interface AppDataSource {

  suspend fun getProducts(): DataDomain
}
