package dev.rest.finatra.controller

import javax.inject.Inject

import com.google.inject.Provider
import com.twitter.finagle.http.{ Cookie, Request }
import dev.rest.finatra.model._
import dev.rest.finatra.model.dao.{ AccountProfileRegistration, LoginRequest }
import dev.rest.finatra.service._
import dev.rest.finatra.swagger.{ SimpleSwaggerSupport, SwaggerDocument }

class AccountController @Inject() (
  accountService:      AccountService,
  profileService:      ProfileService,
  registrationService: RegistrationService,
  loginService:        LoginService,
  subject:             Provider[Option[Token]]
) extends CommonController(subject) with SimpleSwaggerSupport {

  {

    import SwaggerDocument.FindAccountByUsername
    get("/account/:username") { request: Request =>
      println("Ahiyddasdas")
      accountService.findByUsername(request.getParam("username"))
    }

    get("/") { request: Request =>
      import java.lang.management.ManagementFactory
      import java.lang.management.RuntimeMXBean
      import scala.collection.JavaConverters._

      val runtimeMxBean = ManagementFactory.getRuntimeMXBean
      val listOfArguments = runtimeMxBean.getInputArguments

      response.ok.html(s"""<!DOCTYPE html>
                         |<html lang="en">
                         |    <head>
                         |        <title>Title</title>
                         |    </head>
                         |    <body>
                         |        ${System.getProperty("user.dir")}<br>
                         |        ${System.getProperty("java.home")}<br>
                         |        ${listOfArguments.asScala.mkString("<br>")}
                         |    </body>
                         |</html>
                         |""".stripMargin)
    }

    get("/account") { request: Request =>
      accountService.take(request.getIntParam("limit"))
    }

    post("/account") { request: AccountProfileRegistration =>
      accountService.usernameExists(request.username).flatMap { usernameExists =>
        if (usernameExists) response.badRequest.json("""{"errors":["Username exists"]}""").toFuture
        else {
          accountService.emailExists(request.email).flatMap { emailExists =>
            if (emailExists) response.badRequest.json("""{"errors":["Email exists"]}""").toFuture
            else {
              registrationService.create(request)
            }
          }
        }
      }
    }

    post("/login") { request: LoginRequest =>
      loginService.login(request).flatMap { profile =>
        profile.map { token =>
          val tokenCookie = new Cookie(name = "token", value = token.tokenId)
          val tokenUser = new Cookie(name = "username", value = token.username)
          response.ok.json(token).cookie(tokenCookie).cookie(tokenUser).toFuture
        }.getOrElse(response.unauthorized.toFutureException)
      }
    }

    get("/cookie") { request: Request =>
      if (request.cookies.isDefinedAt("token")) response.ok(request.cookies("token"))
      else response.unauthorized
    }

    get("/memberarea") { request: Request =>
      requireUser { token =>
        //        toResponse(profileService.findByUsername(token.username), response)
        profileService.findByUsername(token.username).flatMap { profile =>
          response.ok(profile).toFuture
        }
      }
    }

    get("/register") { request: Request =>
      notLoggedIn {
        response.ok.toFuture
      }
    }

  }

}
