package com.example.test_ubi.presentation.di

import androidx.appcompat.app.AppCompatActivity
import com.example.test_ubi.R
import com.example.test_ubi.data.business.AverageSpeedCalculator
import com.example.test_ubi.data.business.AverageSpeedCalculatorImpl
import com.example.test_ubi.data.manager.cache.CacheManager
import com.example.test_ubi.data.manager.cache.CacheManagerImpl
import com.example.test_ubi.data.manager.speed.GPSSpeedManagerImpl
import com.example.test_ubi.data.manager.speed.MockSpeedManagerImpl
import com.example.test_ubi.data.manager.speed.SpeedManager
import com.example.test_ubi.data.repository.SpeedRepository
import com.example.test_ubi.data.repository.SpeedRepositoryImpl
import com.example.test_ubi.presentation.component.SnackbarComponent
import com.example.test_ubi.presentation.component.SnackbarComponentImpl
import com.example.test_ubi.presentation.navigation.navigator.MainNavigator
import com.example.test_ubi.presentation.ui.activity.MainActivity
import com.example.test_ubi.presentation.viewmodel.speed.SpeedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    single<AverageSpeedCalculator> { AverageSpeedCalculatorImpl() }

    single<CacheManager> { CacheManagerImpl() }

    single<SpeedRepository> { SpeedRepositoryImpl(get(), get(), get()) }

    viewModel { SpeedViewModel(get()) }

    scope(named<MainActivity>()) {
        scoped { (activity: AppCompatActivity) -> MainNavigator(activity, R.id.activity_main_root_container) }
    }

    factory { SnackbarComponentImpl(get()) as SnackbarComponent }

}

val defaultSpeedModule = module {
    single<SpeedManager> { GPSSpeedManagerImpl(get()) }
}

val mockSpeedModule = module {
    single<SpeedManager> { MockSpeedManagerImpl() }
}