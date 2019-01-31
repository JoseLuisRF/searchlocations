package com.arusoft.mobile.searchlocations.presentation.mapper

import com.arusoft.mobile.searchlocations.domain.model.VenueModel
import com.arusoft.mobile.searchlocations.domain.model.VenuesSearchModel
import com.arusoft.mobile.searchlocations.presentation.model.BaseViewModel
import com.arusoft.mobile.searchlocations.presentation.model.VenueUIModel
import com.arusoft.mobile.searchlocations.presentation.model.VenuesUIModel
import com.arusoft.mobile.searchlocations.util.AppErrorFactory
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
        val uiModel = VenueUIModel(model.name)
        uiModel.status = model.status
        return uiModel
    }
}
