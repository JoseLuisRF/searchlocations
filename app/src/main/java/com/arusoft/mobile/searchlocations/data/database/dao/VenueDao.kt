package com.arusoft.mobile.searchlocations.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.arusoft.mobile.searchlocations.data.database.dao.base.BaseDao
import com.arusoft.mobile.searchlocations.data.database.entities.VenueEntity
import com.arusoft.mobile.searchlocations.data.database.entities.VenueEntityConstants.VENUE_TABLE_NAME

@Dao
interface VenueDao : BaseDao<VenueEntity> {

    @Query(
        value = " select * " +
                " from ${VENUE_TABLE_NAME} " +
                " where id = :id "
    )
    fun selectVenueById(id: String): VenueEntity?
}