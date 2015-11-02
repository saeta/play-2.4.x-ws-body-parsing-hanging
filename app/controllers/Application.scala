package controllers

import javax.inject.Inject

import play.api._
import play.api.http.HeaderNames
import play.api.libs.ws.WSAPI
import play.api.mvc._

import scala.concurrent.ExecutionContext

class Application @Inject() (wsApi: WSAPI)(implicit ec: ExecutionContext) extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }


  def postExample(addBodyLength: Boolean = false) = Action.async {
    val body = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa".getBytes("UTF-8")
    var basicRequest = wsApi.url("http://requestb.in/ty87apty")
      .withMethod("POST")
      .withHeaders(HeaderNames.CONTENT_TYPE -> "application/x-www-form-urlencoded")
      .withBody(body)
    if (addBodyLength) {
      basicRequest = basicRequest.withHeaders(HeaderNames.CONTENT_LENGTH -> body.length.toString)
    }
    basicRequest.stream().map { case (header, stream) =>
      Ok(s"Got Header: $header with stream $stream")
    }
  }
}
