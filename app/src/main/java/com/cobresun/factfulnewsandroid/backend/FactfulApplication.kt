package com.cobresun.factfulnewsandroid.backend

import android.app.Application
import com.cobresun.factfulnewsandroid.BuildConfig
import timber.log.Timber

class FactfulApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
