package com.turtlemint.assignment.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * DateUtil responsible for all the data & time related operation.
 */
object DateUtil {

    private const val SOURCE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    private const val TARGET_DATE_FORMAT = "MM-dd-yyyyy"

    /**
     * Gets the date time in standard format.
     */
    fun getStandardTime(timeStamp: String?): String {
        var result = ""
        try {
            val dateFormat = SimpleDateFormat(SOURCE_DATE_FORMAT)
            val date = dateFormat.parse(timeStamp!!)
            val sdf = SimpleDateFormat(TARGET_DATE_FORMAT, Locale.getDefault())
            result = date?.let { sdf.format(it) } ?: ""
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }
}
