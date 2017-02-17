
import org.scalatest._

import scala.util.matching.Regex


import scala.io.Source

  class sTest extends FlatSpec with Matchers {

    "Regex" should "shouldnt come back" in {
      reg("wowzers") should be(null)
    }

    // Testing normal
    "Regex" should "be parsed" in {
      reg("System.out.println()") should be("System.out.println()")
    }

    // Nested statments
    "Regex" should "be parsed nested" in {
      reg("System.out.println(foo())") should be("System.out.println(foo())")
    }

    // Junk
    "Regex" should "be parsed junk" in {
      reg(" admkl;asjkldjkasjkasd System.out.println() askl;dkl;as;dklas;kl ") should be("System.out.println()")
    }

    def reg(in: String): String = {
      val pattern = new Regex("([a-zA-z0-9.]*(\\(.*\\)))")
      val found =  (pattern.findAllIn(in)).toArray

      if (found.length > 0)
          found{0}
      else
        null
    }
  }