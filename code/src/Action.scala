abstract class Action(i_target:String, i_editeur:Editeur) {
  var target:String = i_target
  var editeur:Editeur = i_editeur
  var curseur:Curseur = editeur.curseur.clone()
  
  def execute()
  def undo()
}

class Copier(i_target:String, i_editeur:Editeur) extends Action(i_target, i_editeur) {
  def execute(){
    val ancienPressePapier:String = editeur.pressePapier.get()
    editeur.pressePapier.set(target) 
    target = ancienPressePapier
  }
  def undo(){
    editeur.pressePapier.set(target)
  }
}

class Coller(i_target:String, i_editeur:Editeur) extends Action(i_target, i_editeur) {
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

class Effacer(i_target:String, i_editeur:Editeur) extends Action(i_target, i_editeur) {
  def execute(){
    editeur.texte.delete(editeur.curseur.debutSelection, editeur.curseur.finSelection)
  }
  def undo(){
    editeur.texte.add(curseur.debutSelection, target) 
  }  
}

class Selectionner(i_target:String, i_editeur:Editeur) extends Action(i_target, i_editeur) {
  def execute(){}
  def undo(){}  
}

class Inserer(i_target:String, i_editeur:Editeur) extends Action(i_target, i_editeur) {
  def execute(){
    editeur.texte.add(editeur.curseur.debutSelection, target)
  }
  def undo(){
    editeur.texte.delete(curseur.debutSelection, curseur.finSelection)
  }  
}

class Deplacer(i_target:String, i_editeur:Editeur) extends Action(i_target, i_editeur) {
  def execute(){}
  def undo(){}  
}

class Remplacer(i_target:String, i_editeur:Editeur) extends Action(i_target, i_editeur) {
  def execute(){}
  def undo(){
    editeur.texte.set(target)
  }  
}