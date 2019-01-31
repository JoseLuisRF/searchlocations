package com.arusoft.mobile.searchlocations.data.model

data class SearchLocationResponse constructor(val response: Response)

data class Response constructor(val venues: List<Venues>)
data class Venues constructor(val id: String)

