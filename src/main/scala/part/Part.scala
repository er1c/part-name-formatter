package part

import java.time.LocalDate
import java.time.format.DateTimeFormatter

case class Part(brand: String, sku: String, qty: Int, releaseDate: LocalDate, endOfLifeDate: LocalDate) {
  // 70 characters
  def toFixedWidth: String = {
    var s = ""

    // Up to 12 characters padded with spaces on the left
    require(brand.length <= 12)
    (0 until (12 - brand.length)).foreach{ i => s += ' ' }
    s += brand

    // Up to 16 characters padded with underscores on the left
    require(sku.length <= 16)
    (0 until (16 - sku.length)).foreach{ i => s += '_' }
    s += sku

    // Up to 10 digits with leading zeros prepended
    (0 until (10 - qty.toString.length)).foreach{ i => s += '0' }
    s += qty.toString

    // Date fields are 16 characters wide and are prepended with spaces
    (0 until (16 - releaseDate.format(DateTimeFormatter.ofPattern("EE YYYY-MM-dd")).length)).foreach{ i => s += ' ' }
    s += releaseDate.format(DateTimeFormatter.ofPattern("EE YYYY-MM-dd"))

    // Date fields are 16 characters wide and are prepended with spaces
    (0 until (16 - endOfLifeDate.format(DateTimeFormatter.ofPattern("EE YYYY-MM-dd")).length)).foreach{ i => s += ' ' }
    s += endOfLifeDate.format(DateTimeFormatter.ofPattern("EE YYYY-MM-dd"))

    s
  }
}