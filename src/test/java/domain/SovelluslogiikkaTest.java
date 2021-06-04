package domain;

import dao.Kuvanlukija;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class SovelluslogiikkaTest {
    Sovelluslogiikka sovelluslogiikka;
    Sovelluslogiikka sovelluslogiikkaMock;
    Kuvanlukija mockKuvanlukija;
    Eigenfaces mockEigenfaces;
    
    @Before
    public void initialize() {
        this.sovelluslogiikka = new Sovelluslogiikka();
        
        mockKuvanlukija = mock(Kuvanlukija.class);
        mockEigenfaces = mock(Eigenfaces.class);
        this.sovelluslogiikkaMock = new Sovelluslogiikka(mockKuvanlukija, mockEigenfaces);
    }
    
    @Test
    public void metodiKirjoitaPikseliKuvaksiKirjoittaaPikselitOikeaanPaikkaanKunVektoriSisaltaaKokonaislukuja() {
        int[] testivektori = new int[10304];
        testivektori[1] = 255;
        
        Image testikuva = sovelluslogiikka.kirjoitaPikselitKuvaksi(testivektori);
        PixelReader lukija = testikuva.getPixelReader();
        
        String vari = lukija.getColor(1,0).toString();
        assertEquals("0xffffffff", vari);
        
        vari = lukija.getColor(0,0).toString();
        assertEquals("0x000000ff", vari);
    }
    
    @Test
    public void metodiKirjoitaPikseliKuvaksiKirjoittaaPikselitOikeaanPaikkaanKunVektoriSisaltaaLiukulukuja() {
        double[] testivektori = new double[10304];
        testivektori[1] = 255.5;
        
        Image testikuva = sovelluslogiikka.kirjoitaPikselitKuvaksi(testivektori);
        PixelReader lukija = testikuva.getPixelReader();
        
        String vari = lukija.getColor(1,0).toString();
        assertEquals("0xffffffff", vari);
        
        vari = lukija.getColor(0,0).toString();
        assertEquals("0x000000ff", vari);
    }
    
    @Test
    public void metodiKirjoitaKeskiarvokasvotKutsuuKeskiarvotLaskevaaMetodia() {
        when(mockEigenfaces.laskeKeskiarvoKasvot()).thenReturn(new double[10304]);
        
        sovelluslogiikkaMock.kirjoitaKeskiarvokasvot();
        verify(mockEigenfaces, times(1)).laskeKeskiarvoKasvot();
    }
    
    @Test
    public void metodiKirjoitaEigenfaceKutsuuEigenfacetLaskevaaMetodia() {
        when(mockEigenfaces.laskeEigenfaces()).thenReturn(new double[10][10304]);
        
        sovelluslogiikkaMock.kirjoitaEigenface(0);
        verify(mockEigenfaces, times(1)).laskeEigenfaces();
    }
    
    @Test
    public void metodiHaeHenkiloMuodostaaOikeanTiedostopolun() {
        when(mockKuvanlukija.lueKuva("att_faces/s20/1.pgm")).thenReturn(new int[10304]);
        
        sovelluslogiikkaMock.haeHenkilo("20", "6");
        verify(mockKuvanlukija).lueKuva(eq("att_faces/s20/6.pgm"));
    }
    
    @Test
    public void metodiHaeHenkiloKutsuuEuklidisenEtaisyydenLaskevaaMetodia() {
        when(mockKuvanlukija.lueKuva("att_faces/s20/1.pgm")).thenReturn(new int[10304]);
        
        sovelluslogiikkaMock.haeHenkilo("20", "6");
        verify(mockEigenfaces, times(1)).euklidinenEtaisyys(any());
    }
    
    @Test
    public void metodiKirjoitaHakutulosMuodostaaOikeanTiedostopolun() {
        when(mockKuvanlukija.lueKuva("att_faces/s20/6.pgm")).thenReturn(new int[10304]);
        
        sovelluslogiikkaMock.kirjoitaHakutulos("20", "6");
        verify(mockKuvanlukija).lueKuva(eq("att_faces/s20/6.pgm"));
    }
}