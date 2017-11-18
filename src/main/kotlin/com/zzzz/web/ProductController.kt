package com.zzzz.web

import com.zzzz.dto.ExecutionResult
import com.zzzz.model.Product
import com.zzzz.service.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@Controller
@RequestMapping("/api/v1/product")
class ProductController {
    @Autowired
    private lateinit var productService: ProductService

    @RequestMapping(value = "/{barcode}/detail",
            method = arrayOf(RequestMethod.GET),
            produces = arrayOf("application/json;charset=UTF-8"))
    @ResponseBody
    fun detail(@PathVariable barcode: Long): ExecutionResult<Product> {
        val result: ExecutionResult<Product>
        result = try {
            val product = productService.getProductByBarcode(barcode)
            if (product == null) {
                ExecutionResult(false, "No product is found.")
            } else {
                ExecutionResult(product)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ExecutionResult(e)
        }
        return result
    }

    @RequestMapping(value = "/list",
            method = arrayOf(RequestMethod.GET),
            produces = arrayOf("application/json;charset=UTF-8"))
    @ResponseBody
    fun list(@RequestParam(required = false) barcode: Long?,
             @RequestParam(required = false) nameContaining: String?,
             @RequestParam(required = false) minPrice: BigDecimal?,
             @RequestParam(required = false) maxPrice: BigDecimal?,
             @RequestParam(required = false) minShelfLife: Int?,
             @RequestParam(required = false) maxShelfLife: Int?,
             @RequestParam(required = false) isRefundable: Boolean?
    ): ExecutionResult<List<Product>> {
        val result: ExecutionResult<List<Product>>
        result = try {
            val products = productService.getProductsWithConstraints(
                    barcode, nameContaining,
                    minPrice, maxPrice,
                    minShelfLife, maxShelfLife,
                    isRefundable
            )
            ExecutionResult(products)
        } catch (e: Exception) {
            ExecutionResult(e)
        }
        return result
    }
}