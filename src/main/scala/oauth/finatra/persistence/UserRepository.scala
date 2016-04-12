package oauth.finatra.persistence

import javax.inject.{ Inject, Singleton }

import oauth.finatra.modules.DatabaseModule.DatabaseSource
import com.twitter.util.Future
import io.getquill._

@Singleton
class UserRepository @Inject() (db: DatabaseSource) {

  def findById(id: Int): Future[Option[Users]] = {
    val q = quote { (id: Int) =>
      query[Users].filter(i => i.id == id).take(1)
    }
    db.run(q)(id).map(_.headOption)
  }

}