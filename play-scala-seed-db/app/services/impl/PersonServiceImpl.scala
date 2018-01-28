package services.impl

import model.Person
import services.PersonService

class PersonServiceImpl extends PersonService{
  override def create(person: Person): Unit = {
    println(s"Stored $person")
  }
  override def find(id:Long):Person =
    Person("Person", "Service")
}
