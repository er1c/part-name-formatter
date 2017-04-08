package part

import java.time.LocalDate
import org.scalatest.{FunSuite, Matchers}

final class PartTests extends FunSuite with Matchers {

  val parts = Vector(
    Part("bosch", "abc123", 123, LocalDate.parse("2016-01-15"), LocalDate.parse("2099-12-31")),
    Part("bendix", "xyz987", 123123123, LocalDate.parse("2014-05-23"), LocalDate.parse("2020-12-31"))
  )

  test("toFixedWidth returns expected value") {
    parts(0).toFixedWidth should equal ("       bosch__________abc1230000000123  Fri 2016-01-15  Thu 2100-12-31")
    parts(1).toFixedWidth should equal ("      bendix__________xyz9870123123123  Fri 2014-05-23  Thu 2021-12-31")
  }
}