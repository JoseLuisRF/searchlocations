package com.arusoft.mobile.searchlocations.data.api

import androidx.lifecycle.LiveData
import com.arusoft.mobile.searchlocations.data.model.VenuesSearchResponse
import com.arusoft.mobile.searchlocations.data.network.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationsService {

    @GET("/v2/venues/search")
    fun searchLocationsNearBy(
        @Query("client_id") clientID: String,
        @Query("client_secret") clientSecret: String,
        @Query(value = "ll") ll: String,
        @Query(value = "v") v: String,
        @Query(value = "query") query: String
    ): LiveData<ApiResponse<VenuesSearchResponse, VenuesSearchResponse>>

}