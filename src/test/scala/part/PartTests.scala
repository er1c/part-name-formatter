package part

import java.time.LocalDate
import org.scalatest.{FunSuite, Matchers}

final class BrandTests extends FunSuite with Matchers {

  test("brand can contain whitespace") {
    val part = Part(" aaa bbb ", "abc123", 123, LocalDate.parse("2016-01-15"), LocalDate.parse("2099-12-31"))
    part.brand should equal (" aaa bbb ")
  }

  test("brand must not be null") {
    assertInvalidBrand(null)
  }

  test("brand must not be empty") {
    assertInvalidBrand("")
  }

  test("brand must not be entirely whitespace") {
    assertInvalidBrand(" ")
  }

  test("brand must be 1-12 characters") {
    assertInvalidBrand("1234567890123")
  }

  def assertInvalidBrand(brand:String) = {
    val thrown = intercept[java.lang.IllegalArgumentException] {
      Part(brand, "abc123", 123, LocalDate.parse("2016-01-15"), LocalDate.parse("2099-12-31"))
    }
    thrown.getMessage should equal ("requirement failed: brand must be 1 to 12 characters")
  }
}

final class SkuTests extends FunSuite with Matchers {

  test("sku must not be null") {
    assertInvalidSku(null)
  }

  test("sku must not be empty") {
    assertInvalidSku("")
  }

  test("sku must not contain whitespace") {
    assertInvalidSku("abc 123")
  }

  test("sku must be 1-16 characters") {
    assertInvalidSku("12345678901234567")
  }

  def assertInvalidSku(sku:String) = {
    val thrown = intercept[java.lang.IllegalArgumentException] {
      Part("bosch", sku, 123, LocalDate.parse("2016-01-15"), LocalDate.parse("2099-12-31"))
    }
    thrown.getMessage should equal ("requirement failed: sku must be 1 to 16 non-whitespace characters")
  }
}

final class QtyTests extends FunSuite with Matchers {

  test("qty can be zero") {
    val part = Part("bosch", "abc123", 0, LocalDate.parse("2016-01-15"), LocalDate.parse("2099-12-31"))
    part.qty should equal (0)
  }

  test("qty must be not be less than zero") {
    val thrown = intercept[java.lang.IllegalArgumentException] {
      Part("bosch", "abc123", -1, LocalDate.parse("2016-01-15"), LocalDate.parse("2099-12-31"))
    }
    thrown.getMessage should equal ("requirement failed: qty must be greater than or equal to 0")
  }
}

final class DateTests extends FunSuite with Matchers {

  test("releaseDate can equal endOfLifeDate") {
    val part = Part("bosch", "abc123", 123, LocalDate.parse("2016-01-15"), LocalDate.parse("2016-01-15"))
    part.releaseDate should equal (part.endOfLifeDate)
  }

  test("releaseDate must not be after endOfLifeDate") {
    val thrown = intercept[java.lang.IllegalArgumentException] {
      Part("bosch", "abc123", 123, LocalDate.parse("2016-01-15"), LocalDate.parse("2016-01-14"))
    }
    thrown.getMessage should equal ("requirement failed: releaseDate must be before or equal to endOfLifeDate")
  }
}

final class ToFixedWidthTests extends FunSuite with Matchers {

  // used to assert existing behavior
  test("toFixedWidth returns expected string") {

    val boschPart = Part("bosch", "abc123", 123, LocalDate.parse("2016-01-15"), LocalDate.parse("2099-12-31"))
    boschPart.toFixedWidth should equal ("       bosch__________abc1230000000123  Fri 2016-01-15  Thu 2099-12-31")

    val bendixPart = Part("bendix", "xyz987", 123123123, LocalDate.parse("2014-05-23"), LocalDate.parse("2020-12-31"))
    bendixPart.toFixedWidth should equal ("      bendix__________xyz9870123123123  Fri 2014-05-23  Thu 2020-12-31")
  }

  test("toFixedWidth returns string of length 70") {
    val part = Part("bosch", "abc123", 123, LocalDate.parse("2016-01-15"), LocalDate.parse("2099-12-31"))
    part.toFixedWidth.length should equal (70)
  }

  test("toFixedWidth returns expected string with minimum length inputs") {
    val part = Part("a", "b", 0, LocalDate.parse("2016-01-15"), LocalDate.parse("2099-12-31"))
    part.toFixedWidth should equal ("           a_______________b0000000000  Fri 2016-01-15  Thu 2099-12-31")
  }

  test("toFixedWidth returns expected string with maximum length inputs") {
    val part = Part("123456789012", "1234567890123456", Int.MaxValue, LocalDate.parse("2016-01-15"), LocalDate.parse("2099-12-31"))
    part.toFixedWidth should equal ("12345678901212345678901234562147483647  Fri 2016-01-15  Thu 2099-12-31")
  }
}