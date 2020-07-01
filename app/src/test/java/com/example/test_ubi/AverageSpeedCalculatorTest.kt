package com.example.test_ubi

import com.example.test_ubi.data.business.AverageSpeedCalculatorImpl
import com.example.test_ubi.data.model.SpeedModel
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class AverageSpeedCalculatorTest {

    private val averageSpeedCalculator = AverageSpeedCalculatorImpl()

    @Test
    fun averageSpeedTest() {
        val currentTime = System.currentTimeMillis()

        //30min at 50km/h + 3min at 30km/h + 40min at 70km/h + 5min at 80km
        val speeds = listOf(
            SpeedModel(0F, currentTime),
            SpeedModel(50F, currentTime  + 1), //30min at 50
            SpeedModel(30F, currentTime + 1800001), //+ 3min at 30
            SpeedModel(70F, currentTime + 1980001), //+ 40min
            SpeedModel(80F, currentTime + 4380001), //+ 5min
            SpeedModel(0F, currentTime + 4680001) //stopped
        )

        assertEquals(61.41F, averageSpeedCalculator.computeAverageSpeed(speeds))
    }
}