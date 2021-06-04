package domain;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.EigenDecomposition;

/**
 * Luokka toteuttaa eigenfaces-algoritmin
 */
public class Eigenfaces {
    private double[][] opetusdata;
    private double[][] kasvoluokat;
    private final int kuvavektorinPituus, kuviaOpetusdatassa;
    
    /**
     * Konstruktori saa opetusdatamatriisin, jonka sisältämät kokonaisluvut luetaan liukulukuina.
     * Turkin ja Pentlandin artikkelissa kuvat ovat opetusdatassa sarakevektoreita, mutta laskennan helpottamiseksi
     * kuvavektoreita käsitellään tässä luokassa rivivektoreina.
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
     * @return double[]-kuvavektori
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
     * 
     * @return double[][] opetusdata, josta on vähennetty keskiarvokasvot
     */
    public double[][] vahennaKeskiarvoKasvotOpetusdatasta(double[] keskiarvoKasvot) {
        double[][] tulos = new double[kuviaOpetusdatassa][kuvavektorinPituus];
        for (int kuvavektori = 0; kuvavektori < kuviaOpetusdatassa; kuvavektori++) {
            for (int pikselinIndeksi = 0; pikselinIndeksi < kuvavektorinPituus; pikselinIndeksi++) {
                tulos[kuvavektori][pikselinIndeksi] = opetusdata[kuvavektori][pikselinIndeksi] - keskiarvoKasvot[pikselinIndeksi];
            }
        }
        return tulos;
    }
    
    /**
     * Metodi vastaa eigenfaces-vektoreiden muodostamisesta.
     * Matriisiluokan avulla muodostetaan L = transpoosi(A)*A, kun A on opetusdatamatriisi, 
     * jonka sarakevektori vastaa yhtä kuvavektoria.
     * EigenDecomposition-luokan avulla matriisin L ominaisvektorit.
     * Yhtälö 6 Turkin ja Pentlandin artikkelista muodostaa eigenfaces-vektorit ominaisvektoreiden avulla.
     * 
     * @return double[][] 10 kpl eigenfaces-vektoreita
     */
    public double[][] laskeEigenfaces() {
        double[][] uusiOpetusdata = vahennaKeskiarvoKasvotOpetusdatasta(laskeKeskiarvoKasvot());
        
        Matriisi matriisi = new Matriisi(uusiOpetusdata);
        matriisi.matriisiKerrottunaMatriisinTranspoosilla();
        
        EigenDecomposition ed = new EigenDecomposition(new Array2DRowRealMatrix(matriisi.getMatriisi()));
        // Ominaisvektorit ovat muusta luokan toiminnallisuudesta poiketen
        // sarakevektoreita ominaisvektorit-matriisissa.
        double[][] ominaisvektorit = ed.getV().getData();
        
        int eigenfacesMaara = 10;
        double[][] eigenfaces = new double[eigenfacesMaara][kuvavektorinPituus];
        
        // yhtälö 6 Turkin ja Pentlandin artikkelista
        for (int kuva = 0; kuva < eigenfacesMaara; kuva++) {
            for (int sigmaIndeksi = 0; sigmaIndeksi < kuviaOpetusdatassa; sigmaIndeksi++) {
                for (int pikseli = 0; pikseli < kuvavektorinPituus; pikseli++) {
                    eigenfaces[kuva][pikseli] += ominaisvektorit[sigmaIndeksi][kuva] * uusiOpetusdata[sigmaIndeksi][pikseli];
                }
            }
        }
        
        return eigenfaces;
    }
    
    /**
     * Metodi laskee Turkin ja Pentlandin artikkelin yhtälön 7 avulla jokaisen opetusdatan henkilön kasvoluokan.
     * Metodi hyödyntää tietoa siitä, että opetusdata sisältää jokaisesta henkilöstä (10 kpl) 4 kuvaa, 
     * jotka ovat opetusdatassa peräkkäisinä riveinä.
     * Opetusdatan kuvavektorin (,josta on vähennetty keskiarvokasvot) ja eigenface-kuvavektorin matriisitulo 
     * muodostaa painon, ja painojen muodostama vektori kuvastaa kunkin eigenface-kuvavektorin osuutta opetusdatan 
     * kuvavektorin esittämisessä.
     * Koska opetusdatassa on 4 kuvaa jokaisesta henkilöstä, lasketaan kasvoluokkaan näiden painojen keskiarvo.
     */
    public void laskeOpetusdatanKasvoluokat() {
        double[][] uusiOpetusdata = vahennaKeskiarvoKasvotOpetusdatasta(laskeKeskiarvoKasvot());
        double[][] eigenfaces = laskeEigenfaces();
        
        this.kasvoluokat = new double[10][10];
        double[] painot = new double[10];
        for (int kuva = 0; kuva < kuviaOpetusdatassa; kuva++) {
            for (int eigenface = 0; eigenface < eigenfaces.length; eigenface++) {
                painot[eigenface] += tulomatriisinAlkio(uusiOpetusdata[kuva], eigenfaces[eigenface]) / 4;
            }
            if ((kuva + 1) % 4 == 0) {
                this.kasvoluokat[(kuva + 1) / 4 - 1] = painot;
                painot = new double[10];
            }
        }
    }
    
    private double tulomatriisinAlkio(double[] eka, double[] toka) {
        int pituus = eka.length;
        double tulos = 0;
        for (int i = 0; i < pituus; i++) {
            tulos += (eka[i] * toka[i]);
        }
        return tulos;
    }
    
    /**
     * Metodi laskee haettavan kuvan kuvavektorin kasvoluokan ja tarkastaa sen euklidisen etäisyyden
     * opetusdatasta muodostettuun 10 kasvoluokkaan.
     * Metodi palauttaa henkilön, jonka kasvoluokan euklidinen etäisyys haettavaan kuvavektoriin on lyhin.
     * 
     * @param int[] haettavan kuvan vektori
     * @return int kasvoluokkaa vastaava henkilö
     */
    public int euklidinenEtaisyys(int[] haettavaKuva) {
        laskeOpetusdatanKasvoluokat();
        
        double[] kuvanPainot = laskeYksittaisenKuvanKasvoluokka(haettavaKuva);
        
        double minEtaisyys = Double.MAX_VALUE;
        int hlo = -1;
        for (int j = 0; j < 10; j++) {
            double etaisyys = 0;
            for (int i = 0; i < 10; i++) {
                etaisyys += Math.pow((kasvoluokat[j][i] - kuvanPainot[i]), 2);
            }
            if (etaisyys < minEtaisyys) {
                minEtaisyys = etaisyys;
                hlo = j;
            }
        }
        return hlo;
    }
    
    private double[] laskeYksittaisenKuvanKasvoluokka(int[] kuva) {
        // vähennä keskiarvokasvot haettavasta kuvasta
        double[] keskiarvo = laskeKeskiarvoKasvot();
        double[] kuvavektori = new double[kuvavektorinPituus];
        for (int i = 0; i < kuvavektorinPituus; i++) {
            kuvavektori[i] = kuva[i] - keskiarvo[i];
        }
        
        // laske haettavan kuvan painot
        double[] kuvanPainot = new double[10];
        double[][] eigenfaces = laskeEigenfaces();
        for (int i = 0; i < 10; i++) {
            kuvanPainot[i] = tulomatriisinAlkio(kuvavektori, eigenfaces[i]);
        }
        
        return kuvanPainot;
    }
}