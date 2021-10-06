package com.example.saveus

import androidx.lifecycle.ViewModel
import java.sql.Time

class SavedPlaceViewModel: ViewModel(){
    var address: String? = null
    var timeStart: Time? = null
    var timeEnd: Time? = null
    var timeLength: String? = null
}