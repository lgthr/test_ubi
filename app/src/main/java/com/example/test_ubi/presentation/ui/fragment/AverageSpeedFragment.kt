package com.example.test_ubi.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.test_ubi.R
import com.example.test_ubi.data.LocationNotEnabledException
import com.example.test_ubi.data.NoLocationPermissionException
import com.example.test_ubi.presentation.component.SnackbarComponent
import com.example.test_ubi.presentation.misc.observeSafe
import com.example.test_ubi.presentation.navigation.listener.MainNavigatorListener
import com.example.test_ubi.presentation.viewmodel.speed.SpeedViewModel
import kotlinx.android.synthetic.main.fragment_average_speed.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AverageSpeedFragment : Fragment() {

    private val speedViewModel: SpeedViewModel by sharedViewModel()
    private val snackbarComponent: SnackbarComponent by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_average_speed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLiveDataObservers()
        speedViewModel.getAverageSpeed()
    }

    private fun setupLiveDataObservers() {
        speedViewModel.speedLiveData.observeSafe(this) { speed ->
            if (speed.speed != 0F) {
                (activity as? MainNavigatorListener)?.showSpeedView()
            }
        }

        speedViewModel.averageSpeedLiveData.observeSafe(this) { averageSpeed ->
            fragment_average_speed_text.text = getString(R.string.km_h_float, averageSpeed)
        }

        speedViewModel.errorLiveData.observeSafe(this) { error ->
            val message = when (error) {
                is NoLocationPermissionException -> getString(R.string.no_location_permission)
                is LocationNotEnabledException -> getString(R.string.location_not_enabled)
                else -> error?.message
            }

            message?.let { snackbarComponent.displayError(it, view) }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            AverageSpeedFragment().apply {
                arguments = Bundle().apply {
                    //add arguments here
                }
            }
    }
}