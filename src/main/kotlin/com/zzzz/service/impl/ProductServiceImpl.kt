package com.zzzz.service.impl

import com.zzzz.dao.ProductDao
import com.zzzz.exception.InsertionFailedException
import com.zzzz.exception.NoItemFoundException
import com.zzzz.exception.UpdateFailedException
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

    override fun insert(
            barcode: Long,
            name: String,
            price: BigDecimal,
            shelfLife: Int,
            isRefundable: Boolean
    ) {
        val rowsAffected = productDao.insert(barcode, name, price, shelfLife, isRefundable)
        if (rowsAffected == 0)
            throw InsertionFailedException()
    }

    override fun update(
            targetBarcode: Long,
            barcode: Long?,
            name: String?,
            price: BigDecimal?,
            shelfLife: Int?,
            isRefundable: Boolean?) {
        val rowsAffected = productDao.update(targetBarcode, barcode, name, price, shelfLife, isRefundable)
        if (rowsAffected == 0)
            throw UpdateFailedException()
    }

    override fun getProductByPk(barcode: Long): Product {
        // Access the DAO
        @Suppress("UnnecessaryVariable")
        val result = productDao.queryByPk(barcode)
        return result?: throw NoItemFoundException()
    }

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