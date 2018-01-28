package dao.slick

import javax.inject.Inject
import scala.concurrent.{ ExecutionContext, Future }
import dao.PersonDao
import model.Person
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile

class SlickPersonDao @Inject()
  (override val dbConfigProvider: DatabaseConfigProvider)(
  implicit executionContext: ExecutionContext)
  extends SlickProfileDao(dbConfigProvider)(executionContext)
    with PersonDao{
  import profile.api._

  override def findByAll(): Future[Seq[model.Person]] = {
    db.run(persons.result)
  }
  override def findById(id: Long): Future[Option[Person]] = {
    val person = persons.filter(_.id===id)
    db.run(person.result.headOption)
  }

  override def create(person: Person): Unit =
    db.run(persons += person).map { _ => () }

  override def update(person: Person): Unit = {
    println(person)
    val personToUpdate = persons.filter(_.id===person.id)
    db.run(personToUpdate.update(person))
  }
}
