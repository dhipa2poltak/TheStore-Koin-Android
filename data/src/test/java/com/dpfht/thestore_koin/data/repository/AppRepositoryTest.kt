package com.dpfht.thestore_koin.data.repository

import com.dpfht.thestore_koin.data.datasource.AppDataSource
import com.dpfht.thestore_koin.data.datasource.NetworkStateDataSource
import com.dpfht.thestore_koin.data.model.remote.Data
import com.dpfht.thestore_koin.data.model.remote.DataResponse
import com.dpfht.thestore_koin.data.model.remote.Product
import com.dpfht.thestore_koin.data.model.remote.toDomain
import com.dpfht.thestore_koin.domain.repository.AppRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class AppRepositoryTest {

  private lateinit var appRepository: AppRepository

  @Mock
  private lateinit var remoteDataSource: AppDataSource

  @Mock
  private lateinit var localDataSource: AppDataSource

  @Mock
  private lateinit var onlineChecker: NetworkStateDataSource

  private val products = arrayListOf(Product(), Product(), Product())
  private val data = Data(products = products)
  private val dataResponse = DataResponse("200", "success", data)

  @Before
  fun setup() {
    appRepository = AppRepositoryImpl(remoteDataSource, localDataSource, onlineChecker)
  }

  @Test
  fun `fetch product in online condition successfully`() = runTest {
    whenever(onlineChecker.isOnline()).thenReturn(true)
    whenever(remoteDataSource.getProducts()).thenReturn(dataResponse.toDomain())

    val expected = dataResponse.toDomain()
    val actual = appRepository.getProducts()

    assertTrue(expected == actual)
  }

  @Test
  fun `fetch product in offline condition successfully`() = runTest {
    whenever(onlineChecker.isOnline()).thenReturn(false)
    whenever(localDataSource.getProducts()).thenReturn(dataResponse.toDomain())

    val expected = dataResponse.toDomain()
    val actual = appRepository.getProducts()

    assertTrue(expected == actual)
  }
}
