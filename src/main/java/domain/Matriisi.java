package domain;

/**
 * Luokka tarjoaa matriisien käsittelyyn tarvittavan laskutoiminnallisuuden
 */
public class Matriisi {
    private double[][] matriisi;
    
    public Matriisi(double[][] matriisi) {
        this.matriisi = matriisi;
    }
    
    /**
     * Transpoosi saadaan, kun matriisin sarakkeet vaihdetaan riveiksi ja rivit sarakkeiksi
     */
    public void transpoosi() {
        int korkeus = matriisi.length;
        int leveys = matriisi[0].length;
        
        double[][] transpoosi = new double[leveys][korkeus];
        
        for (int rivi = 0; rivi < korkeus; rivi++) {
            for (int sarake = 0; sarake < leveys; sarake++) {
                transpoosi[sarake][rivi] = matriisi[rivi][sarake];
            }
        }
        
        matriisi = transpoosi;
    }
    
    /**
     * Metodi palauttaa matriisin
     * 
     * @return double[][] 
     */
    public double[][] getMatriisi() {
        return matriisi;
    }
    
    /**
     * Metodi muodostaa Turkin ja Pentlandin merkintöjä mukaillen matriisin L = matriisin A transpoosi kertaa A.
     * Metodikutsun jälkeen alkuperäinen matriisi A on korvattu matriisilla L.
     */
    public void matriisiKerrottunaMatriisinTranspoosilla() {
        int tulomatriisinKoko = matriisi.length;
        
        double[][] tulomatriisi = new double[tulomatriisinKoko][tulomatriisinKoko];
        
        for (int rivi = 0; rivi < tulomatriisinKoko; rivi++) {
            for (int sarake = 0; sarake < tulomatriisinKoko; sarake++) {
                tulomatriisi[rivi][sarake] = tulomatriisinAlkio(matriisi[rivi], matriisi[sarake]);
            }
        }
        
        matriisi = tulomatriisi;
    }
    
    private double tulomatriisinAlkio(double[] eka, double[] toka) {
        int pituus = eka.length;
        double tulos = 0;
        for (int i = 0; i < pituus; i++) {
            tulos += (eka[i] * toka[i]);
        }
        return tulos;
    }
}