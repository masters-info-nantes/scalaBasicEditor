package fr.univnantes.scalaBasicEditor

abstract class Action(i_editeur:Editeur, i_target:String) {
  var target:String = i_target
  var editeur:Editeur = i_editeur
  var curseur:Curseur = editeur.curseur.clone()
  
  def execute()
  def undo()
}

class Copier(i_editeur:Editeur, i_target:String) extends Action(i_editeur, i_target) {
  def execute(){
    val ancienPressePapier:String = editeur.pressePapier.get()
    editeur.pressePapier.set(target) 
    target = ancienPressePapier
  }
  def undo(){
    editeur.pressePapier.set(target)
  }
}

class Effacer(i_editeur:Editeur, i_target:String, i_debut:Int, i_fin:Int) extends Action(i_editeur, i_target) {
  var debut:Int = i_debut
  var fin:Int = i_fin
  
  def execute(){
    editeur.texte.delete(debut, fin)
    editeur.curseur.finSelection = -1
  }
  def undo(){
    if(curseur.selectionActive()){
     editeur.texte.add(curseur.debutSelection, target)      
    }
    else {
     editeur.texte.add(curseur.debutSelection - 1, target)      
    }
    editeur.curseur.copy(curseur)
  }  
}

class Selectionner(i_editeur:Editeur, i_debut:Int, i_fin:Int) extends Action(i_editeur, "") {
  var debut:Int = i_debut
  var fin:Int = i_fin
  
  def execute(){
    editeur.curseur.debutSelection = debut
    editeur.curseur.finSelection = fin
  }
  def undo(){
    editeur.curseur.finSelection = curseur.debutSelection
  }  
}

class Inserer(i_editeur:Editeur, i_target:String) extends Action(i_editeur, i_target) {
  var replaced:String = _
  
  def execute(){
      if(editeur.curseur.selectionActive()){
       replaced = editeur.getSelection()
       editeur.texte.replace(editeur.curseur.debutSelection, editeur.curseur.finSelection - 1, target)
      }
      else {
       editeur.texte.add(editeur.curseur.debutSelection, target) 
      }
      editeur.curseur.debutSelection += target.length()
      editeur.curseur.finSelection = -1
  }
  def undo(){
    if(curseur.selectionActive()){
     editeur.texte.delete(curseur.debutSelection, curseur.debutSelection + target.length() - 1)
     editeur.texte.add(curseur.debutSelection, replaced)
    }
    else {
     editeur.texte.delete(curseur.debutSelection, curseur.debutSelection + target.length() - 1)
    }   
    editeur.curseur.copy(curseur)
  }  
}

class Coller(i_editeur:Editeur, i_target:String) extends Inserer(i_editeur, i_target) {
  // Pareil que insérer mais permet de garder une meilleure trace des actions
}

class Deplacer(i_editeur:Editeur, i_target:String, i_dest:Int) extends Action(i_editeur, i_target) {
  var dest:Int = i_dest
  
  def execute(){
    editeur.texte.delete(editeur.curseur.debutSelection, editeur.curseur.finSelection - 1)
    editeur.texte.add(dest, target)
    
    editeur.curseur.debutSelection += dest
    editeur.curseur.finSelection += dest
  }
  def undo(){
    editeur.texte.delete(dest, dest + target.length() - 1)
    editeur.texte.add(curseur.debutSelection, target)
    editeur.curseur.copy(curseur)
  }  
}

class Remplacer(i_editeur:Editeur, i_target:String) extends Action(i_editeur, i_target) {
  var replaced:String = _
  def execute(){
    replaced = this.editeur.getSelection()
    editeur.texte.delete(this.editeur.curseur.debutSelection, this.editeur.curseur.finSelection - 1)
    editeur.texte.add(this.editeur.curseur.debutSelection, target)
    editeur.curseur.finSelection = editeur.curseur.debutSelection + target.length()
  }
  def undo(){
    editeur.texte.delete(curseur.debutSelection, curseur.debutSelection + target.length() - 1)
    editeur.texte.add(curseur.debutSelection, replaced) 
    editeur.curseur.copy(curseur)
  }  
}

class DeplacerCurseur(i_editeur:Editeur, i_target:String, i_dest:Int) extends Action(i_editeur, i_target) {
  var dest:Int = i_dest
  def execute(){
    editeur.curseur.debutSelection = dest
    editeur.curseur.finSelection = -1
  }
  def undo(){
    editeur.curseur.copy(curseur)
  }
}