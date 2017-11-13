package com.zzzz.dao

import com.zzzz.model.Product
import org.apache.ibatis.annotations.Param
import org.springframework.dao.DuplicateKeyException
import java.math.BigDecimal

interface ProductDao {
    fun insert(@Param("barcode") barcode: Long,
               @Param("name") name: String,
               @Param("price") price: BigDecimal,
               @Param("shelfLife") shelfLife: Int,
               @Param("isRefundable") isRefundable: Boolean): Int

    @Throws(DuplicateKeyException::class)
    fun update(@Param("targetBarcode") targetBarcode: Long,
               @Param("barcode") barcode: Long?,
               @Param("name") name: String?,
               @Param("price") price: BigDecimal?,
               @Param("shelfLife") shelfLife: Int?,
               @Param("isRefundable") isRefundable: Boolean?): Int

    fun queryByBarcode(barcode: Long): Product?

    fun queryWithConstraints(@Param("barcode") barcode: Long?,
                             @Param("nameContaining") nameContaining: String?,
                             @Param("minPrice") minPrice: BigDecimal?,
                             @Param("maxPrice") maxPrice: BigDecimal?,
                             @Param("minShelfLife") minShelfLife: BigDecimal?,
                             @Param("maxShelfLife") maxShelfLife: BigDecimal?,
                             @Param("isRefundable") isRefundable: Boolean?): List<Product>

    fun delete(@Param("barcode") barcode: Long): Int
}