package dao

import com.google.inject.ImplementedBy
import dao.slick.SlickPersonDao
import model.Person

import scala.concurrent.Future

@ImplementedBy(classOf[SlickPersonDao])
trait PersonDao {
  def findByAll():Future[Seq[Person]]
  def findById(id:Long):Future[Option[Person]]
  def create(person:Person)
  def update(person:Person)
}
