package com.dpfht.thestore_koin.domain.usecase

import com.dpfht.thestore_koin.domain.repository.AppRepository
import org.mockito.Mock

open class BaseUseCaseTest {

  @Mock
  protected lateinit var appRepository: AppRepository
}
