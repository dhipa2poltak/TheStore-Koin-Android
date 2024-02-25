package com.dpfht.thestore_koin.domain.usecase

import com.dpfht.thestore_koin.domain.entity.AppException
import com.dpfht.thestore_koin.domain.entity.DataDomain
import com.dpfht.thestore_koin.domain.entity.DataEntity
import com.dpfht.thestore_koin.domain.entity.ProductEntity
import com.dpfht.thestore_koin.domain.entity.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class GetProductsUseCaseTest: BaseUseCaseTest() {

  private lateinit var getProductUseCase: GetProductsUseCase

  private val products = arrayListOf(ProductEntity(), ProductEntity(), ProductEntity())
  private val data = DataEntity(products = products)
  private val dataResult = DataDomain(data)

  @Before
  fun setup() {
    getProductUseCase = GetProductsUseCaseImpl(appRepository)
  }

  @Test
  fun `fetch product successfully`() = runTest {
    whenever(appRepository.getProducts()).thenReturn(dataResult)

    val expected = Result.Success(dataResult)
    val actual = getProductUseCase()

    assertTrue(expected == actual)
  }

  @Test
  fun `failed fetch product`() = runTest {
    val msg = "error in conversion"

    whenever(appRepository.getProducts()).then {
      throw AppException(msg)
    }

    val expected = Result.Error(msg)
    val actual = getProductUseCase()

    assertTrue(expected == actual)
  }
}
