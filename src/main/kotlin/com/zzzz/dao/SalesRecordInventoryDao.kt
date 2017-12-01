package com.zzzz.dao

import com.zzzz.model.InvoiceInventory
import org.apache.ibatis.annotations.Param
import java.time.LocalDateTime

interface SalesRecordInventoryDao {
    fun insert(
            @Param("userId") userId: Long,
            @Param("time") time: LocalDateTime,
            @Param("inventoryList") inventoryList: List<InvoiceInventory>
    ): Int

    // Return type undefined
//    fun queryWithConstraints(
//            @PathVariable("userId") userId: Long? = null,
//            @PathVariable("time") time: LocalDateTime? = null,
//            @PathVariable("barcode") barcode: Long? = null,
//            @PathVariable("minProductionDate") minProductionDate: LocalDate? = null,
//            @PathVariable("maxProductionDate") maxProductionDate: LocalDate? = null,
//            @PathVariable("qty") qty: Short? = null
//    ): List<InvoiceInventory>

    fun queryInvoiceInventoryList(
            @Param("userId") userId: Long,
            @Param("time") time: LocalDateTime
    ): List<InvoiceInventory>
}