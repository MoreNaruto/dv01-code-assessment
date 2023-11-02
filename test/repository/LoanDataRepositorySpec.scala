package repository

import model.LoanData
import org.scalamock.scalatest.MockFactory
import org.scalatest.BeforeAndAfter
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import orm.LoanDataMapper

class LoanDataRepositorySpec extends AnyFlatSpec with Matchers with BeforeAndAfter with MockFactory{

  def beforeGetLoans(): List[LoanData] = {
    List(
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
      ),
      LoanData(
        id = 126367373, loanAmount = 74000, fundedAmount = 74000, term = "60 months", interestRate = "15.05%",
        grade = "A", subGrade = "A1", employeeTitle = "Employee #4", homeOwnership = "MORTGAGE", issuedDate = "Apr-2017",
        loanStatus = "Fully Paid", zipCode = "462xx", stateAddress = "IN", ficoRangeLow = "645", ficoRangeHigh = "651"
      ),
      LoanData(
        id = 126285300, loanAmount = 10000, fundedAmount = 40000, term = "36 months",
        interestRate = "6.08%", grade = "D", subGrade = "D1", employeeTitle = "Employee #5",
        homeOwnership = "MORTGAGE", issuedDate = "May-2017", loanStatus = "Fully Paid", zipCode = "928xx",
        stateAddress = "CA", ficoRangeLow = "700", ficoRangeHigh = "704"
      ),
      LoanData(
        id = 126367373, loanAmount = 84000, fundedAmount = 24000, term = "60 months", interestRate = "15.05%",
        grade = "B", subGrade = "B3", employeeTitle = "Employee #6", homeOwnership = "MORTGAGE", issuedDate = "Jul-2017",
        loanStatus = "Fully Paid", zipCode = "462xx", stateAddress = "IN", ficoRangeLow = "505", ficoRangeHigh = "509"
      )
    )
  }

  "LoanDataRepository" should "retrieve loans based on size and sorting by issued date" in {
    val mockLoanDataMapper = mock[LoanDataMapper]

    (mockLoanDataMapper.retrieveData _).expects().returning(beforeGetLoans())

    val loanDataRepository = new LoanDataRepository(mockLoanDataMapper)

    val sortedLoans = loanDataRepository.getLoansBySizeAndSort(3, "issued_date")

    sortedLoans.size shouldBe 3
    sortedLoans.head.employeeTitle shouldBe "Employee #1"
    sortedLoans.apply(1).employeeTitle shouldBe "Employee #2"
    sortedLoans.apply(2).employeeTitle shouldBe "Employee #3"
  }

  "LoanDataRepository" should "retrieve loans based on size and sorting by loan amount" in {
    val mockLoanDataMapper = mock[LoanDataMapper]

    (mockLoanDataMapper.retrieveData _).expects().returning(beforeGetLoans())

    val loanDataRepository = new LoanDataRepository(mockLoanDataMapper)

    val sortedLoans = loanDataRepository.getLoansBySizeAndSort(4, "loan_amount")

    sortedLoans.size shouldBe 4
    sortedLoans.head.employeeTitle shouldBe "Employee #6"
    sortedLoans.apply(1).employeeTitle shouldBe "Employee #4"
    sortedLoans.apply(2).employeeTitle shouldBe "Employee #3"
    sortedLoans.apply(3).employeeTitle shouldBe "Employee #1"
  }

  "LoanDataRepository" should "retrieve loans based on size and sorting by grade" in {
    val mockLoanDataMapper = mock[LoanDataMapper]

    (mockLoanDataMapper.retrieveData _).expects().returning(beforeGetLoans())

    val loanDataRepository = new LoanDataRepository(mockLoanDataMapper)

    val sortedLoans = loanDataRepository.getLoansBySizeAndSort(5, "grade")

    sortedLoans.size shouldBe 5
    sortedLoans.head.employeeTitle shouldBe "Employee #4"
    sortedLoans.apply(1).employeeTitle shouldBe "Employee #1"
    sortedLoans.apply(2).employeeTitle shouldBe "Employee #3"
    sortedLoans.apply(3).employeeTitle shouldBe "Employee #6"
    sortedLoans.apply(4).employeeTitle shouldBe "Employee #2"
  }

  "LoanDataRepository" should "retrieve loans based on size and sorting by fico low" in {
    val mockLoanDataMapper = mock[LoanDataMapper]

    (mockLoanDataMapper.retrieveData _).expects().returning(beforeGetLoans())

    val loanDataRepository = new LoanDataRepository(mockLoanDataMapper)

    val sortedLoans = loanDataRepository.getLoansBySizeAndSort(3, "fico_low")

    sortedLoans.size shouldBe 3
    sortedLoans.head.employeeTitle shouldBe "Employee #6"
    sortedLoans.apply(1).employeeTitle shouldBe "Employee #4"
    sortedLoans.apply(2).employeeTitle shouldBe "Employee #5"
  }

  "LoanDataRepository" should "retrieve loans based on size and sorting by fico high" in {
    val mockLoanDataMapper = mock[LoanDataMapper]

    (mockLoanDataMapper.retrieveData _).expects().returning(beforeGetLoans())

    val loanDataRepository = new LoanDataRepository(mockLoanDataMapper)

    val sortedLoans = loanDataRepository.getLoansBySizeAndSort(4, "fico_high")

    sortedLoans.size shouldBe 4
    sortedLoans.head.employeeTitle shouldBe "Employee #3"
    sortedLoans.apply(1).employeeTitle shouldBe "Employee #1"
    sortedLoans.apply(2).employeeTitle shouldBe "Employee #2"
    sortedLoans.apply(3).employeeTitle shouldBe "Employee #5"
  }

}
