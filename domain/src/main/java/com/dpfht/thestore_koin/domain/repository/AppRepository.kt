package com.dpfht.thestore_koin.domain.repository

import com.dpfht.thestore_koin.domain.entity.DataDomain
import com.dpfht.thestore_koin.domain.entity.Result

interface AppRepository {

  suspend fun getProducts(): Result<DataDomain>
}
