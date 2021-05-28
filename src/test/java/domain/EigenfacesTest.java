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
       
       eigenfaces = new Eigenfaces(testiOpetusdata, leveys, korkeus);
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
        eigenfaces.vahennaKeskiarvoKasvotOpetusdatasta(keskiarvokuva);
        
        assertEquals(195, eigenfaces.getOpetusdata()[0][0], 0.1);
        assertEquals(195, eigenfaces.getOpetusdata()[0][10303], 0.1);
    }
}