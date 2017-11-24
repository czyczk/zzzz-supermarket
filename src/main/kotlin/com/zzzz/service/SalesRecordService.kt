package com.zzzz.service

import com.zzzz.exception.IncorrectItemTypeException
import com.zzzz.exception.InsertionFailedException
import com.zzzz.exception.NoItemFoundException
import com.zzzz.model.Invoice
import com.zzzz.model.SalesRecord

interface SalesRecordService {
    @Throws(InsertionFailedException::class, IncorrectItemTypeException::class)
    fun insertAfterSalesRecord(
            userId: Long,
            time: Long,
            type: String,
            reason: String?,
            invoiceId: Long
    )

    @Throws(InsertionFailedException::class, IncorrectItemTypeException::class)
    fun insertPurchaseRecord(
            userId: Long,
            time: Long,
            invoice: Invoice
    )

    @Throws(NoItemFoundException::class)
    fun getSalesRecordByPk(
            userId: Long,
            time: Long
    ): SalesRecord

    @Throws(IncorrectItemTypeException::class)
    fun getSalesRecordsWithConstraints(
            userId: Long?,
            minTime: Long?, maxTime: Long?,
            type: String?,
            invoiceId: Long?
    ): List<SalesRecord>
}