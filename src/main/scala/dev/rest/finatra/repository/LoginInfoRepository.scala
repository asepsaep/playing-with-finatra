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
class LoginInfoRepository @Inject() (db: DatabaseSource) {

  def create(username: String, userAgent: String) = {
    db.run {
      quote {
        query[LoginInfo].schema(_.generated(_.id))insert
      }
    }(new LoginInfo(-1, username, new DateTime(), userAgent))
  }

  def findByUsername(username: String): Future[Seq[LoginInfo]] = {
    db.run {
      quote { (username: String) =>
        query[LoginInfo].filter(i => i.username == username)
      }
    }(username)
  }

  def findById(id: Long): Future[Option[LoginInfo]] = {
    db.run {
      quote { (id: Long) =>
        query[LoginInfo].filter(i => i.id == id).take(1)
      }
    }(id).map(_.headOption)
  }

}
