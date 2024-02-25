package com.dpfht.thestore_koin.data.datasource.remote

import com.dpfht.thestore_koin.data.datasource.AppDataSource
import com.dpfht.thestore_koin.data.model.remote.Data
import com.dpfht.thestore_koin.data.model.remote.DataResponse
import com.dpfht.thestore_koin.data.model.remote.Product
import com.dpfht.thestore_koin.data.model.remote.toDomain
import com.dpfht.thestore_koin.framework.data.datasource.remote.RemoteDataSourceImpl
import com.dpfht.thestore_koin.framework.data.datasource.remote.rest.RestService
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
class RemoteDataSourceTest {

  private lateinit var remoteDataSource: AppDataSource

  @Mock
  private lateinit var restService: RestService

  @Before
  fun setup() {
    remoteDataSource = RemoteDataSourceImpl(restService)
  }

  @Test
  fun `ensure when calling getProducts method in RemoteDataSource, it returns records of product`() = runTest {
    val products = arrayListOf(Product(), Product(), Product())
    val data = Data(products = products)
    val dataResponse = DataResponse("200", "success", data)

    whenever(restService.getProducts()).thenReturn(dataResponse)

    val actual = remoteDataSource.getProducts()

    assertTrue(dataResponse.toDomain() == actual)
  }
}
