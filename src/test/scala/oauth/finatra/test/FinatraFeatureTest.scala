package oauth.finatra.test

import com.twitter.finagle.http.Status.Ok
import com.twitter.finatra.http.test.EmbeddedHttpServer
import com.twitter.inject.server.FeatureTest
import oauth.finatra.FinatraServer

class FinatraFeatureTest extends FeatureTest {

  override val server = new EmbeddedHttpServer(new FinatraServer)

  "Server" should {
    "ping" in {
      server.httpGet(
        path = "/ping",
        andExpect = Ok,
        withBody = """{"pong":"pong"}"""
      )
    }
  }
}
