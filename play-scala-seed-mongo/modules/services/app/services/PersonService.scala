package services

import com.google.inject.ImplementedBy
import model.Person
import services.impl.PersonServiceImpl

import scala.concurrent.Future
@ImplementedBy(classOf[PersonServiceImpl])
trait PersonService {
  def find(id:Long):Future[Option[Person]]
  def create(person: Person)
  def modify(person: Person)
}
