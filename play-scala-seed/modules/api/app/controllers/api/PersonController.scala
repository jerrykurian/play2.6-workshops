package controllers.api

import javax.inject._

import model.Person
import play.api._
import play.api.mvc._
import services.PersonService
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
import play.api.libs.json._
@Singleton
class PersonController @Inject()(cc: ControllerComponents,
                                 personService: PersonService)
  extends AbstractController(cc) {
  implicit val personReads = Json.reads[Person]
  implicit val personWrites = Json.writes[Person]
  def find(id:Long) = Action.async { implicit request: Request[AnyContent] =>
    val personFuture = personService.find(id)
    personFuture.map(p=>{
      p match {
        case Some(person) => {
          Ok(Json.toJson(person))
        }
        case None => {
          Ok(Json.toJson("{}"))
        }
      }
    })
  }
  def create = Action (parse.json){
    request => {
      val result = Json.fromJson[Person](request.body)
      result match {
        case JsSuccess(p:Person, path:JsPath)=>{
          personService.create(p)
          Ok("Done")
        }
        case e: JsError => {
          InternalServerError("Error")
        }
      }
    }
  }
}
