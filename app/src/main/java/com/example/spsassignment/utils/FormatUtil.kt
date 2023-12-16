package com.example.spsassignment.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

object FormatUtil {
    fun getFormattedRating(rating: Double?): String {
        val df = DecimalFormat("#.#", DecimalFormatSymbols(Locale.US))
        return df.format(rating ?: 0.0)
    }
}