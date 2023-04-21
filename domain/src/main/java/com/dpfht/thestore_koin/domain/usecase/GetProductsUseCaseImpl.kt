package com.dpfht.thestore_koin.domain.usecase

import com.dpfht.thestore_koin.domain.entity.DataDomain
import com.dpfht.thestore_koin.domain.entity.Result
import com.dpfht.thestore_koin.domain.repository.AppRepository

class GetProductsUseCaseImpl(
  private val appRepository: AppRepository
): GetProductsUseCase {

  override suspend operator fun invoke(): Result<DataDomain> {
    return appRepository.getProducts()
  }
}
