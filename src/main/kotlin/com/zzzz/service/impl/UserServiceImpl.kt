package com.zzzz.service.impl

import com.zzzz.dao.UserDao
import com.zzzz.enum.UserTypeEnum
import com.zzzz.exception.IncorrectItemTypeException
import com.zzzz.exception.InsertionFailedException
import com.zzzz.exception.NoItemFoundException
import com.zzzz.exception.UpdateFailedException
import com.zzzz.model.User
import com.zzzz.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Service

@Service
class UserServiceImpl : UserService {
    @Suppress("SpringKotlinAutowiring")
    @Autowired
    private lateinit var userDao: UserDao

    override fun insert(username: String, password: String, type: String) {
        @Suppress("NAME_SHADOWING")
        val type = parseType(type)
        val rowsAffected = userDao.insert(username, password, type)
        if (rowsAffected == 0)
            throw InsertionFailedException()
    }

    override fun update(targetId: Long, username: String?, password: String?, type: String?) {
        @Suppress("NAME_SHADOWING")
        val type = if (type != null)
            parseType(type)
        else
            null
        try {
            userDao.update(targetId, username, password, type)
        } catch (e: DuplicateKeyException) {
            throw UpdateFailedException(e.message)
        }
    }

    override fun getUserByPk(id: Long): User {
        val result = userDao.queryByPk(id)
        return result?: throw NoItemFoundException()
    }

    override fun getUserByUsername(username: String): User {
        val result = userDao.queryByUsername(username)
        return result?: throw NoItemFoundException()
    }

    override fun getUsersByType(type: String): List<User> {
        @Suppress("NAME_SHADOWING")
        val type = parseType(type)
        @Suppress("UnnecessaryVariable")
        val result = userDao.queryByType(type)
        return result
    }

    private fun parseType(type: String): UserTypeEnum {
        return UserTypeEnum.fromDisplayName(type) ?:
                throw IncorrectItemTypeException()
    }
}