package com.arusoft.mobile.searchlocations.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.arusoft.mobile.searchlocations.data.database.entities.VenueEntityConstants.VENUE_TABLE_NAME

@Entity(tableName = VENUE_TABLE_NAME)
data class VenueEntity constructor(

    @PrimaryKey
    val id: String,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val city: String,
    val state: String,
    val country: String,
    val categoryName: String,
    val address: String,
    val distance: Int,
    val url: String
)

object VenueEntityConstants {
    const val VENUE_TABLE_NAME = "venues"
}