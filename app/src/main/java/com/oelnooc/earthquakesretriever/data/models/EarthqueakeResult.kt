package com.oelnooc.earthquakesretriever.data.models

import com.google.gson.annotations.SerializedName

data class EarthqueakeResult(
    @SerializedName("bbox")
    val dataBox: List<Double>,
    val features: List<Feature>,
    val metadata: Metadata,
    val type: String
)