package domain;

import java.util.Arrays;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import utils.Matriisi;

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
        double[] keskiarvokuva = eigenfaces.laskeKeskiarvokasvot();
        
        assertEquals(5, keskiarvokuva[0], 0.001);
        assertEquals(5, keskiarvokuva[10303], 0.001);
    }
    
    @Test
    public void keskiarvoKasvotVahennetaanOpetusdatasta() {
        double[] keskiarvokuva = eigenfaces.laskeKeskiarvokasvot();
        double[][] opetusdata = eigenfaces.vahennaKeskiarvokasvot();
        
        assertEquals(195, opetusdata[0][0], 0.1);
        assertEquals(195, opetusdata[0][10303], 0.1);
    }
    
    @Test
    public void keskiarvoKasvotVahennetaanAnnetustaVektorista() {
        int[] vektori = new int[10304];
        double[] tulos = eigenfaces.vahennaKeskiarvokasvot(vektori);
        assertEquals(-5, tulos[0], 0.1);
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
    
    @Test
    public void ensimmaisenEigenfacenEnsimmainenJaViimeinenPikseliOnLaskettuOikein() {
        double[][] eigenfacesVektorit = eigenfaces.laskeEigenfaces();
        assertEquals(197.48417658131498, eigenfacesVektorit[0][0], 0.001);
        assertEquals(197.48417658131498, eigenfacesVektorit[0][10303], 0.001);
    }
    
    @Test
    public void eigenfacetLaskettuKonstruktorikutsunJalkeen() {
        assertFalse(Matriisi.tyhja(eigenfaces.getEigenfaces()));
    }
    
    @Test
    public void ensimmainenJaViimeinenKasvoluokkaalkioLaskettuOikein() {
        double[][] kasvoluokat = eigenfaces.laskeKasvoluokat();
        assertEquals(17773.575892318346, kasvoluokat[0][0], 0.1);
        assertEquals(-1.942890293094024E-14, kasvoluokat[9][9], 0.1);
    }
    
    @Test
    public void lyhinEuklidinenEtaisyysPalauttaaMahdollisenIndeksin() {
        int arvaus = eigenfaces.lyhinEuklidinenEtaisyys(new int[10304]);
        assertTrue(arvaus >= 0 && arvaus < 10);
    }
    
    @Test
    public void etsiEnemmistoaVastaavaHenkiloJosEnemmistoLoytyy() {
        int[] hlot = {3,4,4};
        assertEquals(4, eigenfaces.etsiEnemmistoaVastaavaHenkilo(3, hlot));
    }
    
    @Test
    public void palautaLahinHenkiloJosEnemmistoaEiLoydy() {
        int[] hlot = {3,4,5};
        assertEquals(3, eigenfaces.etsiEnemmistoaVastaavaHenkilo(3, hlot));
    }
    
    @Test
    public void etsiKLyhyintaEuklidistaEtaisyytta() {
        int tulos = eigenfaces.kLyhyintaEuklidistaEtaisyytta(new int[10304], 3);
        assertEquals(3, tulos);
    }
    
    @Test
    public void etsiPienimpiaEtaisyyksiaVastaavatHenkilotPalauttaaHenkilotPienimmastaSuurimpaan() {
        double[] etaisyydet = new double[10];
        for (int i = 2; i < 12; i++) {
            etaisyydet[i-2] = i%10;
        }
        
        double[] etaisyydetPienimmastaSuurimpaan = etaisyydet.clone();
        Arrays.sort(etaisyydetPienimmastaSuurimpaan);
        int[] hlot = eigenfaces.etsiPienimpiaEtaisyyksiaVastaavatHenkilot(3, etaisyydet, etaisyydetPienimmastaSuurimpaan);
        
        int[] verrattava = {2,3,0};
        assertArrayEquals(verrattava, hlot);
    }
}