package dev.rest.finatra

import com.github.xiaodongw.swagger.finatra.SwaggerController
import com.twitter.finagle.http.{ Request, Response }
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.{ CommonFilters, LoggingMDCFilter, TraceIdMDCFilter }
import com.twitter.finatra.http.routing.HttpRouter
import com.twitter.inject.requestscope.FinagleRequestScopeFilter
import dev.rest.finatra.controller._
import dev.rest.finatra.filter._
import dev.rest.finatra.modules._
import dev.rest.finatra.swagger.FinatraSwaggerDocument

object FinatraServerMain extends FinatraServer

class FinatraServer extends HttpServer {

  override def modules = Seq(TypesafeConfigModule, DatabaseModule, AuthModule) ++ HttpClientModules.modules

  override def jacksonModule = CustomJacksonModule

  override def defaultFinatraHttpPort = ":9999"

  override def configureHttp(router: HttpRouter) {
    router
      .filter[LoggingMDCFilter[Request, Response]]
      .filter[TraceIdMDCFilter[Request, Response]]
      .filter[CommonFilters]
      .filter[FinagleRequestScopeFilter[Request, Response]]
      .filter[AuthFilter]
      .add(new SwaggerController(swagger = FinatraSwaggerDocument))
      .add[AccountController]

  }

}
