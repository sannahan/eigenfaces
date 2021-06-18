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
}