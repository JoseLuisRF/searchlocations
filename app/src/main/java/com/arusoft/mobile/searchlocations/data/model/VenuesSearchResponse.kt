package com.arusoft.mobile.searchlocations.data.model

data class VenuesSearchResponse constructor(val response: ResponseDTO)

data class ResponseDTO constructor(val venues: List<VenueDTO>)

data class VenueDTO constructor(
    val id: String,
    val name: String,
    val location: VenueLocationDTO,
    val categories: List<VenueCategoryDTO>
)

data class VenueLocationDTO constructor(
    val address: String,
    val crossStreet: String,
    val lat: Double,
    val lng: Double,
    val distance: Int,
    val postalCode: String,
    val cc: String,
    val city: String,
    val state: String,
    val country: String,
    val labeledLatLngs: List<VenueLabelLedLatLngsDTO>,
    val formattedAddress: List<String>
)

data class VenueLabelLedLatLngsDTO constructor(
    val label: String,
    val lat: Double,
    val lng: Double
)

data class VenueIconDTO constructor(
    val prefix: String,
    val suffix: String
)

data class VenueCategoryDTO constructor(
    val id: String?,
    val name: String?,
    val pluralName: String?,
    val shortName: String?,
    val primary: Boolean,
    val icon: VenueIconDTO,
    val venuePage: VenuePageDTO
)

data class VenuePageDTO constructor(val id: String)

