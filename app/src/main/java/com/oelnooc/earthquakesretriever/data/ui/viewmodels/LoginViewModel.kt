package com.oelnooc.earthquakesretriever.data.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    private val _navigateToEventMap = MutableLiveData<Boolean>()
    val navigateToEventMap: LiveData<Boolean>
        get() = _navigateToEventMap

    private val _navigateToRegistry = MutableLiveData<Boolean>()
    val navigateToRegistry: LiveData<Boolean>
        get() = _navigateToRegistry

    fun onLoginButtonClicked() {
        _navigateToEventMap.value = true
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
}