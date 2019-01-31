package com.arusoft.mobile.searchlocations.di.modules

import com.arusoft.mobile.searchlocations.presentation.ui.LocationsFragment
import com.arusoft.mobile.searchlocations.presentation.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [LocationsFeatureModule::class])
    abstract fun mainActivity(): MainActivity


    @ContributesAndroidInjector(modules = [LocationsFeatureModule::class])
    abstract fun locationsFragment(): LocationsFragment

}