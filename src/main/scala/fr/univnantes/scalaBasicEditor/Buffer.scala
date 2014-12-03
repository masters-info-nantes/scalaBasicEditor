package fr.univnantes.scalaBasicEditor

class Buffer extends Subject[Buffer]{
  var contenu:String = new String("")

  def add(index:Int, text:String){
    if(contenu.length() == 0){
      contenu = text
    }
    else if(index == 0){
      contenu = text + contenu
    }
    else if(index == contenu.length()){
      contenu += text
    }
    else {
     // Ne supprime pas le caractère à index
     replace(index, index - 1, text)        
    }
    this.notifyObservers()
  }
  
  def delete(debut:Int, fin:Int){
    replace(debut, fin, new String(""))   
  }
  
  def replace(debut:Int, fin:Int, text:String){

    // Dépassement
    if(fin < 0 || debut >= contenu.length() 
        || debut < 0 || fin >= contenu.length())
    {
      throw new IndexOutOfBoundsException()
    }
    
    // Extrait les 3 parties de la chaîne
    val avant:String = contenu.substring(0, debut)
    val apres:String = contenu.substring(fin + 1, contenu.length()) // Caractere à indexFin doit être supprimé

    contenu = avant.concat(text.concat(apres))
    this.notifyObservers()
  }
  
  def set(text:String){
    contenu = text
    this.notifyObservers()
  }
  
  def get():String = {
    return contenu
  }

  def get(debut:Int, fin:Int):String = {
    
    // Dépassement
    if(fin < 0 || debut >= contenu.length() 
        || debut < 0 || fin >= contenu.length())
    {
      throw new IndexOutOfBoundsException()
    }
    
    return contenu.substring(debut, fin + 1)
  }
  
  def clear(){
    contenu = new String("")
    this.notifyObservers()
  }
}