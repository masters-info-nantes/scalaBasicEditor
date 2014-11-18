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
    
    // Dépassement non gérable
    if(fin < 0 || debut >= contenu.length()){
      throw new IndexOutOfBoundsException();
    }
    
    // Dépassement à recadrer
    val indexDebut:Int = if(debut < 0) 0 else debut;
    val indexFin:Int = if(fin >= contenu.length()) (contenu.length() - 1) else fin 

    // Extrait les 3 parties de la chaîne
    val avant:String = contenu.substring(0, indexDebut)
    val apres:String = contenu.substring(indexFin + 1, contenu.length()) // Caractere à indexFin doit être supprimé

    contenu = avant.concat(text.concat(apres))
  }
  
  def set(text:String){
    contenu = text
  }
  
  def get():String = {
    return contenu
  }
  
  def clear(){
    contenu = new String("")
  }
}