package dao;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class KuvanlukijaTest {
    Kuvanlukija kuvanlukija;
    
    @Before
    public void initialize() {
        kuvanlukija = new Kuvanlukija(92, 112);
    }
    
    @Test
    public void opetusdatamatriisiinLuetaan40Kuvaa() {
        int[][] opetusdata = kuvanlukija.lueOpetusdata(1, 1, 10,4);
        
        boolean kaikkiKuvatLuettu = true;
        
        for (int rivi = 0; rivi < 40; rivi++) {
            boolean kuvavektoriOnTyhja = true;
            for (int sarake = 0; sarake < 10304; sarake++) {
                if (opetusdata[rivi][sarake] != 0) {
                    kuvavektoriOnTyhja = false;
                }
            }
            if (kuvavektoriOnTyhja) {
                kaikkiKuvatLuettu = false;
            }
        }
        
        assertTrue(kaikkiKuvatLuettu);
    }
}