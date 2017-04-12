package dev.rest.finatra.service

import javax.inject.Inject

import com.twitter.util.Future
import dev.rest.finatra.model._
import dev.rest.finatra.model.dao.AccountProfileRegistration

class RegistrationService @Inject() (accountService: AccountService, profileService: ProfileService) {

  def create(account: AccountProfileRegistration): Future[Option[Profile]] = {
    for {
      newAccount <- accountService.create(account)
      newProfile <- profileService.create(account)
    } yield newProfile
  }

}
