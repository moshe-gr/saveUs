package com.example.saveus

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PlaceDao {
    @Query("SELECT * FROM savePlace")
    fun getAll(): LiveData<List<SavePlace>>

    @Query("SELECT * FROM savePlace WHERE time_start / 1000 / 60 / 60 / 24 BETWEEN :startDate AND :endDate")
    fun getByDate(startDate: Long, endDate: Long): LiveData<List<SavePlace>>

    @Insert
    fun insert(savePlace: SavePlace)

    @Delete
    fun delete(savePlace: SavePlace)
}