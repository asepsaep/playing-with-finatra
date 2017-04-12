package dev.rest.finatra.service

import javax.inject.Inject

import com.twitter.util.Future
import dev.rest.finatra.model.Token
import dev.rest.finatra.repository.TokenRepository

class TokenService @Inject() (tokenRepository: TokenRepository) {

  def createNewToken(username: String): Future[Option[Token]] = {
    tokenRepository.create(username).flatMap { id =>
      findTokenById(id)
    }
  }

  def findToken(username: String, tokenId: String): Future[Option[Token]] = {
    tokenRepository.findToken(username, tokenId)
  }

  def findTokenByTokenId(tokenId: String): Future[Option[Token]] = {
    tokenRepository.findByTokenId(tokenId)
  }

  def findTokenById(id: Long): Future[Option[Token]] = {
    tokenRepository.findById(id)
  }

  def deleteToken(tokenId: String) = {
    tokenRepository.delete(tokenId)
  }

}
