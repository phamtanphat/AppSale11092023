package com.example.appsale11092023.util

import java.text.DecimalFormat

object StringUtils {
    fun formatCurrency(number: Int): String {
        return DecimalFormat("#,###").format(number)
    }
}