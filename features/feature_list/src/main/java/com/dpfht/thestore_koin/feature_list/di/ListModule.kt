package com.dpfht.thestore_koin.feature_list.di

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.dpfht.thestore_koin.data.repository.AppRepository
import com.dpfht.thestore_koin.domain.model.DomainProduct
import com.dpfht.thestore_koin.feature_list.R
import com.dpfht.thestore_koin.feature_list.adapter.ProductListAdapter
import com.dpfht.thestore_koin.feature_list.view.ProductListViewModel
import com.dpfht.thestore_koin.navigation.NavigationService
import com.dpfht.thestore_koin.usecases.GetProductsUseCase
import com.dpfht.thestore_koin.usecases.GetProductsUseCaseImpl
import com.dpfht.thestore_koin.util.net.OnlineChecker
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

  fun provideProducts(): ArrayList<DomainProduct> {
    return arrayListOf()
  }

  fun provideGetProductsUseCase(appRepository: AppRepository): GetProductsUseCase {
    return GetProductsUseCaseImpl(appRepository)
  }

  fun provideProductListViewModel(
    getProductsUseCase: GetProductsUseCase,
    products: ArrayList<DomainProduct>,
    onlineChecker: OnlineChecker
  ): ProductListViewModel {
    return ProductListViewModel(getProductsUseCase, products, onlineChecker)
  }

  fun provideProductListAdapter(products: ArrayList<DomainProduct>): ProductListAdapter {
    return ProductListAdapter(products)
  }

  fun provideLoadingDialog(context: Context): AlertDialog {
    return AlertDialog.Builder(context)
      .setCancelable(false)
      .setView(R.layout.dialog_loading)
      .create()
  }
}
