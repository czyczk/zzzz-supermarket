package com.zzzz.web

import com.zzzz.dto.ExecutionResult
import com.zzzz.enum.UserTypeEnum
import com.zzzz.exception.IncorrectItemTypeException
import com.zzzz.exception.InsertionFailedException
import com.zzzz.exception.NoItemFoundException
import com.zzzz.exception.UpdateFailedException
import com.zzzz.model.User
import com.zzzz.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/api/v1/user")
class UserController {
    @Autowired
    private lateinit var userService: UserService

    @RequestMapping(value = "/creation",
            method = arrayOf(RequestMethod.POST),
            produces = arrayOf("application/json;charset=UTF-8"))
    @ResponseBody
    fun creation(@RequestParam username: String,
                 @RequestParam password: String,
                 @RequestParam type: String): ExecutionResult<Any> {
        val result: ExecutionResult<Any>
        result = try {
            userService.insert(username, password, type)
            ExecutionResult(true)
        } catch (e: InsertionFailedException) {
            e.printStackTrace()
            ExecutionResult(e)
        } catch (e: IncorrectItemTypeException) {
            ExecutionResult(e)
        }
        return result
    }

    @RequestMapping(value = "/update",
            method = arrayOf(RequestMethod.POST),
            produces = arrayOf("application/json;charset=UTF-8"))
    @ResponseBody
    fun update(@RequestParam targetId: Long,
               @RequestParam(required = false) username: String?,
               @RequestParam(required = false) password: String?,
               @RequestParam(required = false) type: String?): ExecutionResult<Any> {
        val result: ExecutionResult<Any>
        result = try {
            userService.update(targetId, username, password, type)
            ExecutionResult(true)
        } catch (e: UpdateFailedException) {
            e.printStackTrace()
            ExecutionResult(e)
        } catch (e: IncorrectItemTypeException) {
            ExecutionResult(e)
        }
        return result
    }

    @RequestMapping(value = "/{id}/detail",
            method = arrayOf(RequestMethod.GET),
            produces = arrayOf("application/json;charset=UTF-8"))
    @ResponseBody
    fun detail(@PathVariable id: Long): ExecutionResult<User> {
        val result: ExecutionResult<User>
        result = try {
            val user = userService.getUserByPk(id)
            ExecutionResult(user)
        } catch (e: NoItemFoundException) {
            ExecutionResult(e)
        }
        return result
    }

    @RequestMapping(value = "/list",
            method = arrayOf(RequestMethod.GET),
            produces = arrayOf("application/json;charset=UTF-8"))
    @ResponseBody
    fun list(@RequestParam type: String): ExecutionResult<List<User>> {
        val result: ExecutionResult<List<User>>
        result = try {
            val list = userService.getUsersByType(type)
            ExecutionResult(list)
        } catch (e: IncorrectItemTypeException) {
            ExecutionResult(e)
        }
        return result
    }
}