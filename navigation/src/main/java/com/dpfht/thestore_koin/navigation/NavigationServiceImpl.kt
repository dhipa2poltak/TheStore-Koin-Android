package com.dpfht.thestore_koin.navigation

import android.os.Bundle
import androidx.navigation.NavController
import com.dpfht.thestore_koin.framework.navigation.NavigationService
import com.dpfht.thestore_koin.framework.R as frameworkR

class NavigationServiceImpl(private val navController: NavController): NavigationService {

  override fun navigateFromSplashToList() {
    val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)
    navGraph.setStartDestination(frameworkR.id.listFragment)

    navController.graph = navGraph
  }

  override fun navigateFromListToDetails(title: String, price: String, desc: String, image: String, navController: NavController?) {
    val bundle = Bundle()
    bundle.putString("title", title)
    bundle.putString("price", price)
    bundle.putString("desc", desc)
    bundle.putString("image", image)

    if (navController != null) {
      navController.navigate(com.dpfht.thestore_koin.feature_details.R.id.productDetailsFragment, bundle)
    } else {
      this.navController.navigate(R.id.navigateListToDetails, bundle)
    }
  }

  override fun navigateFromListToError(message: String) {
    val bundle = Bundle()
    bundle.putString("message", message)

    navController.navigate(R.id.navigateListToError, bundle)
  }
}
