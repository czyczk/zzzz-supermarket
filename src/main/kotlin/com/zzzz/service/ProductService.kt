package com.zzzz.service

import com.zzzz.model.Product

interface ProductService {
    fun getProductByBarcode(barcode: String): Product?

    fun getProductsWithConstraints(
            barcode: String,
            nameContaining: String,
            minPrice: String,
            maxPrice: String,
            minShelfLife: String,
            maxShelfLife: String,
            isRefundable: String): List<Product>
}