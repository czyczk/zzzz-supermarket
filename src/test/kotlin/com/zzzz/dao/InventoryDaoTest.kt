package com.zzzz.dao

import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDate
import kotlin.test.assertEquals

class InventoryDaoTest : DaoUnitTestBase() {
    @Suppress("SpringKotlinAutowiring")
    @Autowired
    private lateinit var inventoryDao: InventoryDao

    @Test
    fun testInsert() {
        val barcode = 1111244671111L
        val productionDate = LocalDate.of(2017, 10, 3)!!
        val manufacturer = "芜湖"
        val qtyInStock: Short = 1
        val qtyOnShelf: Short = 6

        val rowsAffected = inventoryDao.insert(barcode, productionDate, manufacturer, qtyInStock, qtyOnShelf)
        assertEquals(0, rowsAffected)
    }
}