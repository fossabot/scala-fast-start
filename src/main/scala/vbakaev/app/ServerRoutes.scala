package vbakaev.app

import java.time.Clock

import akka.http.scaladsl.server.{Route, RouteConcatenation}
import vbakaev.app.config.AppConfig
import vbakaev.app.interfaces._
import vbakaev.app.interfaces.swagger.{SwaggerInterface, SwaggerUIInterface}

class ServerRoutes(appConfig: AppConfig)(implicit clock: Clock) extends RouteConcatenation {

  private val documentedServices: Set[Interface] = Set(
    new StatusInterface()
  )

  private val services: Set[Interface] = Set(
    SwaggerUIInterface,
    new SwaggerInterface(appConfig.http.appRoot, documentedServices)
  )

  val routes: Route = (services ++ documentedServices).map(_.routes).reduce(_ ~ _)

}
