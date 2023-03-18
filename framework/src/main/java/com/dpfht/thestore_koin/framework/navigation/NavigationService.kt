package com.dpfht.thestore_koin.framework.navigation

interface NavigationService {

  fun navigateFromSplashToList()
  fun navigateFromListToDetails(title: String, price: String, desc: String, image: String)
  fun navigateFromListToError(message: String)
}
