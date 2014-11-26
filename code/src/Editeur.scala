
class Editeur(i_texte: Buffer, i_curseur:Curseur) extends Observer[Buffer]{
      
  var texte:Buffer = i_texte
  
  var curseur:Curseur = i_curseur
  var pressePapier:Buffer = new Buffer()
  
  var _macro:Macro = _
  var captureMacro:Boolean = false
  var invocateur:Invocateur = new Invocateur()
    
  def undo(){
    this.invocateur.undoLast()
  }
  
  def copier(){
    if(this.curseur.selectionActive()){
      var action:Action = new Copier(this, this.getSelection())
      this.executerAction(action)
    }
  }
  
  def coller(){
    if(this.pressePapier.contenu.length() > 0){
      var action:Action = new Coller(this, this.pressePapier.contenu)
      this.executerAction(action)
    }
  }
  
  def effacer(){   
    // Cas général : appui sur le touche [Backspace]
    var debut:Integer = this.curseur.debutSelection - 1 // Le buffer compte par caractère et non par espace inter-caractère
    var action:Action = new Effacer(this, this.texte.get(debut, debut), debut, debut) 

    // Cas avec une sélection
    if(this.curseur.selectionActive()){
      action = new Effacer(this, this.getSelection(), this.curseur.debutSelection, this.curseur.finSelection - 1)
    }

    this.executerAction(action) 
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
    this.executerAction(action)
  }
  
  def inserer(text:String){
    var action:Action = new Inserer(this, text)
    this.executerAction(action)
  }
  
  def deplacer(dest:Integer){  
    if(this.curseur.selectionActive()){
      
      if(dest < 0 || dest > this.texte.contenu.length()){
        throw new IndexOutOfBoundsException()
      }
      
      var action:Action = new Deplacer(this, this.getSelection(), dest)
      this.executerAction(action)
    }
  }
  
  def remplacer(text:String){
    if(this.curseur.selectionActive()){      
      var action:Action = new Remplacer(this, text)
      this.executerAction(action)
    }
  }
  
  def deplacerCurseur(dest:Integer){
    if(dest < 0 || dest > this.texte.contenu.length()){
      throw new IndexOutOfBoundsException()
    }
    var action:Action = new DeplacerCurseur(this, "", dest)
    this.executerAction(action)
  }
  
  // Le curseur est forcément au bon endroit (voir déplacerCurseur et sélectionner)
  def getSelection():String = {
    return this.texte.get(this.curseur.debutSelection, this.curseur.finSelection - 1)
  }
  
  def executerAction(action:Action){
    if(this.captureMacro){
      this._macro.ajouterAction(action)
    }
    this.invocateur.ajouterEtExecuter(action)
  }
  
  def demarrerEnregistrementMacro(){
    this._macro = new Macro(this, "")
    this.captureMacro = true
  }
  
  def arreterEnregitrementMacro(){
    this.captureMacro = false
  }
  
  def jouerMacro(){
    if(!this.captureMacro){
     this.invocateur.ajouterEtExecuter(_macro)   
    }
  }
  
  def receiveUpdate(subject: Buffer){
    this.texte.contenu = subject.contenu
  }
}