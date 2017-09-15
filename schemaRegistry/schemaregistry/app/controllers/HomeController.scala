package controllers

import javax.inject._
import play.api._
import play.api.mvc._

import play.api.libs.json._
import com.sksamuel.avro4s.{ AvroOutputStream, AvroInputStream }
import models.Schema

case class Place(name:String) 


/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents, es: Schema) extends AbstractController(cc) {

  implicit val placeReads = Json.reads[Place]
  implicit val placeWrites = Json.writes[Place]

  
  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }
  
  def listPlaces() = Action { implicit request: Request[AnyContent] =>
    val json = Place("Berlin")
 
    Ok(Json.toJson(json))
  }
  
  def newSchema(key: String) = Action { implicit request: Request[AnyContent] =>
    val json = request.body.asJson
    println(json)
    println("----")
    json match {
      case Some(jsonText) => { 
        val indexResponse = es.addDocument(key, Json.asciiStringify(jsonText))
        val response = Map("version" -> indexResponse.version)
        Ok(Json.toJson(response))
      }
      case None => NoContent  
    }
   
  }
}
