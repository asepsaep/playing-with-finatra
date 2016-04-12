package oauth.finatra

import oauth.finatra.modules._
import com.github.xiaodongw.swagger.finatra.SwaggerController
import com.twitter.finagle.http.{ Request, Response }
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.{ CommonFilters, LoggingMDCFilter, TraceIdMDCFilter }
import com.twitter.finatra.http.routing.HttpRouter
import oauth.finatra.app.v1.fake.FakeController
import oauth.finatra.app.v1.ping.PingController
import oauth.finatra.app.v1.user.UserController
import oauth.finatra.modules.{ TypesafeConfigModule, CustomJacksonModule, HttpClientModules, DatabaseModule }
import oauth.finatra.swagger.FinatraSwaggerDocument

object FinatraServerMain extends FinatraServer

class FinatraServer extends HttpServer {

  override def modules = Seq(TypesafeConfigModule, DatabaseModule) ++ HttpClientModules.modules

  override def jacksonModule = CustomJacksonModule

  override def defaultFinatraHttpPort = ":9999"

  override def configureHttp(router: HttpRouter) {
    router
      .filter[LoggingMDCFilter[Request, Response]]
      .filter[TraceIdMDCFilter[Request, Response]]
      .filter[CommonFilters]
      .add(new SwaggerController(swagger = FinatraSwaggerDocument))
      .add[PingController]
      .add[UserController]
      .add[FakeController]

  }

}
