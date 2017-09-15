package models

import com.sksamuel.elastic4s.{ElasticsearchClientUri, TcpClient}
import com.sksamuel.elastic4s.searches.RichSearchResponse
import org.elasticsearch.action.support.WriteRequest.RefreshPolicy
import org.elasticsearch.common.settings.Settings
import com.sksamuel.elastic4s.http.HttpClient
import com.sksamuel.elastic4s.http.search.SearchResponse
import com.sksamuel.elastic4s.http.index.IndexResponse

// circe
import com.sksamuel.elastic4s.circe._
import io.circe.generic.auto._


class Schema {
 
  // you must import the DSL to use the syntax helpers
  import com.sksamuel.elastic4s.http.ElasticDsl._

  val client = HttpClient(ElasticsearchClientUri("localhost", 9200))

  def addDocument(key:String, doc:String): IndexResponse = {
    client.execute {
        indexInto("schemas" / "avro").doc(doc).withId(key)
    }.await
  }
}