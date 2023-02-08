package com.dpfht.thestore_koin.feature_splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dpfht.thestore_koin.feature_splash.databinding.FragmentSplashBinding
import com.dpfht.thestore_koin.navigation.NavigationService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SplashFragment : Fragment(), KoinComponent {

  private val navigationService: NavigationService by inject()

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    val binding = FragmentSplashBinding.inflate(inflater, container, false)

    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val handler = Handler(Looper.getMainLooper())
    handler.postDelayed({
                        navigateToNextScreen()
    }, 3000)
  }

  private fun navigateToNextScreen() {
    navigationService.navigateFromSplashToList()
  }
}
