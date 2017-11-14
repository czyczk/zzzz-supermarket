package com.zzzz.model

import java.time.LocalDate
import kotlin.properties.Delegates

class Inventory {
    var barcode: Long by Delegates.notNull<Long>()

    var productionDate: LocalDate by Delegates.notNull<LocalDate>()

    var manufacturer: String by Delegates.notNull<String>()

    var qtyInStock: Short by Delegates.notNull<Short>()

    var qtyOnShelf: Short by Delegates.notNull<Short>()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Inventory

        if (barcode != other.barcode) return false
        if (productionDate != other.productionDate) return false
        if (manufacturer != other.manufacturer) return false
        if (qtyInStock != other.qtyInStock) return false
        if (qtyOnShelf != other.qtyOnShelf) return false

        return true
    }

    override fun hashCode(): Int {
        var result = barcode.hashCode()
        result = 31 * result + productionDate.hashCode()
        result = 31 * result + manufacturer.hashCode()
        result = 31 * result + qtyInStock
        result = 31 * result + qtyOnShelf
        return result
    }

    override fun toString(): String {
        return "Inventory(barcode=$barcode, productionDate=$productionDate, manufacturer='$manufacturer', qtyInStock=$qtyInStock, qtyOnShelf=$qtyOnShelf)"
    }
}