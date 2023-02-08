package com.dpfht.thestore_koin

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.dpfht.thestore_koin.R.id
import com.dpfht.thestore_koin.databinding.ActivityMainBinding
import com.dpfht.thestore_koin.navigation.NavigationService
import com.dpfht.thestore_koin.navigation.NavigationServiceImpl
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding
  private lateinit var navController: NavController

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setToolbar()
    setContentView(binding.root)

    val navHostFragment =
      supportFragmentManager.findFragmentById(id.my_nav_host_fragment) as NavHostFragment
    navController = navHostFragment.navController
    NavigationUI.setupActionBarWithNavController(this, navController)

    loadKoinModules(module {
      single<NavigationService> { NavigationServiceImpl(navController) }
    })
  }

  override fun onSupportNavigateUp(): Boolean {
    return navController.navigateUp() || super.onSupportNavigateUp()
  }

  private fun setToolbar() {
    setSupportActionBar(binding.toolbar)

    when (baseContext.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
      Configuration.UI_MODE_NIGHT_YES -> {
        binding.toolbar.setTitleTextColor(0xFFFFFFFF.toInt())
      }
      Configuration.UI_MODE_NIGHT_NO -> {
        binding.toolbar.setTitleTextColor(0xFFFF0000.toInt())
      }
      Configuration.UI_MODE_NIGHT_UNDEFINED -> {
        binding.toolbar.setTitleTextColor(0xFFFFFFFF.toInt())
      }
    }
  }
}
