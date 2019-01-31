package com.arusoft.mobile.searchlocations.data.repository.mappers

import com.arusoft.mobile.searchlocations.data.model.SearchLocationResponse
import com.arusoft.mobile.searchlocations.domain.model.LocationModel
import javax.inject.Inject

class LocationDataMapper @Inject constructor() {

    fun convert(response: SearchLocationResponse): LocationModel {
        val model = LocationModel()
        return model
    }

}