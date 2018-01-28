package controllers.api

import javax.inject._

import jwt.JwtUtil
import model.{Auth, Person}
import play.api.mvc._
import services.PersonService

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
import play.api.libs.json._

@Singleton
class AuthController @Inject()(cc: ControllerComponents, personService: PersonService)
  extends AbstractController(cc) {
  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  implicit val authReads = Json.reads[Auth]

  def authenticate = Action(parse.json){
    request => {
      val result = Json.fromJson[Auth](request.body)
      result match {
        case JsSuccess(p:Auth, path:JsPath)=>{
          Ok(JwtUtil.createToken(p.userName))
        }
        case e: JsError => {
          InternalServerError("Error")
        }
      }
    }
  }
}
