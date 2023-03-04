package com.sample.googlerepositories.utils.extensions

import android.app.Service
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 *  extension to hide keyboard
 */
fun View.hideKeyboard() {
    (this.context.getSystemService(Service.INPUT_METHOD_SERVICE) as? InputMethodManager)?.hideSoftInputFromWindow(
        applicationWindowToken,
        0
    )
}

/**
 *  extension to change view visibility to visible
 */
fun View.toVisible() {
    this.visibility = View.VISIBLE
}

/**
 *  extension to change view visibility to gone
 */
fun View.toGone() {
    this.visibility = View.GONE
}
