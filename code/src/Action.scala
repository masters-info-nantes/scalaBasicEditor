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
  def undo(){
    editeur.curseur.finSelection = curseur.debutSelection
  }  
}

class Inserer(i_editeur:Editeur, i_target:String) extends Action(i_editeur, i_target) {
  var replaced:String = _
  
  def execute(){
      if(editeur.curseur.selectionActive()){
       replaced = editeur.texte.get(editeur.curseur.debutSelection, editeur.curseur.finSelection)
       editeur.texte.replace(editeur.curseur.debutSelection, editeur.curseur.finSelection - 1, target)
      }
      else {
       editeur.texte.add(editeur.curseur.debutSelection, target) 
      }
  }
  def undo(){
    if(curseur.selectionActive()){
     editeur.texte.delete(curseur.debutSelection, curseur.finSelection)
     editeur.texte.add(curseur.debutSelection, replaced)
    }
    else {
     editeur.texte.delete(curseur.debutSelection, curseur.debutSelection + target.length() - 1)
    }
  }  
}

class Coller(i_editeur:Editeur, i_target:String) extends Inserer(i_editeur, i_target) {
  // Pareil que insérer mais permet de garder une meilleure trace des actions
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