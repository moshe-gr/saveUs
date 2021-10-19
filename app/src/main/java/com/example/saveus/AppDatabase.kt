package com.example.saveus

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(SavePlace::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun placeDao(): PlaceDao

    companion object {
        var db: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (db == null) {
                db = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java, "savedPlaces"
                ).build()
            }
            return db
        }

    }

    fun insert(savePlace: SavePlace) {
        Thread {
            Runnable {
                val placeDao = db!!.placeDao()
                placeDao.insert(savePlace)
            }.run()
        }.start()
    }

    fun getAll(): LiveData<List<SavePlace>> {
        val placeDao = db!!.placeDao()
        return placeDao.getAll()
    }

    fun getByDate(day: Long, zoneOffset: Int): LiveData<List<SavePlace>> {
        val placeDao = db!!.placeDao()
        return placeDao.getByDate(day, zoneOffset)
    }

    fun delete(savePlace: SavePlace) {
        Thread {
            Runnable {
                val placeDao = db!!.placeDao()
                placeDao.delete(savePlace)
            }.run()
        }.start()
    }

    fun update(savePlace: SavePlace) {
        Thread {
            Runnable {
                val placeDao = db!!.placeDao()
                placeDao.update(savePlace)
            }.run()
        }.start()
    }
}
