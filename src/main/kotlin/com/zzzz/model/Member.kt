package com.zzzz.model

import kotlin.properties.Delegates

class Member {
    var id: Long by Delegates.notNull<Long>()

    var name: String by Delegates.notNull<String>()

    var phoneNumber: String by Delegates.notNull<String>()

    var address: String by Delegates.notNull<String>()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Member

        if (id != other.id) return false
        if (name != other.name) return false
        if (phoneNumber != other.phoneNumber) return false
        if (address != other.address) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + phoneNumber.hashCode()
        result = 31 * result + address.hashCode()
        return result
    }

    override fun toString(): String {
        return "M(id=$id, name='$name', phoneNumber='$phoneNumber', address='$address')"
    }
}