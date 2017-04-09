package part

import java.time.LocalDate
import java.time.format.DateTimeFormatter

/** A catalog part.
  *
  * @param brand the part's brand, must be 1-12 characters
  * @param sku the part's sku, must be 1-16 chracters, must not contain whitespace characters
  * @param qty the part's quantity, must be greater than or equal to zero
  * @param releaseDate the part's release date, must be before or equal to endOfLifeDate
  * @param endOfLifeDate the part's end of life date, must be equal to or after releaseDate
  */
case class Part(brand: String, sku: String, qty: Int, releaseDate: LocalDate, endOfLifeDate: LocalDate) {

  require(brand != null && !brand.trim.isEmpty && brand.length <= 12,
    "brand must be 1 to 12 characters")

  require(sku != null && !sku.trim.isEmpty && sku == sku.replaceAll("\\s","") && sku.length <= 16,
    "sku must be 1 to 16 non-whitespace characters")

  require(qty >= 0,
    "qty must be greater than or equal to 0")

  require(releaseDate.isBefore(endOfLifeDate) || releaseDate.isEqual(endOfLifeDate),
    "releaseDate must be before or equal to endOfLifeDate")

  /** Returns a 70-character fixed-width string representation of Part data. */
  lazy val toFixedWidth: String = {

    // Pattern for day-of-week year-of-era month day-of-month (ex. "Sat 2017-04-08")
    val PartDatePattern = "EE yyyy-MM-dd"

    val formattedReleaseDate = releaseDate.format(DateTimeFormatter.ofPattern(PartDatePattern))
    val formattedEndOfLifeDate = endOfLifeDate.format(DateTimeFormatter.ofPattern(PartDatePattern))

    val sb = StringBuilder.newBuilder

    sb ++= f"$brand%12s"
    sb ++= f"$sku%16s".replaceAllLiterally(" ", "_")
    sb ++= f"$qty%010d"
    sb ++= f"$formattedReleaseDate%16s"
    sb ++= f"$formattedEndOfLifeDate%16s"

    sb.toString
  }
}