package services

import javax.inject.Inject

import dao.CustomerDao
import model.Customer

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
class CustomerServiceImpl @Inject() (customerDao:CustomerDao) extends CustomerService {
  override def create(customerDto: dto.Customer): Unit = {
    println("Creating in impl")
    val customer = Customer(None, customerDto.firstName,
      customerDto.lastName)
    customerDao.create(customer)
  }

  override def findById(id: String): Future[Option[dto.Customer]] = {
    customerDao.findById(id).map(futC=>{
      futC match {
        case Some(customer) => Some(dto.Customer(customer._id.get.stringify,
          customer.first_name, customer.last_name))
        case None => None
      }
    })
  }
}
