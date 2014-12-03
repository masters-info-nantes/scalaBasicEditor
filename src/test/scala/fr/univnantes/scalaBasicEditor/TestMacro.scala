package fr.univnantes.scalaBasicEditor

//import org.junit.Assert._
//import org.junit.Test
//import org.junit.Before
import org.junit._
import Assert._

class TestMacro {

  var editeur:Editeur = _
  
  @Before def initialize() {
    var buffer = new Buffer()
    var curseur = new Curseur()
    editeur = new Editeur(buffer, curseur)
  }
  
  @Test
  def macroNormal() { 
    editeur.demarrerEnregistrementMacro()
    
    editeur.inserer("HelloChangedWorld")
    editeur.deplacerCurseur(5)
    editeur.selectionner(12)
    editeur.effacer()
    
    assertEquals("HelloWorld", editeur.texte.contenu) 
    editeur.arreterEnregitrementMacro()
    editeur.texte.clear()
    editeur.deplacerCurseur(0)
    assertEquals("", editeur.texte.contenu) 
        
    editeur.jouerMacro()
    assertEquals("HelloWorld", editeur.texte.contenu) 
  }
}