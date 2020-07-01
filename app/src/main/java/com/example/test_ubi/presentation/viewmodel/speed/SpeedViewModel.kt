package com.example.test_ubi.presentation.viewmodel.speed

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.test_ubi.data.model.SpeedModel
import com.example.test_ubi.data.repository.SpeedRepository
import com.example.test_ubi.presentation.viewmodel.SingleLiveEvent
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class SpeedViewModel(
    private val speedRepository: SpeedRepository
) : ViewModel() {

    val speedLiveData = MutableLiveData<SpeedModel>()
    val averageSpeedLiveData = MutableLiveData<Float>()
    val errorLiveData = SingleLiveEvent<Throwable>()

    private var speedDisposable: Disposable? = null

    fun observeSpeed() {
        if (speedDisposable != null && speedDisposable?.isDisposed != true) {
            return
        }

        speedDisposable = speedRepository
            .observeSpeed()
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onError = {
                    errorLiveData.postValue(it)
                },
                onNext = {
                    speedLiveData.postValue(it)
                }
            )
    }

    fun getAverageSpeed() {
        speedRepository
            .getAverageSpeed()
            .subscribeOn(Schedulers.computation())
            .subscribeBy(
                onError = {
                    errorLiveData.postValue(it)
                },
                onSuccess = {
                    averageSpeedLiveData.postValue(it)
                }
            )
    }
}