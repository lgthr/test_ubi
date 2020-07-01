package com.example.test_ubi.presentation.component

import android.view.View

interface SnackbarComponent {
    fun displayError(errorMessage: String, view: View?)
    fun displayMessage(message: String, view: View?)
    fun displayMessageWithColor(message: String, color: Int, view: View?)
}