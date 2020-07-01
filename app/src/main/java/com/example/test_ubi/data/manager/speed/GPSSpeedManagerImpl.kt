package com.example.test_ubi.data.manager.speed

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.example.test_ubi.data.LocationNotEnabledException
import com.example.test_ubi.data.NoLocationPermissionException
import com.example.test_ubi.data.entity.SpeedEntity
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

class GPSSpeedManagerImpl(private val context: Context) : SpeedManager, LocationListener {

    private val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    private var speedSubject: PublishSubject<SpeedEntity> = PublishSubject.create()

    override fun observeSpeed(): Observable<SpeedEntity> {
        if (speedSubject.hasComplete() || speedSubject.hasThrowable()) {
            speedSubject = PublishSubject.create()
        }

        requestLocationUpdates()

        return speedSubject
    }

    override fun stopObservingSpeed() {
        speedSubject.onComplete()
    }

    override fun onLocationChanged(location: Location?) {
        location?.speed?.let {
            speedSubject.onNext(SpeedEntity(it * MS_TO_KMH_COEFFICIENT, System.currentTimeMillis()))
        }
    }

    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
        //
    }

    override fun onProviderEnabled(p0: String?) {
        //
    }

    override fun onProviderDisabled(p0: String?) {
        //
    }

    private fun requestLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
            speedSubject.onError(NoLocationPermissionException())
        } else {
            checkLocationEnabled()
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0F, this)
        }
    }

    private fun checkLocationEnabled() {
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            speedSubject.onError(LocationNotEnabledException())
        }
    }

    private companion object {
        const val MS_TO_KMH_COEFFICIENT = 3.6F
    }
}