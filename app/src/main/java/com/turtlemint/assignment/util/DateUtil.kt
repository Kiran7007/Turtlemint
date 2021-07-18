package com.turtlemint.assignment.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * DateUtil responsible for all the data & time related operation.
 */
object DateUtil {

    /**
     * Gets the date time in standard format.
     */
    fun getStandardTime(timeStamp: String?): String {
        var result = ""
        try {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            val date = dateFormat.parse(timeStamp!!)
            val sdf = SimpleDateFormat("MM-dd-yyyyy", Locale.getDefault())
            result = date?.let { sdf.format(it) } ?: ""
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }
}