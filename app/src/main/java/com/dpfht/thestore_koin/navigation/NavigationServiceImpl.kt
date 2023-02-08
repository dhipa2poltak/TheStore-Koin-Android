package com.dpfht.thestore_koin.navigation

import android.os.Bundle
import androidx.navigation.NavController
import com.dpfht.thestore_koin.R
import com.dpfht.thestore_koin.navigation.NavigationService

class NavigationServiceImpl(private val navController: NavController): NavigationService {

  override fun navigateFromSplashToList() {
    val navGraph = navController.navInflater.inflate(R.navigation.navgraph)
    navGraph.setStartDestination(R.id.listFragment)

    navController.graph = navGraph
  }

  override fun navigateFromListToDetails(title: String, price: String, desc: String, image: String) {
    val bundle = Bundle()
    bundle.putString("title", title)
    bundle.putString("price", price)
    bundle.putString("desc", desc)
    bundle.putString("image", image)

    navController.navigate(R.id.navigateListToDetails, bundle)
  }

  override fun navigateFromListToError(message: String) {
    val bundle = Bundle()
    bundle.putString("message", message)

    navController.navigate(R.id.navigateListToError, bundle)
  }
}
