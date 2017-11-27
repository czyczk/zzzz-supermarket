package com.zzzz.model.serializer

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import com.zzzz.util.DateUtil
import java.time.LocalDate

class LocalDateToMilliSerializer : StdSerializer<LocalDate>(LocalDate::class.java) {
    override fun serialize(value: LocalDate?, gen: JsonGenerator, provider: SerializerProvider?) {
        if (value == null)
            gen.writeNull()
        else
            gen.writeNumber(DateUtil.toMilli(value.atStartOfDay()))
    }
}