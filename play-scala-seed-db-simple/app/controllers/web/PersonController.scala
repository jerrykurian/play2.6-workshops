package controllers.web

import javax.inject._

import model.{Person}
import play.api._
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._
import services.PersonService
import play.api.data.format.Formats._

import scala.concurrent.ExecutionContext.Implicits.global
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
      "id" -> optional(of[Long]),
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
    personService.create(personData)
    Ok(views.html.person.view(personData))
  }
  def modify(id:Long) = Action(parse.form(personForm)){
    implicit request =>
      val personData:Person = request.body
      personService.modify(personData)
      Ok(views.html.person.view(personData))
  }
  def find(id:Long) = Action.async {
    implicit request: Request[AnyContent] =>
      val personFuture = personService.find(id)
      personFuture.map(p=>{
        p match {
          case Some(person) => {
            val filledPersonForm = personForm.fill(person)
            Ok(views.html.person.edit(filledPersonForm, id))
          }
          case None => {
            Ok(views.html.person.edit(personForm, id))
          }
        }
      })
  }
}
