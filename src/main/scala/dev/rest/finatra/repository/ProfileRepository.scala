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
class ProfileRepository @Inject() (db: DatabaseSource) {

  def findById(id: Long): Future[Option[Profile]] = {
    db.run {
      quote { (id: Long) =>
        query[Profile].filter(i => i.id == id).take(1)
      }
    }(id).map(_.headOption)
  }

  def findByUsername(username: String): Future[Option[Profile]] = {
    db.run {
      quote { (username: String) =>
        query[Profile].filter(i => i.username == username).take(1)
      }
    }(username).map(_.headOption)
  }

  def findByName(name: String): Future[Seq[Profile]] = {
    db.run {
      quote { (name: String) =>
        query[Profile].filter(i => i.fullname.contains(name))
      }
    }(name)
  }

  def create(username: String, fullname: String, address: String) = {
    db.run {
      quote {
        query[Profile].schema(_.generated(_.id)).insert
      }
    }(new Profile(-1, username, fullname, address, new DateTime()))
  }

  def delete(username: String) = {
    db.run {
      quote { (username: String) =>
        query[Profile].filter(i => i.username == username).delete
      }
    }(username)
  }

}
