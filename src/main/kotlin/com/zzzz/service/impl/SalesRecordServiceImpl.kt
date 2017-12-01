package com.zzzz.service.impl

import com.zzzz.dao.SalesRecordDao
import com.zzzz.dao.SalesRecordInventoryDao
import com.zzzz.enum.SalesRecordTypeEnum
import com.zzzz.exception.IncorrectItemTypeException
import com.zzzz.exception.InsertionFailedException
import com.zzzz.exception.NoItemFoundException
import com.zzzz.model.InvoiceInventory
import com.zzzz.model.SalesRecord
import com.zzzz.service.InvoiceService
import com.zzzz.service.SalesRecordService
import com.zzzz.util.ParseUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class SalesRecordServiceImpl : SalesRecordService {
    @Suppress("SpringKotlinAutowiring")
    @Autowired
    private lateinit var salesRecordDao: SalesRecordDao

    @Suppress("SpringKotlinAutowiring")
    @Autowired
    private lateinit var salesRecordInventoryDao: SalesRecordInventoryDao

    @Suppress("SpringKotlinAutowiring")
    @Autowired
    private lateinit var invoiceService: InvoiceService

    @Transactional
    override fun insertAfterSalesRecord(
            userId: Long,
            time: Long,
            type: String,
            reason: String?,
            invoiceId: Long,
            inventoryList: List<InvoiceInventory>) {
        @Suppress("NAME_SHADOWING")
        val time = ParseUtil.parseAs<LocalDateTime>(time.toString())
        @Suppress("NAME_SHADOWING")
        val type = SalesRecordTypeEnum.fromDisplayName(type) ?:
                throw IncorrectItemTypeException()
        // For purchase records, insertPurchaseRecord() should be invoked instead
        if (type == SalesRecordTypeEnum.PURCHASE)
            throw IncorrectItemTypeException("Incorrect method to be invoked for purchase records.")

        try {
            // Insert the after sales record first
            salesRecordDao.insert(userId, time, type, reason, invoiceId)
            // Insert the inventory list
            salesRecordInventoryDao.insert(userId, time, inventoryList)
        } catch (e: DuplicateKeyException) {
            e.printStackTrace()
            throw InsertionFailedException(e.mostSpecificCause.message)
        } catch (e: DataIntegrityViolationException) {
            e.printStackTrace()
            throw InsertionFailedException(e.mostSpecificCause.message)
        }
    }

    override fun insertPurchaseRecord(
            userId: Long,
            time: Long,
            invoiceId: Long
    ) {
        // Insert the purchase record
        @Suppress("NAME_SHADOWING")
        val time = ParseUtil.parseAs<LocalDateTime>(time.toString())

        val type = SalesRecordTypeEnum.PURCHASE
        val reason = null
        try {
            salesRecordDao.insert(userId, time, type, reason, invoiceId)
        } catch (e: DuplicateKeyException) {
            e.printStackTrace()
            throw InsertionFailedException(e.mostSpecificCause.message)
        } catch (e: DataIntegrityViolationException) {
            e.printStackTrace()
            throw InsertionFailedException(e.mostSpecificCause.message)
        }
    }

    @Transactional
    override fun getSalesRecordByPk(userId: Long, time: Long): SalesRecord {
        @Suppress("NAME_SHADOWING")
        val time = ParseUtil.parseAs<LocalDateTime>(time.toString())
        val salesRecordHelper = salesRecordDao.queryByPk(userId, time) ?:
                throw NoItemFoundException()
        @Suppress("UnnecessaryVariable")
        val salesRecord = if (salesRecordHelper.type == SalesRecordTypeEnum.PURCHASE) {
            val invoiceHelper = invoiceService.getInvoiceByPk(salesRecordHelper.invoiceId)
            SalesRecord(salesRecordHelper, invoiceHelper)
        } else {
            val inventoryList = salesRecordInventoryDao.queryInvoiceInventoryList(userId, time)
            SalesRecord(salesRecordHelper, inventoryList)
        }
        return salesRecord
    }

    @Transactional
    override fun getSalesRecordsWithConstraints(
            userId: Long?,
            minTime: Long?, maxTime: Long?,
            type: String?,
            invoiceId: Long?
    ): List<SalesRecord> {
        @Suppress("NAME_SHADOWING")
        val minTime = ParseUtil.parseAsOrNull<LocalDateTime>(minTime?.toString())
        @Suppress("NAME_SHADOWING")
        val maxTime = ParseUtil.parseAsOrNull<LocalDateTime>(maxTime?.toString())
        @Suppress("NAME_SHADOWING")
        val type = parseType(type)

        // Get a list of sales record helpers (they do not contain differences)
        val salesRecordHelpers = salesRecordDao.queryWithConstraints(userId, minTime, maxTime, type, invoiceId)
        // Assemble sales records with their corresponding invoice helpers
        @Suppress("UnnecessaryVariable")
        val salesRecords = salesRecordHelpers.map {
            if (it.type == SalesRecordTypeEnum.PURCHASE) {
                // Get invoice helpers by the invoice IDs in the sales record helpers
                // Exceptions are thrown during this process if there are any
                val invoiceHelper = invoiceService.getInvoiceByPk(it.invoiceId)
                SalesRecord(it, invoiceHelper)
            } else {
                // Get the invoice inventory list by the primary keys of the sales record helpers
                val inventoryList = salesRecordInventoryDao.queryInvoiceInventoryList(it.userId, it.time)
                SalesRecord(it, inventoryList)
            }
        }
        return salesRecords
    }

    private fun parseType(type: String?): SalesRecordTypeEnum? {
        if (type == null) return null
        return SalesRecordTypeEnum.fromDisplayName(type) ?:
                throw IncorrectItemTypeException()
    }
}