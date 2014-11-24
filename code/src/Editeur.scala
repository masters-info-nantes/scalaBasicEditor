
class Editeur(i_texte: Buffer, i_curseur:Curseur){
  var texte:Buffer = i_texte
  var curseur:Curseur = i_curseur
  var pressePapier:Buffer = new Buffer()
  var invocateur:Invocateur = new Invocateur()

  def copier(){
    if(this.curseur.selectionActive()){
      var action:Action = new Copier(texte.contenu.substring(curseur.debutSelection, curseur.finSelection + 1), this)
      invocateur.ajouterEtExecuter(action)
    }
  }
  
  def coller(){
    if(this.pressePapier.contenu.length() > 0){
      var action:Action = new Coller(this.pressePapier.contenu, this)
      invocateur.ajouterEtExecuter(action)
    }
  }
  def effacer(){
    if(this.curseur.selectionActive()){
      var action:Action = new Effacer("", this)
      invocateur.ajouterEtExecuter(action)   
    }
  }
  
  def selectionner(){}
  
  def inserer(text:String){
    var action:Action = new Inserer(text, this)
    invocateur.ajouterEtExecuter(action)
  }
  
  def deplacer(){}
  def remplacer(){}
}