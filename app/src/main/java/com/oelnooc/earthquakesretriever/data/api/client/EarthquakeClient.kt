package com.oelnooc.earthquakesretriever.data.api.client

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EarthquakeClient {

    companion object{
        const val BASE_URL = "https://earthquake.usgs.gov/fdsnws/event/"

        fun getInstance() : EarthquakeService
        {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(EarthquakeService::class.java)
        }
    }
}