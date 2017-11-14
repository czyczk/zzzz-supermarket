package com.zzzz.dao

import com.zzzz.model.Inventory
import com.zzzz.model.helper.InvoiceInventoryHelper
import org.apache.ibatis.annotations.Param
import java.time.LocalDate

interface InvoiceInventoryDao {
    fun insertInventoryList(
            @Param("invoiceId") invoiceId: Long,
            @Param("inventoryList") inventoryList: List<Inventory>
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