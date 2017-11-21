package com.zzzz.service.impl

import com.zzzz.dao.InvoiceDao
import com.zzzz.dao.InvoiceInventoryDao
import com.zzzz.exception.InsertionFailedException
import com.zzzz.exception.NoItemFoundException
import com.zzzz.model.Inventory
import com.zzzz.model.Invoice
import com.zzzz.model.helper.InvoiceHelper
import com.zzzz.service.InvoiceService
import com.zzzz.util.ParseUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.LocalDateTime

@Service
class InvoiceServiceImpl : InvoiceService {
    @Suppress("SpringKotlinAutowiring")
    @Autowired
    private lateinit var invoiceDao: InvoiceDao

    @Suppress("SpringKotlinAutowiring")
    @Autowired
    private lateinit var invoiceInventoryDao: InvoiceInventoryDao

    @Transactional
    override fun insert(
            time: Long,
            memberId: Long?,
            totalPrice: BigDecimal,
            discountedPrice: BigDecimal?,
            inventoryList: List<Inventory>
    ) {
        @Suppress("NAME_SHADOWING")
        val time = ParseUtil.parseAs<LocalDateTime>(time.toString())

        // Insert the invoice
        var rowsAffected = invoiceDao.insert(time, memberId, totalPrice, discountedPrice)
        if (rowsAffected == 0)
            throw InsertionFailedException()

        // Get the last insert ID
        val invoiceId = invoiceDao.selectLastInsertId()

        // Insert the inventory list
        rowsAffected = invoiceInventoryDao.insertInventoryList(invoiceId, inventoryList)
        if (rowsAffected == 0)
            throw InsertionFailedException()
    }

    override fun getInvoiceByPk(id: Long): InvoiceHelper {
        @Suppress("UnnecessaryVariable")
        val result = invoiceDao.queryByPk(id) ?:
                throw NoItemFoundException()
        return result
    }

    @Transactional
    override fun getInvoiceWithListByPk(id: Long): Invoice {
        val invoiceHelper = getInvoiceByPk(id)
        val inventoryList = invoiceInventoryDao.queryById(id)
        @Suppress("UnnecessaryVariable")
        val invoice = Invoice(invoiceHelper, inventoryList)
        return invoice
    }

    override fun getInvoicesWithConstraints(
            id: Long?,
            minTime: Long?, maxTime: Long?,
            memberId: Long?,
            minTotalPrice: BigDecimal?, maxTotalPrice: BigDecimal?,
            minDiscountedPrice: BigDecimal?, maxDiscountedPrice: BigDecimal?
    ): List<InvoiceHelper> {
        @Suppress("NAME_SHADOWING")
        val minTime = ParseUtil.parseAsOrNull<LocalDateTime>(minTime.toString())
        @Suppress("NAME_SHADOWING")
        val maxTime = ParseUtil.parseAsOrNull<LocalDateTime>(maxTime.toString())
        @Suppress("UnnecessaryVariable")
        val invoiceList = invoiceDao.queryWithConstraints(id, minTime, maxTime, memberId, minTotalPrice, maxTotalPrice, minDiscountedPrice, maxDiscountedPrice)
        return invoiceList
    }
}