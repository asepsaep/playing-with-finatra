package oauth.finatra.persistence

import java.util.Date

case class Users(
  id:        Int,
  name:      String,
  createdAt: Date
)
