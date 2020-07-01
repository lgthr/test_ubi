package com.example.test_ubi.data.manager.speed

import com.example.test_ubi.data.entity.SpeedEntity
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit

class MockSpeedManagerImpl :
    SpeedManager {

    private var speedState =
        SpeedState.ASCENDING

    private val speedObservable = Observable
        .interval(PERIOD, TimeUnit.MILLISECONDS)
        .map {
            val instance = it + 1 //to begin at 1 instead of 0
            val remainder = instance % 150

            computeSpeedState(remainder)
            val speed = computeSpeed(remainder)
            val timestamp = System.currentTimeMillis()

            SpeedEntity(speed, timestamp)
        }

    override fun observeSpeed(): Observable<SpeedEntity> {
        return speedObservable
    }

    override fun stopObservingSpeed() {
    }

    private fun computeSpeedState(x: Long) {
        speedState = when (x) {
            in 1..50 -> SpeedState.ASCENDING
            in 51..100 -> SpeedState.DESCENDING
            else -> SpeedState.STOPPED
        }
    }

    private fun computeSpeed(x: Long): Float {
        val speed =  when(speedState) {
            SpeedState.ASCENDING -> x
            SpeedState.DESCENDING -> 100 - x
            SpeedState.STOPPED -> 0
        }

        return speed.toFloat()
    }

    private enum class SpeedState {
        ASCENDING,
        DESCENDING,
        STOPPED
    }

    private companion object {
        const val PERIOD = 100L
    }
}