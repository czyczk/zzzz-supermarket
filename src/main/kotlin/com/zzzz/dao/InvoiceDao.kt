package com.zzzz.dao

import com.zzzz.model.Inventory
import com.zzzz.model.Invoice
import com.zzzz.model.helper.InvoiceHelper
import org.apache.ibatis.annotations.Param
import java.math.BigDecimal
import java.time.LocalDateTime

interface InvoiceDao {
    fun insert(
            @Param("time") time: LocalDateTime,
            @Param("memberId") memberId: Long?,
            @Param("totalPrice") totalPrice: BigDecimal,
            @Param("discountedPrice") discountedPrice: BigDecimal?
    ): Int

    fun selectLastInsertId(): Long

    fun queryById(id: Long): InvoiceHelper?

    fun queryWithConstraints(
            @Param("invoiceId") id: Long?,
            @Param("minTime") minTime: LocalDateTime?,
            @Param("maxTime") maxTime: LocalDateTime?,
            @Param("memberId") memberId: Long?,
            @Param("minTotalPrice") minTotalPrice: BigDecimal?,
            @Param("maxTotalPrice") maxTotalPrice: BigDecimal?,
            @Param("minDiscountedPrice") minDiscountedPrice: BigDecimal?,
            @Param("maxDiscountedPrice") maxDiscountedPrice: BigDecimal?
    ): List<Invoice>

    fun delete(id: Long): Int
}