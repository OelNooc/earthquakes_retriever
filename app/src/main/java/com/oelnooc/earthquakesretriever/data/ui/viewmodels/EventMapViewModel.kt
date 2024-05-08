package com.oelnooc.earthquakesretriever.data.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oelnooc.earthquakesretriever.data.api.client.EarthquakeClient
import com.oelnooc.earthquakesretriever.data.models.Feature
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EventMapViewModel : ViewModel() {
    private val _earthquakeData = MutableLiveData<List<Feature>>()
    val earthquakeData get() = _earthquakeData

    fun fetchEarthquakeData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val service = EarthquakeClient.getInstance().getData(
                    format = "geojson",
                    startTime = "2020-01-01",
                    endTime = "2020-01-02"
                )
                val features = service.features
                _earthquakeData.postValue(features)
            } catch (e: Exception) {
                _earthquakeData.postValue(emptyList())
            }
        }
    }
}