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

    @Insert
    fun insertAll(vararg savePlace: SavePlace)

    @Delete
    fun delete(savePlace: SavePlace)
}