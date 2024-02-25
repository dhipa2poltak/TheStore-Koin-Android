package com.dpfht.thestore_koin.data.datasource.local

import android.content.Context
import android.content.res.AssetManager
import androidx.test.core.app.ApplicationProvider
import com.dpfht.thestore_koin.data.datasource.AppDataSource
import com.dpfht.thestore_koin.domain.entity.AppException
import com.dpfht.thestore_koin.framework.data.datasource.local.LocalDataSourceImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.robolectric.RobolectricTestRunner
import java.io.InputStream
import java.nio.charset.Charset

@RunWith(RobolectricTestRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class LocalDataSourceTest {

  private lateinit var localDataSource: AppDataSource
  private val expectedCount = 15

  private val msg = "this is error message"

  @Test
  fun `get products method returns 15 record products`() = runTest {
    val context = ApplicationProvider.getApplicationContext<Context>()
    localDataSource = LocalDataSourceImpl(context.assets)

    val actual = localDataSource.getProducts().data.products.size

    assertTrue(expectedCount == actual)
  }

  @Test
  fun `get products method returns empty data`() = runTest {
    val asset: AssetManager = mock()
    localDataSource = LocalDataSourceImpl(asset)

    whenever(asset.open(anyString())).thenReturn(InputStream.nullInputStream())

    val actual = localDataSource.getProducts()

    assertTrue(actual.data.products.isEmpty())
  }

  @Test
  fun `get products method generates exception`() = runTest {
    val asset: AssetManager = mock()
    localDataSource = LocalDataSourceImpl(asset)

    whenever(asset.open(anyString())).then {
      throw Exception(msg)
    }

    var actual = ""

    try {
      localDataSource.getProducts()
    } catch (e: AppException) {
      actual = e.message
    }

    assertTrue(msg == actual)
  }

  @Test
  fun `get products method generates error in conversion`() = runTest {
    val asset: AssetManager = mock()
    localDataSource = LocalDataSourceImpl(asset)

    whenever(asset.open(anyString())).thenReturn("this is incorrect format".byteInputStream(Charset.defaultCharset()))

    var actual = ""

    try {
      localDataSource.getProducts()
    } catch (e: AppException) {
      actual = e.message
    }

    assertTrue(actual.isNotEmpty())
  }
}
