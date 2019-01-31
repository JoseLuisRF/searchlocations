package com.arusoft.mobile.searchlocations.data.repository.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.arusoft.mobile.searchlocations.data.datasource.LocationsCloudDataSource
import com.arusoft.mobile.searchlocations.data.datasource.LocationsService
import com.arusoft.mobile.searchlocations.data.model.SearchLocationRequest
import com.arusoft.mobile.searchlocations.data.model.SearchLocationResponse
import com.arusoft.mobile.searchlocations.data.network.ApiResponse
import com.arusoft.mobile.searchlocations.data.repository.mappers.LocationDataMapper
import com.arusoft.mobile.searchlocations.domain.model.LocationModel
import com.arusoft.mobile.searchlocations.util.LiveDataBuilder
import javax.inject.Inject

class LocationsCloudDataSourceImpl @Inject constructor(
    val service: LocationsService,
    val dataMapper: LocationDataMapper
) :
    LocationsCloudDataSource {

    override fun fetchLocationsNearBy(request: SearchLocationRequest): LiveData<LocationModel> {
        return Transformations.switchMap(service.searchLocationsNearBy(request.latlang)) { apiResponse ->

            if (apiResponse != null && apiResponse.isSuccessful && apiResponse.body != null) {
                LiveDataBuilder.create(dataMapper.convert(apiResponse.body!!))

            } else if (apiResponse != null && apiResponse.isSuccessful.not()) {
                LiveDataBuilder.create(dataMapper.convert(apiResponse.body!!))
            } else {
                LiveDataBuilder.create(dataMapper.convert(apiResponse.body!!))
            }
        }
    }
}