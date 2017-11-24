package com.zzzz.model

import com.zzzz.enum.SalesRecordTypeEnum
import com.zzzz.model.helper.InvoiceHelper
import com.zzzz.model.helper.SalesRecordHelper
import java.math.BigDecimal
import java.time.LocalDateTime
import kotlin.properties.Delegates

class SalesRecord(salesRecordHelper: SalesRecordHelper, invoiceHelper: InvoiceHelper) {
    var userId: Long by Delegates.notNull()
        private set

    var time: LocalDateTime
        private set

    var type: SalesRecordTypeEnum
        private set

    var reason: String? = null
        private set

    var invoiceId: Long by Delegates.notNull()
        private set

    var difference: BigDecimal
        private set

    init {
        userId = salesRecordHelper.userId
        time = salesRecordHelper.time
        type = salesRecordHelper.type
        reason = salesRecordHelper.reason
        invoiceId = salesRecordHelper.invoiceId
        difference = invoiceHelper.discountedPrice ?: invoiceHelper.totalPrice
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SalesRecord

        if (userId != other.userId) return false
        if (time != other.time) return false
        if (type != other.type) return false
        if (reason != other.reason) return false
        if (invoiceId != other.invoiceId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = userId.hashCode()
        result = 31 * result + time.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + (reason?.hashCode() ?: 0)
        result = 31 * result + invoiceId.hashCode()
        return result
    }
}