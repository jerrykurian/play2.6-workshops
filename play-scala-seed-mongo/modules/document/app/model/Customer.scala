package model

import reactivemongo.bson.BSONObjectID

case class Customer(_id: Option[BSONObjectID],
                    first_name:String,
                    last_name:String) {

}
