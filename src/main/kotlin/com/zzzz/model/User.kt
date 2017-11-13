package com.zzzz.model

import com.zzzz.enum.UserTypeEnum
import kotlin.properties.Delegates

class User {
    var id: Long by Delegates.notNull<Long>()

    var username: String by Delegates.notNull<String>()

    var password: String by Delegates.notNull<String>()

    var type: UserTypeEnum by Delegates.notNull<UserTypeEnum>()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false
        if (username != other.username) return false
        if (password != other.password) return false
        if (type != other.type) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + username.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + type.hashCode()
        return result
    }

    override fun toString(): String {
        return "User[id=$id,username=$username,password=$password,type=$type]"
    }
}

