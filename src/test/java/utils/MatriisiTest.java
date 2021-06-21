package utils;

import utils.Matriisi;
import java.util.Arrays;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MatriisiTest {
    Matriisi m;
    
    @Before
    public void initialize() {
        double[][] testimatriisi = {{1,2,3},{4,5,6},{7,8,9}};
        m = new Matriisi(testimatriisi);
    }
    
    @Test
    public void transpoosiVaihtaaSarakkeetRiveiksiJaRivitSarakkeiksi() {
        m.transpoosi();
        double[][] testimatriisinTranspoosi = {{1,4,7},{2,5,8},{3,6,9}};
        
        assertArrayEquals(testimatriisinTranspoosi, m.getMatriisi());
    }   
    
    @Test
    public void matriisinKertominenItsensaTranspoosillaKorvaaMatriisinArvon() {
        double[][] matriisinAlkuperainenArvo = m.getMatriisi();
        m.matriisiKerrottunaMatriisinTranspoosilla();
        
        assertFalse(Arrays.equals(matriisinAlkuperainenArvo, m.getMatriisi()));
    }
    
    @Test
    public void matriisinKertominenItsensaTranspoosillaMuodostaaOikeanTulomatriisin() {
        m.matriisiKerrottunaMatriisinTranspoosilla();
        double[][] tulomatriisi = {{14,32,50},{32,77,122},{50,122,194}};
        
        assertArrayEquals(tulomatriisi, m.getMatriisi());
    }
}