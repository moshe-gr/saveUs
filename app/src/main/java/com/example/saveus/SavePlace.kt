package com.example.saveus

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SavePlace (
    @PrimaryKey(autoGenerate = true) val pid: Int = 0,
    @ColumnInfo(name = "address") var address: String? = null,
    @ColumnInfo(name = "time_start") var timeStart: Long? = null,
    @ColumnInfo(name = "time_end") var timeEnd: Long? = null,
    @ColumnInfo(name = "time_length") var timeLength: String? = null,
    @ColumnInfo(name = "latitude") var latitude: Double? = null,
    @ColumnInfo(name = "longitude") var longitude: Double? = null
)