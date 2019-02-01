package com.arusoft.mobile.searchlocations.presentation.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arusoft.mobile.searchlocations.presentation.mapper.PresentationModelMapper
import com.arusoft.mobile.searchlocations.presentation.model.VenueUIModel
import com.google.android.gms.maps.model.MarkerOptions
import javax.inject.Inject

class VenueDetailsViewModel @Inject constructor(val mapper: PresentationModelMapper) : ViewModel() {

    val venueDetailsLiveData: MutableLiveData<VenueUIModel?> = MutableLiveData()

    val markerLocationLiveData = MediatorLiveData<MarkerOptions>()

    init {
        markerLocationLiveData.addSource(venueDetailsLiveData) { response ->
            response?.let {
                markerLocationLiveData.postValue(mapper.convertToMarker(it))
            }
        }
    }

    fun updateVenueMarkerLocation() {
        venueDetailsLiveData.value?.let {
            markerLocationLiveData.postValue(mapper.convertToMarker(it))
        }

    }


}