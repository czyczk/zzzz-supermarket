package com.zzzz.service.impl

import com.zzzz.dao.InvoiceDao
import com.zzzz.dao.InvoiceInventoryDao
import com.zzzz.exception.InsertionFailedException
import com.zzzz.exception.NoItemFoundException
import com.zzzz.model.Invoice
import com.zzzz.model.InvoiceInventory
import com.zzzz.model.helper.InvoiceHelper
import com.zzzz.service.InvoiceService
import com.zzzz.util.ParseUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.DuplicateKeyException
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
            invoiceInventoryList: List<InvoiceInventory>
    ): Long {
        @Suppress("NAME_SHADOWING")
        val time = ParseUtil.parseAs<LocalDateTime>(time.toString())

        // Insert the invoice
        try {
            invoiceDao.insert(time, memberId, totalPrice, discountedPrice)

            // Get the last insert ID
            val invoiceId = invoiceDao.selectLastInsertId()

            // Insert the inventory list
            invoiceInventoryDao.insertInventoryList(invoiceId, invoiceInventoryList)

            // Return ID of the last inserted item
            return invoiceId
        } catch (e: DuplicateKeyException) {
            throw InsertionFailedException(e.mostSpecificCause.message)
        } catch (e: DataIntegrityViolationException) {
            throw InsertionFailedException(e.mostSpecificCause.message)
        }
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
        val minTime = ParseUtil.parseAsOrNull<LocalDateTime>(minTime?.toString())
        @Suppress("NAME_SHADOWING")
        val maxTime = ParseUtil.parseAsOrNull<LocalDateTime>(maxTime?.toString())
        @Suppress("UnnecessaryVariable")
        val invoiceList = invoiceDao.queryWithConstraints(id, minTime, maxTime, memberId, minTotalPrice, maxTotalPrice, minDiscountedPrice, maxDiscountedPrice)
        return invoiceList
    }
}