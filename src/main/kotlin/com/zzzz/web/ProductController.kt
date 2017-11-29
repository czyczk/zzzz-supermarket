package com.zzzz.web

import com.zzzz.dto.ExecutionResult
import com.zzzz.exception.InsertionFailedException
import com.zzzz.exception.NoItemFoundException
import com.zzzz.exception.UpdateFailedException
import com.zzzz.model.Product
import com.zzzz.service.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RestController
@RequestMapping("/api/v1/product")
class ProductController {
    @Autowired
    private lateinit var productService: ProductService

    @RequestMapping(value = "/creation",
            method = arrayOf(RequestMethod.POST),
            produces = arrayOf("application/json;charset=UTF-8"))
    fun creation(@RequestBody params: Product): ExecutionResult<Any> {
        val result: ExecutionResult<Any>
        result = try {
            productService.insert(params.barcode, params.name, params.price, params.shelfLife, params.isRefundable)
            ExecutionResult(true)
        } catch (e: InsertionFailedException) {
            e.printStackTrace()
            ExecutionResult(e)
        }
        return result
    }

    @RequestMapping(value = "/update",
            method = arrayOf(RequestMethod.POST),
            produces = arrayOf("application/json;charset=UTF-8"))
    fun update(@RequestBody params: ProductUpdateHelper): ExecutionResult<Any> {
        val result: ExecutionResult<Any>
        result = try {
            productService.update(params.targetBarcode, params.barcode, params.name, params.price, params.shelfLife, params.isRefundable)
            ExecutionResult(true)
        } catch (e: UpdateFailedException) {
            // TODO Exception type unknown
            e.printStackTrace()
            ExecutionResult(e)
        }
        return result
    }

    @RequestMapping(value = "/{barcode}/detail",
            method = arrayOf(RequestMethod.GET),
            produces = arrayOf("application/json;charset=UTF-8"))
    fun detail(@PathVariable barcode: Long): ExecutionResult<Product> {
        val result: ExecutionResult<Product>
        result = try {
            val product = productService.getProductByPk(barcode)
            ExecutionResult(product)
        } catch (e: NoItemFoundException) {
            ExecutionResult(e)
        }
        return result
    }

    @RequestMapping(value = "/list",
            method = arrayOf(RequestMethod.GET),
            produces = arrayOf("application/json;charset=UTF-8"))
    fun list(@RequestParam(required = false) barcode: Long?,
             @RequestParam(required = false) nameContaining: String?,
             @RequestParam(required = false) minPrice: BigDecimal?,
             @RequestParam(required = false) maxPrice: BigDecimal?,
             @RequestParam(required = false) minShelfLife: Int?,
             @RequestParam(required = false) maxShelfLife: Int?,
             @RequestParam(required = false) isRefundable: Boolean?
    ): ExecutionResult<List<Product>> {
        val result: ExecutionResult<List<Product>>
        val products = productService.getProductsWithConstraints(
                barcode, nameContaining,
                minPrice, maxPrice,
                minShelfLife, maxShelfLife,
                isRefundable
        )
        result = ExecutionResult(products)
        return result
    }

    class ProductUpdateHelper {
        var targetBarcode: Long = 0
        var barcode: Long? = null
        var name: String? = null
        var price: BigDecimal? = null
        var shelfLife: Int? = null
        var isRefundable: Boolean? = null
    }
}