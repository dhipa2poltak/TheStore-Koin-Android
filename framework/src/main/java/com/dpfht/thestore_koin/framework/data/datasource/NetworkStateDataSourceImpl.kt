package com.dpfht.thestore_koin.framework.data.datasource

import com.dpfht.thestore_koin.data.datasource.NetworkStateDataSource
import com.dpfht.thestore_koin.framework.util.net.OnlineChecker

class NetworkStateDataSourceImpl(
  private val onlineChecker: OnlineChecker
): NetworkStateDataSource {

  override fun isOnline() = onlineChecker.isOnline()
}
