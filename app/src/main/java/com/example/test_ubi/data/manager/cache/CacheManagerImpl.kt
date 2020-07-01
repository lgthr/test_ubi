package com.example.test_ubi.data.manager.cache

import com.example.test_ubi.data.model.SpeedModel

class CacheManagerImpl : CacheManager {

    private val speeds = mutableListOf<SpeedModel>()

    override fun addToCache(speedModel: SpeedModel) {
        speeds.add(speedModel)
    }

    override fun getSpeeds(): List<SpeedModel> {
        return speeds.toList()
    }

    override fun clearSpeeds() {
        speeds.clear()
    }
}