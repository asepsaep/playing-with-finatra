package oauth.finatra.swagger

import com.github.xiaodongw.swagger.finatra.FinatraOperation._
import com.twitter.finagle.http.Response
import io.swagger.models.Info
import io.swagger.models.Swagger
import io.swagger.models.Operation
import oauth.finatra.app.v1.ping.Pong
import oauth.finatra.app.v1.user.UserDto

object FinatraSwaggerDocument extends Swagger

object SwaggerDocument {
  FinatraSwaggerDocument.info(new Info()
    .description("The Finatra Seed Project, this is a sample for swagger document generation")
    .version("0.0.1-SNAPSHOT")
    .title("Finatra Seed Project API"))

  implicit protected val finatraSwagger = FinatraSwaggerDocument

  implicit lazy val PingDocument: Operation = swagger { o =>
    o.summary("Ping")
    o.tag("Default")
    o.responseWith[Pong](200, "OK!")
    o.responseWith(404, "Oops, not found!")
  }

  implicit lazy val FindUserById: Operation = swagger { o =>
    o.summary("By Id with Quill")
    o.tag("Find User")
    o.routeParam[Int]("Id", "User Id")
    o.responseWith[UserDto](200, "OK!")
    o.responseWith(404, "Oops not found")
  }

  protected def swagger(f: Operation => Unit): Operation = {
    val op = new Operation
    f(op)
    op
  }

}
