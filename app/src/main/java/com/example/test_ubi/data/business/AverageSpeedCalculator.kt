package com.example.test_ubi.data.business

import com.example.test_ubi.data.model.SpeedModel

interface AverageSpeedCalculator {

    fun computeAverageSpeed(speeds: List<SpeedModel>): Float
}