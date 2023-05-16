package com.dpfht.thestore_koin.feature_list.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dpfht.thestore_koin.domain.entity.ProductEntity
import com.dpfht.thestore_koin.domain.usecase.GetProductsUseCase
import com.dpfht.thestore_koin.domain.entity.Result.ErrorResult
import com.dpfht.thestore_koin.domain.entity.Result.Success
import com.dpfht.thestore_koin.framework.util.net.OnlineChecker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductListViewModel constructor(
  private val getProdutsUseCase: GetProductsUseCase,
  val products: ArrayList<ProductEntity>,
  private val onlineChecker: OnlineChecker
): ViewModel() {

  private val mIsShowDialogLoading = MutableLiveData<Boolean>()
  val isShowDialogLoading: LiveData<Boolean> = mIsShowDialogLoading

  private val mErrorMessage = MutableLiveData<String>()
  val errorMessage: LiveData<String> = mErrorMessage

  private val mToastMessage = MutableLiveData<String>()
  val toastMessage: LiveData<String> = mToastMessage

  private val _notifyItemInserted = MutableLiveData<Int>()
  val notifyItemInserted: LiveData<Int> = _notifyItemInserted

  private val _banner = MutableLiveData<String>()
  val banner: LiveData<String> = _banner

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

  private fun onSuccess(banner: String, products: ArrayList<ProductEntity>) {
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

  fun getProduct(position: Int): ProductEntity {
    return products[position]
  }

  fun refresh() {
    products.clear()
    start()
  }
}
