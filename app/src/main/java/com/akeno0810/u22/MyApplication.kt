package com.akeno0810.u22

import android.app.Application

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppState.onCreateApplication(applicationContext)
    }
}