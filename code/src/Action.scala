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

class Coller(i_editeur:Editeur, i_target:String) extends Action(i_editeur, i_target) {
  def execute(){
      if(editeur.curseur.selectionActive()){
       editeur.texte.replace(editeur.curseur.debutSelection, editeur.curseur.finSelection, target)         
      }
      else {
       editeur.texte.add(editeur.curseur.debutSelection, target) 
      }
  }
  def undo(){
    editeur.texte.delete(curseur.debutSelection, curseur.finSelection)
  } 
}

class Effacer(i_editeur:Editeur, i_target:String) extends Action(i_editeur, i_target) {
  def execute(){
    editeur.texte.delete(editeur.curseur.debutSelection, editeur.curseur.finSelection)
  }
  def undo(){
    editeur.texte.add(curseur.debutSelection, target) 
  }  
}

class Selectionner(i_editeur:Editeur, i_debut:Integer, i_fin:Integer) extends Action(i_editeur, "") {
  var debut:Integer = i_debut
  var fin:Integer = i_fin
  
  def execute(){
    editeur.curseur.debutSelection = debut
    editeur.curseur.finSelection = fin
  }
  def undo(){}  
}

class Inserer(i_editeur:Editeur, i_target:String) extends Action(i_editeur, i_target) {
  def execute(){
    editeur.texte.add(editeur.curseur.debutSelection, target)
  }
  def undo(){
    editeur.texte.delete(curseur.debutSelection, curseur.debutSelection + target.length() - 1)
  }  
}

class Deplacer(i_editeur:Editeur, i_target:String) extends Action(i_editeur, i_target) {
  def execute(){}
  def undo(){}  
}

class Remplacer(i_editeur:Editeur, i_target:String) extends Action(i_editeur, i_target) {
  def execute(){}
  def undo(){
    editeur.texte.set(target)
  }  
}