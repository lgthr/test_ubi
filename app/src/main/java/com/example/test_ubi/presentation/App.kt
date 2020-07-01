package com.example.test_ubi.presentation

import android.app.Application
import com.example.test_ubi.BuildConfig.DATA_SOURCE
import com.example.test_ubi.presentation.di.appModule
import com.example.test_ubi.presentation.di.defaultSpeedModule
import com.example.test_ubi.presentation.di.mockSpeedModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

const val DEFAULT_DATA_SOURCE = 0
const val MOCK_DATA_SOURCE = 1

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Use Koin Android Logger
            androidLogger()
            // declare Android context
            androidContext(this@App)
            // declare modules to use

            val modules = when (DATA_SOURCE) {
                MOCK_DATA_SOURCE ->  listOf(appModule, mockSpeedModule)
                else ->  listOf(appModule, defaultSpeedModule)
            }

            modules(modules)
        }
    }
}