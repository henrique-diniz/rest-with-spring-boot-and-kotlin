package com.hdz.util

class NumericTreatment {
    fun isNumeric(strNumber: String?): Boolean? {
        val number = strNumber?.replace(",", ".")
        return number?.matches("[-+]?[0-9]*\\.?[0-9]+".toRegex())
    }

    fun convertToDouble(strNumber: String?): Double? {
        val number = strNumber?.replace(",", ".")
        if (isNumeric(number) == true) return number?.toDouble()
        return 0.0
    }
}