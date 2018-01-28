package system

import com.google.inject.AbstractModule
import com.google.inject.name.Names
import services.PersonService
import services.impl.PersonServiceImpl

class Module extends AbstractModule{
  def configure() = {
    bind(classOf[PersonService]).annotatedWith(Names.named("personService")).
      to(classOf[PersonServiceImpl])
  }
}
