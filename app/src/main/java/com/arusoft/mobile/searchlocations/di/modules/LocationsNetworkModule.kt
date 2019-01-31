package com.arusoft.mobile.searchlocations.di.modules

import com.arusoft.mobile.searchlocations.data.datasource.LocationsCloudDataSource
import com.arusoft.mobile.searchlocations.data.repository.datasource.LocationsCloudDataSourceImpl
import dagger.Module
import dagger.Provides

@Module
class LocationsNetworkModule {

    @Provides
    fun providesLocationsCloudDataSource(dataSource: LocationsCloudDataSourceImpl): LocationsCloudDataSource =
        dataSource

}
