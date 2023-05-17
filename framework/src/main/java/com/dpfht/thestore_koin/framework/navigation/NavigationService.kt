package com.dpfht.thestore_koin.framework.navigation

import androidx.navigation.NavController

interface NavigationService {

  fun navigateFromSplashToList()
  fun navigateFromListToDetails(title: String, price: String, desc: String, image: String, navController: NavController?)
  fun navigateFromListToError(message: String)
}
