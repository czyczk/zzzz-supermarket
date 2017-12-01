package com.zzzz.model.helper

import com.zzzz.model.InvoiceInventory
import kotlin.properties.Delegates

class InvoiceInventoryHelper {
    var invoiceId: Long by Delegates.notNull()
    var invoiceInventory: InvoiceInventory? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as InvoiceInventoryHelper

        if (invoiceId != other.invoiceId) return false
        if (invoiceInventory != other.invoiceInventory) return false

        return true
    }

    override fun hashCode(): Int {
        var result = invoiceId.hashCode()
        result = 31 * result + (invoiceInventory?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "InvoiceInventoryHelper(invoiceId=$invoiceId, invoiceInventory=$invoiceInventory)"
    }
}