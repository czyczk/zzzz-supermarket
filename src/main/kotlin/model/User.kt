package model

import enum.UserTypeEnum

class User {
    var id: Long = 0L

    lateinit var username: String

    lateinit var password: String

    lateinit var type: UserTypeEnum
}