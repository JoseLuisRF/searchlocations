package com.arusoft.mobile.searchlocations.data.repository.datasource

import com.arusoft.mobile.searchlocations.data.database.AppDataBase
import com.arusoft.mobile.searchlocations.data.database.datasource.VenuesDiskDataSource
import com.arusoft.mobile.searchlocations.data.repository.mappers.VenuesDataMapper
import com.arusoft.mobile.searchlocations.domain.model.VenueModel
import javax.inject.Inject

class VenuesDiskDataSourceImpl @Inject constructor(
    private val dataBase: AppDataBase,
    private val mapper: VenuesDataMapper
) :
    VenuesDiskDataSource {

    override fun isFavorite(venueId: String): Boolean =
        dataBase.getVenueDao().selectVenueById(venueId) != null


    override fun addFavorite(venue: VenueModel): Long =
        dataBase.getVenueDao().upsert(mapper.convertToEntity(venue))


    override fun removeFavorite(venue: VenueModel): Int =
        dataBase.getVenueDao().delete(mapper.convertToEntity(venue))

}