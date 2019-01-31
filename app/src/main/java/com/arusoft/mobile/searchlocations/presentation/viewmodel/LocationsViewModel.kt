package com.arusoft.mobile.searchlocations.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import com.arusoft.mobile.searchlocations.domain.UseCaseGetLocations
import com.arusoft.mobile.searchlocations.presentation.mapper.PresentationModelMapper
import com.arusoft.mobile.searchlocations.presentation.model.VenuesList
import com.arusoft.mobile.searchlocations.presentation.model.VenuesUIModel
import javax.inject.Inject

class LocationsViewModel @Inject constructor(
    val useCaseGetLocations: UseCaseGetLocations,
    val mapper: PresentationModelMapper
) : ViewModel() {

    val currentLocation: MutableLiveData<VenuesList> = MutableLiveData()

    val venuesLiveData : LiveData<VenuesUIModel> = switchMap(currentLocation) { request ->
        map(
            useCaseGetLocations.execute(
                UseCaseGetLocations.Params(
                    request.latitude,
                    request.longitude
                )
            )
        ) { response ->
            mapper.convert(response)
        }
    }
}
