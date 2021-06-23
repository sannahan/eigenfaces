package utils;

/**
 * Luokka tarjoaa työkaluja matriisien käsittelyyn.
 */
public class Matriisi {
    /**
     * Metodi muuttaa kaksi kokonaislukumatriisia yhdeksi liukulukumatriisiksi.
     * Jos toinen matriiseista on tyhjä, se annetaan jälkimmäisenä parametrina.
     * 
     * @param matriisi1 kokonaislukumatriisi
     * @param matriisi2 kokonaislukumatriisi
     * 
     * @return double[][] liukulukumatriisi
     */
    public static double[][] getLiukulukumatriisi(int[][] matriisi1, int[][] matriisi2) {
        if (matriisi1.length == 0) {
            throw new IllegalArgumentException("Ensimmäinen matriisi ei saa olla tyhjä");
        }
        
        int riveja = matriisi1.length + matriisi2.length;
        int sarakkeita = matriisi1[0].length;
        
        double[][] liukulukumatriisi = new double[riveja][sarakkeita];
 
        for (int rivi = 0; rivi < matriisi1.length; rivi++) {
            for (int sarake = 0; sarake < sarakkeita; sarake++) {
                liukulukumatriisi[rivi][sarake] = matriisi1[rivi][sarake];
            }
        }
        for (int rivi = matriisi1.length; rivi < riveja; rivi++) {
            for (int sarake = 0; sarake < sarakkeita; sarake++) {
                liukulukumatriisi[rivi][sarake] = matriisi2[rivi-matriisi1.length][sarake];
            }
        }
        
        return liukulukumatriisi;
    }
    
    /**
     * Metodi laskee matriisin L = transpoosi(A)*A.
     * 
     * @param double[][] matriisi A
     * @return double[][] matriisi L
     */
    public static double[][] matriisiKerrottunaItsensaTranspoosilla(double[][] matriisi) {
        int tulomatriisinKoko = matriisi.length;
        
        double[][] tulomatriisi = new double[tulomatriisinKoko][tulomatriisinKoko];
        
        for (int rivi = 0; rivi < tulomatriisinKoko; rivi++) {
            for (int sarake = 0; sarake < tulomatriisinKoko; sarake++) {
                tulomatriisi[rivi][sarake] = tulomatriisinAlkio(matriisi[rivi], matriisi[sarake]);
            }
        }
        
        return tulomatriisi;
    }
    
    /**
     * Metodi laskee matriisikertolaskun yhden alkion.
     * 
     * @param double[] rivi
     * @param double[] sarake
     * @return double tulos
     */
    public static double tulomatriisinAlkio(double[] eka, double[] toka) {
        int pituus = eka.length;
        
        double tulos = 0;
        for (int i = 0; i < pituus; i++) {
            tulos += (eka[i] * toka[i]);
        }
        
        return tulos;
    }
    
    /**
     * Metodi tarkistaa, onko double[][] tyhjä käymällä läpi ensimmäisen rivin ja sarakkeen sekä diagonaalin alkioita.
     * 
     * @param double[][]
     * @return true, jos nollasta poikkeavia alkioita ei löydy; false, jos löytyy
     */
    public static boolean tyhja(double[][] matriisi) {
        int minimi = Math.min(matriisi.length, matriisi[0].length);
        boolean kaikkiNollia = true;
        for (int indeksi = 0; indeksi < minimi; indeksi++) {
            if (matriisi[indeksi][0] != 0 || matriisi[0][indeksi] != 0 || matriisi[indeksi][indeksi] != 0) {
                kaikkiNollia = false;
            }
        }
        return kaikkiNollia;
    }
}