package dev.rest.finatra.controller

import com.google.inject.Provider
import com.twitter.finagle.http.{ Response, Status }
import com.twitter.finatra.http.Controller
import com.twitter.finatra.http.response.ResponseBuilder
import com.twitter.util.Future
import dev.rest.finatra.common.error._
import dev.rest.finatra.model._

class CommonController(subject: Provider[Option[Token]]) extends Controller {

  def requireUser(f: Token => Future[Response]) = {
    subject.get().map { token =>
      f.apply(token)
    } getOrElse {
      response.unauthorized
    }.toFuture
  }

  def notLoggedIn(f: => Future[Response]) = {
    subject.get().map { token =>
      response.forbidden.toFuture
    } getOrElse {
      f
    }
  }

  def toResponse(outcome: Future[_], responseBuilder: ResponseBuilder, status: Status = Status.Ok): Future[Response] = {
    outcome.flatMap { result =>
      status match {
        case Status.Ok      => responseBuilder.ok(result).toFuture
        case Status.Created => responseBuilder.created(result).toFuture
        case _              => responseBuilder.internalServerError.toFuture
      }
    } rescue {
      case UnauthorizedError(s) => responseBuilder.unauthorized.toFutureException
      case ServerError(s, e)    => responseBuilder.internalServerError.toFutureException
    }
  }

}
