package com.zzzz.service

import com.zzzz.model.Product
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ProductServiceTest : ServiceTestBase() {
    @Suppress("SpringKotlinAutowiring")
    @Autowired
    private lateinit var productService: ProductService

    @Test
    fun testQueryByBarcode() {
        val barcode = "6903244675147"
        val product = productService.getProductByBarcode(barcode)
        assertTrue(product != null)
        product as Product
        assertEquals("心相印茶语 丝享 390张", product.name)
    }

    @Test
    fun testQueryWithConstraints() {
        val nameContaining = "豆本豆"
        val products = productService.getProductsWithConstraints(
                "", nameContaining,
                "", "",
                "", "",
                "")
        assertEquals(2, products.count())
    }
}