package dev.rest.finatra.test

import com.twitter.inject.IntegrationTest
import com.twitter.inject.app.TestInjector
import dev.rest.finatra.modules.TypesafeConfigModule
import dev.rest.finatra.modules.CustomJacksonModule

class FinatraServiceTest extends IntegrationTest {
  val modules = Seq(TypesafeConfigModule, CustomJacksonModule)

  override val injector = TestInjector(modules: _*)

  "product get" should {

    "item " in {
      //      val productService = injector.instance[ProductService]

      //      val response = productService.getProductById(1)

    }
  }

}
