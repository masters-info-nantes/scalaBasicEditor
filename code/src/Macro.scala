import scala.collection.mutable.Queue

class Macro(i_editeur:Editeur, i_target:String) extends Action(i_editeur, i_target) {
  var actions:Queue[Action] = new Queue()
  
  def ajouterAction(action:Action){
    this.actions.enqueue(action)
  }
  
  def execute(){
    var actionsToExec:Queue[Action] = this.actions.clone()
    while(!actionsToExec.isEmpty){
      actionsToExec.dequeue().execute()
    }
  }
  
  def undo(){
    var actionsToUndo:Queue[Action] = this.actions.clone()
    while(!actionsToUndo.isEmpty){
      actionsToUndo.dequeue().execute()
    }    
  }
}