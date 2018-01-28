package controllers.web

import javax.inject._

import model.Person
import play.api._
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._
import services.PersonService

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class PersonController @Inject()(cc: ControllerComponents,
                                 personService:PersonService)
  extends AbstractController(cc) with play.api.i18n.I18nSupport{

  val personForm = Form(
    mapping(
      "firstName" -> nonEmptyText,
      "lastName" -> text
    )(Person.apply)(Person.unapply)
  )

  def index() = Action {implicit request =>
    Ok(views.html.person.index(personForm))
  }

  def create = Action(parse.form(personForm)){ implicit request =>
    val personData = request.body
    Ok(views.html.person.view(personData))
  }
  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def find(id:Long) = Action {
    implicit request: Request[AnyContent] =>
      val person = personService.find(id)

      Ok(views.html.person.view(person))
  }
}
