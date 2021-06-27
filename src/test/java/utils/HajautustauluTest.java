package utils;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class HajautustauluTest {
    Hajautustaulu<Integer, String> hajautustaulu;
    
    @Before
    public void initialize() {
        this.hajautustaulu = new Hajautustaulu<>();
    }
    
    @Test
    public void hajautustauluunLisattyArvoLoytyyHakiessa() {
        this.hajautustaulu.lisaa(1, "yksi");
        assertEquals("yksi", this.hajautustaulu.hae(1));
    }
    
    @Test
    public void olemassaolevaanAvaimeenLisattyArvoKorvaaEdellisenArvon() {
        this.hajautustaulu.lisaa(1, "yksi");
        this.hajautustaulu.lisaa(1, "ykkönen");
        assertEquals("ykkönen", this.hajautustaulu.hae(1));
    }
    
    @Test
    public void poistaminenPoistaaArvon() {
        this.hajautustaulu.lisaa(1, "yksi");
        this.hajautustaulu.poista(1);
        assertEquals(null, this.hajautustaulu.hae(1));
    }
    
    @Test
    public void poistaminenPalauttaaPoistetunArvon() {
        this.hajautustaulu.lisaa(1, "yksi");
        assertEquals("yksi", this.hajautustaulu.poista(1));
    }
    
    @Test
    public void hajautustauluKasvaaTarvittaessa() {
        for (int i = 0; i < 15; i++) {
            this.hajautustaulu.lisaa(i, String.valueOf(i));
        }
        assertEquals(15, this.hajautustaulu.koko());
    }
    
    @Test
    public void arvotLoytyvatHajautustaulunKasvattamisenJalkeen() {
        for (int i = 0; i < 15; i++) {
            this.hajautustaulu.lisaa(i, String.valueOf(i));
        }
        boolean kaikkiLoytyivat = true;
        for (int i = 0; i < 15; i++) {
            if (!this.hajautustaulu.hae(i).equals(String.valueOf(i))) {
                kaikkiLoytyivat = false;
            }
        }
        assertTrue(kaikkiLoytyivat);
    }
    
    @Test
    public void josPoistettavaaEiLoydyPalautetaanNull() {
        assertEquals(null, this.hajautustaulu.poista(1));
    }
}