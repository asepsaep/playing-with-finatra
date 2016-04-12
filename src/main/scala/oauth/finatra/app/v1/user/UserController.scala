package oauth.finatra.app.v1.user

import javax.inject.Inject
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import oauth.finatra.swagger.{ SimpleSwaggerSupport, SwaggerDocument }

class UserController @Inject() (userService: UserService) extends Controller with SimpleSwaggerSupport {

  {
    import SwaggerDocument.FindUserById
    get("/users/:id") { request: Request =>
      userService.findByIdWithQuill(request.getIntParam("id"))
    }
  }

}
