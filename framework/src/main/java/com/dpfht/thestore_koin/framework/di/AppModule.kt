package com.dpfht.thestore_koin.framework.di

import android.content.Context
import com.dpfht.thestore_koin.data.datasource.AppDataSource
import com.dpfht.thestore_koin.data.datasource.NetworkStateDataSource
import com.dpfht.thestore_koin.data.repository.AppRepositoryImpl
import com.dpfht.thestore_koin.domain.entity.ProductEntity
import com.dpfht.thestore_koin.domain.repository.AppRepository
import com.dpfht.thestore_koin.domain.usecase.GetProductsUseCase
import com.dpfht.thestore_koin.domain.usecase.GetProductsUseCaseImpl
import com.dpfht.thestore_koin.framework.data.datasource.local.LocalDataSourceImpl
import com.dpfht.thestore_koin.framework.data.datasource.local.NetworkStateDataSourceImpl
import com.dpfht.thestore_koin.framework.data.datasource.local.onlinechecker.DefaultOnlineChecker
import com.dpfht.thestore_koin.framework.data.datasource.local.onlinechecker.OnlineChecker
import com.dpfht.thestore_koin.framework.data.datasource.remote.RemoteDataSourceImpl
import com.dpfht.thestore_koin.framework.data.datasource.remote.rest.RestService
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
  single(named("remote")) { provideRemoteDataSource(get()) }
  single(named("local")) { provideLocalDataSource(androidContext()) }
  single { provideOnlineChecker(androidContext()) }
  single { provideNetworkStateDataSource(get()) }
  single { provideAppRepository(get(named("remote")), get(named("local")), get()) }
  factory { provideGetProductsUseCase(get()) }
  factory { provideProducts() }
}

private fun provideProducts(): ArrayList<ProductEntity> {
  return arrayListOf()
}

private fun provideGetProductsUseCase(appRepository: AppRepository): GetProductsUseCase {
  return GetProductsUseCaseImpl(appRepository)
}

private fun provideRemoteDataSource(restService: RestService): AppDataSource {
  return RemoteDataSourceImpl(restService)
}

private fun provideLocalDataSource(context: Context): AppDataSource {
  return LocalDataSourceImpl(context.assets)
}

private fun provideOnlineChecker(context: Context): OnlineChecker {
  return DefaultOnlineChecker(context)
}

private fun provideNetworkStateDataSource(onlineChecker: OnlineChecker): NetworkStateDataSource {
  return NetworkStateDataSourceImpl(onlineChecker)
}

private fun provideAppRepository(
  remoteDataSource: AppDataSource,
  localDataSource: AppDataSource,
  networkStateDataSource: NetworkStateDataSource
): AppRepository {
  return AppRepositoryImpl(remoteDataSource, localDataSource, networkStateDataSource)
}
