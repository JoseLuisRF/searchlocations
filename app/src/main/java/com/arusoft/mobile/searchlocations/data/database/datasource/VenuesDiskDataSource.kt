package com.arusoft.mobile.searchlocations.data.database.datasource

import com.arusoft.mobile.searchlocations.domain.model.VenueModel

interface VenuesDiskDataSource {

    fun isFavorite(venueId: String) : Boolean

    fun addFavorite(venue: VenueModel) : Long

    fun removeFavorite(venue: VenueModel) : Int
}