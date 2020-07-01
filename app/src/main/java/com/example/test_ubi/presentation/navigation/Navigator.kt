package com.example.test_ubi.presentation.navigation

interface Navigator {
    fun start()
    fun resume()
    fun pause()
    fun stop()
    fun destroy()
}