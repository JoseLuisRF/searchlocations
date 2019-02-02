package com.arusoft.mobile.searchlocations.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arusoft.mobile.searchlocations.di.scopes.ViewModelKey
import com.arusoft.mobile.searchlocations.presentation.viewmodel.LocationsViewModel
import com.arusoft.mobile.searchlocations.presentation.viewmodel.OnFavoriteViewModel
import com.arusoft.mobile.searchlocations.presentation.viewmodel.VenueDetailsViewModel
import com.arusoft.mobile.searchlocations.util.AppErrorFactory
import com.arusoft.mobile.searchlocations.util.AppErrorFactoryImpl
import com.arusoft.mobile.searchlocations.util.ProvidingViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [VenuesDataSourceModule::class])
abstract class LocationsFeatureModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ProvidingViewModelFactory): ViewModelProvider.Factory

    @Binds
    abstract fun bind(errorFactory: AppErrorFactoryImpl): AppErrorFactory

    @Binds
    @IntoMap
    @ViewModelKey(LocationsViewModel::class)
    abstract fun bindLocationsViewModel(viewModel: LocationsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(VenueDetailsViewModel::class)
    abstract fun bindVenueDetailsViewModel(viewModel: VenueDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OnFavoriteViewModel::class)
    abstract fun bindOnFavoriteViewModel(viewModel: OnFavoriteViewModel): ViewModel
}