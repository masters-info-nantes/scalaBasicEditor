// Luna: http://download.scala-ide.org/sdk/lithium/e44/scala211/dev/site

object Main {
  def main(args: Array[String]) {
    println("Hello, world!")

    var buffer = new Buffer()
    var curseur = new Curseur()
    
    var geany = new Editeur(buffer, curseur)
    geany.insert("salut")

    println(geany.texte.contenu)
    println(buffer.contenu)
  }
}