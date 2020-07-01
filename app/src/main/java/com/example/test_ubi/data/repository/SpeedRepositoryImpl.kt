package com.example.test_ubi.data.repository

import com.example.test_ubi.data.business.AverageSpeedCalculator
import com.example.test_ubi.data.manager.cache.CacheManager
import com.example.test_ubi.data.manager.speed.SpeedManager
import com.example.test_ubi.data.mapper.speedEntityToModel
import com.example.test_ubi.data.model.SpeedModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class SpeedRepositoryImpl(
    private var speedManager: SpeedManager,
    private val cacheManager: CacheManager,
    private val averageSpeedCalculator: AverageSpeedCalculator
) : SpeedRepository {

    override fun observeSpeed(): Observable<SpeedModel> {
        return speedManager
            .observeSpeed()
            .distinctUntilChanged { t1, t2 -> t1.speed == t2.speed }
            .map { speedEntityToModel(it) }
            .doOnNext { cacheManager.addToCache(it) }
    }

    override fun getAverageSpeed(): Single<Float> {
        val speeds = cacheManager.getSpeeds()
        cacheManager.clearSpeeds()

        return Single.just(averageSpeedCalculator.computeAverageSpeed(speeds))
    }
}