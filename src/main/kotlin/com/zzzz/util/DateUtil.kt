package com.zzzz.util

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

class DateUtil {
    companion object {
        fun toMilli(localDateTime: LocalDateTime): Long =
                ZonedDateTime.of(localDateTime, ZoneId.systemDefault()).toInstant().toEpochMilli()
    }
}