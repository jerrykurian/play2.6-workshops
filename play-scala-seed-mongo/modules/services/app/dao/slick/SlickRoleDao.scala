package dao.slick

import javax.inject.Inject

import scala.concurrent.{ExecutionContext, Future}
import dao.{PersonDao, RoleDao}
import model.Role
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile

class SlickRoleDao @Inject()
(protected val dbConfigProvider: DatabaseConfigProvider)(
  implicit executionContext: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] with RoleDao{
  override def findByName(name: String) = ???
}
