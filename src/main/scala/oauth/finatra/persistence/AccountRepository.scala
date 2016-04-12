package oauth.finatra.persistence

import javax.inject.{ Inject, Singleton }

import com.twitter.util.Future
import oauth.finatra.modules.DatabaseModule.DatabaseSource
import io.getquill._

@Singleton
class AccountRepository @Inject() (db: DatabaseSource) {

  def findById(id: Int): Future[Option[Account]] = {
    val q = quote { (id: Int) =>
      query[Account].filter(i => i.id == id).take(1)
    }
    db.run(q)(id).map(_.headOption)
  }

  def findByUsername(username: String): Future[Option[Account]] = {
    val q = quote { (username: String) =>
      query[Account].filter(i => i.username == username).take(1)
    }
    db.run(q)(username).map(_.headOption)
  }

  def findByEmail(email: String): Future[Option[Account]] = {
    val q = quote { (email: String) =>
      query[Account].filter(i => i.email == email).take(1)
    }
    db.run(q)(email).map(_.headOption)
  }

}
