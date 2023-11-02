package orm

import com.opencsv.{CSVParserBuilder, CSVReaderBuilder}
import model.LoanData
import play.twirl.api.TwirlHelperImports.twirlJavaCollectionToScala

import javax.inject.{Inject, Singleton}
import scala.io.Source

/*
Ideally what would be replaced with an ORM that connects to a DB
 */
@Singleton
class LoanDataMapper @Inject()(val csvFilePath: String) {
  private var cachedLoanData: Map[Long, LoanData] = Map.empty
  def retrieveData(): List[LoanData] = {
    //This would be replaced with a caching service
    if (cachedLoanData.nonEmpty) {
      return cachedLoanData.values.toList
    }
    try {
      var currentCsvFilePath = csvFilePath
      if (csvFilePath.isEmpty) {
        currentCsvFilePath = "conf/data/LoanStats_securev1_2017Q4.csv"
      }
      val reader = new CSVReaderBuilder(Source.fromFile(currentCsvFilePath).bufferedReader())
        .withCSVParser(
          new CSVParserBuilder()
            .withSeparator(',')
            .withIgnoreQuotations(true)
            .build()
        )
        .withSkipLines(2)
        .build()

      val loanData = reader.readAll()
        .map(fields => {
          try {
            var loanAmountField = 0
            var fundedAmountField = 0
            if (fields(2).nonEmpty) {
              loanAmountField = fields(2).toInt
            }
            if (fields(3).nonEmpty) {
              fundedAmountField = fields(3).toInt
            }
            LoanData(id = fields(0).toLong, loanAmount = loanAmountField, fundedAmount = fundedAmountField,
              term = fields(5).trim, interestRate = fields(6).trim, grade = fields(8), subGrade = fields(9), employeeTitle = fields(10),
              homeOwnership = fields(12), issuedDate = fields(15), loanStatus = fields(16), zipCode = fields(22),
              stateAddress = fields(23), ficoRangeLow = fields(27), ficoRangeHigh = fields(28))
          } catch {
            case e: Exception => LoanData()
          }
        })

      val removedInvalidLoans = loanData
        .filter(loan => loan.id != 0L)
      removedInvalidLoans
        .foreach(loan => cachedLoanData = cachedLoanData + (loan.id -> loan) )
      removedInvalidLoans.toList
    } catch {
      case e: Exception =>
        throw new Exception(e)
    }
  }
}
