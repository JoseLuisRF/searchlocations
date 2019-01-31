package com.arusoft.mobile.searchlocations.domain.model

data class VenuesSearchModel constructor(val venues: List<VenueModel> = listOf()) : BaseModel()

data class VenueModel constructor(val id: String = "", val name: String = "") : BaseModel()