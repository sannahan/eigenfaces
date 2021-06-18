package utils;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class ListaTest {
    private Lista<Integer> listaKokonaisluvuille;
    
    @Before
    public void initialize() {
        listaKokonaisluvuille = new Lista<>();
        
        for (int i = 0; i < 15; i++) {
            listaKokonaisluvuille.lisaa(i);
        }
    }
    
    @Test
    public void listaVoiSisaltaaKaikenlaisiaOlioita() {
        Lista<String> listaMerkkijonoille = new Lista<>();
        
        listaMerkkijonoille.lisaa("nolla");
        
        assertTrue(listaKokonaisluvuille.anna(0) instanceof Integer);
        assertTrue(listaMerkkijonoille.anna(0) instanceof String);
    }
    
    @Test
    public void listanKokoKasvaaTarvittaessaAlkuperaisestaKoostaanJokaOnKymmenen() {
        assertEquals(15, listaKokonaisluvuille.koko());
        assertEquals(20, listaKokonaisluvuille.taulukonKoko());
    }
    
    @Test
    public void listaltaVoiPoistaaArvoja() {
        for (int i = 0; i < 15; i++) {
            listaKokonaisluvuille.poista(i);
        }
        
        assertEquals(0, listaKokonaisluvuille.koko());
    }
    
    @Test
    public void listaKutistuuTarvittaessa() {
        for (int i = 0; i < 10; i++) {
            listaKokonaisluvuille.poista(i);
        }
        
        assertEquals(10, listaKokonaisluvuille.taulukonKoko());
    }
    
    @Test
    public void listaPalauttaaTrueJosArvoLoytyyListalta() {
        assertTrue(listaKokonaisluvuille.sisaltaa(0));
    }
    
    @Test
    public void listaPalauttaaFalseJosArvoEiLoydyListalta() {
        assertFalse(listaKokonaisluvuille.sisaltaa(15));
    }
}