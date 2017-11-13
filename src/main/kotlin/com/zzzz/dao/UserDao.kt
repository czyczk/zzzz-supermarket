package com.zzzz.dao

import com.zzzz.enum.UserTypeEnum
import com.zzzz.model.User
import org.apache.ibatis.annotations.Param

interface UserDao {
    fun queryById(id: Long): User?

    fun queryByUsername(username: String): User?

    fun queryByType(type: UserTypeEnum): List<User>

    fun insert(@Param("username") username: String,
               @Param("password") password: String,
               @Param("type") type: UserTypeEnum): Int
}