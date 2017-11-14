package com.zzzz.dao

import com.zzzz.model.Inventory
import org.apache.ibatis.annotations.Param
import java.time.LocalDate

interface InventoryDao {
    fun insert(
            @Param("barcode") barcode: Long,
            @Param("productionDate") productionDate: LocalDate,
            @Param("manufacturer") manufacturer: String,
            @Param("qtyInStock") qtyInStock: Short,
            @Param("qtyOnShelf") qtyOnShelf: Short
    ): Int

    fun update(
            @Param("targetBarcode") targetBarcode: Long,
            @Param("targetProductionDate") targetProductionDate: LocalDate,
            @Param("manufacturer") manufacturer: String?,
            @Param("qtyInStock") qtyInStock: Short?,
            @Param("qtyOnShelf") qtyOnShelf: Short?
    ): Int

    fun queryByBarcodeAndProductionDate(
            @Param("barcode") barcode: Long,
            @Param("productionDate") produtionDate: LocalDate
    ): Inventory?

    fun queryWithConstraints(
            @Param("barcode") barcode: Long?,
            @Param("minProductionDate") minProductionDate: LocalDate?,
            @Param("maxProductionDate") maxProductionDate: LocalDate?,
            @Param("manufacturerContaining") manufacturerContaining: String?,
            @Param("minQtyInStock") minQtyInStock: Short?,
            @Param("maxQtyInStock") maxQtyInStock: Short?,
            @Param("minQtyOnShelf") minQtyOnShelf: Short?,
            @Param("maxQtyOnShelf") maxQtyOnShelf: Short?
    ): List<Inventory>

    fun delete(
            @Param("barcode") barcode: Long,
            @Param("productionDate") productionDate: LocalDate
    ): Int
}