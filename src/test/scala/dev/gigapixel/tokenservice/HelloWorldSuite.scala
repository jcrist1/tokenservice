package dev.gigapixel.tokenservice

import cats.effect.{IO, SyncIO}
import munit.CatsEffectSuite

class HelloWorldSuite extends CatsEffectSuite {

  test("test hello world says hi") {
    HelloTokens
      .tokenizer[IO]
      .map(it => assertEquals(it.tokenize("Boop"), it.tokenize("Boop")))
  }
}
