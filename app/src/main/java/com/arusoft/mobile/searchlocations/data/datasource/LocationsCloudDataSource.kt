package com.arusoft.mobile.searchlocations.data.datasource

import androidx.lifecycle.LiveData
import com.arusoft.mobile.searchlocations.data.model.SearchLocationRequest
import com.arusoft.mobile.searchlocations.domain.model.LocationModel

interface LocationsCloudDataSource {

    fun fetchLocationsNearBy(request: SearchLocationRequest): LiveData<LocationModel>
}