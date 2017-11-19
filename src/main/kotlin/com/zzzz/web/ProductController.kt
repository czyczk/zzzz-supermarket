package com.zzzz.web

import com.zzzz.dto.ExecutionResult
import com.zzzz.exception.InsertionFailedException
import com.zzzz.exception.NoItemFoundException
import com.zzzz.exception.UpdateFailedException
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

    @RequestMapping(value = "/creation",
            method = arrayOf(RequestMethod.POST),
            produces = arrayOf("application/json;charset=UTF-8"))
    @ResponseBody
    fun creation(@RequestParam barcode: Long,
                 @RequestParam name: String,
                 @RequestParam price: BigDecimal,
                 @RequestParam shelfLife: Int,
                 @RequestParam isRefundable: Boolean): ExecutionResult<Any> {
        val result: ExecutionResult<Any>
        result = try {
            productService.insert(barcode, name, price, shelfLife, isRefundable)
            ExecutionResult(true)
        } catch (e: InsertionFailedException) {
            ExecutionResult(e)
        }
        return result
    }

    @RequestMapping(value = "/update",
            method = arrayOf(RequestMethod.POST),
            produces = arrayOf("application/json;charset=UTF-8"))
    @ResponseBody
    fun update(@RequestParam targetBarcode: Long,
               @RequestParam(required = false) barcode: Long?,
               @RequestParam(required = false) name: String?,
               @RequestParam(required = false) price: BigDecimal?,
               @RequestParam(required = false) shelfLife: Int?,
               @RequestParam(required = false) isRefundable: Boolean?): ExecutionResult<Any> {
        val result: ExecutionResult<Any>
        result = try {
            productService.update(targetBarcode, barcode, name, price, shelfLife, isRefundable)
            ExecutionResult(true)
        } catch (e: UpdateFailedException) {
            // TODO Exception type unknown
            ExecutionResult(e)
        }
        return result
    }

    @RequestMapping(value = "/{barcode}/detail",
            method = arrayOf(RequestMethod.GET),
            produces = arrayOf("application/json;charset=UTF-8"))
    @ResponseBody
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
        val products = productService.getProductsWithConstraints(
                barcode, nameContaining,
                minPrice, maxPrice,
                minShelfLife, maxShelfLife,
                isRefundable
        )
        result = ExecutionResult(products)
        return result
    }
}