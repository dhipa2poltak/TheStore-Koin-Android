package com.dpfht.thestore_koin.feature_list.di

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.dpfht.thestore_koin.domain.entity.ProductEntity
import com.dpfht.thestore_koin.domain.usecase.GetProductsUseCase
import com.dpfht.thestore_koin.feature_list.R
import com.dpfht.thestore_koin.feature_list.adapter.ProductListAdapter
import com.dpfht.thestore_koin.feature_list.view.ProductListViewModel
import com.dpfht.thestore_koin.framework.data.datasource.local.onlinechecker.OnlineChecker
import com.dpfht.thestore_koin.framework.navigation.NavigationService
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ListModule {

  var needLoaded = true
  val module = module {
    factory { provideProductListAdapter(get()) }
    factory { provideLoadingDialog(it[0]) }
    viewModel {
      provideProductListViewModel(get(), get(), get(), get(), get())
    }
  }

  private fun provideProductListAdapter(products: ArrayList<ProductEntity>): ProductListAdapter {
    return ProductListAdapter(products)
  }

  private fun provideProductListViewModel(
    getProductsUseCase: GetProductsUseCase,
    products: ArrayList<ProductEntity>,
    adapter: ProductListAdapter,
    onlineChecker: OnlineChecker,
    navigationService: NavigationService
  ): ProductListViewModel {
    return ProductListViewModel(getProductsUseCase, products, adapter, onlineChecker, navigationService)
  }

  private fun provideLoadingDialog(context: Context): AlertDialog {
    return AlertDialog.Builder(context)
      .setCancelable(false)
      .setView(R.layout.dialog_loading)
      .create()
  }
}
