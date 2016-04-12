package oauth.finatra.modules

import com.google.inject.{ Singleton, Provides }
import com.twitter.inject.TwitterModule
import com.typesafe.config.Config
import io.getquill.FinagleMysqlSourceConfig
import io.getquill.naming.SnakeCase
import io.getquill._
import io.getquill.sources.finagle.mysql.FinagleMysqlSource

object DatabaseModule extends TwitterModule {

  type DatabaseSource = FinagleMysqlSource[SnakeCase]

  @Provides @Singleton
  def provideDataBaseSource(conf: Config): DatabaseSource = source(new FinagleMysqlSourceConfig[SnakeCase]("") {
    override def config = conf.getConfig("quill.db")
  })

}
