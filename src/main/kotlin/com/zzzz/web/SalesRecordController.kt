package com.zzzz.web

import com.zzzz.dto.ExecutionResult
import com.zzzz.exception.IncorrectItemTypeException
import com.zzzz.exception.InsertionFailedException
import com.zzzz.exception.NoItemFoundException
import com.zzzz.model.Invoice
import com.zzzz.model.SalesRecord
import com.zzzz.service.SalesRecordService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/api/v1/salesRecord")
class SalesRecordController {
    @Autowired
    private lateinit var salesRecordService: SalesRecordService

    @RequestMapping(value = "/afterSalesRecord/creation",
            method = arrayOf(RequestMethod.POST),
            produces = arrayOf("application/json;charset=UTF-8"))
    fun afterSalesRecordCreation(
            @RequestParam userId: Long,
            @RequestParam time: Long,
            @RequestParam type: String,
            @RequestParam(required = false) reason: String?,
            @RequestParam invoiceId: Long): ExecutionResult<Any> {
        val result: ExecutionResult<Any>
        result = try {
            salesRecordService.insertAfterSalesRecord(userId, time, type, reason, invoiceId)
            ExecutionResult(true)
        } catch (e: InsertionFailedException) {
            e.printStackTrace()
            ExecutionResult(e)
        } catch (e: IncorrectItemTypeException) {
            ExecutionResult(e)
        }
        return result
    }

    @RequestMapping(value = "/purchaseRecord/creation",
            method = arrayOf(RequestMethod.POST),
            produces = arrayOf("application/json;charset=UTF-8"))
    fun purchaseRecordCreation(
            @RequestParam userId: Long,
            @RequestParam time: Long,
            @RequestParam invoice: Invoice
    ): ExecutionResult<Any> {
        val result: ExecutionResult<Any>
        result = try {
            salesRecordService.insertPurchaseRecord(userId, time, invoice)
            ExecutionResult(true)
        } catch (e: InsertionFailedException) {
            e.printStackTrace()
            ExecutionResult(e)
        } catch (e: IncorrectItemTypeException) {
            ExecutionResult(e)
        }
        return result
    }

    @RequestMapping(value = "/{userId}/{time}/detail",
            method = arrayOf(RequestMethod.GET),
            produces = arrayOf("application/json;charset=UTF-8"))
    fun detail(@PathVariable userId: Long,
               @PathVariable time: Long): ExecutionResult<SalesRecord> {
        val result: ExecutionResult<SalesRecord>
        result = try {
            val salesRecord = salesRecordService.getSalesRecordByPk(userId, time)
            ExecutionResult(salesRecord)
        } catch (e: NoItemFoundException) {
            ExecutionResult(e)
        }
        return result
    }

    @RequestMapping(value = "/list",
            method = arrayOf(RequestMethod.GET),
            produces = arrayOf("application/json;charset=UTF-8"))
    fun list(
            @RequestParam(required = false) userId: Long?,
            @RequestParam(required = false) minTime: Long?, @RequestParam(required = false) maxTime: Long?,
            @RequestParam(required = false) type: String?,
            @RequestParam(required = false) invoiceId: Long?
    ): ExecutionResult<List<SalesRecord>> {
        val result: ExecutionResult<List<SalesRecord>>
        result = try {
            val list = salesRecordService.getSalesRecordsWithConstraints(userId, minTime, maxTime, type, invoiceId)
            ExecutionResult(list)
        } catch (e: IncorrectItemTypeException) {
            ExecutionResult(e)
        }
        return result
    }
}