package com.rbobillo.turing.io

import cats.effect.IO
import com.persist.{JsonMapper, JsonOps}
import com.persist.json.ReadCodec.jobj

object Input {

  def readDescription(path: String): IO[String] =
    IO(scala.io.Source.fromFile(path)).map(_.mkString)

  // TODO: extract from Input (-> Utils ?), and use a parsing lib
  def parseJson(json: String): Map[String, Any] =
    JsonMapper.ToObject(JsonOps.Json(json))

}
