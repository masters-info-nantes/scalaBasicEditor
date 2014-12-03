package fr.univnantes.scalaBasicEditor

//import org.junit.Assert._
//import org.junit.Test
//import org.junit.Before
import org.junit._
import Assert._

class TestEditeur {

  var editeur:Editeur = _
  
  @Before def initialize() {
    var buffer = new Buffer()
    var curseur = new Curseur()
    editeur = new Editeur(buffer, curseur)
  }
  
  /* ---------- Editeur :: deplacerCurseur(dest:Int) ------- */  
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
   
  /* ---------- Editeur :: inserer(text:String) ------- */   
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
  
  @Test def insererEditeurPleinAvecSelection() { 
    editeur.inserer("HelloChangedWorld")
    editeur.deplacerCurseur(5)
    editeur.selectionner(12)
    
    editeur.copier()
    editeur.deplacerCurseur(0)
    editeur.selectionner(5)
    editeur.coller()
    assertEquals("ChangedChangedWorld", editeur.texte.contenu)      
    
    editeur.undo()
    assertEquals("HelloChangedWorld", editeur.texte.contenu)     
  }  
  
  /* ---------- Editeur :: getSelection() ------- */   
  @Test def getSelectionNormal() { 
    editeur.inserer("HelloChangedWorld")
    editeur.deplacerCurseur(5)
    editeur.selectionner(12)
    
    assertEquals("Changed", editeur.getSelection())
  }
  
  /* ---------- Editeur :: selectionner(i_fin:Int) ------- */    
  @Test def selectionnerNormal() {     
    editeur.inserer("HelloWorld")
    editeur.deplacerCurseur(5)
    
    editeur.selectionner(8)
    assertEquals("Wor", editeur.getSelection())
    
    editeur.selectionner(2)
    assertEquals("llo", editeur.getSelection())
  }
  
  @Test(expected=classOf[IndexOutOfBoundsException]) def selectionnerInf() {     
    editeur.inserer("HelloWorld")
    editeur.deplacerCurseur(5)
    editeur.selectionner(-100)
  }  
  
  @Test(expected=classOf[IndexOutOfBoundsException]) def selectionnerSup() {     
    editeur.inserer("HelloWorld")
    editeur.deplacerCurseur(5)
    editeur.selectionner(100)
  } 
  
  /* ---------- Editeur :: copier() ------- */  
  @Test def copierVide() { 
    editeur.copier()
    assertEquals("", editeur.pressePapier.contenu)
  }  
  
  @Test def copierPlein() { 
    editeur.inserer("HelloChangedWorld")
    editeur.deplacerCurseur(5)
    editeur.selectionner(12)
    
    editeur.copier()
    assertEquals("Changed", editeur.pressePapier.contenu)
  }   
  
  /* ---------- Editeur :: coller() ------- */  
  // Pareil que insérer dans les autres cas  
  @Test def collerVide() { 
    editeur.coller()
    assertEquals("", editeur.texte.contenu)
  }  
  
  /* ---------- Editeur :: effacer() ------- */   
  @Test def effacerSansSelection() { 
    editeur.inserer("HellooWorld")
    editeur.deplacerCurseur(6)
    
    editeur.effacer();
    assertEquals("HelloWorld", editeur.texte.contenu)
    
    editeur.undo()
    assertEquals("HellooWorld", editeur.texte.contenu)    
  } 
  
  @Test def effacerAvecSelection() { 
    editeur.inserer("HelloChangedWorld")
    editeur.deplacerCurseur(5)
    editeur.selectionner(12)
    
    editeur.effacer();   
    assertEquals("HelloWorld", editeur.texte.contenu)
    
    editeur.undo()
    assertEquals("HelloChangedWorld", editeur.texte.contenu)    
  }
  
  /* ---------- Editeur :: deplacer(dest:Int) ------- */   
  @Test(expected=classOf[IndexOutOfBoundsException]) def deplacerInf() { 
    editeur.inserer("HelloChangedWorld")
    editeur.deplacerCurseur(5)
    editeur.selectionner(12)
    
    editeur.deplacer(-100)   
  }   
  
  @Test(expected=classOf[IndexOutOfBoundsException]) def deplacerSup() { 
    editeur.inserer("HelloChangedWorld")
    editeur.deplacerCurseur(5)
    editeur.selectionner(12)
    
    editeur.deplacer(100)   
  }  
  
  @Test def deplacerNormal() { 
    editeur.inserer("HelloChangedWorld")
    editeur.deplacerCurseur(5)
    editeur.selectionner(12)
    
    editeur.deplacer(0)
    assertEquals("ChangedHelloWorld", editeur.texte.contenu)
    
    editeur.undo()
    assertEquals("HelloChangedWorld", editeur.texte.contenu)    
  }  
  
  /* ---------- Editeur :: remplacer() ------- */   
  @Test def remplacer() { 
    editeur.inserer("HelloChangedWorld")
    editeur.deplacerCurseur(5)
    editeur.selectionner(12)
    
    editeur.remplacer("Replaced")  
    assertEquals("HelloReplacedWorld", editeur.texte.contenu)

    editeur.undo()
    assertEquals("HelloChangedWorld", editeur.texte.contenu)    
  } 
  
  /* ---------- Editeur :: Test multiple avec observer ------- */
  @Test def multipleEditeur() { 
    var buffer = new Buffer()
    
    var editeur1:Editeur = new Editeur(buffer, new Curseur())
    var editeur2:Editeur = new Editeur(new Buffer(), new Curseur())
    buffer.addObserver(editeur2)
    
    assertEquals("", editeur1.texte.contenu)
    assertEquals("", editeur2.texte.contenu) 
    
    editeur1.inserer("HelloWorld")
    
    assertEquals("HelloWorld", editeur1.texte.contenu)
    assertEquals("HelloWorld", editeur2.texte.contenu)     
  }   
  
  /* ---------- Editeur :: Test complet ------- */   
  @Test def editeurComplet() { 
    editeur.inserer("HelloChangedWorld")
    editeur.deplacerCurseur(5)
    editeur.selectionner(12)
    
    editeur.remplacer("Replaced")  
    assertEquals("HelloReplacedWorld", editeur.texte.contenu)

    editeur.selectionner(13)
    editeur.copier()
    editeur.effacer()
    assertEquals("HelloWorld", editeur.texte.contenu)    
    
    editeur.deplacerCurseur(10)
    editeur.coller()
    assertEquals("HelloWorldReplaced", editeur.texte.contenu)
    
    editeur.deplacerCurseur(10)
    editeur.selectionner(18)
    editeur.deplacer(5)
    assertEquals("HelloReplacedWorld", editeur.texte.contenu)   
    
    editeur.undo()
    assertEquals("HelloWorldReplaced", editeur.texte.contenu)     
    
    editeur.undo()
    editeur.undo()
    editeur.undo()
    assertEquals("HelloWorld", editeur.texte.contenu)
    
    editeur.undo()
    editeur.undo()
    assertEquals("HelloReplacedWorld", editeur.texte.contenu)
    
    editeur.undo()
    editeur.undo()
    editeur.undo()
    assertEquals("HelloChangedWorld", editeur.texte.contenu)
    
    editeur.undo()
    editeur.undo()
    editeur.undo()
    assertEquals("", editeur.texte.contenu)    
  }   
}