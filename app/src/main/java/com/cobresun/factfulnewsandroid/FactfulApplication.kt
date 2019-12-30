package com.cobresun.factfulnewsandroid

import android.app.Application
import timber.log.Timber

class FactfulApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
