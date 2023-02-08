package com.dpfht.bookstore_koin.usecases

import com.dpfht.bookstore_koin.domain.model.DomainDataResponse

interface GetProductsUseCase {

  suspend operator fun invoke(): UseCaseResultWrapper<DomainDataResponse>
}
