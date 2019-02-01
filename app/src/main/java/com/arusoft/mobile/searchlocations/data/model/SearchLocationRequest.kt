package com.arusoft.mobile.searchlocations.data.model

data class SearchLocationRequest(val latitude: Double, val longitude: Double, val query: String) {

    var latlang: String = "$latitude,$longitude"
        private set
}