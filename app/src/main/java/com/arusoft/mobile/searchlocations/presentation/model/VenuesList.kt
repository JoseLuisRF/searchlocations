package com.arusoft.mobile.searchlocations.presentation.model

data class VenuesList constructor(val latitude: Double = 0.0, val longitude: Double = 0.0)

data class VenuesUIModel constructor(val venues: List<VenueUIModel>) : BaseViewModel()

data class VenueUIModel constructor(val name: String) : BaseViewModel()