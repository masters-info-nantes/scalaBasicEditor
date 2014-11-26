class Curseur {
  var debutSelection:Int = 0
  var finSelection:Int = -1
  
  def selectionActive():Boolean = {
    return debutSelection >= 0 && finSelection > 0
  }
  
  def copy(curseur:Curseur){
    this.debutSelection = curseur.debutSelection
    this.finSelection = curseur.finSelection
  }
  
  override def clone():Curseur = {
    var curseur:Curseur =  new Curseur()
    
    curseur.debutSelection = debutSelection
    curseur.finSelection = finSelection
    
    return curseur
  }
}