package com.arusoft.mobile.searchlocations.di.modules

import com.arusoft.mobile.searchlocations.presentation.ui.VenuesHomeFragment
import com.arusoft.mobile.searchlocations.presentation.ui.MainActivity
import com.arusoft.mobile.searchlocations.presentation.ui.VenueDetailsFragment
import com.arusoft.mobile.searchlocations.presentation.ui.VenuesMapFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [LocationsFeatureModule::class])
    abstract fun mainActivity(): MainActivity


    @ContributesAndroidInjector(modules = [LocationsFeatureModule::class])
    abstract fun locationsFragment(): VenuesHomeFragment

    @ContributesAndroidInjector(modules = [LocationsFeatureModule::class])
    abstract fun venueDetailsFragment(): VenueDetailsFragment

    @ContributesAndroidInjector(modules = [LocationsFeatureModule::class])
    abstract fun venueVenuesMapFragment(): VenuesMapFragment

}