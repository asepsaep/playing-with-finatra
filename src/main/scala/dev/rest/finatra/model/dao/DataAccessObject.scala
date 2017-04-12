package dev.rest.finatra.model.dao

import com.twitter.finatra.request.FormParam
import com.twitter.finatra.validation._

case class AccountDao(
  username: String,
  email:    String
)

case class AccountProfileRegistration(
  @FormParam @NotEmpty username:String,
  @FormParam @NotEmpty email:   String,
  @FormParam @NotEmpty password:String,
  @FormParam @NotEmpty fullname:String,
  @FormParam @NotEmpty address: String
)

case class LoginRequest(
  @FormParam @NotEmpty username:String,
  @FormParam @NotEmpty password:String
)