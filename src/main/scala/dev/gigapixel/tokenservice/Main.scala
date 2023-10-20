package dev.gigapixel.tokenservice

import cats.effect.{IO, IOApp}

import cats.implicits._
import cats.syntax.traverse.toTraverseOps

object Main extends IOApp.Simple {
  import IO.asyncForIO

  // This is your new "main"!
  def run: IO[Unit] = TokenizerServer.run
}
