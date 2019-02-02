package com.arusoft.mobile.searchlocations.domain.model

data class VenuesSearchModel constructor(val venues: List<VenueModel> = listOf()) : BaseModel()

data class VenueModel constructor(
    val id: String = "",
    val name: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val city: String = "",
    val state: String = "",
    val country: String = "",
    val categoryName: String = "",
    val formattedAddress: List<String> = listOf(),
    val address: String = "",
    val distance: Int = 0,
    val url: String = "",
    val favorite: Boolean = false
) : BaseModel()