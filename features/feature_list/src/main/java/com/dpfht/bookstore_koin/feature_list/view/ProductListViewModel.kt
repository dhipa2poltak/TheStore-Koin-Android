package com.dpfht.bookstore_koin.feature_list.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dpfht.bookstore_koin.domain.model.DomainProduct
import com.dpfht.bookstore_koin.ext.toRupiahString
import com.dpfht.bookstore_koin.navigation.NavigationService
import com.dpfht.bookstore_koin.usecases.GetProductsUseCase
import com.dpfht.bookstore_koin.usecases.UseCaseResultWrapper.ErrorResult
import com.dpfht.bookstore_koin.usecases.UseCaseResultWrapper.Success
import com.dpfht.bookstore_koin.util.net.OnlineChecker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductListViewModel constructor(
  private val getProdutsUseCase: GetProductsUseCase,
  val products: ArrayList<DomainProduct>,
  private val onlineChecker: OnlineChecker
): ViewModel() {

  private val mIsShowDialogLoading = MutableLiveData<Boolean>()
  val isShowDialogLoading: LiveData<Boolean>
    get() = mIsShowDialogLoading

  private val mErrorMessage = MutableLiveData<String>()
  val errorMessage: LiveData<String>
    get() = mErrorMessage

  private val mToastMessage = MutableLiveData<String>()
  val toastMessage: LiveData<String>
    get() = mToastMessage

  private val _notifyItemInserted = MutableLiveData<Int>()
  val notifyItemInserted: LiveData<Int>
    get() = _notifyItemInserted

  private val _banner = MutableLiveData<String>()
  val banner: LiveData<String>
    get() = _banner

  fun start() {
    if (products.isEmpty()) {
      if (!onlineChecker.isOnline()) {
        mToastMessage.value = "you are in offline mode"
      }

      getProducts()
    }
  }

  private fun getProducts() {
    mIsShowDialogLoading.value = true

    viewModelScope.launch(Dispatchers.Main) {
      when (val result = getProdutsUseCase()) {
        is Success -> {
          onSuccess(result.value.data.banner, result.value.data.products)
        }
        is ErrorResult -> {
          onError(result.message)
        }
      }
    }
  }

  private fun onSuccess(banner: String, products: ArrayList<DomainProduct>) {
    if (banner.isNotEmpty()) {
      _banner.value = banner
    }

    for (product in products) {
      this.products.add(product)
      _notifyItemInserted.value = this.products.size - 1
    }

    mIsShowDialogLoading.value = false
  }

  private fun onError(message: String) {
    mErrorMessage.value = message
    mIsShowDialogLoading.value = false
  }

  fun getProduct(position: Int): DomainProduct {
    return products[position]
  }
}
