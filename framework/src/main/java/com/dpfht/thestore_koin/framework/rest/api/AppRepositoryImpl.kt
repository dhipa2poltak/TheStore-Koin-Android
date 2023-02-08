package com.dpfht.thestore_koin.framework.rest.api

import com.dpfht.thestore_koin.data.repository.AppDataSource
import com.dpfht.thestore_koin.data.repository.AppRepository
import com.dpfht.thestore_koin.domain.model.DomainDataResponse
import com.dpfht.thestore_koin.usecases.UseCaseResultWrapper
import com.dpfht.thestore_koin.util.net.OnlineChecker

class AppRepositoryImpl(
  private val remoteDataSource: AppDataSource,
  private val localDataSource: AppDataSource,
  private val onlineChecker: OnlineChecker
): AppRepository {

  override suspend fun getProducts(): UseCaseResultWrapper<DomainDataResponse> {
    return if (onlineChecker.isOnline())
      remoteDataSource.getProducts()
    else
      localDataSource.getProducts()
  }
}
