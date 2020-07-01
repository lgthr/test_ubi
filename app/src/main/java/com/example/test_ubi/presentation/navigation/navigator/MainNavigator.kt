package com.example.test_ubi.presentation.navigation.navigator

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import com.example.test_ubi.presentation.navigation.Navigator
import com.example.test_ubi.presentation.ui.fragment.AverageSpeedFragment
import com.example.test_ubi.presentation.ui.fragment.SpeedFragment

class MainNavigator(
    private val activity: AppCompatActivity,
    @IdRes private val rootContainer: Int
) : Navigator {

    private val fragmentManager = activity.supportFragmentManager

    override fun start() {

    }

    override fun resume() {

    }

    override fun pause() {

    }

    override fun stop() {

    }

    override fun destroy() {

    }

    fun showSpeedView() {
        val fragment = SpeedFragment.newInstance()
        fragmentManager
            .beginTransaction()
            .replace(rootContainer, fragment)
            .commit()
    }

    fun showSpeedAverageFragment() {
        val fragment = AverageSpeedFragment.newInstance()
        fragmentManager
            .beginTransaction()
            .replace(rootContainer, fragment)
            .commit()
    }

    fun onBackPressed() {
        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack()
        } else {
            activity.finish()
        }
    }
}