// Luna: http://download.scala-ide.org/sdk/lithium/e44/scala211/dev/site
object Main {
  def main(args: Array[String]) {
    println("Hello, world!")

    var buffer = new Buffer()
    var geany = new Editeur(buffer)
    geany.insert("salut")

    println(geany.buffer.contenu)
    println(buffer.contenu)
  }
}