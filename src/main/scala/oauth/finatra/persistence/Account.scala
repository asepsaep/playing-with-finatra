package oauth.finatra.persistence

case class Account(
  id:       Int,
  username: String,
  password: String,
  email:    String
)
