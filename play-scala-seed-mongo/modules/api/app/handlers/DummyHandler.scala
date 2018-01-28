package handlers

import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global
class DummyHandler {
  def call(implicit req:Request, response: Response): Unit ={
    val futureVal = DummyController.act(10)

    futureVal.onComplete{
      case Success(data)=>response.send(data)
      case Failure(ex) => response.send(ex.getLocalizedMessage)
    }

  }

}
