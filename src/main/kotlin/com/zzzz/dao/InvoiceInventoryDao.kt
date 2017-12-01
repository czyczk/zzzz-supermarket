package com.zzzz.dao

import com.zzzz.model.InvoiceInventory
import com.zzzz.model.helper.InvoiceInventoryHelper
import org.apache.ibatis.annotations.Param
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.DuplicateKeyException
import java.time.LocalDate

interface InvoiceInventoryDao {
    @Throws(DuplicateKeyException::class, DataIntegrityViolationException::class)
    fun insertInventoryList(
            @Param("invoiceId") invoiceId: Long,
            @Param("invoiceInventoryList") invoiceInventoryList: List<InvoiceInventory>
    ): Int

    fun queryByPk(
            @Param("invoiceId") invoiceId: Long,
            @Param("barcode") barcode: Long,
            @Param("productionDate") productionDate: LocalDate
    ): InvoiceInventoryHelper?

    fun queryById(invoiceId: Long): List<InvoiceInventoryHelper>

    fun delete(@Param("invoiceId") invoiceId: Long,
               @Param("barcode") barcode: Long,
               @Param("productionDate") productionDate: LocalDate): Int
}