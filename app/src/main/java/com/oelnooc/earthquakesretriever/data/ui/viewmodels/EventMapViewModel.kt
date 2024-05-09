package com.oelnooc.earthquakesretriever.data.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oelnooc.earthquakesretriever.data.api.client.EarthquakeClient
import com.oelnooc.earthquakesretriever.data.models.Feature
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

class EventMapViewModel : ViewModel() {
    private val _earthquakeData = MutableLiveData<List<Feature>>()
    val earthquakeData: LiveData<List<Feature>> get() = _earthquakeData

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

    fun filterEarthquakesByDate(startDate: String, endDate: String) {
        val allEarthquakeData = _earthquakeData.value ?: return

        // Filtrar las características por fecha
        val filteredFeatures = allEarthquakeData.filter { feature ->
            val eventTime = feature.properties.time
            val eventDate = SimpleDateFormat("yyyy-MM-dd").format(Date(eventTime))
            eventDate in startDate..endDate
        }

        _earthquakeData.value = filteredFeatures
    }
}