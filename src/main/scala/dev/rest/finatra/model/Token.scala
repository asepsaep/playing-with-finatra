package dev.rest.finatra.model

import java.util.UUID

import org.joda.time.DateTime

case class Token(
  id:         Long,
  tokenId:    String,
  username:   String,
  validUntil: DateTime
)
