package com.example.test_ubi.data.manager.speed

import com.example.test_ubi.data.entity.SpeedEntity
import io.reactivex.rxjava3.core.Observable


interface SpeedManager {

    fun observeSpeed(): Observable<SpeedEntity>
    fun stopObservingSpeed()
}