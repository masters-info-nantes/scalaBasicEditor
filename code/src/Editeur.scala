
class Editeur(i_texte: TexteEditable, i_curseur:Curseur){
  def texte = i_texte
  def curseur = i_curseur

  def insert(i_text:String){
    this.texte.add(i_text)
  }
}