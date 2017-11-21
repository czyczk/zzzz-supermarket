package localExperiment

import com.zzzz.util.ParseUtil
import java.time.*


fun main(args: Array<String>) {
//    val now = "1510987732488"
//    val l = ParseUtil.parseAsOrNull<Long>(now)
//    println(l)
//    val t = ParseUtil.parseAsOrNull<LocalDateTime>(now)
//    println(t)

    val barcode = 3886719283748L
    val productionDate = LocalDate.of(2004, 3, 2)
    val str = barcode.toString() + String.format("%04d%02d%02d", productionDate.year, productionDate.monthValue, productionDate.dayOfMonth)
    println(str)
}