package dev.rest.finatra.model

import org.joda.time.DateTime

case class Profile(
  id:         Long,
  username:   String,
  fullname:   String,
  address:    String,
  created_at: DateTime
)
