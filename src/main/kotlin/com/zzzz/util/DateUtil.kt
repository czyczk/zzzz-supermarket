package com.zzzz.util

import java.time.*

class DateUtil {
    companion object {
        fun toMilli(localDateTime: LocalDateTime): Long =
                ZonedDateTime.of(localDateTime, ZoneId.systemDefault()).toInstant().toEpochMilli()

        fun fromMilliToTime(epochMilli: Long): LocalDateTime =
                ZonedDateTime.ofInstant(Instant.ofEpochMilli(epochMilli), ZoneId.systemDefault()).toLocalDateTime()

        fun fromMilliToDate(epochMilli: Long): LocalDate = fromMilliToTime(epochMilli).toLocalDate()
    }
}