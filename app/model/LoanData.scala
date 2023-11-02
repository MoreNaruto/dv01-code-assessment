package model

case class LoanData(
                     id: Long = 0,
                     loanAmount: Int = 0,
                     fundedAmount: Int = 0,
                     term: String = "",
                     interestRate: String = "",
                     grade: String = "",
                     subGrade: String = "",
                     employeeTitle: String = "",
                     homeOwnership: String = "",
                     issuedDate: String = "",
                     loanStatus: String = "",
                     zipCode: String = "",
                     stateAddress: String = "",
                     ficoRangeLow: String = "",
                     ficoRangeHigh: String = "",
                   )
