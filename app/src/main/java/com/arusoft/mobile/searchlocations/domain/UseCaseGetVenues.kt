package com.arusoft.mobile.searchlocations.domain

import androidx.lifecycle.LiveData
import com.arusoft.mobile.searchlocations.data.api.datasource.LocationsCloudDataSource
import com.arusoft.mobile.searchlocations.data.model.SearchLocationRequest
import com.arusoft.mobile.searchlocations.domain.base.NetworkBoundResource
import com.arusoft.mobile.searchlocations.domain.model.VenuesSearchModel
import com.arusoft.mobile.searchlocations.util.AppExecutors
import com.arusoft.mobile.searchlocations.util.LiveDataBuilder
import javax.inject.Inject

class UseCaseGetVenues @Inject constructor(
    appExecutors: AppExecutors,
    private val cloudDataSource: LocationsCloudDataSource
) : NetworkBoundResource<VenuesSearchModel, UseCaseGetVenues.Params>(appExecutors) {

    override fun shouldFetch(data: VenuesSearchModel?): Boolean = true

    override fun loadFromDb(params: Params): LiveData<VenuesSearchModel> =
        LiveDataBuilder.create(VenuesSearchModel())

    override fun createCall(params: Params): LiveData<VenuesSearchModel> =
        cloudDataSource.fetchLocationsNearBy(
            SearchLocationRequest(
                params.latitude,
                params.longitude,
                params.query
            )
        )

    override fun getLoadingObject(): VenuesSearchModel = VenuesSearchModel()


    data class Params constructor(
        val latitude: Double,
        val longitude: Double,
        val query: String
    )
}