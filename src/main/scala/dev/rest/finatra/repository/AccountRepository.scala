package dev.rest.finatra.repository

import javax.inject.{ Inject, Singleton }

import com.twitter.util.Future
import dev.rest.finatra.model._
import dev.rest.finatra.modules.DatabaseModule.DatabaseSource
import scala.concurrent.ExecutionContext.Implicits.global
import io.getquill._
import org.joda.time.DateTime
import dev.rest.finatra.common.ops._
import com.github.t3hnar.bcrypt._

@Singleton
class AccountRepository @Inject() (db: DatabaseSource) {

  def findById(id: Long): Future[Option[Account]] = {
    db.run {
      quote { (id: Long) =>
        query[Account].filter(i => i.id == id).take(1)
      }
    }(id).map(_.headOption)
  }

  def findByUsername(username: String): Future[Option[Account]] = {
    db.run {
      quote { (username: String) =>
        query[Account].filter(i => i.username == username).take(1)
      }
    }(username).map(_.headOption)
  }

  def findByEmail(email: String): Future[Option[Account]] = {
    db.run {
      quote { (email: String) =>
        query[Account].filter(i => i.email == email).take(1)
      }
    }(email).map(_.headOption)
  }

  def login(username: String, password: String): Future[Option[Account]] = {
    db.run {
      quote { (username: String) =>
        query[Account].filter(i => i.username == username).take(1)
      }
    }(username).map(_.find(account => password.isBcrypted(account.password)))
  }

  def take(number: Int): Future[List[Account]] = {
    db.run {
      quote { (number: Int) =>
        query[Account].take(number)
      }
    }(number)
  }

  def takeAll(): Future[List[Account]] = {
    db.run {
      quote {
        query[Account]
      }
    }
  }

  def create(username: String, email: String, password: String) = {
    db.run {
      quote {
        query[Account].schema(_.generated(_.id)).insert
      }
    }(new Account(-1, username, email, password.bcrypt))
  }

  def delete(username: String) = {
    db.run {
      quote { (username: String) =>
        query[Account].filter(i => i.username == username).delete
      }
    }(username)
  }

}
