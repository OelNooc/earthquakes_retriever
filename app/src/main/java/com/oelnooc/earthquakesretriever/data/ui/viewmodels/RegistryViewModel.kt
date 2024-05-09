package com.oelnooc.earthquakesretriever.data.ui.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel

class RegistryViewModel(private val sharedPreferences: SharedPreferences) : ViewModel() {

    fun saveUserData(nombre: String, apellido: String, email: String, contraseña: String) {
        val editor = sharedPreferences.edit()
        editor.putString("Nombre", nombre)
        editor.putString("Apellido", apellido)
        editor.putString("Email", email)
        editor.putString("Contraseña", contraseña)
        editor.apply()
    }
}