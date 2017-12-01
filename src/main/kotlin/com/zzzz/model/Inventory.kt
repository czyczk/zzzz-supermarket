package com.zzzz.model

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.zzzz.model.serializer.LocalDateToMilliSerializer
import java.time.LocalDate
import kotlin.properties.Delegates

class Inventory {
    var barcode: Long by Delegates.notNull()

    @JsonSerialize(using = LocalDateToMilliSerializer::class)
    lateinit var productionDate: LocalDate

    var manufacturer: String by Delegates.notNull()

    var qtyInStock: Short by Delegates.notNull()

    var qtyOnShelf: Short by Delegates.notNull()

    val shelfBarcode: String
        get() {
            return String.format("%013d", barcode) +
                    String.format(
                            "%04d%02d%02d", productionDate.year, productionDate.monthValue, productionDate.dayOfMonth
                    )
        }

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