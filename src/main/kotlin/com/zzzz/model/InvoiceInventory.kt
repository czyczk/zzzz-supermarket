package com.zzzz.model

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.zzzz.model.serializer.LocalDateToMilliSerializer
import com.zzzz.util.ParseUtil
import java.math.BigDecimal
import java.time.LocalDate
import kotlin.properties.Delegates

class InvoiceInventory {
    var barcode: Long by Delegates.notNull()

    @JsonSerialize(using = LocalDateToMilliSerializer::class)
    var productionDate: LocalDate? = null
    fun setProductionDate(productionDate: Long) {
        this.productionDate = ParseUtil.parseAs(productionDate.toString())
    }

    var name: String? = null

    var qty: Short by Delegates.notNull()

    var unitPrice: BigDecimal? = null

    var cost: BigDecimal? = null

    val shelfBarcode: String
        get() {
            return barcode.toString() +
                    String.format(
                            "%04d%02d%02d", productionDate!!.year, productionDate!!.monthValue, productionDate!!.dayOfMonth
                    )
        }

    override fun toString(): String {
        return "InvoiceInventory(barcode=$barcode, productionDate=$productionDate, name=$name, qty=$qty, unitPrice=$unitPrice, cost=$cost)"
    }
}
