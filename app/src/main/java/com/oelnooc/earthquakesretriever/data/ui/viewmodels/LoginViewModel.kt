package com.oelnooc.earthquakesretriever.data.ui.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel(private val sharedPreferences: SharedPreferences) : ViewModel() {

    private val _navigateToEventMap = MutableLiveData<Boolean>()
    val navigateToEventMap: LiveData<Boolean>
        get() = _navigateToEventMap

    private val _navigateToRegistry = MutableLiveData<Boolean>()
    val navigateToRegistry: LiveData<Boolean>
        get() = _navigateToRegistry

    private val _showInvalidCredentialsSnackbar = MutableLiveData<Boolean>()
    val showInvalidCredentialsSnackbar: LiveData<Boolean>
        get() = _showInvalidCredentialsSnackbar

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    fun onLoginButtonClicked(username: String, password: String) {
        val storedUsername = sharedPreferences.getString("Nombre", "")
        val storedPassword = sharedPreferences.getString("Contraseña", "")

        if (username == storedUsername && password == storedPassword) {
            _navigateToEventMap.value = true
        } else {
            _showInvalidCredentialsSnackbar.value = true
            _errorMessage.value = "Credenciales inválidas, intenta nuevamente"
        }
    }

    fun onRegisterTextClicked() {
        _navigateToRegistry.value = true
    }

    fun doneNavigatingToEventMap() {
        _navigateToEventMap.value = false
    }

    fun doneNavigatingToRegistry() {
        _navigateToRegistry.value = false
    }

    fun doneShowingInvalidCredentialsSnackbar() {
        _showInvalidCredentialsSnackbar.value = false
    }
}