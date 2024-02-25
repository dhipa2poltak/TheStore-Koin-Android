package com.dpfht.thestore_koin.data.datasource.local

import com.dpfht.thestore_koin.data.datasource.NetworkStateDataSource
import com.dpfht.thestore_koin.framework.data.datasource.local.NetworkStateDataSourceImpl
import com.dpfht.thestore_koin.framework.data.datasource.local.onlinechecker.OnlineChecker
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class NetworkStateDataSourceTest {

  private lateinit var networkStateDataSource: NetworkStateDataSource

  @Mock
  private lateinit var onlineChecker: OnlineChecker

  @Before
  fun setup() {
    networkStateDataSource = NetworkStateDataSourceImpl(onlineChecker)
  }

  @Test
  fun `ensure isOnline true`() {
    whenever(onlineChecker.isOnline()).thenReturn(true)

    assertTrue(networkStateDataSource.isOnline())
  }

  @Test
  fun `ensure isOnline false`() {
    whenever(onlineChecker.isOnline()).thenReturn(false)

    assertFalse(networkStateDataSource.isOnline())
  }
}
