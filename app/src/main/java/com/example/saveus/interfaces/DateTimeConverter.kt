package com.example.saveus.interfaces

import java.util.*

interface DateTimeConverter {
    fun msToDays(ms: Long) = ms / 1000 / 60 / 60 / 24
    fun daysToMs(days: Long) = days * 1000 * 60 * 60 * 24
    fun addZoneDstOffset(timeInMs: Long) = TimeZone.getDefault().getOffset(timeInMs) + timeInMs
    fun getCurrentTimeInMs() = System.currentTimeMillis()
    fun dateToShow(day: Int, month: Int, year: Int) = ""
    fun getCalendarFromMs(ms: Long): Calendar {
        val myCalendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault())
        myCalendar.timeInMillis = ms
        return myCalendar
    }
}