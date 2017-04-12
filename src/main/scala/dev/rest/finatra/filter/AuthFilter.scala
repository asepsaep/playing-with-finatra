package dev.rest.finatra.filter

import com.google.inject.Inject
import com.twitter.finagle.http.{ Cookie, CookieMap, Request, Response }
import com.twitter.finagle.{ Service, SimpleFilter }
import com.twitter.inject.requestscope.FinagleRequestScope
import com.twitter.util.Future
import dev.rest.finatra.model._
import dev.rest.finatra.service.TokenService

class AuthFilter @Inject() (
  requestScope: FinagleRequestScope,
  tokenService: TokenService
) extends SimpleFilter[Request, Response] {

  override def apply(request: Request, service: Service[Request, Response]): Future[Response] = {
    resolveUserFromRequest(request).flatMap { token =>
      requestScope.seed[Option[Token]](token)
      service(request)
    }
  }

  private def resolveUserFromRequest(request: Request): Future[Option[Token]] = {
    val (userCookie, tokenCookie) = extractCookieFromRequest(request.cookies)
    userCookie.map { username =>
      tokenCookie.map { tokenId =>
        tokenService.findToken(username.value, tokenId.value)
      }.getOrElse(Future { None })
    }.getOrElse(Future { None })
  }

  private def extractCookieFromRequest(cookies: CookieMap): (Option[Cookie], Option[Cookie]) = {
    (cookies.get("username"), cookies.get("token"))
  }

}
