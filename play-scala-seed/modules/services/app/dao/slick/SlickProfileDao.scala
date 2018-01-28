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
  protected val roles = TableQuery[RoleTable]
  private val personroles = TableQuery[PersonRoleTable]

  class PersonTable(tag: Tag) extends Table[Person](tag, "person") {
    def id = column[Option[Long]]("id",O.PrimaryKey,O.AutoInc)
    def firstName = column[String]("firstName")
    def lastName = column[String]("lastName")
    def personRoles = for {
      a <- personroles if a.personFK==id
      r <- roles if r.id==a.roleFK
    }yield (r)

    def * = (id, firstName, lastName) <>
      (Person.tupled, Person.unapply _)
  }
  class RoleTable(tag: Tag) extends Table[Role](tag, "role") {
    def id = column[Option[Long]]("id",O.PrimaryKey,O.AutoInc)
    def name = column[String]("name")

    def * = (id, name) <> (Role.tupled, Role.unapply)
  }
  class PersonRoleTable(tag: Tag) extends Table[(Long,Long)](tag, "person_role") {
    def personId = column[Long]("person_id")
    def roleId = column[Long]("role_id")

    def * = (personId, roleId)
    def personFK = foreignKey("person_key", personId, persons)(p=>p.id.get)
    def roleFK = foreignKey("role_key", roleId, roles)(r=>r.id.get)
  }
}
