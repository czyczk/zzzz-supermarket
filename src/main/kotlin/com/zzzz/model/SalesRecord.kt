package com.zzzz.model

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.zzzz.enum.SalesRecordTypeEnum
import com.zzzz.model.helper.InvoiceHelper
import com.zzzz.model.helper.SalesRecordHelper
import com.zzzz.model.serializer.LocalDateTimeToMilliSerializer
import java.math.BigDecimal
import java.time.LocalDateTime
import kotlin.properties.Delegates

class SalesRecord {
    var userId: Long by Delegates.notNull()
        private set

    @JsonSerialize(using = LocalDateTimeToMilliSerializer::class)
    var time: LocalDateTime
        private set

    var type: SalesRecordTypeEnum
        private set

    var reason: String? = null
        private set

    var invoiceId: Long by Delegates.notNull()
        private set

    lateinit var difference: BigDecimal
        private set

    var inventoryList: List<InvoiceInventory>? = null
        private set

    private constructor(salesRecordHelper: SalesRecordHelper) {
        userId = salesRecordHelper.userId
        time = salesRecordHelper.time
        type = salesRecordHelper.type
        reason = salesRecordHelper.reason
        invoiceId = salesRecordHelper.invoiceId
    }

    constructor(salesRecordHelper: SalesRecordHelper, invoiceHelper: InvoiceHelper) : this(salesRecordHelper) {
        difference = invoiceHelper.discountedPrice ?: invoiceHelper.totalPrice
    }

    constructor(salesRecordHelper: SalesRecordHelper, inventoryList: List<InvoiceInventory>) : this(salesRecordHelper) {
        this.inventoryList = inventoryList
        this.difference = BigDecimal.ZERO
        inventoryList.forEach {
            this.difference -= it.cost!!
        }
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