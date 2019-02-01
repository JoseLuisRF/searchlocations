package com.arusoft.mobile.searchlocations.data.repository.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.arusoft.mobile.searchlocations.data.datasource.LocationsCloudDataSource
import com.arusoft.mobile.searchlocations.data.datasource.LocationsService
import com.arusoft.mobile.searchlocations.data.model.SearchLocationRequest
import com.arusoft.mobile.searchlocations.data.repository.mappers.LocationDataMapper
import com.arusoft.mobile.searchlocations.domain.model.VenuesSearchModel
import com.arusoft.mobile.searchlocations.util.ErrorConstants.TECHNICAL_DIFFICULTIES_EXCEPTION
import com.arusoft.mobile.searchlocations.util.LiveDataBuilder
import com.arusoft.mobile.searchlocations.util.NetworkUtil
import javax.inject.Inject

class LocationsCloudDataSourceImpl @Inject constructor(
    val service: LocationsService,
    val dataMapper: LocationDataMapper,
    val networkUtil: NetworkUtil
) : LocationsCloudDataSource {

    override fun fetchLocationsNearBy(request: SearchLocationRequest): LiveData<VenuesSearchModel> {
        return if (networkUtil.isConnected()) {
            Transformations.switchMap(
                service.searchLocationsNearBy(
                    ll = request.latlang,
                    clientSecret = "W43ISJ5UYLHDOT4RGYTOTDNF0W2GK5DYXBUA2EGTGZ4T4CAQ",
                    clientID = "S5CDTSDOSWUNVQJ1VDBQLECPQ2RPNANOM5KIJT2HD5VUZUTD",
                    v = "20190131",
                    query = request.query
                )
            ) { apiResponse ->
                if (apiResponse != null && apiResponse.isSuccessful && apiResponse.body != null) {
                    LiveDataBuilder.create(dataMapper.convert(apiResponse.body))

                } else if (apiResponse != null && apiResponse.isSuccessful.not() && apiResponse.errorBody != null) {
                    LiveDataBuilder.create(dataMapper.convert(apiResponse.errorBody!!))
                } else {
                    LiveDataBuilder.create(
                        dataMapper.createDomainModel(
                            TECHNICAL_DIFFICULTIES_EXCEPTION,
                            VenuesSearchModel::class.java
                        )
                    )
                }
            }
        } else {
            LiveDataBuilder.create(
                dataMapper.createDomainModel(
                    TECHNICAL_DIFFICULTIES_EXCEPTION,
                    VenuesSearchModel::class.java
                )
            )
        }

    }
}