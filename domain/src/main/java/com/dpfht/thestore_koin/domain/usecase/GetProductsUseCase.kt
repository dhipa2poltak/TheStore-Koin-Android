package com.dpfht.thestore_koin.domain.usecase

import com.dpfht.thestore_koin.domain.entity.DataDomain
import com.dpfht.thestore_koin.domain.entity.Result

interface GetProductsUseCase {

  suspend operator fun invoke(): Result<DataDomain>
}
