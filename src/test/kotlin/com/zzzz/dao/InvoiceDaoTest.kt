package com.zzzz.dao

import com.zzzz.model.Invoice
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

class InvoiceDaoTest : DaoUnitTestBase() {
    @Suppress("SpringKotlinAutowiring")
    @Autowired
    private lateinit var invoiceDao: InvoiceDao

    @Suppress("SpringKotlinAutowiring")
    @Autowired
    private lateinit var invoiceInventoryDao: InvoiceInventoryDao

    @Test
    fun testQueryById() {
        val id = 1L
        val invoiceHelper = invoiceDao.queryById(id)
        println(invoiceHelper)
        val inventories = invoiceInventoryDao.queryById(id)
        println(inventories)
        val invoice = Invoice(invoiceHelper!!, inventories)
        println(invoice)
    }

    @Test
    fun testSelectLastInsertId() {
        val lastId = invoiceDao.selectLastInsertId()
        println("LastId = $lastId")
    }
}