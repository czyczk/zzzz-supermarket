package com.zzzz.service

import com.zzzz.exception.InsertionFailedException
import com.zzzz.exception.NoItemFoundException
import com.zzzz.exception.UpdateFailedException
import com.zzzz.model.Product
import java.math.BigDecimal

interface ProductService {
    @Throws(InsertionFailedException::class)
    fun insert(barcode: Long,
               name: String,
               price: BigDecimal,
               shelfLife: Int,
               isRefundable: Boolean)

    // TODO Exception type unknown
    @Throws(UpdateFailedException::class)
    fun update(targetBarcode: Long,
               barcode: Long?,
               name: String?,
               price: BigDecimal?,
               shelfLife: Int?,
               isRefundable: Boolean?)

    @Throws(NoItemFoundException::class)
    fun getProductByPk(barcode: Long): Product

    fun getProductsWithConstraints(
            barcode: Long?,
            nameContaining: String?,
            minPrice: BigDecimal?,
            maxPrice: BigDecimal?,
            minShelfLife: Int?,
            maxShelfLife: Int?,
            isRefundable: Boolean?): List<Product>
}