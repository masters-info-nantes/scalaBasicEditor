import org.junit.Assert._
import org.junit.Test
import org.junit.Before

class TestEditeur {

  var editeur:Editeur = _
  
  @Before def initialize() {
    var buffer = new Buffer()
    var curseur = new Curseur()
    editeur = new Editeur(buffer, curseur)
  }
  
  @Test def copierVide() { 
    editeur.copier()
    assertEquals("", editeur.pressePapier.contenu)
  }
  
  @Test(expected=classOf[IndexOutOfBoundsException]) def deplacerCurseurInf() { 
    editeur.deplacerCurseur(-100)
  }
  
  @Test(expected=classOf[IndexOutOfBoundsException]) def deplacerCurseurSup() { 
    editeur.deplacerCurseur(100)
  }
    
  @Test def deplacerCurseurNormal() { 
    editeur.inserer("HelloWorld")
    editeur.deplacerCurseur(4)
  }
    
  @Test def insererRien() { 
    editeur.inserer("")
    assertEquals("", editeur.texte.contenu)
  }
  
  @Test def insererEditeurVide() {     
    editeur.inserer("HelloWorld")
    assertEquals("HelloWorld", editeur.texte.contenu)
    
    editeur.undo()
    assertEquals("", editeur.texte.contenu)    
  }
  
  @Test def insererEditeurPleinSansSelection() { 
    editeur.inserer("HelloWorld")
    editeur.deplacerCurseur(5)
    editeur.inserer("Changed")
    assertEquals("HelloChangedWorld", editeur.texte.contenu) 
    
    editeur.undo()
    assertEquals("HelloWorld", editeur.texte.contenu)     
  }
  
  @Test def selectionnerInf() {     
    editeur.inserer("HelloWorld")
    editeur.deplacerCurseur(5)
    editeur.selectionner(2)
  }
}