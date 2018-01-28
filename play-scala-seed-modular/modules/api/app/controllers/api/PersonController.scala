package controllers.api

import javax.inject._

import model.Person
import play.api._
import play.api.mvc._
import services.PersonService

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
import play.api.libs.json._
@Singleton
class PersonController @Inject()(cc: ControllerComponents, personService: PersonService)
  extends AbstractController(cc) {
  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  implicit val personReads = Json.reads[Person]
  implicit val personWrites = Json.writes[Person]

  def find(id:Long) = Action { implicit request: Request[AnyContent] =>
    val res = personService.find(id)
    Ok(Json.toJson(res))
  }
  def create = Action(parse.json){
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
