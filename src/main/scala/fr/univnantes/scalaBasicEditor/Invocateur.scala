package fr.univnantes.scalaBasicEditor

import scala.collection.mutable.Stack

class Invocateur {
  val actions:Stack[Action] = new Stack()
  
  def ajouterEtExecuter(action:Action){
    this.ajouter(action)
    action.execute()
  }
  
  def ajouter(action:Action){
    actions.push(action) 
  }
  
  def undoLast(){
    actions.pop().undo()
  }
}