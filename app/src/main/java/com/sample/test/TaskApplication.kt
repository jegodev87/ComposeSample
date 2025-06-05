package com.sample.test

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class TaskApplication : Application() {
    override fun onCreate() {
        super.onCreate()

    }


}