package com.example.saveus

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import java.time.ZoneOffset

class SavedPlacesViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext

    fun addSavedPlace(savePlace: SavePlace) {
        AppDatabase.getInstance(context)?.insert(savePlace)
    }

    fun getPlaces(): LiveData<List<SavePlace>>? {
        return AppDatabase.getInstance(context)?.getAll()
    }

    fun getPlacesByDate(day: Long, zoneOffset: Int): LiveData<List<SavePlace>>? {
        return AppDatabase.getInstance(context)?.getByDate(day, zoneOffset)
    }
}