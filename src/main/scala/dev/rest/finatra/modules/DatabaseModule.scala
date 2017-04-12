package dev.rest.finatra.modules

import com.github.mauricio.async.db.postgresql.PostgreSQLConnection
import com.google.inject.{ Provides, Singleton }
import com.twitter.inject.TwitterModule
import com.typesafe.config.Config
//import io.getquill.FinagleMysqlSourceConfig
import io.getquill._
import io.getquill.naming.SnakeCase
import io.getquill.sources.async.PostgresAsyncSource
import io.getquill.sources.sql.idiom.PostgresDialect
//import io.getquill.sources.finagle.mysql.FinagleMysqlSource

object DatabaseModule extends TwitterModule {

  //  type DatabaseSource = FinagleMysqlSource[SnakeCase]
  //
  //  @Provides @Singleton
  //  def provideDataBaseSource(conf: Config): DatabaseSource = source(new FinagleMysqlSourceConfig[SnakeCase]("") {
  //    override def config = conf.getConfig("quill.db")
  //  })

  type DatabaseSource = PostgresAsyncSource[PostgresDialect, SnakeCase, PostgreSQLConnection]

  @Provides @Singleton
  def provideDataBaseSource(conf: Config): DatabaseSource = source(new PostgresAsyncSourceConfig[SnakeCase]("") {
    override def config = conf.getConfig("db")
  })

}
