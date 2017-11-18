package com.zzzz.service.impl

import com.zzzz.dao.ProductDao
import com.zzzz.model.Product
import com.zzzz.service.ProductService
import com.zzzz.util.ParseUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class ProductServiceImpl : ProductService {
    @Suppress("SpringKotlinAutowiring")
    @Autowired
    private lateinit var productDao: ProductDao

    @Suppress("NAME_SHADOWING")
    @Throws(NumberFormatException::class, IllegalArgumentException::class)
    override fun getProductByBarcode(barcode: String): Product? {
        // Format conversion
        val barcode = ParseUtil.parseAs<Long>(barcode)

        // Access the DAO
        @Suppress("UnnecessaryVariable")
        val result = productDao.queryByBarcode(barcode)
        return result
    }

    @Suppress("NAME_SHADOWING")
    @Throws(NumberFormatException::class)
    override fun getProductsWithConstraints(
            barcode: String,
            nameContaining: String,
            minPrice: String,
            maxPrice: String,
            minShelfLife: String,
            maxShelfLife: String,
            isRefundable: String
    ): List<Product> {
        // Format conversion
        val barcode = ParseUtil.parseAsOrNull<Long>(barcode)
        val minPrice = ParseUtil.parseAsOrNull<BigDecimal>(minPrice)
        val maxPrice = ParseUtil.parseAsOrNull<BigDecimal>(maxPrice)
        val minShelfLife = ParseUtil.parseAsOrNull<Int>(minShelfLife)
        val maxShelfLife = ParseUtil.parseAsOrNull<Int>(maxShelfLife)
        val isRefundable = ParseUtil.parseAsOrNull<Boolean>(isRefundable)

        // Access the DAO
        @Suppress("UnnecessaryVariable")
        val result = productDao.queryWithConstraints(barcode, nameContaining,
                minPrice, maxPrice,
                minShelfLife, maxShelfLife,
                isRefundable)
        return result
    }
}