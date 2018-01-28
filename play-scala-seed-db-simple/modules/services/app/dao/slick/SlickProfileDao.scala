package dao.slick

import javax.inject.Inject

import dao.PersonDao
import model.{Person, Role}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

class SlickProfileDao @Inject()
  (protected val dbConfigProvider: DatabaseConfigProvider)(
    implicit executionContext: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile]{
  import profile.api._

  protected val persons = TableQuery[PersonTable]

  class PersonTable(tag: Tag) extends Table[Person](tag, "person") {
    def id = column[Option[Long]]("id",O.PrimaryKey,O.AutoInc)
    def firstName = column[String]("firstName")
    def lastName = column[String]("lastName")
    def * = (id, firstName, lastName) <>
      (Person.tupled, Person.unapply _)
  }
}
