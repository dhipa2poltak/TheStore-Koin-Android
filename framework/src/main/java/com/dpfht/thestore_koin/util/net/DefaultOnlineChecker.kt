package com.dpfht.thestore_koin.util.net

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest.Builder
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.LOLLIPOP
import android.os.Handler
import android.util.Log
import com.dpfht.thestore_koin.util.net.OnlineChecker.OnlineCheckerListener


class DefaultOnlineChecker(application: Application) : OnlineChecker {

  companion object {
    private const val TAG = "DefaultOnlineChecker"
  }

  private val application: Application
  private val connectivityManager: ConnectivityManager

  init {
    this.application = application
    connectivityManager = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
  }

  override fun isOnline(): Boolean {
    val netInfo = connectivityManager.activeNetworkInfo
    return netInfo != null && netInfo.isConnectedOrConnecting
  }

  override fun setOnNetworkStateChangedListener(listener: OnlineCheckerListener?) {
    val mainHandler = Handler(application.mainLooper)


    if (SDK_INT >= LOLLIPOP) {
      val builder = Builder()
      builder.addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
      builder.addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
      builder.addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
      builder.addTransportType(NetworkCapabilities.TRANSPORT_VPN)
      val callback: NetworkCallback = object : NetworkCallback() {
        override fun onAvailable(network: Network) {
          super.onAvailable(network)
          Log.i(TAG, "isOnline: " + isOnline())
          val publish = Runnable { listener!!.networkStateChanged(isOnline()) }
          mainHandler.post(publish)
        }

        override fun onLost(network: Network) {
          super.onLost(network)
          Log.i(TAG, "isOnline: " + isOnline())
          val publish = Runnable { listener!!.networkStateChanged(isOnline()) }
          mainHandler.post(publish)
        }
      }
      connectivityManager.registerNetworkCallback(builder.build(), callback)
    } else {
      val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
      val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
          Log.i(TAG, "isOnline: " + isOnline())
          val publish = Runnable { listener!!.networkStateChanged(isOnline()) }
          mainHandler.post(publish)
        }
      }
      application.registerReceiver(receiver, filter)
    }
  }
}
