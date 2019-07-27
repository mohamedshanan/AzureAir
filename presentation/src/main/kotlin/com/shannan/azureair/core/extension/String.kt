
package com.shannan.azureair.core.extension

import android.text.TextUtils

fun String.Companion.empty() = ""

fun String.extractNumber(): String {

    if (TextUtils.isEmpty(String.toString())) return ""

    val sb = StringBuilder()
    var found = false
    for (c in String.toString()) {
        if (Character.isDigit(c)) {
            sb.append(c)
            found = true
        } else if (found) {
            break
        }
    }

    return sb.toString()
}