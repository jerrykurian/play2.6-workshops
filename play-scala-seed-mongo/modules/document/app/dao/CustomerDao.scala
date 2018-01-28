package dao

import com.google.inject.ImplementedBy
import dao.mongo.MongoCustomerDao
import model.Customer

import scala.concurrent.Future

@ImplementedBy(classOf[MongoCustomerDao])
trait CustomerDao {
  def findByAll():Future[Seq[Customer]]
  def findById(id:String):Future[Option[Customer]]
  def findByName(firstName:String, lastName:String):Future[Option[Customer]]
  def create(person:Customer)
  def update(person:Customer)
}
