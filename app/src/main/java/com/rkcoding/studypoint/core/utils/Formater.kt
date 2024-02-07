package com.rkcoding.studypoint.core.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.time.Duration.Companion.hours

@RequiresApi(Build.VERSION_CODES.O)
fun Long?.toDateFormat(): String{
    val date: LocalDate = this?.let {
        Instant
            .ofEpochMilli(it)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    } ?: LocalDate.now()

    return date.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
}


fun Long.toHour(): Float{
    val hours = this.toFloat() / 3600f
    return "%.2f".format(hours).toFloat()
}


fun Int.pad(): String{
    return this.toString().padStart(length = 2, padChar = '0')
}