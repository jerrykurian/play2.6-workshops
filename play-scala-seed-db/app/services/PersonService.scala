package services

import model.Person
import services.impl.PersonServiceImpl
@ImplementedBy(classOf[PersonServiceImpl])
trait PersonService {
  def find(id:Long):Person
  def create(person: Person)
}
