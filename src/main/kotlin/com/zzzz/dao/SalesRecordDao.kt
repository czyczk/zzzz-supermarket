package com.zzzz.dao

import com.zzzz.enum.SalesRecordTypeEnum
import com.zzzz.model.helper.SalesRecordHelper
import org.apache.ibatis.annotations.Param
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.DuplicateKeyException
import java.time.LocalDateTime

interface SalesRecordDao {
    @Throws(DuplicateKeyException::class, DataIntegrityViolationException::class)
    fun insert(
            @Param("userId") userId: Long,
            @Param("time") time: LocalDateTime,
            @Param("type") type: SalesRecordTypeEnum,
            @Param("reason") reason: String?,
            @Param("invoiceId") invoiceId: Long
    ): Int

    fun delete(
            @Param("userId") userId: Long,
            @Param("time") time: LocalDateTime
    ): Int

    fun queryByPk(
            @Param("userId") userId: Long,
            @Param("time") time: LocalDateTime
    ): SalesRecordHelper?

    fun queryWithConstraints(
            @Param("userId") userId: Long?,
            @Param("minTime") minTime: LocalDateTime?,
            @Param("maxTime") maxTime: LocalDateTime?,
            @Param("type") type: SalesRecordTypeEnum?,
            @Param("invoiceId") invoiceId: Long?
    ): List<SalesRecordHelper>
}