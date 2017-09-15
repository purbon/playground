package tools

import java.io.File
import com.sksamuel.avro4s.AvroOutputStream
import com.sksamuel.avro4s._
import com.sksamuel.avro4s.AvroSchema


 case class Author(name: String, email: String)
 case class Book(title: String, description: String, yearOfPublication: Integer, authors: List[Author])

 case class Ingredient(name: String, sugar: Double, fat: Double)
case class Pizza(name: String, ingredients: Seq[Ingredient], vegetarian: Boolean, vegan: Boolean, calories: Int)


class AvroGenerator {

  implicit val evidence = SchemaFor[Book]
  val schema = AvroSchema[Book]

  def print = {
    //val authors = List(new Author("Pere", "pere.urbon@gmail.com"))
    //val book = Book("Data Engineering", "A book about data enginyering", 2017, authors)
   // val os = AvroOutputStream.data[Book](new File("book.avro"))

  //  os.write(Seq(book))
    
    val pepperoni = Pizza("pepperoni", Seq(Ingredient("pepperoni", 12, 4.4), Ingredient("onions", 1, 0.4)), false, false, 98)
val hawaiian = Pizza("hawaiian", Seq(Ingredient("ham", 1.5, 5.6), Ingredient("pineapple", 5.2, 0.2)), false, false, 91)

val os = AvroOutputStream.data[Pizza](new File("pizzas.avro"))
os.write(Seq(pepperoni, hawaiian))

    os.flush()
    os.close()
  }
}