package com.dpfht.thestore_koin.framework.data.datasource.local.onlinechecker

interface OnlineChecker {

  fun isOnline(): Boolean

  fun setOnNetworkStateChangedListener(listener: OnlineCheckerListener?)

  interface OnlineCheckerListener {
    fun networkStateChanged(isOnline: Boolean)
  }
}
