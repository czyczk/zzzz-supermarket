package com.zzzz.model

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.zzzz.model.helper.InvoiceHelper
import com.zzzz.model.helper.InvoiceInventoryHelper
import com.zzzz.model.serializer.LocalDateTimeToMilliSerializer
import java.math.BigDecimal
import java.time.LocalDateTime
import kotlin.properties.Delegates

class Invoice {
    var id: Long by Delegates.notNull()

    @JsonSerialize(using = LocalDateTimeToMilliSerializer::class)
    lateinit var time: LocalDateTime

    var memberId: Long? = null

    var totalPrice: BigDecimal by Delegates.notNull()

    var discountedPrice: BigDecimal? = null

    var invoiceInventoryList: List<InvoiceInventory>? = null

    constructor()

    constructor(invoiceHelper: InvoiceHelper) {
        this.id = invoiceHelper.id
        this.time = invoiceHelper.time
        this.memberId = invoiceHelper.memberId
        this.totalPrice = invoiceHelper.totalPrice
        this.discountedPrice = invoiceHelper.discountedPrice
    }

    constructor(invoiceHelper: InvoiceHelper, listInvoiceInventoryHelper: List<InvoiceInventoryHelper>): this(invoiceHelper) {
        this.invoiceInventoryList = listInvoiceInventoryHelper.map { it.invoiceInventory!! }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Invoice

        if (id != other.id) return false
        if (time != other.time) return false
        if (memberId != other.memberId) return false
        if (totalPrice != other.totalPrice) return false
        if (discountedPrice != other.discountedPrice) return false
        if (invoiceInventoryList != other.invoiceInventoryList) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + time.hashCode()
        result = 31 * result + (memberId?.hashCode() ?: 0)
        result = 31 * result + totalPrice.hashCode()
        result = 31 * result + (discountedPrice?.hashCode() ?: 0)
        result = 31 * result + (invoiceInventoryList?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Invoice(id=$id, time=$time, memberId=$memberId, totalPrice=$totalPrice, discountedPrice=$discountedPrice, invoiceInventoryList=$invoiceInventoryList)"
    }
}