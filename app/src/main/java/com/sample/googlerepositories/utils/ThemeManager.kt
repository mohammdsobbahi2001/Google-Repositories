package com.sample.googlerepositories.utils

import android.content.res.Configuration
import com.sample.googlerepositories.utils.enums.Themes

/**
 * object used to manage app theme
 */
object ThemeManager {

    /**
     *  gets system theme and return it as [Themes] enum
     *
     *  @return system theme as [Themes]
     *
     */
    fun getSystemTheme(): Themes {
        return when (Application.application.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {
                Themes.Light
            }
            Configuration.UI_MODE_NIGHT_YES -> {
                Themes.Dark
            }
            else -> {
                Themes.Light
            }
        }
    }
}