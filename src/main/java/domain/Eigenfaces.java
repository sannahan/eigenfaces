package domain;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.EigenDecomposition;

/**
 * Luokka toteuttaa eigenfaces-algoritmin
 */
public class Eigenfaces {
    private Matriisi opetusdata;
    private double[][] kasvoluokat;
    private final int kuvavektorinPituus, kuviaOpetusdatassa, kuviaPerHenkilo, eigenfacesMaara;
    
    public Eigenfaces(int kuviaPerHenkilo, int[][] opetusdata) {
        this(kuviaPerHenkilo, opetusdata, new int[0][0]);
    }
    
    /**
     * Konstruktori saa opetusdatamatriisin, jonka sisältämät kokonaisluvut luetaan liukulukuina.
     * Turkin ja Pentlandin artikkelissa kuvat ovat opetusdatassa sarakevektoreita, mutta laskennan helpottamiseksi
     * kuvavektoreita käsitellään tässä luokassa rivivektoreina.
     * 
     * @param opetusdata kuvavektorit sisältävä matriisi
     */
    public Eigenfaces(int kuviaPerHenkilo, int[][] ensimmainenOpetusdatamatriisi, int[][] toinenOpetusdatamatriisi) {
        this.kuviaOpetusdatassa = ensimmainenOpetusdatamatriisi.length + toinenOpetusdatamatriisi.length;
        this.kuvavektorinPituus = ensimmainenOpetusdatamatriisi[0].length;
        this.kuviaPerHenkilo = kuviaPerHenkilo;
        this.eigenfacesMaara = 10;
        
        this.opetusdata = new Matriisi(ensimmainenOpetusdatamatriisi, toinenOpetusdatamatriisi);
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
                keskiarvoKasvot[pikselinIndeksi] += opetusdata.getMatriisi()[kuvavektori][pikselinIndeksi];
            }
            keskiarvoKasvot[pikselinIndeksi] = (double) keskiarvoKasvot[pikselinIndeksi] / kuviaOpetusdatassa;
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
                tulos[kuvavektori][pikselinIndeksi] = opetusdata.getMatriisi()[kuvavektori][pikselinIndeksi] - keskiarvoKasvot[pikselinIndeksi];
            }
        }
        return tulos;
    }
    
    /**
     * Metodi vastaa eigenfaces-vektoreiden muodostamisesta.
     * Matriisiluokan avulla muodostetaan L = transpoosi(A)*A, kun A on opetusdatamatriisi, 
     * jonka sarakevektori vastaa yhtä kuvavektoria.
     * EigenDecomposition-luokan avulla lasketaan matriisin L ominaisvektorit.
     * Yhtälö 6 Turkin ja Pentlandin artikkelista muodostaa eigenfaces-vektorit ominaisvektoreiden avulla.
     * 
     * @return double[][] 10 kpl eigenfaces-vektoreita
     */
    public double[][] laskeEigenfaces() {
        double[][] uusiOpetusdata = vahennaKeskiarvoKasvotOpetusdatasta(laskeKeskiarvoKasvot());
        double[][] matriisiKerrottunaMatriisinTranspoosilla = laskeMatriisiKertotulo(uusiOpetusdata);
        double[][] ominaisvektorit = laskeOminaisvektorit(matriisiKerrottunaMatriisinTranspoosilla);
        
        return muodostaEigenfaces(uusiOpetusdata, ominaisvektorit);
    }
    
    private double[][] laskeMatriisiKertotulo(double[][] opetusdata) {
        Matriisi matriisi = new Matriisi(opetusdata);
        matriisi.matriisiKerrottunaMatriisinTranspoosilla();
        return matriisi.getMatriisi();
    }
    
    private double[][] laskeOminaisvektorit(double[][] kerrottuMatriisi) {
        EigenDecomposition ed = new EigenDecomposition(new Array2DRowRealMatrix(kerrottuMatriisi));
        // Ominaisvektorit ovat muusta luokan toiminnallisuudesta poiketen
        // sarakevektoreita ominaisvektorit-matriisissa.
        return ed.getV().getData();
    }
    
    private double[][] muodostaEigenfaces(double[][] opetusdata, double[][] ominaisvektorit) {
        double[][] eigenfaces = new double[eigenfacesMaara][kuvavektorinPituus];
        
        // yhtälö 6 Turkin ja Pentlandin artikkelista
        for (int kuva = 0; kuva < eigenfacesMaara; kuva++) {
            for (int sigmaIndeksi = 0; sigmaIndeksi < kuviaOpetusdatassa; sigmaIndeksi++) {
                for (int pikseli = 0; pikseli < kuvavektorinPituus; pikseli++) {
                    eigenfaces[kuva][pikseli] += ominaisvektorit[sigmaIndeksi][kuva] * opetusdata[sigmaIndeksi][pikseli];
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
        
        this.kasvoluokat = new double[kuviaOpetusdatassa/kuviaPerHenkilo][eigenfacesMaara];
        double[] painot = new double[eigenfacesMaara];
        for (int kuva = 0; kuva < kuviaOpetusdatassa; kuva++) {
            for (int eigenface = 0; eigenface < eigenfaces.length; eigenface++) {
                painot[eigenface] += tulomatriisinAlkio(uusiOpetusdata[kuva], eigenfaces[eigenface]) / kuviaPerHenkilo;
            }
            if ((kuva + 1) % kuviaPerHenkilo == 0) {
                this.kasvoluokat[(kuva + 1) / kuviaPerHenkilo - 1] = painot;
                painot = new double[eigenfacesMaara];
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
     * Metodi palauttaa sen henkilön kasvoluokan indeksin, jonka euklidinen etäisyys haettavaan kuvavektoriin on lyhin.
     * 
     * @param int[] haettavan kuvan vektori
     * @return int kasvoluokkaa vastaava henkilö
     */
    public int lyhinEuklidinenEtaisyys(int[] haettavaKuva) {
        laskeOpetusdatanKasvoluokat();
        
        double[] kuvanPainot = laskeYksittaisenKuvanKasvoluokka(haettavaKuva);
        
        double minEtaisyys = Double.MAX_VALUE;
        int hlo = -1;
        for (int luokka = 0; luokka < kasvoluokat.length; luokka++) {
            double etaisyys = 0;
            for (int paino = 0; paino < eigenfacesMaara; paino++) {
                etaisyys += Math.pow((kasvoluokat[luokka][paino] - kuvanPainot[paino]), 2);
            }
            if (etaisyys < minEtaisyys) {
                minEtaisyys = etaisyys;
                hlo = luokka;
            }
        }
        return hlo;
    }
    
    /**
     * Metodi laskee k kasvoluokkaa, jotka ovat lähimpänä haettavaa kuvaa.
     * K-lähimmän naapurin menetelmää hyödynnetään, jos jokin kasvoluokka saa enemmistön.
     * Jos enemmistöä ei löydy, valitaan se yksittäinen kasvoluokka, joka on lähimpänä haettavaa kuvaa.
     * 
     * @param haettavaKuva pariton kokonaisluku
     * @param k-lähimmän naapurin menetelmä
     * @return int kasvoluokkaa vastaava henkilö
     */
    public int kLyhyintaEuklidistaEtaisyytta(int[] haettavaKuva, int k) {
        if (k % 2 == 0) {
            throw new IllegalArgumentException("Kokonaislukuparametrin tulee olla pariton");
        }
        
        laskeOpetusdatanKasvoluokat();
        
        double[] kuvanPainot = laskeYksittaisenKuvanKasvoluokka(haettavaKuva);
        
        double[] etaisyydet = new double[kasvoluokat.length];
        for (int luokka = 0; luokka < kasvoluokat.length; luokka++) {
            double etaisyys = 0;
            for (int paino = 0; paino < 10; paino++) {
                etaisyys += Math.pow((kasvoluokat[luokka][paino] - kuvanPainot[paino]), 2);
            }
            etaisyydet[luokka] = etaisyys;
        }
        
        double[] etaisyydetPienimmastaSuurimpaan = etaisyydet.clone();
        Arrays.sort(etaisyydetPienimmastaSuurimpaan);
        
        // valitaan pienimpiä etäisyyksiä vastaavien kasvoluokkien indeksit
        // jos lasketaan esim. kolmen lähimmän naapurin tapausta,
        // indeksit 0, 1 ja 2 vastaavat ensimmäistä opetusdatan henkilöä eli tapausta 0
        int[] hlot = new int[k];
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < kasvoluokat.length; j++) {
                if (etaisyydetPienimmastaSuurimpaan[i] == etaisyydet[j]) {
                    hlot[i] = j / k;
                }
            }
        }
        
        // lasketaan, kuinka monta kertaa kukakin alkio esiintyy aineistossa
        Map<Integer, Integer> frekvenssit = new HashMap<>();
        for (int i = 0; i < k; i++) {
            if (!frekvenssit.containsKey(hlot[i])) {
                frekvenssit.put(hlot[i], 0);
            }
            frekvenssit.put(hlot[i], frekvenssit.get(hlot[i]) + 1);
        }
        
        // jos löytyy enemmistö, palautetaan se
        for (Integer i: frekvenssit.keySet()) {
            if (frekvenssit.get(i) > k / 2) {
                return i;
            }
        }
        
        // jos naapureista ei löydy enemmistöä
        return lyhinEuklidinenEtaisyys(haettavaKuva);
    }
    
    private double[] laskeYksittaisenKuvanKasvoluokka(int[] kuva) {
        // vähennä keskiarvokasvot haettavasta kuvasta
        double[] keskiarvo = laskeKeskiarvoKasvot();
        double[] kuvavektori = new double[kuvavektorinPituus];
        for (int i = 0; i < kuvavektorinPituus; i++) {
            kuvavektori[i] = kuva[i] - keskiarvo[i];
        }
        
        // laske haettavan kuvan painot
        double[] kuvanPainot = new double[eigenfacesMaara];
        double[][] eigenfaces = laskeEigenfaces();
        for (int i = 0; i < eigenfacesMaara; i++) {
            kuvanPainot[i] = tulomatriisinAlkio(kuvavektori, eigenfaces[i]);
        }
        
        return kuvanPainot;
    }
}