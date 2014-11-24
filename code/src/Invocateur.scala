import scala.collection.mutable.Stack

class Invocateur {
  val actions:Stack[Action] = new Stack()
  
  def ajouterEtExecuter(action:Action){
    actions.push(action)
    action.execute()
  }
  
  def undoLast(){
    actions.pop().undo()
  }
}