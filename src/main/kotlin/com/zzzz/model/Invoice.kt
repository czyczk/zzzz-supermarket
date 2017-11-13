package com.zzzz.model

import java.math.BigDecimal
import java.time.LocalDateTime
import kotlin.properties.Delegates

class Invoice {
    val id: Long by Delegates.notNull<Long>()

    val time: LocalDateTime by Delegates.notNull<LocalDateTime>()

    var memberId: Long? = null
        private set

    val totalPrice: BigDecimal by Delegates.notNull<BigDecimal>()

    var discountedPrice: BigDecimal? = null
        private set
}