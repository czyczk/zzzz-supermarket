package com.zzzz.service.impl

import com.zzzz.dao.ProductDao
import com.zzzz.model.Product
import com.zzzz.service.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class ProductServiceImpl : ProductService {
    @Suppress("SpringKotlinAutowiring")
    @Autowired
    private lateinit var productDao: ProductDao

    @Throws(NumberFormatException::class, IllegalArgumentException::class)
    override fun getProductByBarcode(barcode: Long): Product? {
        // Access the DAO
        @Suppress("UnnecessaryVariable")
        val result = productDao.queryByBarcode(barcode)
        return result
    }

    @Throws(NumberFormatException::class)
    override fun getProductsWithConstraints(
            barcode: Long?,
            nameContaining: String?,
            minPrice: BigDecimal?,
            maxPrice: BigDecimal?,
            minShelfLife: Int?,
            maxShelfLife: Int?,
            isRefundable: Boolean?
    ): List<Product> {
        // Access the DAO
        @Suppress("UnnecessaryVariable")
        val result = productDao.queryWithConstraints(barcode, nameContaining,
                minPrice, maxPrice,
                minShelfLife, maxShelfLife,
                isRefundable)
        return result
    }
}