package com.arusoft.mobile.searchlocations.presentation.model

import com.arusoft.mobile.searchlocations.util.CurrentLocation.CURRENT_LATITUDE
import com.arusoft.mobile.searchlocations.util.CurrentLocation.CURRENT_LONGITUDE

data class VenueSearchInput constructor(
    val latitude: Double = CURRENT_LATITUDE,
    val longitude: Double = CURRENT_LONGITUDE,
    val query: String = ""
)

data class VenuesUIModel constructor(val venues: List<VenueUIModel>) : BaseViewModel()

data class VenueUIModel constructor(
    val id: String,
    val distance: Int,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val city: String,
    val state: String,
    val country: String,
    val categoryName: String,
    val address: String,
    val formattedAddress: List<String>
) : BaseViewModel()