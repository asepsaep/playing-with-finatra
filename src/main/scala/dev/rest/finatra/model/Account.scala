package dev.rest.finatra.model

case class Account(
  id:       Long,
  username: String,
  email:    String,
  password: String
)
