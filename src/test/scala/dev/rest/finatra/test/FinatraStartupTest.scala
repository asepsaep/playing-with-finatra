package dev.rest.finatra.test

import com.google.inject.Stage
import com.twitter.finatra.http.test.EmbeddedHttpServer
import com.twitter.inject.Test
import dev.rest.finatra.FinatraServer

class FinatraStartupTest extends Test {

  val server = new EmbeddedHttpServer(
    stage = Stage.PRODUCTION,
    twitterServer = new FinatraServer
  )

  "server" in {
    server.assertHealthy()
  }
}
