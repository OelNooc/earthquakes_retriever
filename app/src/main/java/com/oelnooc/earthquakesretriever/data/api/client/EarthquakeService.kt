package com.oelnooc.earthquakesretriever.data.api.client

import com.oelnooc.earthquakesretriever.data.models.EarthqueakeResult
import retrofit2.http.GET
import retrofit2.http.Query

interface EarthquakeService {

    @GET("1/query")
    suspend fun getData(
        @Query("format") format: String,
        @Query("starttime") startTime: String,
        @Query("endtime") endTime: String
    ): EarthqueakeResult
}