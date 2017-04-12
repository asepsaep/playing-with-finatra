package dev.rest.finatra.model

import org.joda.time.DateTime

case class LoginInfo(
  id:        Long,
  username:  String,
  loginAt:   DateTime,
  userAgent: String
)
