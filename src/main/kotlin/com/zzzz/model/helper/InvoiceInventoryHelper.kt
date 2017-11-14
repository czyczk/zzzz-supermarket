package com.zzzz.model.helper

import com.zzzz.model.Inventory
import kotlin.properties.Delegates

class InvoiceInventoryHelper {
    var invoiceId: Long by Delegates.notNull()
    var inventory: Inventory? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as InvoiceInventoryHelper

        if (invoiceId != other.invoiceId) return false
        if (inventory != other.inventory) return false

        return true
    }

    override fun hashCode(): Int {
        var result = invoiceId.hashCode()
        result = 31 * result + (inventory?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "InvoiceInventoryHelper(invoiceId=$invoiceId, inventory=$inventory)"
    }
}