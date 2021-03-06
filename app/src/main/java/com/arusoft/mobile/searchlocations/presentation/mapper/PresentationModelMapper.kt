package com.arusoft.mobile.searchlocations.presentation.mapper

import com.arusoft.mobile.searchlocations.domain.model.VenueModel
import com.arusoft.mobile.searchlocations.domain.model.VenuesSearchModel
import com.arusoft.mobile.searchlocations.presentation.model.BaseViewModel
import com.arusoft.mobile.searchlocations.presentation.model.VenueUIModel
import com.arusoft.mobile.searchlocations.presentation.model.VenuesUIModel
import com.arusoft.mobile.searchlocations.util.AppErrorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import javax.inject.Inject


class PresentationModelMapper @Inject constructor(val errorFactory: AppErrorFactory) {

    fun convert(model: VenuesSearchModel): VenuesUIModel {
        val uiModel =
            VenuesUIModel(model.venues.map { convert(it) }.let { venues -> venues.sortedBy { d -> d.distance } })
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
            distance = model.distance,
            url = model.url,
            favorite = model.favorite
        )
        uiModel.status = model.status
        return uiModel
    }

    fun convert(uiModel: VenueUIModel): VenueModel {
        val model = VenueModel(
            id = uiModel.id,
            name = uiModel.name,
            latitude = uiModel.latitude,
            longitude = uiModel.longitude,
            city = uiModel.city,
            state = uiModel.state,
            country = uiModel.country,
            categoryName = uiModel.categoryName,
            formattedAddress = uiModel.formattedAddress,
            address = uiModel.address,
            distance = uiModel.distance,
            url = uiModel.url,
            favorite = uiModel.favorite
        )
        if (uiModel.error) {
            model.setError(uiModel.errorCode)
        }
        model.status = uiModel.status
        return model
    }

    fun convertToMarker(model: VenueUIModel): MarkerOptions = MarkerOptions()
        .position(LatLng(model.latitude, model.longitude))
        .title(model.name)

    /**
     * Shallow copy of base class [BaseViewModel]
     *
     * @param [BaseViewModel] base class
     * @return [T] sub class
     */
    fun <T : BaseViewModel> T.baseCopy(base: BaseViewModel): T {
        status = base.status
        error = base.error
        errorCode = base.errorCode
        return this
    }


}
