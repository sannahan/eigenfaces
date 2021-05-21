package domain;

import dao.Kuvanlukija;

/**
 * Luokka toteuttaa eigenfaces-algoritmin
 */
public class Eigenfaces {
    private int[][] opetusdata;
    private final int leveys, korkeus;
    
    /**
     * Konstruktori saa opetusdatamatriisin
     * 
     * @param opetusdata kuvavektorit sisältävä matriisi 
     */
    public Eigenfaces(int[][] opetusdata, int leveys, int korkeus) {
        this.opetusdata = opetusdata;
        this.leveys = leveys;
        this.korkeus = korkeus;
    }
    
    /**
     * Metodi laskee keskiarvokasvot ("the average face") opetusdatan kuvista
     * 
     * @return int[]-kuvavektori
     */
    public int[] laskeKeskiarvoKasvot() {
        int[] keskiarvoKasvot = new int[leveys*korkeus];
        for (int pikselinIndeksi = 0; pikselinIndeksi < leveys*korkeus; pikselinIndeksi++) {
            for (int kuvavektori = 0; kuvavektori < 40; kuvavektori++) {
                keskiarvoKasvot[pikselinIndeksi] += opetusdata[kuvavektori][pikselinIndeksi];
            }
            keskiarvoKasvot[pikselinIndeksi] /= 40;
        }
        return keskiarvoKasvot;
    }
}