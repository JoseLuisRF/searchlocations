package com.arusoft.mobile.searchlocations.data.repository.mappers

import com.arusoft.mobile.searchlocations.data.model.VenuesSearchResponse
import com.arusoft.mobile.searchlocations.data.model.VenueDTO
import com.arusoft.mobile.searchlocations.domain.model.BaseModel
import com.arusoft.mobile.searchlocations.domain.model.VenueModel
import com.arusoft.mobile.searchlocations.domain.model.VenuesSearchModel
import com.arusoft.mobile.searchlocations.util.ErrorConstants.PARSING_ERROR
import javax.inject.Inject

class LocationDataMapper @Inject constructor() {

    fun convert(response: VenuesSearchResponse): VenuesSearchModel {
        val model = VenuesSearchModel(response.response.venues.map { convert(it) })
        model.status = BaseModel.SUCCESS
        return model
    }

    fun convert(response: VenueDTO): VenueModel {
        val model = VenueModel()
        if (response.id.isNullOrBlank() ||
            response.name.isNullOrBlank()
        ) {
            model.setError(PARSING_ERROR)
        } else {
            model.status = BaseModel.SUCCESS
        }

        return model
    }

    /**
     * Creates an error Model from a model type
     *
     * @param errorCode A custom error code associated
     * @param clazz The model type
     * @return [T] An instance of T type
     */
    fun <T : BaseModel> createDomainModel(
        errorCode: Int = 0,
        clazz: Class<T>,
        status: String = BaseModel.LOADING
    ): T {
        val model = clazz.newInstance()
        if (errorCode != 0) {
            model.setError(errorCode)
        } else {
            model.status = status
        }
        return model
    }

}