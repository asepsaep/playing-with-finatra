package dev.rest.finatra.test

import com.twitter.finagle.http.Status.Ok
import com.twitter.finatra.http.test.EmbeddedHttpServer
import com.twitter.inject.server.FeatureTest
import dev.rest.finatra.FinatraServer

class FinatraFeatureTest extends FeatureTest {

  override val server = new EmbeddedHttpServer(new FinatraServer)

  "Server" should {
    "return OK" in {
      server.httpGet(
        path = "/account",
        andExpect = Ok
      //        withBody = """{"pong":"pong"}"""
      )
    }
  }
}
