package services.impl

import javax.inject.Inject

import dao.PersonDao
import model.Person
import services.PersonService

import scala.concurrent.Future

class PersonServiceImpl @Inject() (val personDao:PersonDao)
  extends PersonService{
  override def create(person: Person): Unit = {
    personDao.create(person)
    println(s"Stored $person")
  }
  override def find(id:Long):Future[Option[Person]] =
    personDao.findById(id)

  override def modify(person: Person): Unit = {
    personDao.update(person)
  }
}
