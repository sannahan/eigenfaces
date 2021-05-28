package domain;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.EigenDecomposition;

/**
 * Luokka toteuttaa eigenfaces-algoritmin
 */
public class Eigenfaces {
    private double[][] opetusdata;
    private final int kuvavektorinPituus, kuviaOpetusdatassa;
    
    /**
     * Konstruktori saa opetusdatamatriisin, jonka sisältämät kokonaisluvut luetaan liukulukuina
     * 
     * @param opetusdata kuvavektorit sisältävä matriisi
     * @param leveys yhden kuvan leveys pikseleinä
     * @param korkeus yhden kuvan korkeus pikseleinä
     */
    public Eigenfaces(int[][] opetusdata, int leveys, int korkeus) {
        this.kuvavektorinPituus = leveys * korkeus;
        this.kuviaOpetusdatassa = opetusdata.length;
        this.opetusdata = new double[kuviaOpetusdatassa][kuvavektorinPituus];
        for (int rivi = 0; rivi < kuviaOpetusdatassa; rivi++) {
            for (int sarake = 0; sarake < kuvavektorinPituus; sarake++) {
                this.opetusdata[rivi][sarake] = opetusdata[rivi][sarake];
            }
        }
    }
    
    /**
     * Metodi laskee keskiarvokasvot ("the average face") opetusdatan kuvista
     * 
     * @return int[]-kuvavektori
     */
    public double[] laskeKeskiarvoKasvot() {
        double[] keskiarvoKasvot = new double[kuvavektorinPituus];
        for (int pikselinIndeksi = 0; pikselinIndeksi < kuvavektorinPituus; pikselinIndeksi++) {
            for (int kuvavektori = 0; kuvavektori < kuviaOpetusdatassa; kuvavektori++) {
                keskiarvoKasvot[pikselinIndeksi] += opetusdata[kuvavektori][pikselinIndeksi];
            }
            keskiarvoKasvot[pikselinIndeksi] = (double) keskiarvoKasvot[pikselinIndeksi] / 40;
        }
        return keskiarvoKasvot;
    }
    
    /**
     * Metodi vähentää keskiarvokasvot ("the average face") opetusdatan kuvista
     */
    public void vahennaKeskiarvoKasvotOpetusdatasta(double[] keskiarvoKasvot) {
        for (int kuvavektori = 0; kuvavektori < kuviaOpetusdatassa; kuvavektori++) {
            for (int pikselinIndeksi = 0; pikselinIndeksi < kuvavektorinPituus; pikselinIndeksi++) {
                opetusdata[kuvavektori][pikselinIndeksi] -= keskiarvoKasvot[pikselinIndeksi];
            }
        }
    }
    
    /**
     * Metodi vastaa eigenfaces-vektoreiden muodostamisesta.
     * Matriisiluokan avulla muodostetaan L = transpoosi(A)*A, kun A on opetusdatamatriisi, jonka sarakevektori vastaa yhtä kuvavektoria.
     * EigenDecomposition-luokan avulla matriisin L ominaisvektorit.
     * Yhtälö 6 Turkin ja Pentlandin artikkelista muodostaa eigenfaces-vektorit ominaisvektoreiden avulla.
     * 
     * @return double[][] 10 kpl eigenfaces-vektoreita
     */
    public double[][] laskeEigenfaces() {
        vahennaKeskiarvoKasvotOpetusdatasta(laskeKeskiarvoKasvot());
        
        Matriisi matriisi = new Matriisi(opetusdata);
        matriisi.matriisiKerrottunaMatriisinTranspoosilla();
        
        EigenDecomposition ed = new EigenDecomposition(new Array2DRowRealMatrix(matriisi.getMatriisi()));
        double[][] ominaisvektorit = ed.getV().getData();
        
        double[][] eigenfaces = new double[10][kuvavektorinPituus];
        
        // yhtälö 6 Turkin ja Pentlandin artikkelista
        for (int kuva = 0; kuva < 10; kuva++) {
            for (int sigmaIndeksi = 0; sigmaIndeksi < 40; sigmaIndeksi++) {
                for (int pikseli = 0; pikseli < kuvavektorinPituus; pikseli++) {
                    eigenfaces[kuva][pikseli] += ominaisvektorit[sigmaIndeksi][kuva] * opetusdata[sigmaIndeksi][pikseli];
                }
            }
        }
        
        return eigenfaces;
    }
    
    /**
     * Metodi palauttaa opetusdatamatriisin
     * 
     * @return double[][] opetusdatamatriisi 
     */
    public double[][] getOpetusdata() {
        return opetusdata;
    }
}