package com.sample.googlerepositories.utils

import android.app.Application

/**
 * main application class
 */
class Application : Application() {

    override fun onCreate() {
        application = this

        super.onCreate()

    }

    companion object {
        lateinit var application: Application
    }
}