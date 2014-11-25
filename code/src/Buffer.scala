class Buffer {
  var contenu:String = new String("")

  def add(index:Int, text:String){
    if(contenu.length() == 0){
      contenu = text
    }
    else {
     // Ne supprime pas le caractère à index
     replace(index, index - 1, text)        
    }
  }
  
  def append(text:String) {   // A voir
    add(contenu.length()-1,text)
  }
  
  def delete(debut:Int, fin:Int){
    replace(debut, fin, new String(""))   
  }
  
  def replace(debut:Int, fin:Int, text:String){

    // Dépassement
    if(fin < -1 || debut >= contenu.length() 
        || debut < 0 || fin >= contenu.length())
    {
      throw new IndexOutOfBoundsException()
    }
    
    // Extrait les 3 parties de la chaîne
    val avant:String = contenu.substring(0, debut) //if(debut == 0) "" else contenu.substring(0, debut)
    val apres:String = contenu.substring(fin + 1, contenu.length()) // Caractere à indexFin doit être supprimé

    contenu = avant.concat(text.concat(apres))
  }
  
  def set(text:String){
    contenu = text
  }
  
  def get():String = {
    return contenu
  }
  
  // TODO : faire les tests mais déjà testé avec replace
  def get(debut:Integer, fin:Integer):String = {
    
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
  }
}