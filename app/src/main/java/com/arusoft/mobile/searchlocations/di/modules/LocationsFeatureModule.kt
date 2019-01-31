package com.arusoft.mobile.searchlocations.di.modules

import androidx.lifecycle.ViewModelProvider
import com.arusoft.mobile.searchlocations.util.ProvidingViewModelFactory
import dagger.Binds
import dagger.Module

@Module(includes = [LocationsNetworkModule::class])
abstract class LocationsFeatureModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ProvidingViewModelFactory): ViewModelProvider.Factory
}