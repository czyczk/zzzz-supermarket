package com.zzzz.enum

enum class SalesRecordTypeEnum(val index: Int, val displayName: String) {
    PURCHASE(0, "Purchase"),
    EXCHANGE(1, "Exchange"),
    REFUND(2, "Refund");

    companion object {
        @JvmStatic
        fun fromIndex(index: Int): SalesRecordTypeEnum? {
            return values().firstOrNull { it.index == index }
        }

        @JvmStatic
        fun fromDisplayName(displayName: String): SalesRecordTypeEnum? {
            return values().firstOrNull { it.displayName == displayName }
        }
    }
}