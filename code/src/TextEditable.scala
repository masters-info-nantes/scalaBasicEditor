class TexteEditable {
  var contenu:String = new String("")

  def add(i_text:String){
    contenu = this.contenu.concat(i_text)
  }
}