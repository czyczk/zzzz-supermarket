package com.zzzz.model.helper

import com.zzzz.enum.SalesRecordTypeEnum
import java.time.LocalDateTime
import kotlin.properties.Delegates

class SalesRecordHelper {
    var userId: Long by Delegates.notNull()

    var time: LocalDateTime by Delegates.notNull()

    var type: SalesRecordTypeEnum by Delegates.notNull()

    var reason: String? = null

    var invoiceId: Long by Delegates.notNull()
}