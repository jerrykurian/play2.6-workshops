package dao

import model.Role

trait RoleDao {
  def findByName(name:String) : Option[Role]
}
