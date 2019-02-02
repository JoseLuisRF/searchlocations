package com.arusoft.mobile.searchlocations.domain


import androidx.lifecycle.LiveData
import com.arusoft.mobile.searchlocations.data.database.datasource.VenuesDiskDataSource
import com.arusoft.mobile.searchlocations.data.repository.mappers.baseCopy
import com.arusoft.mobile.searchlocations.domain.base.NetworkBoundResource
import com.arusoft.mobile.searchlocations.domain.model.VenuesSearchModel
import com.arusoft.mobile.searchlocations.util.AppExecutors
import com.arusoft.mobile.searchlocations.util.LiveDataBuilder
import javax.inject.Inject

class UseCaseUpdateVenues @Inject constructor(
    appExecutors: AppExecutors,
    private val diskDataSource: VenuesDiskDataSource
) :
    NetworkBoundResource<VenuesSearchModel, UseCaseUpdateVenues.Params>(appExecutors) {

    override fun shouldFetch(data: VenuesSearchModel?): Boolean = false

    override fun loadFromDb(params: UseCaseUpdateVenues.Params): LiveData<VenuesSearchModel> {
        val venues = params.model.venues.map {
            val favorite = diskDataSource.isFavorite(it.id)
            it.copy(favorite = favorite)
                .baseCopy(it)
        }

        return LiveDataBuilder.create(params.model.copy(venues = venues).baseCopy(params.model))
    }

    override fun createCall(params: UseCaseUpdateVenues.Params): LiveData<VenuesSearchModel> =
        LiveDataBuilder.create(VenuesSearchModel())


    override fun getLoadingObject(): VenuesSearchModel = VenuesSearchModel()

    data class Params constructor(val model: VenuesSearchModel)
}