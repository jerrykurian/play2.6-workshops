package dao.mongo

import javax.inject.Inject

import dao.CustomerDao
import play.modules.reactivemongo.{ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.Future
import javax.inject.Inject

import model.Customer

import scala.concurrent.Future
import play.api.Logger

import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.functional.syntax._
import play.api.libs.json._
import reactivemongo.api.ReadPreference
import reactivemongo.bson.{BSONDocument, BSONObjectID}
// Reactive Mongo imports
import reactivemongo.api.Cursor
import play.modules.reactivemongo.{ // ReactiveMongo Play2 plugin
  MongoController,
  ReactiveMongoApi,
  ReactiveMongoComponents
}
// BSON-JSON conversions/collection
import reactivemongo.play.json._
import play.modules.reactivemongo.json.collection._
object JsonFormats{
  import play.api.libs.json._

  implicit val customerFormat: OFormat[Customer] =
    Json.format[Customer]
}
trait CustomerDocument{
  def firstName = "first_name"
}
class MongoCustomerDao @Inject()
  (val reactiveMongoApi: ReactiveMongoApi)
  extends CustomerDao with ReactiveMongoComponents with CustomerDocument  {
  import JsonFormats._
  def customerCol: Future[JSONCollection] =
    reactiveMongoApi.database.map(_.collection("customers"))
  override def findByAll(): Future[Seq[Customer]] = {
    val query = Json.obj()
    customerCol.flatMap(_.find(query)
      .cursor[Customer](ReadPreference.primary)
      .collect[Seq]()
    )
  }

  override def findById(id: String): Future[Option[Customer]] = {
    val query = BSONDocument("_id" -> BSONObjectID.parse(id).get)
    customerCol.flatMap(_.find(query).one[Customer])
  }

  override def findByName(firstNameVal: String, lastName:String): Future[Option[Customer]] = {
    val query = BSONDocument(firstName -> firstNameVal,
      "last_name"->lastName)
    customerCol.flatMap(_.find(query).one[Customer])
  }

  override def create(customer: Customer): Unit = {
    println(s"Creating customer $customer")
    customerCol.flatMap(_.insert(customer))
  }

  override def update(customer: Customer): Unit = {
    val selector = BSONDocument("_id" -> customer._id.get)
    val updateModifier = BSONDocument(
      "$set" -> BSONDocument(
        "first_name" -> customer.first_name
      )
    )
    customerCol.flatMap(
      _.findAndUpdate(selector, updateModifier, fetchNewObject = true).
        map(_.result[Customer])
    )
  }
}
