package com.arusoft.mobile.searchlocations.data.datasource

import androidx.lifecycle.LiveData
import com.arusoft.mobile.searchlocations.data.model.SearchLocationResponse
import com.arusoft.mobile.searchlocations.data.network.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationsService {

    @GET("/v2/venues/search")
    fun searchLocationsNearBy(@Query(value = "ll") ll: String): LiveData<ApiResponse<SearchLocationResponse, SearchLocationResponse>>

}