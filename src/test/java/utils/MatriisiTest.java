package utils;

import java.util.Arrays;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MatriisiTest {
    double[][] testimatriisi = {{1,2,3},{4,5,6}}; 
    
    @Test
    public void matriisinKertominenItsensaTranspoosillaPalauttaaOikeanMatriisin() {
        double[][] tulos = Matriisi.matriisiKerrottunaItsensaTranspoosilla(testimatriisi);
        double[][] verrattava = {{14,32},{32,77}};
        
        assertTrue(Arrays.equals(tulos[0], verrattava[0]) && Arrays.equals(tulos[1], verrattava[1]));
    }
    
    @Test
    public void muodostetaanYksiLiukulukumatriisiKahdestaKokonaislukumatriisista() {
        int[][] matriisi1 = {{1,2,3,4},
                            {5,6,7,8},
                            {9,10,11,12}};
        int[][] matriisi2 = {{13,14,15,16},
                            {17,18,19,20},
                            {21,22,23,24}};
        int riveja = 3;
        int sarakkeita = 4;
        
        double[][] yhdistettyMatriisi = Matriisi.getLiukulukumatriisi(matriisi1, matriisi2);
        
        boolean arvotTasmaavat = true;
        for (int rivi = 0; rivi < riveja * 2; rivi++) {
            for (int sarake = 0; sarake < sarakkeita; sarake++) {
                if (rivi < riveja) {
                    if (yhdistettyMatriisi[rivi][sarake] != (double) matriisi1[rivi][sarake]) {
                        arvotTasmaavat = false;
                    }
                } else {
                    if (yhdistettyMatriisi[rivi][sarake] != (double) matriisi2[rivi-riveja][sarake]) {
                        arvotTasmaavat = false;
                    }
                }
            }
        }
        
        assertTrue(arvotTasmaavat);
        assertEquals(riveja * 2, yhdistettyMatriisi.length);
        assertEquals(sarakkeita, yhdistettyMatriisi[0].length);
    }
    
    @Test
    public void muodostetaanYksiLiukulukumatriisiKahdestaKokonaislukumatriisistaKunToinenMatriisiOnTyhja() {
        int[][] matriisi1 = {{1,2,3,4},
                            {5,6,7,8},
                            {9,10,11,12}};
        int[][] matriisi2 = new int[0][0];
        int riveja = 3;
        int sarakkeita = 4;
        
        double[][] yhdistettyMatriisi = Matriisi.getLiukulukumatriisi(matriisi1, matriisi2);
        
        boolean arvotTasmaavat = true;
        for (int rivi = 0; rivi < riveja; rivi++) {
            for (int sarake = 0; sarake < sarakkeita; sarake++) {
                if (yhdistettyMatriisi[rivi][sarake] != (double) matriisi1[rivi][sarake]) {
                    arvotTasmaavat = false;
                }
            }
        }
        
        assertTrue(arvotTasmaavat);
        assertEquals(riveja, yhdistettyMatriisi.length);
        assertEquals(sarakkeita, yhdistettyMatriisi[0].length);
    }
    
    @Test
    public void matriisitYhdistavanMetodinEnsimmainenParametriEiSaaOllaTyhja() {
        try {
            Matriisi.getLiukulukumatriisi(new int[0][0], new int[0][0]);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Ensimmäinen matriisi ei saa olla tyhjä", e.getMessage());
        }
    }
    
    @Test
    public void palautetaanTrueJosMatriisiOnTyhja() {
        assertTrue(Matriisi.tyhja(new double[10][10]));
    }
    
    @Test
    public void palautetaanFalseJosMatriisiEiOleTyhja() {
        assertFalse(Matriisi.tyhja(testimatriisi));
    }
    
    @Test
    public void tulomatriisinAlkioLasketaanOikein() {
        double[] rivi = {1,2,3};
        double[] sarake = {1,2,3};
        assertEquals(14, Matriisi.tulomatriisinAlkio(rivi, sarake), 0.1);
    }
}