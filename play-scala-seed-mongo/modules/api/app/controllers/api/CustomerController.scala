package controllers.api

import javax.inject._

import dto.Customer
import play.api.mvc._
import services.CustomerService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.control.Exception

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
import play.api.libs.json._

@Singleton
class CustomerController @Inject()
(cc: ControllerComponents,
 customerService: CustomerService)
  extends AbstractController(cc) {
  implicit val customerReads = Json.reads[Customer]
  implicit val customerWrites = Json.writes[Customer]
  def find(id:String) = Action.async { implicit request: Request[AnyContent] =>

    val customerFuture = customerService.findById(id)
    customerFuture.map(c=>{
      c match {
        case Some(customer) => {
          Ok(Json.toJson(customer))
        }
        case None => {
          Ok(Json.toJson("{}"))
        }

      }
    })
  }
  def create = Action (parse.json){
    request => {
      println("Creating Customer")
      val result = Json.fromJson[Customer](request.body)
      result match {
        case JsSuccess(c:Customer, path:JsPath)=>{
          customerService.create(c)
          Ok("Done")
        }
        case e: JsError => {
          InternalServerError("Error")
        }
      }
    }
  }
}
