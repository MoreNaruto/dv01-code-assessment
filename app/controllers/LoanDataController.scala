package controllers

import model.LoanData
import play.api.libs.json.{Json, OFormat}
import play.api.mvc._
import service.LoanDataService

import javax.inject.{Inject, Singleton}

@Singleton
class LoanDataController @Inject()(cc: ControllerComponents, loanDataService: LoanDataService) extends AbstractController(cc) {

  implicit val loanFormat: OFormat[LoanData] = Json.format[LoanData]

  def retrieveLoansAction: Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val queryParams = request.queryString
    val size = queryParams.get("size").flatMap(_.headOption).getOrElse("10").toInt
    val sortBy = queryParams.get("sort").flatMap(_.headOption).getOrElse("loan_amount")

    val loans = loanDataService.getLoans(size, sortBy)

    val json = Json.toJson(loans)
    Ok(json)
  }

}
