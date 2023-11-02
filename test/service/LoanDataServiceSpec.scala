package service

import model.LoanData
import org.scalamock.scalatest.MockFactory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import repository.LoanDataRepository

class LoanDataServiceSpec extends AnyFlatSpec with Matchers with MockFactory {
  "LoanDataService" should "retrieve loans" in {
    val mockLoanDataRepository = mock[LoanDataRepository]

    (mockLoanDataRepository.getLoansBySizeAndSort _)
      .expects(where { (size: Int, sortBy: String) => size == 3 && sortBy == "issued_date" })
      .returning(List(
        LoanData(
          id = 126285300, loanAmount = 40000, fundedAmount = 40000, term = "36 months",
          interestRate = "6.08%", grade = "A", subGrade = "A2", employeeTitle = "Employee #1",
          homeOwnership = "MORTGAGE", issuedDate = "Jan-2017", loanStatus = "Fully Paid", zipCode = "928xx",
          stateAddress = "CA", ficoRangeLow = "780", ficoRangeHigh = "784"
        ),
        LoanData(
          id = 126367373, loanAmount = 24000, fundedAmount = 24000, term = "60 months", interestRate = "15.05%",
          grade = "C", subGrade = "C4", employeeTitle = "Employee #2", homeOwnership = "MORTGAGE", issuedDate = "Feb-2017",
          loanStatus = "Fully Paid", zipCode = "462xx", stateAddress = "IN", ficoRangeLow = "705", ficoRangeHigh = "709"
        ),
        LoanData(
          id = 126285301, loanAmount = 50000, fundedAmount = 50000, term = "36 months",
          interestRate = "6.08%", grade = "B", subGrade = "B1", employeeTitle = "Employee #3",
          homeOwnership = "MORTGAGE", issuedDate = "Mar-2017", loanStatus = "Fully Paid", zipCode = "928xx",
          stateAddress = "CA", ficoRangeLow = "810", ficoRangeHigh = "804"
        )
      ))

    val loanDataService = new LoanDataService(mockLoanDataRepository)

    val actualLoans = loanDataService.getLoans(3, "issued_date")
    actualLoans.head.employeeTitle shouldBe "Employee #1"
    actualLoans.apply(1).employeeTitle shouldBe "Employee #2"
    actualLoans.apply(2).employeeTitle shouldBe "Employee #3"
  }
}
