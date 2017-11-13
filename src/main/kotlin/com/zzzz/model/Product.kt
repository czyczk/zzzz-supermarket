package com.zzzz.model

import java.math.BigDecimal
import kotlin.properties.Delegates

class Product {
    var barcode: Long by Delegates.notNull<Long>()

    var name: String by Delegates.notNull<String>()

    var price: BigDecimal by Delegates.notNull<BigDecimal>()

    var shelfLife: Int by Delegates.notNull<Int>()

    var isRefundable: Boolean by Delegates.notNull<Boolean>()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Product

        if (barcode != other.barcode) return false
        if (name != other.name) return false
        if (price != other.price) return false
        if (shelfLife != other.shelfLife) return false
        if (isRefundable != other.isRefundable) return false

        return true
    }

    override fun hashCode(): Int {
        var result = barcode.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + price.hashCode()
        result = 31 * result + shelfLife
        result = 31 * result + isRefundable.hashCode()
        return result
    }

    override fun toString(): String {
        return "Product(barcode=$barcode, name='$name', price=$price, shelfLife=$shelfLife, isRefundable=$isRefundable)"
    }
}