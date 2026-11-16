package com.birdwastemanagement.app.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

/**
 * Extension functions to automatically convert English numbers to Bangla numbers
 * when the app's selected language is Bangla.
 */
@Composable
fun String.localizeDigits(): String {
    val localeList = LocalConfiguration.current.locales
    val isBangla = if (!localeList.isEmpty) localeList.get(0).language == "bn" else false
    if (!isBangla) return this

    val banglaDigits = arrayOf('০', '১', '২', '৩', '৪', '৫', '৬', '৭', '৮', '৯')
    val sb = StringBuilder()
    for (char in this) {
        if (char in '0'..'9') {
            sb.append(banglaDigits[char - '0'])
        } else {
            sb.append(char)
        }
    }
    return sb.toString()
}

@Composable
fun Int.localizeDigits(): String = this.toString().localizeDigits()

@Composable
fun Double.localizeDigits(): String = this.toString().localizeDigits()
