package com.example.test_ubi.presentation.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.test_ubi.R
import com.example.test_ubi.presentation.navigation.listener.MainNavigatorListener
import com.example.test_ubi.presentation.navigation.navigator.MainNavigator
import org.koin.androidx.scope.lifecycleScope
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity(), MainNavigatorListener {

    private val navigator: MainNavigator by lifecycleScope.inject { parametersOf(this) }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showSpeedView()
    }

    override fun showSpeedView() {
        navigator.showSpeedView()
    }

    override fun showSpeedAverageView() {
        navigator.showSpeedAverageFragment()
    }

    override fun onBackPressed() {
        navigator.onBackPressed()
    }
}