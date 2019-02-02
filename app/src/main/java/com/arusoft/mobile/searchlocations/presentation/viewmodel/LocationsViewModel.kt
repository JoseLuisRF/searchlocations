package com.arusoft.mobile.searchlocations.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import com.arusoft.mobile.searchlocations.domain.UseCaseGetVenues
import com.arusoft.mobile.searchlocations.domain.UseCaseUpdateVenues
import com.arusoft.mobile.searchlocations.presentation.mapper.PresentationModelMapper
import com.arusoft.mobile.searchlocations.presentation.model.VenueSearchInput
import com.arusoft.mobile.searchlocations.presentation.model.VenuesUIModel
import javax.inject.Inject

class LocationsViewModel @Inject constructor(
    val useCaseGetLocations: UseCaseGetVenues,
    val useCaseUpdateVenues: UseCaseUpdateVenues,
    val mapper: PresentationModelMapper
) : ViewModel() {
    val currentLocation: MutableLiveData<VenueSearchInput> = MutableLiveData()

    val venuesLiveData: LiveData<VenuesUIModel> = switchMap(currentLocation) { request ->
        switchMap(
            useCaseGetLocations.execute(
                UseCaseGetVenues.Params(
                    request.latitude,
                    request.longitude,
                    request.query
                )
            )
        ) { response ->
            map(useCaseUpdateVenues.execute(UseCaseUpdateVenues.Params(response))) {
                mapper.convert(it)
            }
        }
    }
}


