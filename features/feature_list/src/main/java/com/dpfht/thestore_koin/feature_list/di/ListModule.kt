package com.dpfht.thestore_koin.feature_list.di

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.dpfht.thestore_koin.domain.repository.AppRepository
import com.dpfht.thestore_koin.domain.entity.ProductEntity
import com.dpfht.thestore_koin.feature_list.R
import com.dpfht.thestore_koin.feature_list.adapter.ProductListAdapter
import com.dpfht.thestore_koin.feature_list.view.ProductListViewModel
import com.dpfht.thestore_koin.domain.usecase.GetProductsUseCase
import com.dpfht.thestore_koin.domain.usecase.GetProductsUseCaseImpl
import com.dpfht.thestore_koin.framework.util.net.OnlineChecker
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ListModule {

  var needLoaded = true
  val module = module {
    factory { provideProducts() }
    factory { provideGetProductsUseCase(get()) }
    factory { provideProductListAdapter(get()) }
    factory { provideLoadingDialog(it[0]) }
    viewModel {
      provideProductListViewModel(get(), get(), get())
    }
  }

  private fun provideProducts(): ArrayList<ProductEntity> {
    return arrayListOf()
  }

  private fun provideGetProductsUseCase(appRepository: AppRepository): GetProductsUseCase {
    return GetProductsUseCaseImpl(appRepository)
  }

  private fun provideProductListViewModel(
    getProductsUseCase: GetProductsUseCase,
    products: ArrayList<ProductEntity>,
    onlineChecker: OnlineChecker
  ): ProductListViewModel {
    return ProductListViewModel(getProductsUseCase, products, onlineChecker)
  }

  private fun provideProductListAdapter(products: ArrayList<ProductEntity>): ProductListAdapter {
    return ProductListAdapter(products)
  }

  private fun provideLoadingDialog(context: Context): AlertDialog {
    return AlertDialog.Builder(context)
      .setCancelable(false)
      .setView(R.layout.dialog_loading)
      .create()
  }
}
