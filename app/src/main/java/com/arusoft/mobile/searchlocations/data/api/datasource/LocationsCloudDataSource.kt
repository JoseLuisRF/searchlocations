package com.arusoft.mobile.searchlocations.data.api.datasource

import androidx.lifecycle.LiveData
import com.arusoft.mobile.searchlocations.data.model.SearchLocationRequest
import com.arusoft.mobile.searchlocations.domain.model.VenuesSearchModel

interface LocationsCloudDataSource {

    fun fetchLocationsNearBy(request: SearchLocationRequest): LiveData<VenuesSearchModel>
}