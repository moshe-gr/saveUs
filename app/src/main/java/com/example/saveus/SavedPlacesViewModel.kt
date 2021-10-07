package com.example.saveus

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SavedPlacesViewModel : ViewModel() {
    var savedPlaces = MutableLiveData(ArrayList<SavePlace>())

    fun addSavePlaceses(savePlace: SavePlace) {
        savedPlaces.value?.add(savePlace)
        savedPlaces.value = savedPlaces.value

    }
}