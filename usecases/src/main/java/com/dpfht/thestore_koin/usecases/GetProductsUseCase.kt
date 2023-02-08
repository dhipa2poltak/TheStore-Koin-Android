package com.dpfht.thestore_koin.usecases

import com.dpfht.thestore_koin.domain.model.DomainDataResponse

interface GetProductsUseCase {

  suspend operator fun invoke(): UseCaseResultWrapper<DomainDataResponse>
}
