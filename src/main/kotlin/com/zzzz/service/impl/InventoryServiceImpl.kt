package com.zzzz.service.impl

import com.zzzz.dao.InventoryDao
import com.zzzz.exception.InsertionFailedException
import com.zzzz.exception.NoItemFoundException
import com.zzzz.exception.UpdateFailedException
import com.zzzz.model.Inventory
import com.zzzz.service.InventoryService
import com.zzzz.util.ParseUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class InventoryServiceImpl : InventoryService {
    @Suppress("SpringKotlinAutowiring")
    @Autowired
    private lateinit var inventoryDao: InventoryDao

    override fun insert(
            barcode: Long,
            productionDate: Long,
            manufacturer: String,
            qtyInStock: Short,
            qtyOnShelf: Short) {
        @Suppress("NAME_SHADOWING")
        val productionDate = ParseUtil.parseAs<LocalDate>(productionDate.toString())
        val rowsAffected = inventoryDao.insert(barcode, productionDate, manufacturer, qtyInStock, qtyOnShelf)
        if (rowsAffected == 0)
            throw InsertionFailedException()
    }

    override fun update(
            targetBarcode: Long,
            targetProductionDate: Long,
            manufacturer: String?,
            qtyInStock: Short?,
            qtyOnShelf: Short?) {
        @Suppress("NAME_SHADOWING")
        val targetProductionDate = ParseUtil.parseAs<LocalDate>(targetProductionDate.toString())
        val rowsAffected = inventoryDao.update(targetBarcode, targetProductionDate, manufacturer, qtyInStock, qtyOnShelf)
        if (rowsAffected == 0)
            throw UpdateFailedException()
    }

    override fun getInventoryByPk(barcode: Long, productionDate: Long): Inventory {
        @Suppress("NAME_SHADOWING")
        val productionDate = ParseUtil.parseAs<LocalDate>(productionDate.toString())
        val result = inventoryDao.queryByPk(barcode, productionDate)
        return result?:
            throw NoItemFoundException()
    }

    override fun getInventoriesWithConstraints(
            barcode: Long?,
            minProductionDate: Long?,
            maxProductionDate: Long?,
            manufacturerContaining: String?,
            minQtyInStock: Short?,
            maxQtyInStock: Short?,
            minQtyOnShelf: Short?,
            maxQtyOnShelf: Short?
    ): List<Inventory> {
        @Suppress("NAME_SHADOWING")
        val minProductionDate = ParseUtil.parseAsOrNull<LocalDate>(minProductionDate?.toString())
        @Suppress("NAME_SHADOWING")
        val maxProductionDate = ParseUtil.parseAsOrNull<LocalDate>(maxProductionDate?.toString())
        @Suppress("UnnecessaryVariable")
        val result = inventoryDao.queryWithConstraints(barcode, minProductionDate, maxProductionDate, manufacturerContaining, minQtyInStock, maxQtyInStock, minQtyOnShelf, maxQtyOnShelf)
        return result
    }

}