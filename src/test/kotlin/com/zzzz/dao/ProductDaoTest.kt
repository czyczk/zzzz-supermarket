package com.zzzz.dao

import com.zzzz.model.Product
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import java.math.BigDecimal
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ProductDaoTest : DaoUnitTestBase() {
    @Suppress("SpringKotlinAutowiring")
    @Autowired
    private lateinit var productDao: ProductDao

    @Test
    fun testQueryByBarcode() {
        val barcode = 6903244675147L
        val p = productDao.queryByBarcode(barcode)

        assertTrue(p != null)
        p as Product

        val name = "心相印茶语 丝享 390张"
        val price = BigDecimal(3)
        val shelfLife = 25920
        val isRefundable = true

        assertEquals(barcode, p.barcode)
        assertEquals(name, p.name)
        assertEquals(price, p.price)
        assertEquals(shelfLife, p.shelfLife)
        assertEquals(isRefundable, p.isRefundable)
    }

    @Test
    fun testQueryWithConstraintsBarcodeAndName() {
        val barcode = 6903244675147L
        val name = "丝享"
        val p = productDao.queryWithConstraints(barcode, name)
        println(p)
    }

    @Test
    fun testInsertPkConflict() {
        val barcode = 6903244675147L
        val name = "心相印茶语 丝享 390张"
        val price = BigDecimal(3)
        val shelfLife = 25920
        val isRefundable = true

        val rAffected = productDao.insert(barcode, name, price, shelfLife, isRefundable)
        assertEquals(0, rAffected)
    }
}