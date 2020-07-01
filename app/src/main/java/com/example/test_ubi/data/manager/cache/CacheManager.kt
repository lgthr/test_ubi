package com.example.test_ubi.data.manager.cache

import com.example.test_ubi.data.model.SpeedModel

interface CacheManager {
    fun addToCache(speedModel: SpeedModel)
    fun getSpeeds(): List<SpeedModel>
    fun clearSpeeds()
}