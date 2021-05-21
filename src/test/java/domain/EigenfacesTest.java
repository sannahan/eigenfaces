package domain;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class EigenfacesTest {
    Eigenfaces eigenfaces;
    
    @Before
    public void initialize() {
       int leveys = 92;
       int korkeus = 112;
       
       int[][] testiOpetusdata = new int[40][leveys*korkeus];
       testiOpetusdata[0][0] = 200;
       testiOpetusdata[0][10303] = 200;
       
       eigenfaces = new Eigenfaces(testiOpetusdata, leveys, korkeus);
    }
    
    @Test
    public void keskiarvokasvojenEnsimmainenJaViimeinenPikseliOnLaskettuOikein() {
        int[] keskiarvokuva = eigenfaces.laskeKeskiarvoKasvot();
        
        assertEquals(5, keskiarvokuva[0]);
        assertEquals(5, keskiarvokuva[10303]);
    }
}