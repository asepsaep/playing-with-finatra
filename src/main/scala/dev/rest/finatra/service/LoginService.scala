package dev.rest.finatra.service

import javax.inject.Inject

import com.twitter.util.Future
import dev.rest.finatra.model._
import dev.rest.finatra.model.dao.LoginRequest

class LoginService @Inject() (
  accountService: AccountService,
  profileService: ProfileService,
  tokenService:   TokenService
) {

  def login(account: LoginRequest): Future[Option[Token]] = {
    accountService.login(account).flatMap { validAccount =>
      //      if (validAccount) profileService.findByUsername(account.username)
      if (validAccount) tokenService.createNewToken(account.username)
      else Future { None }
    }
  }

}
