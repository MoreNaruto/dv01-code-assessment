package repository

import model.LoanData
import orm.LoanDataMapper

import java.text.SimpleDateFormat
import javax.inject.{Inject, Singleton}


@Singleton
class LoanDataRepository @Inject()(val loanDataMapper: LoanDataMapper) {

  def getLoansBySizeAndSort(size: Int, sortBy: String): List[LoanData] = {
    val loans = loanDataMapper.retrieveData()

    val result = sortBy match {
      case "issued_date" =>
        val dateFormat = new SimpleDateFormat("MMM-yyyy")
        val dateComparator: Ordering[LoanData] = Ordering.by(loan => dateFormat.parse(loan.issuedDate))
        loans.sorted(dateComparator)
      case "loan_amount" =>
        loans.sortBy(_.loanAmount).reverse
      case "grade" =>
        loans.sortBy(_.grade).sortBy(_.subGrade)
      case "fico_low" =>
        loans.sortBy(_.ficoRangeLow)
      case "fico_high" =>
        loans.sortBy(_.ficoRangeHigh).reverse
      case _ => loans
    }

    result.take(size)
  }
}
