package com.oelnooc.earthquakesretriever.data.models

data class Feature(
    val geometry: Geometry,
    val id: String,
    val properties: Properties,
    val type: String
)