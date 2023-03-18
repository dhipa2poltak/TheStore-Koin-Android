package com.dpfht.thestore_koin.data.repository

import com.dpfht.thestore_koin.domain.model.DomainDataResponse
import com.dpfht.thestore_koin.data.UseCaseResultWrapper

interface AppDataSource {

  suspend fun getProducts(): UseCaseResultWrapper<DomainDataResponse>
}
