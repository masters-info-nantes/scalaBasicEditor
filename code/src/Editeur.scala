
class Editeur(i_texte: Buffer, i_curseur:Curseur){
  var texte:Buffer = i_texte
  var curseur:Curseur = i_curseur
  var pressePapier:Buffer = new Buffer()
  var invocateur:Invocateur = new Invocateur()

  def copier(){
    var action:Action = new Copier(texte.contenu.substring(curseur.debutSelection, curseur.finSelection + 1), this)
    invocateur.ajouterEtExecuter(action)
  }
  
  def coller(){}
  def effacer(){}
  def selectionner(){}
  
  def inserer(text:String){
    var action:Action = new Inserer(text, this)
    invocateur.ajouterEtExecuter(action)
  }
  
  def deplacer(){}
  def remplacer(){}
}