package com.jerlendds.moblab

import android.app.Application
import timber.log.Timber

class MobLabApplication : Application() {
    lateinit var appContainer: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        DebugTools.init(this)
        appContainer = AppContainer(this)
    }
}
