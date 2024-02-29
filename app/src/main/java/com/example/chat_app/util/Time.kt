package com.example.chat_app.util

import com.example.chat_app.util.Constants.TIME_ZONE
import java.util.Calendar
import java.util.TimeZone

object Time {

    fun getCurrentTimeInMillis(): Long {
        val calendar = Calendar.getInstance()
        calendar.timeZone = TimeZone.getTimeZone(TIME_ZONE)
        return calendar.timeInMillis
    }
}