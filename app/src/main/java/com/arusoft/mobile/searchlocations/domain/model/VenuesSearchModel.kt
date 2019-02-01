package com.arusoft.mobile.searchlocations.domain.model

data class VenuesSearchModel constructor(val venues: List<VenueModel> = listOf()) : BaseModel()

data class VenueModel constructor(
    val id: String,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val city: String,
    val state: String,
    val country: String,
    val categoryName: String,
    val formattedAddress: List<String>,
    val address: String,
    val distance: Int,
    val url: String
) : BaseModel()