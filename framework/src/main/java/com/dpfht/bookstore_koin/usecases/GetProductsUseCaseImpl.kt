package com.dpfht.bookstore_koin.usecases

import com.dpfht.bookstore_koin.data.repository.AppRepository
import com.dpfht.bookstore_koin.domain.model.DomainDataResponse

class GetProductsUseCaseImpl(
  private val appRepository: AppRepository
): GetProductsUseCase {

  override suspend operator fun invoke(): UseCaseResultWrapper<DomainDataResponse> {
    return appRepository.getProducts()
  }
}
