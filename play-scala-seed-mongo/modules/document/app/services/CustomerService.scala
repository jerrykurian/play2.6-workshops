package services

import com.google.inject.ImplementedBy
import dto.Customer

import scala.concurrent.Future

@ImplementedBy(classOf[CustomerServiceImpl])
trait CustomerService {
  def create(customer: Customer)
  def findById(id: String):Future[Option[Customer]]
}
