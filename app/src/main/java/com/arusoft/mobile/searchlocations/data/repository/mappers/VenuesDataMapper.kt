package com.arusoft.mobile.searchlocations.data.repository.mappers

import com.arusoft.mobile.searchlocations.data.database.entities.VenueEntity
import com.arusoft.mobile.searchlocations.data.model.VenuesSearchResponse
import com.arusoft.mobile.searchlocations.data.model.VenueDTO
import com.arusoft.mobile.searchlocations.domain.model.BaseModel
import com.arusoft.mobile.searchlocations.domain.model.VenueModel
import com.arusoft.mobile.searchlocations.domain.model.VenuesSearchModel
import com.arusoft.mobile.searchlocations.util.ErrorConstants.PARSING_ERROR
import javax.inject.Inject

class VenuesDataMapper @Inject constructor() {

    fun convert(response: VenuesSearchResponse): VenuesSearchModel {
        val model = VenuesSearchModel(response.response.venues.map { convert(it) })
        model.status = BaseModel.SUCCESS
        return model
    }

    fun convert(response: VenueDTO): VenueModel {
        val model = VenueModel(
            id = response.id,
            name = response.name,
            latitude = response.location.lat,
            longitude = response.location.lng,
            city = response.location.city ?: "",
            state = response.location.state ?: "",
            country = response.location.country ?: "",
            categoryName = response.categories.firstOrNull { it.primary }?.name ?: "",
            formattedAddress = response.location.formattedAddress,
            distance = response.location.distance,
            address = response.location.address ?: "",
            url = response.delivery?.url ?: ""
        )

        if (response.id.isNullOrBlank() ||
            response.name.isNullOrBlank()
        ) {
            model.setError(PARSING_ERROR)
        } else {
            model.status = BaseModel.SUCCESS
        }

        return model
    }

    fun convertToEntity(model: VenueModel): VenueEntity = VenueEntity(
        id = model.id,
        name = model.name,
        latitude = model.latitude,
        longitude = model.longitude,
        city = model.city,
        state = model.state,
        country = model.country,
        categoryName = model.categoryName,
        address = model.address,
        distance = model.distance,
        url = model.url
    )

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

/**
 * Shallow copy of base class [BaseModel]
 *
 * @param [BaseModel] base class
 * @return [T] sub class
 */
fun <T : BaseModel> T.baseCopy(base: BaseModel): T {
    status = base.status
    error = base.error
    errorCode = base.errorCode
    return this
}