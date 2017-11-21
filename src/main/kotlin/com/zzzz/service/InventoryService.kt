package com.zzzz.service

import com.zzzz.exception.InsertionFailedException
import com.zzzz.exception.NoItemFoundException
import com.zzzz.exception.UpdateFailedException
import com.zzzz.model.Inventory
import java.time.LocalDate

interface InventoryService {
    @Throws(InsertionFailedException::class)
    fun insert(barcode: Long,
               productionDate: Long,
               manufacturer: String,
               qtyInStock: Short,
               qtyOnShelf: Short)

    // TODO Exception type unknown
    @Throws(UpdateFailedException::class)
    fun update(targetBarcode: Long,
               targetProductionDate: Long,
               manufacturer: String?,
               qtyInStock: Short?,
               qtyOnShelf: Short?)

    @Throws(NoItemFoundException::class)
    fun getInventoryByPk(barcode: Long, productionDate: Long): Inventory

    fun getInventoriesWithConstraints(
            barcode: Long?,
            minProductionDate: Long?,
            maxProductionDate: Long?,
            manufacturerContaining: String?,
            minQtyInStock: Short?,
            maxQtyInStock: Short?,
            minQtyOnShelf: Short?,
            maxQtyOnShelf: Short?
    ): List<Inventory>
}