package com.zzzz.web

import com.zzzz.dto.ExecutionResult
import com.zzzz.exception.InsertionFailedException
import com.zzzz.exception.NoItemFoundException
import com.zzzz.model.Inventory
import com.zzzz.model.Invoice
import com.zzzz.model.helper.InvoiceHelper
import com.zzzz.service.InvoiceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@Controller
@RequestMapping("/api/v1/invoice")
class InvoiceController {
    @Autowired
    private lateinit var invoiceService: InvoiceService

    // TODO The way to pass inventory lists unchecked
    @RequestMapping(value = "/creation",
            method = arrayOf(RequestMethod.POST),
            produces = arrayOf("application/json;charset=UTF-8"))
    @ResponseBody
    fun creation(
            @RequestParam time: Long,
            @RequestParam memberId: Long?,
            @RequestParam totalPrice: BigDecimal,
            @RequestParam discountedPrice: BigDecimal?,
            @RequestParam inventoryList: List<Inventory>
    ): ExecutionResult<Any> {
        val result: ExecutionResult<Any>
        result = try {
            invoiceService.insert(time, memberId, totalPrice, discountedPrice, inventoryList)
            ExecutionResult(true)
        } catch (e: InsertionFailedException) {
            e.printStackTrace()
            ExecutionResult(false)
        }
        return result
    }

    @RequestMapping(value = "/{id}/detail",
            method = arrayOf(RequestMethod.GET),
            produces = arrayOf("application/json;charset=UTF-8"))
    @ResponseBody
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
    @ResponseBody
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