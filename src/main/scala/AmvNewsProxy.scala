import akka.actor.ActorSystem
import colossus.IOSystem
import colossus.protocols.http.HttpMethod._
import colossus.protocols.http.UrlParsing._
import colossus.protocols.http.{Http, HttpServer, Initializer, RequestHandler}
import colossus.service.Callback
import colossus.service.GenRequestHandler.PartialHandler

object AmvNewsProxy extends App {

  implicit val actorSystem = ActorSystem()
  implicit val ioSystem    = IOSystem()

  HttpServer.start("amv-news-proxy", 9000) { initContext =>
    new Initializer(initContext) {
      override def onConnect =
        serverContext =>
          new RequestHandler(serverContext) {
            override def handle: PartialHandler[Http] = {
              case request @ Get on Root => Callback.successful(request.ok("Hello world!"))
            }
        }
    }
  }
}
