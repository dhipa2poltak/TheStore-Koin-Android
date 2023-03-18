package com.dpfht.thestore_koin.framework.di

import android.app.Application
import android.content.Context
import com.dpfht.thestore_koin.data.repository.AppDataSource
import com.dpfht.thestore_koin.data.repository.AppRepository
import com.dpfht.thestore_koin.framework.AppRepositoryImpl
import com.dpfht.thestore_koin.framework.LocalDataSourceImpl
import com.dpfht.thestore_koin.framework.RemoteDataSourceImpl
import com.dpfht.thestore_koin.framework.rest.api.RestService
import com.dpfht.thestore_koin.util.net.DefaultOnlineChecker
import com.dpfht.thestore_koin.util.net.OnlineChecker
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
  single(named("remote")) { provideRemoteDataSource(get()) }
  single(named("local")) { provideLocalDataSource(androidContext()) }
  single { provideOnlineChecker(androidApplication()) }
  single { provideAppRepository(get(named("remote")), get(named("local")), get()) }
}

fun provideRemoteDataSource(restService: RestService): AppDataSource {
  return RemoteDataSourceImpl(restService)
}

fun provideLocalDataSource(context: Context): AppDataSource {
  return LocalDataSourceImpl(context.assets)
}

fun provideOnlineChecker(app: Application): OnlineChecker {
  return DefaultOnlineChecker(app)
}

fun provideAppRepository(
  remoteDataSource: AppDataSource,
  localDataSource: AppDataSource,
  onlineChecker: OnlineChecker
): AppRepository {
  return AppRepositoryImpl(remoteDataSource, localDataSource, onlineChecker)
}
