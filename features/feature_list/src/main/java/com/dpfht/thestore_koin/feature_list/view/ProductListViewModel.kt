package com.dpfht.thestore_koin.feature_list.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.dpfht.thestore_koin.domain.entity.ProductEntity
import com.dpfht.thestore_koin.domain.entity.Result.Error
import com.dpfht.thestore_koin.domain.entity.Result.Success
import com.dpfht.thestore_koin.domain.usecase.GetProductsUseCase
import com.dpfht.thestore_koin.feature_list.adapter.ProductListAdapter
import com.dpfht.thestore_koin.framework.data.datasource.local.onlinechecker.OnlineChecker
import com.dpfht.thestore_koin.framework.ext.toRupiahString
import com.dpfht.thestore_koin.framework.navigation.NavigationService
import kotlinx.coroutines.launch

class ProductListViewModel constructor(
  private val getProdutsUseCase: GetProductsUseCase,
  private val products: ArrayList<ProductEntity>,
  val adapter: ProductListAdapter,
  private val onlineChecker: OnlineChecker,
  private val navigationService: NavigationService
): ViewModel() {

  private val mIsShowDialogLoading = MutableLiveData<Boolean>()
  val isShowDialogLoading: LiveData<Boolean> = mIsShowDialogLoading

  private val mToastMessage = MutableLiveData<String>()
  val toastMessage: LiveData<String> = mToastMessage

  private val _banner = MutableLiveData<String>()
  val banner: LiveData<String> = _banner

  init {
    adapter.products = products
  }

  fun start() {
    if (products.isEmpty()) {
      mToastMessage.postValue(
        if (!onlineChecker.isOnline()) {
          "you are in offline mode"
        } else {
          ""
        }
      )

      getProducts()
    }
  }

  private fun getProducts() {
    mIsShowDialogLoading.postValue(true)

    viewModelScope.launch {
      when (val result = getProdutsUseCase()) {
        is Success -> {
          onSuccess(result.value.data.banner, result.value.data.products)
        }
        is Error -> {
          onError(result.message)
        }
      }
    }
  }

  private fun onSuccess(banner: String, products: List<ProductEntity>) {
    if (banner.isNotEmpty()) {
      _banner.postValue(banner)
    }

    for (product in products) {
      this@ProductListViewModel.products.add(product)
      adapter.notifyItemInserted(this@ProductListViewModel.products.size - 1)
    }

    mIsShowDialogLoading.postValue(false)
  }

  private fun onError(message: String) {
    mIsShowDialogLoading.postValue(false)
    navigationService.navigateFromListToError(message)
  }

  fun refresh() {
    products.clear()
    adapter.notifyDataSetChanged()
    start()
  }

  fun navigateFromListToDetails(position: Int, navController: NavController?) {
    val product = products[position]

    navigationService.navigateFromListToDetails(
      product.productName,
      "${product.price.toRupiahString()} / pcs",
      product.description,
      product.images?.large ?: "",
      navController
    )
  }
}
