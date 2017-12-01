package com.zzzz.web

import com.zzzz.dto.ExecutionResult
import com.zzzz.exception.InsertionFailedException
import com.zzzz.exception.NoItemFoundException
import com.zzzz.model.Invoice
import com.zzzz.model.InvoiceInventory
import com.zzzz.model.helper.InvoiceHelper
import com.zzzz.service.InvoiceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal
import kotlin.properties.Delegates

@RestController
@RequestMapping("/api/v1/invoice")
class InvoiceController {
    @Autowired
    private lateinit var invoiceService: InvoiceService

    @RequestMapping(value = "/creation",
            method = arrayOf(RequestMethod.POST),
            produces = arrayOf("application/json;charset=UTF-8"))
    fun creation(@RequestBody params: InvoiceCreationHelper): ExecutionResult<Long> {
        val result: ExecutionResult<Long>
        result = try {
            val invoiceId = invoiceService.insert(params.time, params.memberId, params.totalPrice, params.discountedPrice, params.inventoryList)
            ExecutionResult(invoiceId)
        } catch (e: InsertionFailedException) {
            e.printStackTrace()
            ExecutionResult(false)
        }
        return result
    }

    class InvoiceCreationHelper {
        var time: Long by Delegates.notNull()
        var memberId: Long? = null
        lateinit var totalPrice: BigDecimal
        var discountedPrice: BigDecimal? = null
        var inventoryList: List<InvoiceInventory> by Delegates.notNull()
    }

    @RequestMapping(value = "/{id}/detail",
            method = arrayOf(RequestMethod.GET),
            produces = arrayOf("application/json;charset=UTF-8"))
    fun detail(@PathVariable id: Long): ExecutionResult<Invoice> {
        val result: ExecutionResult<Invoice>
        result = try {
            val invoice = invoiceService.getInvoiceWithListByPk(id)
            ExecutionResult(invoice)
        } catch (e: NoItemFoundException) {
            ExecutionResult(e)
        }
        return result
    }

    @RequestMapping(value = "/list",
            method = arrayOf(RequestMethod.GET),
            produces = arrayOf("application/json;charset=UTF-8"))
    fun list(
            @RequestParam id: Long?,
            @RequestParam minTime: Long?, @RequestParam maxTime: Long?,
            @RequestParam memberId: Long?,
            @RequestParam minTotalPrice: BigDecimal?, @RequestParam maxTotalPrice: BigDecimal?,
            @RequestParam minDiscountedPrice: BigDecimal?, @RequestParam maxDiscountedPrice: BigDecimal?
    ): ExecutionResult<List<InvoiceHelper>> {
        val result: ExecutionResult<List<InvoiceHelper>>
        val invoiceHelper = invoiceService.getInvoicesWithConstraints(id, minTime, maxTime, memberId, minTotalPrice, maxTotalPrice, minDiscountedPrice, maxDiscountedPrice)
        result = ExecutionResult(invoiceHelper)
        return result
    }

}