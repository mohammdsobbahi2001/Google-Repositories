package com.sample.googlerepositories.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.viewModels
import com.sample.googlerepositories.R
import com.sample.googlerepositories.core.BaseFragment
import com.sample.googlerepositories.databinding.FragmentSplashBinding
import com.sample.googlerepositories.utils.AnimationUtils
import com.sample.googlerepositories.utils.constants.TimeConstants.SPLASH_TIME_OUT

/**
 * A simple [SplashFragment] that displays a splash screen on app launch.
 * This fragment is displayed for [SPLASH_TIME_OUT] time before navigating to the repositories list screen.
 */
class SplashFragment : BaseFragment<FragmentSplashBinding, SplashVM>(R.layout.fragment_splash) {

    override val viewModel: SplashVM by viewModels()

    override fun onFragmentCreated(savedInstanceState: Bundle?) {

        // fade in splash screen logo
        AnimationUtils.animateFadeIn2000FillAfter(binding.imgLogo)

        // handler for splashing in 3 seconds
        Handler(Looper.getMainLooper()).postDelayed(
            {
                // navigate to repositories list view using Navigation Directions
                navigateTo(SplashFragmentDirections.actionSplashFragmentToRepositoryListFragment())
            },
            SPLASH_TIME_OUT
        )
    }

}