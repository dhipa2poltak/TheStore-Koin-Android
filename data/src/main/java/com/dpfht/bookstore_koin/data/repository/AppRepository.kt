package com.dpfht.bookstore_koin.data.repository

import com.dpfht.bookstore_koin.domain.model.DomainDataResponse
import com.dpfht.bookstore_koin.usecases.UseCaseResultWrapper

interface AppRepository {

  suspend fun getProducts(): UseCaseResultWrapper<DomainDataResponse>
}
