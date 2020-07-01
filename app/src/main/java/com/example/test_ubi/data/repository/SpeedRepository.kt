package com.example.test_ubi.data.repository

import com.example.test_ubi.data.model.SpeedModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface SpeedRepository {
    fun observeSpeed(): Observable<SpeedModel>
    fun getAverageSpeed(): Single<Float>
}