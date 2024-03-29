package com.dpfht.thestore_koin

import android.app.Application
import com.dpfht.thestore_koin.framework.Config
import com.dpfht.thestore_koin.framework.di.appModule
import com.dpfht.thestore_koin.framework.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TheApplication: Application() {

  override fun onCreate() {
    Config.BASE_URL = BuildConfig.BASE_URL
    super.onCreate()

    startKoin {
      androidContext(this@TheApplication)
      androidLogger()
      modules(appModule, networkModule)
    }
  }
}
