package orm

import model.LoanData
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper

class LoanDataMapperSpec extends AnyFlatSpec with Matchers{
  private val testCsvFilePath = "test/resources/test_data.csv"
    "LoanDataMapper" should "retrieves a list of LoanData" in {
      val loanDataMapper = new LoanDataMapper(testCsvFilePath)
      val firstTwoLoanData = List(
        LoanData(
          id = 126285300, loanAmount = 40000, fundedAmount = 40000, term = "36 months",
          interestRate = "6.08%", grade = "A", subGrade = "A2", employeeTitle = "Loss Mitigation Manager",
          homeOwnership = "MORTGAGE", issuedDate = "Dec-2017", loanStatus = "Fully Paid", zipCode = "928xx",
          stateAddress = "CA", ficoRangeLow = "780", ficoRangeHigh = "784"
        ),
        LoanData(
          id = 126367373, loanAmount = 24000, fundedAmount = 24000, term = "60 months", interestRate = "15.05%",
          grade = "C", subGrade = "C4", employeeTitle = "Manager", homeOwnership = "MORTGAGE", issuedDate = "Dec-2017",
          loanStatus = "Fully Paid", zipCode = "462xx", stateAddress = "IN", ficoRangeLow = "705", ficoRangeHigh = "709"
        ),
      )

      val result = loanDataMapper.retrieveData()

      result.length shouldBe 5
      result.head shouldBe firstTwoLoanData.head
      result.apply(1) shouldBe firstTwoLoanData.apply(1)
    }
}
