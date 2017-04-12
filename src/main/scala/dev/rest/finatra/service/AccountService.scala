package dev.rest.finatra.service

import javax.inject.Inject

import com.twitter.util.Future
import dev.rest.finatra.TwitterConverters._
import dev.rest.finatra.model.Account
import dev.rest.finatra.model.dao.{ AccountDao, AccountProfileRegistration, LoginRequest }
import dev.rest.finatra.repository.AccountRepository

import scala.concurrent.ExecutionContext.Implicits.global

class AccountService @Inject() (accountRepository: AccountRepository) {

  def usernameExists(username: String): Future[Boolean] = {
    accountRepository.findByUsername(username).map(_.isDefined)
  }

  def emailExists(email: String): Future[Boolean] = {
    accountRepository.findByEmail(email).map(_.isDefined)
  }

  def findByUsername(username: String): Future[Option[AccountDao]] = {
    accountRepository.findByUsername(username).map(_.map(toAccountDto(_)))
  }

  def findByEmail(email: String): Future[Option[AccountDao]] = {
    accountRepository.findByEmail(email).map(_.map(toAccountDto(_)))
  }

  def login(account: LoginRequest): Future[Boolean] = {
    accountRepository.login(account.username, account.password).map(_.isDefined)
  }

  def take(number: Int): Future[List[AccountDao]] = {
    if (number > 0)
      accountRepository.take(number).map(_.map(toAccountDto(_)))
    else
      accountRepository.takeAll().map(_.map(toAccountDto(_)))
  }

  def create(account: AccountProfileRegistration): Future[Option[AccountDao]] = {
    for {
      insertion <- accountRepository.create(account.username, account.email, account.password)
      newAccount <- accountRepository.findByUsername(account.username)
    } yield newAccount.map(toAccountDto((_)))
  }

  def delete(username: String): Future[Boolean] = {
    for {
      deletion <- accountRepository.delete(username)
      deleted <- accountRepository.findByUsername(username).map(_.isEmpty)
    } yield deleted
  }

  private def toAccountDto(account: Account): AccountDao = {
    AccountDao(
      username = account.username,
      email = account.email
    )
  }

}
