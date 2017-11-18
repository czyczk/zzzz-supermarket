package com.zzzz.service

import com.zzzz.model.Product
import java.math.BigDecimal

interface ProductService {
    fun getProductByBarcode(barcode: Long): Product?

    fun getProductsWithConstraints(
            barcode: Long?,
            nameContaining: String?,
            minPrice: BigDecimal?,
            maxPrice: BigDecimal?,
            minShelfLife: Int?,
            maxShelfLife: Int?,
            isRefundable: Boolean?): List<Product>
}