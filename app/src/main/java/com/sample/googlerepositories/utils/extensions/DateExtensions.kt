package com.sample.googlerepositories.utils.extensions

import com.sample.googlerepositories.utils.constants.DateFormat
import com.sample.googlerepositories.utils.constants.DateFormat.DATE_FORMAT_DD_MM_YYYY
import com.sample.googlerepositories.utils.constants.DateFormat.DATE_FORMAT_YYYY_MM_DD_T_HH_MM_SS_SSSSSSS
import java.text.SimpleDateFormat
import java.util.*

/**
 *  converts a GMT string of pattern [DATE_FORMAT_YYYY_MM_DD_T_HH_MM_SS_SSSSSSS] into
 *  date string of patter [DATE_FORMAT_DD_MM_YYYY]
 *
 *  @param locale is locale to format based on
 *  @return GMT string as [DATE_FORMAT_DD_MM_YYYY] date string, or null if failure
 */
fun String.formatGMTDateStringAsDDMMYYYYString(locale: Locale = Locale.ENGLISH): String? {
    return try {
        val dateFormatFull = SimpleDateFormat(
            DATE_FORMAT_YYYY_MM_DD_T_HH_MM_SS_SSSSSSS,
            locale
        )

        val dateFormat =
            SimpleDateFormat(DATE_FORMAT_DD_MM_YYYY, locale)

        val gmtDate = dateFormatFull.parse(this)
        if (gmtDate != null) {
            return dateFormat.format(gmtDate.time)
        } else {
            null
        }
    } catch (ex: Exception) {
        try {
            val dateFormatFull =
                SimpleDateFormat(DateFormat.DATE_FORMAT_YYYY_MM_DD_T_HH_MM_SS, locale)

            val dateFormat =
                SimpleDateFormat(DATE_FORMAT_DD_MM_YYYY, locale)

            val gmtDate = dateFormatFull.parse(this)
            if (gmtDate != null) {
                return dateFormat.format(gmtDate.time)
            } else {
                null
            }
        } catch (ex: Exception) {
            null
        }
    }
}