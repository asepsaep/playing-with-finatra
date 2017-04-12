package dev.rest.finatra.repository

import javax.inject.{ Inject, Singleton }

import com.twitter.util.Future
import dev.rest.finatra.model._
import dev.rest.finatra.modules.DatabaseModule.DatabaseSource
import scala.concurrent.ExecutionContext.Implicits.global
import io.getquill._
import org.joda.time.DateTime
import dev.rest.finatra.common.ops._

@Singleton
class TokenRepository @Inject() (db: DatabaseSource) {

  val tokenLength = 64
  val expireDays = 365 // 1 Year
  val random = new scala.util.Random(new java.security.SecureRandom())

  implicit class ReturningId[T](a: Action[T]) {
    def returningId = quote(infix"$a RETURNING id; -- ".as[Action[T]])
  }

  def findToken(username: String, tokenId: String): Future[Option[Token]] = {
    db.run {
      quote { (username: String, tokenId: String) =>
        query[Token].filter(i => i.username == username && i.tokenId == tokenId)
      }
    }(username, tokenId).map(_.headOption)
  }

  def findById(id: Long): Future[Option[Token]] = {
    db.run {
      quote { (id: Long) =>
        query[Token].filter(i => i.id == id).take(1)
      }
    }(id).map(_.headOption)
  }

  def findByTokenId(tokenId: String): Future[Option[Token]] = {
    db.run {
      quote { (tokenId: String) =>
        query[Token].filter(i => i.tokenId == tokenId).take(1)
      }
    }(tokenId).map(_.headOption)
  }

  def create(username: String): Future[Long] = {
    db.run {
      quote { (tokenId: String, username: String, expireDate: DateTime) =>
        query[Token].schema(_.generated(_.id)).insert(
          _.id -> -1,
          _.tokenId -> tokenId,
          _.username -> username,
          _.validUntil -> expireDate
        ).returningId
      }
    }(generateToken(), username, new DateTime().plusDays(expireDays))
  }

  def delete(tokenId: String) = {
    db.run {
      quote { (tokenId: String) =>
        query[Token].filter(i => i.tokenId == tokenId).delete
      }
    }(tokenId)
  }

  private def generateToken(): String = {
    random.alphanumeric.take(tokenLength).mkString
  }

}