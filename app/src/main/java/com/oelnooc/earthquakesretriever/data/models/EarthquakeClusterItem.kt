package com.oelnooc.earthquakesretriever.data.models

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

data class EarthquakeClusterItem(private val position: LatLng,
                                 private val magnitude: Double,
                                 private val title: String,
                                 private val depth: Double,
                                 private val place: String
) : ClusterItem {
    override fun getPosition(): LatLng {
        return position
    }

    override fun getTitle(): String {
        return "Title: $title \n Magnitude: $magnitude \n Depth: $depth \n Place: $place"
    }

    override fun getSnippet(): String? {
        return null
    }
}
