package com.arusoft.mobile.searchlocations.domain

import androidx.lifecycle.LiveData
import com.arusoft.mobile.searchlocations.data.database.datasource.VenuesDiskDataSource
import com.arusoft.mobile.searchlocations.data.repository.mappers.baseCopy
import com.arusoft.mobile.searchlocations.domain.base.NetworkBoundResource
import com.arusoft.mobile.searchlocations.domain.model.VenueModel
import com.arusoft.mobile.searchlocations.util.AppExecutors
import com.arusoft.mobile.searchlocations.util.LiveDataBuilder
import javax.inject.Inject

class UseCaseMarkFavorite @Inject constructor(
    appExecutors: AppExecutors,
    private val diskDataSource: VenuesDiskDataSource
) : NetworkBoundResource<VenueModel, UseCaseMarkFavorite.Params>(appExecutors) {

    override fun shouldFetch(data: VenueModel?): Boolean = false

    override fun loadFromDb(params: Params): LiveData<VenueModel> {
        val favorite = if (params.isFavorite) {
            diskDataSource.addFavorite(params.venueModel) != 0L
        } else {
            diskDataSource.removeFavorite(params.venueModel) == 0
        }

        val responseModel = params.venueModel.copy(favorite = favorite).baseCopy(params.venueModel)
        return LiveDataBuilder.create(responseModel)
    }

    override fun createCall(params: Params): LiveData<VenueModel> =
        LiveDataBuilder.create(VenueModel())

    override fun getLoadingObject(): VenueModel = VenueModel()

    data class Params constructor(val venueModel: VenueModel, val isFavorite: Boolean)
}