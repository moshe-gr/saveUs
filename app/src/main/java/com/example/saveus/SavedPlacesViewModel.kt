package com.example.saveus

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class SavedPlacesViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext

    fun addSavedPlace(savePlace: SavePlace) {
        AppDatabase.getInstance(context)?.insert(savePlace)
    }

    fun getPlaces(): LiveData<List<SavePlace>>? {
        return AppDatabase.getInstance(context)?.getAll()
    }

    fun getPlacesByDate(startDate: Long, endDate: Long): LiveData<List<SavePlace>>? {
        return AppDatabase.getInstance(context)?.getByDate(startDate, endDate)
    }
}