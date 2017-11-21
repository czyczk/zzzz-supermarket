package com.zzzz.web

import com.zzzz.dto.ExecutionResult
import com.zzzz.exception.InsertionFailedException
import com.zzzz.exception.NoItemFoundException
import com.zzzz.exception.UpdateFailedException
import com.zzzz.model.Inventory
import com.zzzz.service.InventoryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/api/v1/inventory")
class InventoryController {
    @Autowired
    private lateinit var inventoryService: InventoryService

    @RequestMapping(value = "/creation",
            method = arrayOf(RequestMethod.POST),
            produces = arrayOf("application/json;charset=UTF-8"))
    @ResponseBody
    fun creation(@RequestParam barcode: Long,
                 @RequestParam productionDate: Long,
                 @RequestParam manufacturer: String,
                 @RequestParam qtyInStock: Short,
                 @RequestParam qtyOnShelf: Short): ExecutionResult<Any> {
        val result: ExecutionResult<Any>
        result = try {
            inventoryService.insert(barcode, productionDate, manufacturer, qtyInStock, qtyOnShelf)
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
    @ResponseBody
    fun update(@RequestParam targetBarcode: Long,
               @RequestParam targetProductionDate: Long,
               @RequestParam(required = false) manufacturer: String?,
               @RequestParam(required = false) qtyInStock: Short?,
               @RequestParam(required = false) qtyOnShelf: Short?): ExecutionResult<Any> {
        val result: ExecutionResult<Any>
        result = try {
            inventoryService.update(targetBarcode, targetProductionDate, manufacturer, qtyInStock, qtyOnShelf)
            ExecutionResult(true)
        } catch (e: UpdateFailedException) {
            e.printStackTrace()
            ExecutionResult(e)
        }
        return result
    }

    @RequestMapping(value = "/{barcode}/{productionDate}/detail",
            method = arrayOf(RequestMethod.GET),
            produces = arrayOf("application/json;charset=UTF-8"))
    @ResponseBody
    fun detail(@PathVariable barcode: Long,
               @PathVariable productionDate: Long): ExecutionResult<Inventory> {
        val result: ExecutionResult<Inventory>
        result = try {
            val inventory = inventoryService.getInventoryByPk(barcode, productionDate)
            ExecutionResult(inventory)
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
             @RequestParam(required = false) minProductionDate: Long?,
             @RequestParam(required = false) maxProductionDate: Long?,
             @RequestParam(required = false) manufacturerContaining: String?,
             @RequestParam(required = false) minQtyInStock: Short?,
             @RequestParam(required = false) maxQtyInStock: Short?,
             @RequestParam(required = false) minQtyOnShelf: Short?,
             @RequestParam(required = false) maxQtyOnShelf: Short?): ExecutionResult<List<Inventory>> {
        val result: ExecutionResult<List<Inventory>>
        val inventories = inventoryService.getInventoriesWithConstraints(barcode, minProductionDate, maxProductionDate, manufacturerContaining, minQtyInStock, maxQtyInStock, minQtyOnShelf, maxQtyOnShelf)
        result = ExecutionResult(inventories)
        return result
    }
}