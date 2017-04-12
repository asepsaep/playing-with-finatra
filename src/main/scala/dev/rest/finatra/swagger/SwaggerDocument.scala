package dev.rest.finatra.swagger

import com.github.xiaodongw.swagger.finatra.FinatraOperation._
import dev.rest.finatra.model.dao.AccountDao
import io.swagger.models.{ Info, Operation, Swagger }

object FinatraSwaggerDocument extends Swagger

object SwaggerDocument {
  FinatraSwaggerDocument.info(new Info()
    .description("The Finatra Seed Project, this is a sample for swagger document generation")
    .version("0.0.1-SNAPSHOT")
    .title("Finatra Seed Project API"))

  implicit protected val finatraSwagger = FinatraSwaggerDocument

  implicit lazy val FindAccountByUsername: Operation = swagger { o =>
    o.summary("Find an account by its username")
    o.tag("Account")
    o.routeParam[String]("username", "Account username", true)
    o.responseWith[AccountDao](200, "OK!")
    o.responseWith(404, "Account not found")
  }

  protected def swagger(f: Operation => Unit): Operation = {
    val op = new Operation
    f(op)
    op
  }

}
