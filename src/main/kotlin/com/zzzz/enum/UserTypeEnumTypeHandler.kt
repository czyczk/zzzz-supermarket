package com.zzzz.enum

import org.apache.ibatis.type.BaseTypeHandler
import org.apache.ibatis.type.JdbcType
import java.sql.CallableStatement
import java.sql.PreparedStatement
import java.sql.ResultSet

class UserTypeEnumTypeHandler : BaseTypeHandler<UserTypeEnum>() {
    override fun getNullableResult(cs: CallableStatement, columnIndex: Int): UserTypeEnum? {
        val displayName = cs.getString(columnIndex)
        return UserTypeEnum.fromDisplayName(displayName)
    }

    override fun getNullableResult(rs: ResultSet, columnName: String?): UserTypeEnum? {
        val displayName = rs.getString(columnName)
        return UserTypeEnum.fromDisplayName(displayName)
    }

    override fun getNullableResult(rs: ResultSet, columnIndex: Int): UserTypeEnum? {
        val displayName = rs.getString(columnIndex)
        return UserTypeEnum.fromDisplayName(displayName)
    }

    override fun setNonNullParameter(ps: PreparedStatement, i: Int, parameter: UserTypeEnum, jdbcType: JdbcType?) {
        ps.setString(i, parameter.name)
    }

}