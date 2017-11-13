package com.zzzz.model

import kotlin.properties.Delegates

class Member {
    val id: Long by Delegates.notNull<Long>()

    val name: String by Delegates.notNull<String>()

    val phoneNumber: String by Delegates.notNull<String>()

    val address: String by Delegates.notNull<String>()
}