package com.sample.googlerepositories.utils.enums

import android.util.Range

/**
 * enum clas containing API error codes possible to occur
 */
enum class APIErrorCodes(val code: Range<Int>) {
    BadRequest(Range(400, 500)),
    ServerError(Range(500, 600))
}