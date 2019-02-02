package com.arusoft.mobile.searchlocations.di.modules

import com.arusoft.mobile.searchlocations.data.api.datasource.LocationsCloudDataSource
import com.arusoft.mobile.searchlocations.data.database.datasource.VenuesDiskDataSource
import com.arusoft.mobile.searchlocations.data.repository.datasource.LocationsCloudDataSourceImpl
import com.arusoft.mobile.searchlocations.data.repository.datasource.VenuesDiskDataSourceImpl
import dagger.Module
import dagger.Provides

@Module
class VenuesDataSourceModule {

    @Provides
    fun providesLocationsCloudDataSource(dataSource: LocationsCloudDataSourceImpl): LocationsCloudDataSource =
        dataSource

    @Provides
    fun providesVenuesDiskDataSource(dataSource: VenuesDiskDataSourceImpl): VenuesDiskDataSource =
        dataSource

}
