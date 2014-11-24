
class Editeur(i_texte: Buffer, i_curseur:Curseur){
  var texte:Buffer = i_texte
  var curseur:Curseur = i_curseur
  var pressePapier:Buffer = new Buffer()
  var invocateur:Invocateur = new Invocateur()

  def undo(){
    this.invocateur.undoLast()
  }
  
  def copier(){
    if(this.curseur.selectionActive()){
      var target:String = texte.contenu.substring(curseur.debutSelection, curseur.finSelection + 1)
      var action:Action = new Copier(this, target)
      invocateur.ajouterEtExecuter(action)
    }
  }
  
  def coller(){
    if(this.pressePapier.contenu.length() > 0){
      var action:Action = new Coller(this, this.pressePapier.contenu)
      invocateur.ajouterEtExecuter(action)
    }
  }
  
  def effacer(){
    if(this.curseur.selectionActive()){
      var action:Action = new Effacer(this, "")
      invocateur.ajouterEtExecuter(action)   
    }
  }
  
  def selectionner(i_fin:Integer){
    var debut:Integer = this.curseur.debutSelection
    var fin:Integer = i_fin
    var tmp:Integer = -1
    
    // Dépassement
    if(fin < 0 || fin > this.texte.contenu.length()){
      throw new IndexOutOfBoundsException()
    }
    
    // Echange debut et fin s'ils sont inversés
    if(fin < debut){
      tmp = fin
      fin = debut
      debut = tmp
    }
    
    var action:Action = new Selectionner(this, debut, fin)
    invocateur.ajouterEtExecuter(action)
  }
  
  def inserer(text:String){
    var action:Action = new Inserer(this, text)
    invocateur.ajouterEtExecuter(action)
  }
  
  def deplacer(){
    // TODO
  }
  def remplacer(){
    // TODO
  }
  
  def deplacerCurseur(dest:Integer){
    if(dest < 0 || dest > this.texte.contenu.length()){
      throw new IndexOutOfBoundsException()
    }
    this.curseur.debutSelection = dest
    this.curseur.finSelection = -1
  }
  
  // Le curseur est forcément au bon endroit (voir déplacerCurseur et sélectionner)
  def getSelection():String = {
    return this.texte.get(this.curseur.debutSelection, this.curseur.finSelection)
  }
}