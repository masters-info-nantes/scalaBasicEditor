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
  
  /* ---------- Editeur :: deplacerCurseur(dest:Integer) ------- */  
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
  
  /* ---------- Editeur :: selectionner(i_fin:Integer) ------- */    
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
  
  /* ---------- Editeur :: deplacer(dest:Integer) ------- */   
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
}