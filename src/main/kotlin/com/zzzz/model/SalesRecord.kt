package com.zzzz.model

import java.time.LocalDateTime
import kotlin.properties.Delegates

abstract class SalesRecord {
    val userId: Long by Delegates.notNull<Long>()

    abstract val time: LocalDateTime

    val invoice: Invoice by Delegates.notNull<Invoice>()
}