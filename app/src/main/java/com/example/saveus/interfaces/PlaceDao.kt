package com.example.saveus.interfaces

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.saveus.SavePlace

@Dao
interface PlaceDao {
    @Query("SELECT * FROM savePlace")
    fun getAll(): LiveData<List<SavePlace>>

    @Query("SELECT * FROM savePlace WHERE (time_start + :zoneOffset) / 1000 / 60 / 60 / 24 == :day")
    fun getByDate(day: Long, zoneOffset: Int): LiveData<List<SavePlace>>

    @Insert
    fun insert(savePlace: SavePlace)

    @Delete
    fun delete(savePlace: SavePlace)

    @Update
    fun update(savePlace: SavePlace)
}