package com.dpfht.thestore_koin.framework.data.datasource.local

import com.dpfht.thestore_koin.data.datasource.NetworkStateDataSource
import com.dpfht.thestore_koin.framework.data.datasource.local.onlinechecker.OnlineChecker

class NetworkStateDataSourceImpl(
  private val onlineChecker: OnlineChecker
): NetworkStateDataSource {

  override fun isOnline() = onlineChecker.isOnline()
}
