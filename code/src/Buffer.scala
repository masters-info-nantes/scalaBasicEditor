class Buffer {
  var contenu:String = new String("")

  def add(index:Int, text:String){
    replace(index, index, text)
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
    val avant:String = text.substring(0, indexDebut)
    val apres:String = text.substring(indexFin, contenu.length() - 1)
    
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