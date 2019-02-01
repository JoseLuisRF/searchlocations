package com.arusoft.mobile.searchlocations.presentation.mapper

import com.arusoft.mobile.searchlocations.domain.model.VenueModel
import com.arusoft.mobile.searchlocations.domain.model.VenuesSearchModel
import com.arusoft.mobile.searchlocations.presentation.model.VenueUIModel
import com.arusoft.mobile.searchlocations.presentation.model.VenuesUIModel
import com.arusoft.mobile.searchlocations.util.AppErrorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import javax.inject.Inject


class PresentationModelMapper @Inject constructor(val errorFactory: AppErrorFactory) {

    fun convert(model: VenuesSearchModel): VenuesUIModel {
        val uiModel = VenuesUIModel(model.venues.map { convert(it) })
        if (model.error) {
            uiModel.setError(errorFactory.createErrorMessage(model.errorCode))
        }
        uiModel.status = model.status
        return uiModel
    }

    fun convert(model: VenueModel): VenueUIModel {

        val uiModel = VenueUIModel(
            id = model.id,
            name = model.name,
            latitude = model.latitude,
            longitude = model.longitude,
            city = model.city,
            state = model.state,
            country = model.country,
            categoryName = model.categoryName,
            formattedAddress = model.formattedAddress,
            address = model.address,
            distance = model.distance
        )
        uiModel.status = model.status
        return uiModel
    }

    fun convertToMarker(model: VenueUIModel): MarkerOptions = MarkerOptions()
        .position(LatLng(model.latitude, model.longitude))
        .title(model.name)

}
