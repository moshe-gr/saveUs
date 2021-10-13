package com.example.saveus

import java.util.*

interface DateTimeConverter {
    fun msToDays(ms: Long) = ms / 1000 / 60 / 60 / 24
    fun daysToMs(days: Long) = days * 1000 * 60 * 60 * 24
    fun addZoneDstOffset(timeInMs: Long) =
        Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault()).get(Calendar.ZONE_OFFSET) +
                Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault()).get(Calendar.DST_OFFSET) + timeInMs
    fun currentTimeInMs() = System.currentTimeMillis()
}