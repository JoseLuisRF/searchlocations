package com.arusoft.mobile.searchlocations.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.arusoft.mobile.searchlocations.data.database.dao.VenueDao
import com.arusoft.mobile.searchlocations.data.database.entities.VenueEntity

@Database(entities = [VenueEntity::class], version = DATABASE_VERSION)
abstract class AppDataBase : RoomDatabase() {
    abstract fun getVenueDao(): VenueDao
}

private const val DATABASE_VERSION = 1
const val DATABASE_NAME = "venues_data_base_jlrf.db"