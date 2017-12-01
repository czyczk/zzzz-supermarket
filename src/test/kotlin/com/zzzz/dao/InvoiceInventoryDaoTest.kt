package com.zzzz.dao

import com.zzzz.model.InvoiceInventory
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.assertEquals

class InvoiceInventoryDaoTest : DaoUnitTestBase() {
    @Suppress("SpringKotlinAutowiring")
    @Autowired
    private lateinit var invoiceInventoryDao: InvoiceInventoryDao

    @Test
    fun testInsert() {
        val invoiceId = 2L

        val ii1 = InvoiceInventory()
        ii1.barcode = 6903244675147L
        ii1.setProductionDate(1495728000000)
        ii1.qty = 2

        val ii2 = InvoiceInventory()
        ii2.barcode = 6920698493332L
        ii2.setProductionDate(1509120000000)
        ii2.qty = 1

        val invoiceInventoryList = listOf<InvoiceInventory>(ii1, ii2)

        val rowsAffected = invoiceInventoryDao.insertInventoryList(invoiceId, invoiceInventoryList)
        assertEquals(2, rowsAffected)
    }
}