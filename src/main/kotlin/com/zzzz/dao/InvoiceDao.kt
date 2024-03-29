package com.zzzz.dao

import com.zzzz.model.helper.InvoiceHelper
import org.apache.ibatis.annotations.Param
import org.springframework.dao.DuplicateKeyException
import java.math.BigDecimal
import java.time.LocalDateTime

interface InvoiceDao {
    @Throws(DuplicateKeyException::class)
    fun insert(
            @Param("time") time: LocalDateTime,
            @Param("memberId") memberId: Long?,
            @Param("totalPrice") totalPrice: BigDecimal,
            @Param("discountedPrice") discountedPrice: BigDecimal?
    ): Int

    fun selectLastInsertId(): Long

    fun queryByPk(id: Long): InvoiceHelper?

    fun queryWithConstraints(
            @Param("invoiceId") id: Long?,
            @Param("minTime") minTime: LocalDateTime?,
            @Param("maxTime") maxTime: LocalDateTime?,
            @Param("memberId") memberId: Long?,
            @Param("minTotalPrice") minTotalPrice: BigDecimal?,
            @Param("maxTotalPrice") maxTotalPrice: BigDecimal?,
            @Param("minDiscountedPrice") minDiscountedPrice: BigDecimal?,
            @Param("maxDiscountedPrice") maxDiscountedPrice: BigDecimal?
    ): List<InvoiceHelper>
}