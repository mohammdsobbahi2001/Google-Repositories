package com.sample.googlerepositories.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.WindowInsetsControllerCompat
import com.sample.googlerepositories.R
import com.sample.googlerepositories.core.BaseActivity
import com.sample.googlerepositories.databinding.ActivityMainBinding
import com.sample.googlerepositories.utils.ThemeManager
import com.sample.googlerepositories.utils.enums.Themes

/**
 * The main activity for the application that displays a list of google repositories.
 */
class MainActivity : BaseActivity<ActivityMainBinding, MainVM>(R.layout.activity_main) {

    override val viewModel: MainVM by viewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?, extras: Bundle?) {

        // handle status bar color based on theme
        WindowInsetsControllerCompat(
            window, binding.root
        ).isAppearanceLightStatusBars = ThemeManager.getSystemTheme() == Themes.Light


    }

}