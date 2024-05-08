package com.oelnooc.earthquakesretriever

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.oelnooc.earthquakesretriever.data.api.client.EarthquakeClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val service = EarthquakeClient.getInstance()

        lifecycleScope.launch{
            val registros = service.getData("geojson", "2020-01-01", "2020-01-02")
            Log.d("registro", registros.toString())
            println(registros)
        }
    }
}