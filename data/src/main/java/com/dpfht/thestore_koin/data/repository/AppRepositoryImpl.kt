package com.dpfht.thestore_koin.data.repository

import com.dpfht.thestore_koin.data.datasource.AppDataSource
import com.dpfht.thestore_koin.data.datasource.NetworkStateDataSource
import com.dpfht.thestore_koin.domain.entity.AppException
import com.dpfht.thestore_koin.domain.repository.AppRepository
import com.dpfht.thestore_koin.domain.entity.DataDomain

class AppRepositoryImpl(
  private val remoteDataSource: AppDataSource,
  private val localDataSource: AppDataSource,
  private val onlineChecker: NetworkStateDataSource
): AppRepository {

  override suspend fun getProducts(): DataDomain {
    return if (onlineChecker.isOnline())
      try {
        remoteDataSource.getProducts()
      } catch (e: AppException) {
        localDataSource.getProducts()
      }
    else
      localDataSource.getProducts()
  }
}
