package com.zzzz.dao

import com.zzzz.model.Member
import org.apache.ibatis.annotations.Param

interface MemberDao {
    // TODO queryWithConstraints

    fun insert(
            @Param("name") name: String,
            @Param("phoneNumber") phoneNumber: String,
            @Param("address") address: String
    ): Int

    fun update(
            @Param("targetId") targetId: Long,
            @Param("name") name: String?,
            @Param("phoneNumber") phoneNumber: String?,
            @Param("address") address: String?
    ): Int

    fun queryById(id: Long): Member?

    fun delete(id: Long): Int
}