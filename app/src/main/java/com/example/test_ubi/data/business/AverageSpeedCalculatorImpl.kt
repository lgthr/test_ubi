package com.example.test_ubi.data.business

import com.example.test_ubi.data.model.SpeedModel
import java.math.BigDecimal
import java.math.RoundingMode

class AverageSpeedCalculatorImpl : AverageSpeedCalculator {

    override fun computeAverageSpeed(speeds: List<SpeedModel>): Float {
        val sortedSpeeds = sortByTimestamp(speeds)
        val speedsAndTimes = getSpeedsAndTimesPair(sortedSpeeds)

        val totalTime = getTotalTime(speedsAndTimes)
        val totalDistance = getTotalDistance(speedsAndTimes)

        return if (totalTime != 0L) (totalDistance / totalTime).round(2) else 0F
    }

    private fun sortByTimestamp(speeds: List<SpeedModel>): List<SpeedModel> {
        return speeds.sortedBy { it.timestamp }
    }

    private fun getSpeedsAndTimesPair(speeds: List<SpeedModel>): List<Pair<SpeedModel, Long>> {
        val speedsAndTimes = mutableListOf<Pair<SpeedModel, Long>>()

        speeds.forEachIndexed { index, speed ->
            val nextSpeed = speeds.getOrNull(index + 1)
            val amountOfTime = if (nextSpeed != null) {
                nextSpeed.timestamp - speed.timestamp
            } else {
                0
            }

            speedsAndTimes.add(Pair(speed, amountOfTime))
        }

        return speedsAndTimes
    }

    private fun getTotalTime(speedsAndTimes: List<Pair<SpeedModel, Long>>): Long {
        return speedsAndTimes.map { it.second }.sum()
    }

    private fun getTotalDistance(speedsAndTimes: List<Pair<SpeedModel, Long>>): Float {
        return speedsAndTimes.map { it.first.speed * it.second }.sum()
    }

    private fun Float.round(decimals: Int): Float {
        return  BigDecimal(toDouble()).setScale(decimals, RoundingMode.HALF_EVEN).toFloat()
    }
}