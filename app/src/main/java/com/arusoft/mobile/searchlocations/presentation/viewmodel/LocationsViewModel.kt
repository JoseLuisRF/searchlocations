package com.arusoft.mobile.searchlocations.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.arusoft.mobile.searchlocations.domain.UseCaseGetLocations
import javax.inject.Inject

class LocationsViewModel @Inject constructor(
    val useCaseGetLocations: UseCaseGetLocations) : ViewModel() {
}
