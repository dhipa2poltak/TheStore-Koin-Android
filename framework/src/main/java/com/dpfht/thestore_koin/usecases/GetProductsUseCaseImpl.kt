package com.dpfht.thestore_koin.usecases

import com.dpfht.thestore_koin.usecases.GetProductsUseCase
import com.dpfht.thestore_koin.usecases.UseCaseResultWrapper
import com.dpfht.thestore_koin.data.repository.AppRepository
import com.dpfht.thestore_koin.domain.model.DomainDataResponse

class GetProductsUseCaseImpl(
  private val appRepository: AppRepository
): GetProductsUseCase {

  override suspend operator fun invoke(): UseCaseResultWrapper<DomainDataResponse> {
    return appRepository.getProducts()
  }
}
