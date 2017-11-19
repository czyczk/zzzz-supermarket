package com.zzzz.enum

enum class UserTypeEnum(val index: Int, val displayName: String) {
    ADMINISTRATOR(0, "administrator"),
    CLERK(1, "clerk"),
    CS_WORKER(2, "cs_worker");

    companion object {
        @JvmStatic
        fun fromIndex(index: Int): UserTypeEnum? {
            return values().firstOrNull { it.index == index }
        }

        @JvmStatic
        fun fromDisplayName(displayName: String): UserTypeEnum? {
            return values().firstOrNull { it.displayName.equals(displayName, true) }
        }
    }
}