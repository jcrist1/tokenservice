package dev.gigapixel.tokenservice

import cats.effect.Sync
import cats.implicits._
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
object TokenizerRoutes {

  def helloTokens[F[_] : Sync](H: HelloTokens[F]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._
    HttpRoutes.of[F] {
      case GET -> Root / "hello-tokens" / name =>
        for {
          greeting <- H.hello(HelloTokens.Name(name))
          resp <- Ok(greeting)
        } yield resp
    }
  }

}
