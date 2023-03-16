package com.atech.empsearch.util

import android.annotation.SuppressLint
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date


@SuppressLint("SimpleDateFormat")
fun String.convertUTCDate(
    format : String = "yyyy-MM-dd"
) : String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val dateFormat =DateTimeFormatter.ofPattern(format)
        val dateTime = Instant.parse(this)
            .atZone(ZoneId.of("UTC"))
            .toLocalDate()
         dateTime.format(dateFormat)
    } else {
        val dateFormat = SimpleDateFormat(format)
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


inline fun EditText.addTextChangeListener(
    crossinline listener: (String) -> Unit,
) = this.apply {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (s?.length == 1) {
                if (s.toString() == "0") {
                    setText("")
                }
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.let {
                listener(s.toString())
            }
        }
    })
}