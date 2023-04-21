package com.dpfht.thestore_koin.data.datasource

import com.dpfht.thestore_koin.domain.entity.DataDomain
import com.dpfht.thestore_koin.domain.entity.Result

interface AppDataSource {

  suspend fun getProducts(): Result<DataDomain>
}
