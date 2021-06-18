package utils;

import java.util.Arrays;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class SorttausTest {
    private double[] testitaulukko;
    private double[] verrattava;
    
    @Before
    public void initialize() {
        testitaulukko = new double[100];
        verrattava = new double[100];
        
        for (int i = 0; i < 100; i++) {
            double randomLuku = System.nanoTime() % 10;
            testitaulukko[i] = randomLuku;
            verrattava[i] = randomLuku;
        }
    }
    
    @Test
    public void sorttausJarjestaaAlkiotPienimmastaSuurimpaan() {
        double[] jarjestettyTaulukko = new Sorttaus(testitaulukko).sorttausPienimmastaSuurimpaan();
        Arrays.sort(verrattava);
        
        boolean arvotSamat = true;
        for (int i = 0; i < 100; i++) {
            if (jarjestettyTaulukko[i] != verrattava[i]) {
                arvotSamat = false;
                break;
            }
        }
        assertTrue(arvotSamat);
    }
}