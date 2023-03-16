package com.atech.empsearch.util

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date


@SuppressLint("SimpleDateFormat")
fun String.convertUTCDate() : String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val dateFormat =DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val dateTime = Instant.parse(this)
            .atZone(ZoneId.of("UTC"))
            .toLocalDate()
         dateTime.format(dateFormat)
    } else {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val dateTime = Date(this)
        dateFormat.format(dateTime)
    }
