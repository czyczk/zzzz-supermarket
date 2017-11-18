package com.zzzz.util

import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

class ParseUtil {
    companion object {
        inline fun <reified T> parseAs(str: String): T =
                parseAsOrNull<T>(str) ?: throw IllegalArgumentException("The string is empty.")

        @Throws(NumberFormatException::class)
        inline fun <reified T> parseAsOrNull(str: String?): T? {
            if (str == null || str.isEmpty())
                return null

            return when (T::class) {
                String::class -> str as T
                Int::class -> str.toInt() as T
                Long::class -> str.toLong() as T
                Short::class -> str.toShort() as T
                Boolean::class -> str.toBoolean() as T
                BigDecimal::class -> BigDecimal(str) as T
                LocalDateTime::class -> DateUtil.fromMilliToTime(str.toLong()) as T
                LocalDate::class -> DateUtil.fromMilliToDate(str.toLong()) as T
                else -> throw NotImplementedError()
            }
        }
    }
}