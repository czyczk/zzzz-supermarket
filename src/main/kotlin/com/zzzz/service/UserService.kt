package com.zzzz.service

import com.zzzz.exception.IncorrectItemTypeException
import com.zzzz.exception.InsertionFailedException
import com.zzzz.exception.NoItemFoundException
import com.zzzz.exception.UpdateFailedException
import com.zzzz.model.User

interface UserService {
    @Throws(InsertionFailedException::class, IncorrectItemTypeException::class)
    fun insert(
            username: String,
            password: String,
            type: String
    )

    @Throws(UpdateFailedException::class, IncorrectItemTypeException::class)
    fun update(
            targetId: Long,
            username: String?,
            password: String?,
            type: String?
    )

    @Throws(NoItemFoundException::class)
    fun getUserByPk(id: Long): User

    @Throws(NoItemFoundException::class)
    fun getUserByUsername(username: String): User

    @Throws(IncorrectItemTypeException::class)
    fun getUsersByType(type: String): List<User>
}