package com.zzzz.service

import com.zzzz.exception.InsertionFailedException
import com.zzzz.exception.NoItemFoundException
import com.zzzz.model.Inventory
import com.zzzz.model.Invoice
import com.zzzz.model.helper.InvoiceHelper
import java.math.BigDecimal

interface InvoiceService {
    @Throws(InsertionFailedException::class)
    fun insert(
            time: Long,
            memberId: Long?,
            totalPrice: BigDecimal,
            discountedPrice: BigDecimal?,
            inventoryList: List<Inventory>
    )

    @Throws(NoItemFoundException::class)
    fun getInvoiceByPk(id: Long): InvoiceHelper

    @Throws(NoItemFoundException::class)
    fun getInvoiceWithListByPk(id: Long): Invoice

    fun getInvoicesWithConstraints(
            id: Long?,
            minTime: Long?, maxTime: Long?,
            memberId: Long?,
            minTotalPrice: BigDecimal?, maxTotalPrice: BigDecimal?,
            minDiscountedPrice: BigDecimal?, maxDiscountedPrice: BigDecimal?
    ): List<InvoiceHelper>
}