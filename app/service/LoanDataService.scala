package service

import model.LoanData
import repository.LoanDataRepository

import javax.inject.{Inject, Singleton}

@Singleton
class LoanDataService @Inject()(val loanDataRepository: LoanDataRepository) {

  def getLoans(size: Int, sortBy: String): List[LoanData] = {
    loanDataRepository.getLoansBySizeAndSort(size, sortBy)
  }

}
