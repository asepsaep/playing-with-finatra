package dev.rest.finatra.modules

import com.google.inject.{ Provides, Singleton }
import com.twitter.inject.TwitterModule
import com.twitter.inject.requestscope.RequestScopeBinding
import dev.rest.finatra.model.Token

object AuthModule extends TwitterModule with RequestScopeBinding {

  override def configure(): Unit = {
    bindRequestScope[Option[Token]]
  }

}
