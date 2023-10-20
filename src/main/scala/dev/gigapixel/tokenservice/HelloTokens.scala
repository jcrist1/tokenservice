package dev.gigapixel.tokenservice

import cats.Applicative
import dev.gigapixel.tok4j.Tokenizer
import io.circe.{Encoder, Json}
import org.apache.commons.io.IOUtils.resourceToByteArray
import org.http4s.EntityEncoder
import org.http4s.circe.*
import cats.syntax.all.*

trait HelloTokens[F[_]] {
  val tokenizer: Tokenizer
  def hello(n: HelloTokens.Name): F[HelloTokens.Greeting]
}

object HelloTokens {
  def tokenizer[F[_]: Applicative]: F[Tokenizer] = {
    val tokenizerInput = resourceToByteArray("/tokenizer.json").pure[F]
    tokenizerInput.map(Tokenizer.newFromBytes)
  }

  final case class Name(name: String) extends AnyVal

  /** More generally you will want to decouple your edge representations from
    * your internal data structures, however this shows how you can
    * create encoders for your data.
    */
  final case class Greeting(tokens: Seq[String]) extends AnyVal

  private object Greeting {
    implicit val greetingEncoder: Encoder[Greeting] = (a: Greeting) => Json.obj(
      ("message", Json.fromValues(a.tokens.map(Json.fromString)))
    )

    implicit def greetingEntityEncoder[F[_]]: EntityEncoder[F, Greeting] =
      jsonEncoderOf[F, Greeting]
  }

  def impl[F[_]: Applicative](tkzr: Tokenizer): HelloTokens[F] =
    new HelloTokens[F] {
      val tokenizer: Tokenizer = tkzr
      def hello(n: HelloTokens.Name): F[HelloTokens.Greeting] =
        Greeting(tokenizer.tokenize("Hello, " + n.name).toSeq).pure[F]
    }
}
