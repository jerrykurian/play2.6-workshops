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
  extends AbstractController(cc)
    with play.api.i18n.I18nSupport{

  val personForm = Form(
    mapping(
      "firstName" -> nonEmptyText,
      "lastName" -> text
    )(Person.apply)(Person.unapply)
  )
  def index() = Action {implicit request =>
    Ok(views.html.person.index(personForm))
  }
  def create = Action(parse.form(personForm)){
    implicit request =>
    val personData:Person = request.body
    Ok(views.html.person.view(personData))
  }
  def modify(id:Long) = Action(parse.form(personForm)){
    implicit request =>
      val personData:Person = request.body
      Ok(views.html.person.view(personData))
  }
  def find(id:Long) = Action {
    implicit request: Request[AnyContent] =>
      val person = personService.find(id)
      val filledPersonForm = personForm.fill(person)
      Ok(views.html.person.edit(filledPersonForm, id))
  }
}
