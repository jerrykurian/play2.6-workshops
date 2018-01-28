package controllers.web

import controllers.HomeController
import model.Person
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test.Helpers._
import play.api.test._
import org.scalatest.mockito.MockitoSugar
import services.PersonService
import org.mockito.Mockito._

import scala.concurrent.Future
import scala.util.{Failure, Success}
/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 *
 * For more information, see https://www.playframework.com/documentation/latest/ScalaTestingWithScalaTest
 */
class PersonControllerSpec extends PlaySpec with GuiceOneAppPerTest
  with Injecting with MockitoSugar {

  "PersonController GET" should {
    "find the user corresponding to ID 1 " in {
      val m = mock[PersonService]

      val controller = new PersonController(stubControllerComponents(),
        m)
      when(m.find(1)).thenReturn(Future(Some(Person(Some(1),"Test","User"))))
      val futureP = controller.find(1).apply(FakeRequest(GET, "/1"))
      status(futureP) mustBe OK
      contentAsString(futureP) must include ("Test")
      contentAsString(futureP) must include ("User")
    }

    "render the index page from a new instance of controller" in {
      val m = mock[PersonService]

      val controller = new PersonController(stubControllerComponents(),
        m)
      when(m.find(1)).thenReturn(Future(Some(Person(Some(1),"Test","User"))))
      val home = controller.index().apply(FakeRequest(GET, "/"))

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include ("Welcome to Play")
    }

    "render the index page from the application" in {
      val controller = inject[HomeController]
      val home = controller.index().apply(FakeRequest(GET, "/"))

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include ("Welcome to Play")
    }

    "render the index page from the router" in {
      val request = FakeRequest(GET, "/")
      val home = route(app, request).get

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include ("Welcome to Play")
    }
  }
}
