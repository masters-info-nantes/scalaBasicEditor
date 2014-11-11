
class Editeur(i_buffer: Buffer){
  def buffer = i_buffer

  def insert(i_text:String){
    this.buffer.add(i_text)
  }
}