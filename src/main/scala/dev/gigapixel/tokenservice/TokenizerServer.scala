package dev.gigapixel.tokenservice

import cats.effect.{Async, Resource}
import cats.syntax.all.*
import com.comcast.ip4s.*
import fs2.io.net.Network
import org.http4s.ember.client.EmberClientBuilder
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.implicits.*
import org.http4s.server.middleware.Logger

object TokenizerServer {

  def run[F[_]: Async: Network]: F[Unit] = {
    val step = for {
      tokenizer <- HelloTokens.tokenizer[F]
      helloWorldAlg = HelloTokens.impl[F](tokenizer)
      // Combine Service Routes into an HttpApp.
      // Can also be done via a Router if you
      // want to extract segments not checked
      // in the underlying routes.
      httpApp = (
        TokenizerRoutes.helloTokens[F](helloWorldAlg) // <+>
      ).orNotFound
      // With Middlewares in place
      finalHttpApp = Logger.httpApp(true, true)(httpApp)
    } yield finalHttpApp

    step
      .flatMap { finalHttpApp =>
        EmberServerBuilder
          .default[F]
          .withHost(ipv4"0.0.0.0")
          .withPort(port"8080")
          .withHttpApp(finalHttpApp)
          .build
          .useForever
      }
      .map(_ => ())
  }

}
