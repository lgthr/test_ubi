package com.example.test_ubi.presentation.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.test_ubi.R
import com.example.test_ubi.data.LocationNotEnabledException
import com.example.test_ubi.presentation.component.SnackbarComponent
import com.example.test_ubi.presentation.misc.observeSafe
import com.example.test_ubi.presentation.navigation.listener.MainNavigatorListener
import com.example.test_ubi.presentation.viewmodel.speed.SpeedViewModel
import kotlinx.android.synthetic.main.fragment_speed.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions


@RuntimePermissions
class SpeedFragment : Fragment() {

    private val speedViewModel: SpeedViewModel by sharedViewModel()
    private val snackbarComponent: SnackbarComponent by inject()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_speed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLiveDataObservers()
        observeSpeedWithPermissionCheck()

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    @NeedsPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION)
    fun observeSpeed() {
        speedViewModel.observeSpeed()
    }

    private fun setupLiveDataObservers() {
        speedViewModel.speedLiveData.observeSafe(this) { speed ->
            fragment_speed_text.text = getString(R.string.km_h_int, speed.speed.toInt())
            if (speed.speed == 0F) {
                (activity as? MainNavigatorListener)?.showSpeedAverageView()
            }
        }

        speedViewModel.errorLiveData.observeSafe(this) { error ->
            when (error) {
                is LocationNotEnabledException -> enableLocation()
                else -> error?. message?.let { snackbarComponent.displayError(it, view) }
            }
        }
    }

    private fun enableLocation() {
        val enableLocationIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivityForResult(enableLocationIntent, ENABLE_LOCATION_RESULT_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ENABLE_LOCATION_RESULT_CODE) {
            speedViewModel.observeSpeed()
        }
    }

    companion object {
        private const val ENABLE_LOCATION_RESULT_CODE = 999

        @JvmStatic
        fun newInstance() =
            SpeedFragment().apply {
                arguments = Bundle().apply {
                    //add arguments here
                }
            }
    }
}