package com.arusoft.mobile.searchlocations.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.arusoft.mobile.searchlocations.domain.UseCaseMarkFavorite
import com.arusoft.mobile.searchlocations.presentation.mapper.PresentationModelMapper
import com.arusoft.mobile.searchlocations.presentation.model.FavoriteVenueInput
import com.arusoft.mobile.searchlocations.presentation.model.VenueUIModel
import javax.inject.Inject

class OnFavoriteViewModel @Inject constructor(
    private val mapper: PresentationModelMapper,
    private val useCaseMarkFavorite: UseCaseMarkFavorite
) : ViewModel() {
    val favoriteVenue: MutableLiveData<FavoriteVenueInput?> = MutableLiveData()

    private val favoriteLiveData: LiveData<VenueUIModel> =
        Transformations.switchMap(favoriteVenue) { input ->
            input?.let {
                Transformations.map(
                    useCaseMarkFavorite.execute(
                        UseCaseMarkFavorite.Params(
                            mapper.convert(input.venue),
                            input.favorite
                        )
                    )
                ) {
                    mapper.convert(it)
                }
            }
        }

    val onVenueFavorite: MediatorLiveData<VenueUIModel?> = MediatorLiveData()

    init {

        onVenueFavorite.addSource(favoriteLiveData) {
            onVenueFavorite.value = it
        }
    }
}