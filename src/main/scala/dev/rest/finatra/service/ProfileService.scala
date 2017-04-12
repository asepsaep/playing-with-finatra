package dev.rest.finatra.service

import javax.inject.Inject

import com.twitter.util.Future
import dev.rest.finatra.TwitterConverters._
import dev.rest.finatra.model.Profile
import dev.rest.finatra.model.dao.AccountProfileRegistration
import dev.rest.finatra.repository.ProfileRepository

import scala.concurrent.ExecutionContext.Implicits.global

class ProfileService @Inject() (profileRepository: ProfileRepository) {

  def findByUsername(username: String): Future[Option[Profile]] = {
    profileRepository.findByUsername(username)
  }

  def findByName(name: String): Future[Seq[Profile]] = {
    profileRepository.findByName(name)
  }

  def create(account: AccountProfileRegistration): Future[Option[Profile]] = {
    for {
      insertion <- profileRepository.create(account.username, account.fullname, account.address)
      newProfile <- profileRepository.findByUsername(account.username)
    } yield newProfile
  }

  def delete(username: String): Future[Boolean] = {
    for {
      deletion <- profileRepository.delete(username)
      deleted <- profileRepository.findByUsername(username).map(_.isEmpty)
    } yield deleted
  }

}
