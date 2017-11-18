package localExperiment

import com.zzzz.util.ParseUtil
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime


fun main(args: Array<String>) {
    val now = "1510987732488"
    val l = ParseUtil.parseAsOrNull<Long>(now)
    println(l)
    val t = ParseUtil.parseAsOrNull<LocalDateTime>(now)
    println(t)
}