package com.zzzz.model

import com.zzzz.enum.SalesRecordTypeEnum
import com.zzzz.model.helper.SalesRecordHelper
import java.math.BigDecimal
import java.time.LocalDateTime
import kotlin.properties.Delegates

abstract class SalesRecord {
    var userId: Long by Delegates.notNull<Long>()

    lateinit var time: LocalDateTime

    lateinit var type: SalesRecordTypeEnum

    var reason: String? = null

    var invoice: Invoice? = null

    var difference: BigDecimal? = null

    constructor()

    constructor(salesRecordHelper: SalesRecordHelper, invoice: Invoice) {
        this.userId = salesRecordHelper.userId
        this.time = salesRecordHelper.time
        this.type = salesRecordHelper.type
        this.reason = salesRecordHelper.reason
        this.invoice = invoice
        this.difference = invoice.discountedPrice?: invoice.totalPrice
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SalesRecord

        if (userId != other.userId) return false
        if (time != other.time) return false
        if (type != other.type) return false
        if (reason != other.reason) return false
        if (invoice != other.invoice) return false

        return true
    }

    override fun hashCode(): Int {
        var result = userId.hashCode()
        result = 31 * result + time.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + (reason?.hashCode() ?: 0)
        result = 31 * result + (invoice?.hashCode() ?: 0)
        return result
    }


}