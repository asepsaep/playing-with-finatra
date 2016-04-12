package oauth.finatra.test

import com.google.inject.Stage
import com.twitter.finatra.http.test.EmbeddedHttpServer
import com.twitter.inject.Test
import oauth.finatra.FinatraServer

class FinatraStartupTest extends Test {

  val server = new EmbeddedHttpServer(
    stage = Stage.PRODUCTION,
    twitterServer = new FinatraServer
  )

  "server" in {
    server.assertHealthy()
  }
}
