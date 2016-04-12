package oauth.finatra.app.v1.ping

import javax.inject.Inject

import oauth.finatra.swagger.SimpleSwaggerSupport
import com.google.inject.Singleton
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.twitter.util.Future
import com.typesafe.config.Config
import oauth.finatra.swagger.{ SimpleSwaggerSupport, SwaggerDocument }

case class Pong(pong: String)

@Singleton
class PingController @Inject() (config: Config) extends Controller with SimpleSwaggerSupport {

  {
    import SwaggerDocument.PingDocument
    get("/ping") { request: Request =>
      info("ping")
      Future.value(Pong("pong"))
    }
  }
}
