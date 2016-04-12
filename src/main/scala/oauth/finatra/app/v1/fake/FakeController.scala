package oauth.finatra.app.v1.fake

import javax.inject.Inject
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import oauth.finatra.swagger.SimpleSwaggerSupport

class FakeController @Inject() (fakeService: FakeService) extends Controller {

  get("/sleep/:id") { request: Request =>
    fakeService.withSleep(request.getIntParam("id"))
  }
}
