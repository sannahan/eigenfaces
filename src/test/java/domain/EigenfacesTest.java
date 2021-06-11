package domain;

import java.util.Arrays;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class EigenfacesTest {
    Eigenfaces eigenfaces;
    int[][] testiOpetusdata;
    
    @Before
    public void initialize() {
       int leveys = 92;
       int korkeus = 112;
       
       testiOpetusdata = new int[40][leveys*korkeus];
       testiOpetusdata[0][0] = 200;
       testiOpetusdata[0][10303] = 200;
       
       eigenfaces = new Eigenfaces(4, testiOpetusdata);
    }
    
    @Test
    public void keskiarvokasvojenEnsimmainenJaViimeinenPikseliOnLaskettuOikein() {
        double[] keskiarvokuva = eigenfaces.laskeKeskiarvoKasvot();
        
        assertEquals(5, keskiarvokuva[0], 0.001);
        assertEquals(5, keskiarvokuva[10303], 0.001);
    }
    
    @Test
    public void keskiarvoKasvotVahennetaanOpetusdatasta() {
        double[] keskiarvokuva = eigenfaces.laskeKeskiarvoKasvot();
        double[][] opetusdata = eigenfaces.vahennaKeskiarvoKasvotOpetusdatasta(keskiarvokuva);
        
        assertEquals(195, opetusdata[0][0], 0.1);
        assertEquals(195, opetusdata[0][10303], 0.1);
    }
    
    @Test
    public void lahimmanNaapurinLaskevaMetodiHeittaaVirheenJosKParillinen() {
        try {
            eigenfaces.kLyhyintaEuklidistaEtaisyytta(new int[10304], 2);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Kokonaislukuparametrin tulee olla pariton", e.getMessage());
        }
    }
}