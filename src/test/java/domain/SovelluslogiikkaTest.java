package domain;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class SovelluslogiikkaTest {
    Sovelluslogiikka sovelluslogiikka;
    
    @Before
    public void initialize() {
        this.sovelluslogiikka = new Sovelluslogiikka();
    }
    
    @Test
    public void metodiKirjoitaPikseliKuvaksiKirjoittaaPikselitOikeaanPaikkaan() {
        int[] testivektori = new int[10304];
        testivektori[1] = 255;
        
        Image testikuva = sovelluslogiikka.kirjoitaPikselitKuvaksi(testivektori);
        PixelReader lukija = testikuva.getPixelReader();
        
        String vari = lukija.getColor(1,0).toString();
        assertEquals("0xffffffff", vari);
        
        vari = lukija.getColor(0,0).toString();
        assertEquals("0x000000ff", vari);
    }
}