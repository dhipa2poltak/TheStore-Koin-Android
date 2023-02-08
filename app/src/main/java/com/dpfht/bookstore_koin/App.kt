package com.dpfht.bookstore_koin

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.dpfht.bookstore_koin.di.appModule
import com.dpfht.bookstore_koin.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App: Application() {

  override fun onCreate() {
    super.onCreate()

    startKoin {
      androidContext(this@App)
      androidLogger()
      modules(appModule, networkModule)
    }
  }

  override fun attachBaseContext(base: Context) {
    super.attachBaseContext(base)
    MultiDex.install(this)
  }
}
