package com.arusoft.mobile.searchlocations.data.database.dao.base


import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.ROLLBACK)
    fun insert(entity: T) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(entity: T) : Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(entity: T) : Int

    @Delete
    fun delete(entity: T) : Int

}