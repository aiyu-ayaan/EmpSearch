package com.atech.empsearch.util

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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


inline fun <ResponseObject> networkFetchData(
    crossinline fetch: suspend () -> ResponseObject,
    crossinline action: ((ResponseObject) -> ResponseObject) = { it },
): Flow<DataState<ResponseObject>> = flow {
    emit(DataState.Loading)
    try {
        val data = action(fetch())
        emit(DataState.Success(action(data)))
    } catch (e: Exception) {
        emit(DataState.Error(e))
    }
}