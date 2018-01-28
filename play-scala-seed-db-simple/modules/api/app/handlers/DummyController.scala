package handlers

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
object DummyController {
  def act(data:Long)(implicit req:Request):Future[String]= {
    actOnDb(data).
      flatMap(a=>actOnAnotherDb(a))
  }

  def actOnDb(data:Long):Future[String] = Future {
    "Done"
  }
  def actOnAnotherDb(data:String):Future[String] = Future {
    "Done"
  }
}
