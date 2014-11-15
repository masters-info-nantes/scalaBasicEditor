abstract class Action {
  def target:String = new String("")
  
  def execute()
  def undo()
}

class Copier extends Action {
  def execute(){}
  def undo(){}
}

class Coller extends Action {
  def execute(){}
  def undo(){} 
}

class Effacer extends Action {
  def execute(){}
  def undo(){}  
}

class Selectionner extends Action {
  def execute(){}
  def undo(){}  
}

class Inserer extends Action {
  def execute(){}
  def undo(){}  
}

class Deplacer extends Action {
  def execute(){}
  def undo(){}  
}

class Remplacer extends Action {
  def execute(){}
  def undo(){}  
}