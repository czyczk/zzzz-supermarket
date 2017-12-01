package com.zzzz.web

import com.zzzz.dto.ExecutionResult
import com.zzzz.exception.IncorrectItemTypeException
import com.zzzz.exception.InsertionFailedException
import com.zzzz.exception.NoItemFoundException
import com.zzzz.model.InvoiceInventory
import com.zzzz.model.SalesRecord
import com.zzzz.service.SalesRecordService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import kotlin.properties.Delegates

@RestController
@RequestMapping("/api/v1/sales-record")
class SalesRecordController {
    @Autowired
    private lateinit var salesRecordService: SalesRecordService

    @RequestMapping(value = ["/after-sales/creation"],
            method = [(RequestMethod.POST)],
            produces = ["application/json;charset=UTF-8"])
    fun afterSalesRecordCreation(@RequestBody params: AfterSalesRecordCreationHelper): ExecutionResult<Any> {
        val result: ExecutionResult<Any>
        result = try {
            salesRecordService.insertAfterSalesRecord(params.userId, params.time, params.type, params.reason, params.invoiceId, params.inventoryList)
            ExecutionResult(true)
        } catch (e: InsertionFailedException) {
            e.printStackTrace()
            ExecutionResult(e)
        } catch (e: IncorrectItemTypeException) {
            ExecutionResult(e)
        }
        return result
    }

    class AfterSalesRecordCreationHelper {
        var userId: Long by Delegates.notNull()
        var time: Long by Delegates.notNull()
        var type: String by Delegates.notNull()
        var reason: String? = null
        var invoiceId: Long by Delegates.notNull()
        var inventoryList: List<InvoiceInventory> by Delegates.notNull()
    }

    @RequestMapping(value = ["/purchase/creation"],
            method = [(RequestMethod.POST)],
            produces = ["application/json;charset=UTF-8"])
    fun purchaseRecordCreation(@RequestBody params: PurchaseRecordCreationHelper): ExecutionResult<Any> {
        val result: ExecutionResult<Any>
        result = try {
            salesRecordService.insertPurchaseRecord(params.userId, params.time, params.invoiceId)
            ExecutionResult(true)
        } catch (e: InsertionFailedException) {
            e.printStackTrace()
            ExecutionResult(e)
        } catch (e: IncorrectItemTypeException) {
            ExecutionResult(e)
        }
        return result
    }

    class PurchaseRecordCreationHelper {
        var userId: Long by Delegates.notNull()
        var time: Long by Delegates.notNull()
        var invoiceId: Long by Delegates.notNull()
    }

    @RequestMapping(value = ["/{userId}/{time}/detail"],
            method = [(RequestMethod.GET)],
            produces = ["application/json;charset=UTF-8"])
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

    @RequestMapping(value = ["/list"],
            method = [(RequestMethod.GET)],
            produces = ["application/json;charset=UTF-8"])
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