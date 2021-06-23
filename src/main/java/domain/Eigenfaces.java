package domain;

import utils.*;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.EigenDecomposition;

/**
 * Luokka toteuttaa eigenfaces-algoritmin toiminnallisuuden.
 * Turkin ja Pentlandin artikkelissa opetusdata koostuu sarakevektoreista, mutta 
 * kuvavektoreita käsitellään tässä luokassa rivivektoreina.
 */
public class Eigenfaces {
    private double[][] opetusdata;
    private double[] keskiarvokasvot;
    private double[][] opetusdataJostaVahennettyKeskiarvo;
    private double[][] eigenfaces;
    private double[][] kasvoluokat;
    private final int kuvavektorinPituus, kuviaOpetusdatassa, kuviaPerHenkilo;
    private final int eigenfacesMaara = 10;
    
    /**
     * Kuormitettu konstruktori tilanteelle, jossa opetusdata koostuu vain yhdestä matriisista.
     * 
     * @param kuviaPerHenkilo
     * @param opetusdata 
     */
    public Eigenfaces(int kuviaPerHenkilo, int[][] opetusdata) {
        this(kuviaPerHenkilo, opetusdata, new int[0][0]);
    }
    
    /**
     * Konstruktori saa kaksi opetusdatamatriisia, jotka yhdistetään yhdeksi liukulukumatriisiksi.
     * 
     * @param kuviaPerHenkilo
     * @param opetusdata kuvavektorit sisältävä matriisi 1
     * @param opetusdata kuvavektorit sisältävä matriisi 2
     */
    public Eigenfaces(int kuviaPerHenkilo, int[][] ensimmainenOpetusdatamatriisi, int[][] toinenOpetusdatamatriisi) {
        this.kuvavektorinPituus = ensimmainenOpetusdatamatriisi[0].length;
        this.kuviaOpetusdatassa = ensimmainenOpetusdatamatriisi.length + toinenOpetusdatamatriisi.length;
        this.kuviaPerHenkilo = kuviaPerHenkilo;
        
        this.opetusdata = Matriisi.getLiukulukumatriisi(ensimmainenOpetusdatamatriisi, toinenOpetusdatamatriisi);
        suorita();
    }
    
    /**
     * Metodi laskee algoritmin käyttämät keskiarvokasvot, eigenfaces-kuvavektorit ja kasvoluokat.
     */
    private void suorita() {
        this.keskiarvokasvot = laskeKeskiarvokasvot();
        this.opetusdataJostaVahennettyKeskiarvo = vahennaKeskiarvokasvot();
        this.eigenfaces = laskeEigenfaces();
        this.kasvoluokat = laskeKasvoluokat();
    }
    
    /**
     * Metodi antaa keskiarvokasvokuvavektorin.
     * 
     * @return double[] keskiarvokasvot
     */
    public double[] getKeskiarvokasvot() {
        return this.keskiarvokasvot;
    }
    
    /**
     * Metodi antaa eigenfaces-kuvavektorit.
     * 
     * @return double[][] eigenfaces
     */
    public double[][] getEigenfaces() {
        return this.eigenfaces;
    }
    
    /**
     * Metodi laskee keskiarvokasvot ("the average face") opetusdatan kuvista
     * 
     * @return double[]-kuvavektori
     */
    public double[] laskeKeskiarvokasvot() {
        double[] keskiarvokasvot = new double[kuvavektorinPituus];
        for (int pikselinIndeksi = 0; pikselinIndeksi < kuvavektorinPituus; pikselinIndeksi++) {
            for (int kuvavektori = 0; kuvavektori < kuviaOpetusdatassa; kuvavektori++) {
                keskiarvokasvot[pikselinIndeksi] += opetusdata[kuvavektori][pikselinIndeksi];
            }
            keskiarvokasvot[pikselinIndeksi] = (double) keskiarvokasvot[pikselinIndeksi] / kuviaOpetusdatassa;
        }
        return keskiarvokasvot;
    }
    
    /**
     * Metodi vähentää keskiarvokasvot ("the average face") opetusdatasta
     * 
     * @return double[][] opetusdata, josta on vähennetty keskiarvokasvot
     */
    protected double[][] vahennaKeskiarvokasvot() {
        double[][] tulos = new double[kuviaOpetusdatassa][kuvavektorinPituus];
        for (int kuvavektori = 0; kuvavektori < kuviaOpetusdatassa; kuvavektori++) {
            tulos[kuvavektori] = vahennaKeskiarvokasvot(opetusdata[kuvavektori]);
        }
        return tulos;
    }
    
    /**
     * Metodi vähentää keskiarvokasvot ("the average face") annetusta vektorista.
     * 
     * @param double[] vektori, josta keskiarvokasvot vähennetään
     * @return double[] tulos
     */
    protected double[] vahennaKeskiarvokasvot(double[] vektori) {
        double[] tulos = new double[kuvavektorinPituus];
        for (int pikselinIndeksi = 0; pikselinIndeksi < kuvavektorinPituus; pikselinIndeksi++) {
            tulos[pikselinIndeksi] = vektori[pikselinIndeksi] - keskiarvokasvot[pikselinIndeksi];
        }
        return tulos;
    }
    
    /**
     * Metodi vähentää keskiarvokasvot ("the average face") annetusta vektorista, kun vektori sisältää kokonaislukuja.
     * 
     * @param int[] vektori, josta keskiarvokasvot vähennetään
     * @return double[] tulos
     */
    protected double[] vahennaKeskiarvokasvot(int[] vektori) {
        double[] liukulukuvektori = new double[kuvavektorinPituus];
        for (int pikseli = 0; pikseli < kuvavektorinPituus; pikseli++) {
            liukulukuvektori[pikseli] = vektori[pikseli];
        }
        return vahennaKeskiarvokasvot(liukulukuvektori);
    }
    
    /**
     * Metodi vastaa eigenfaces-vektoreiden muodostamisesta.
     * Matriisiluokan avulla muodostetaan L = transpoosi(A)*A, kun A on opetusdatamatriisi, 
     * josta on vähennetty opetusdatan keskiarvo.
     * Matriisin L ominaisvektorit lasketaan EigenDecomposition-luokan avulla.
     * 
     * @return double[][] 10 kpl eigenfaces-vektoreita
     */
    public double[][] laskeEigenfaces() {
        double[][] matriisiL = Matriisi.matriisiKerrottunaItsensaTranspoosilla(opetusdataJostaVahennettyKeskiarvo);
        double[][] ominaisvektorit = laskeOminaisvektorit(matriisiL);
        
        return laskeEigenfaces(ominaisvektorit);
    }
    
    private double[][] laskeOminaisvektorit(double[][] kerrottuMatriisi) {
        EigenDecomposition ed = new EigenDecomposition(new Array2DRowRealMatrix(kerrottuMatriisi));
        // Ominaisvektorit ovat muusta luokan toiminnallisuudesta poiketen sarakevektoreita
        return ed.getV().getData();
    }
    
    private double[][] laskeEigenfaces(double[][] ominaisvektorit) {
        double[][] eigenfaces = new double[eigenfacesMaara][kuvavektorinPituus];
        
        // Yhtälö 6 Turkin ja Pentlandin artikkelista
        for (int eigenface = 0; eigenface < eigenfacesMaara; eigenface++) {
            for (int sigmaIndeksi = 0; sigmaIndeksi < kuviaOpetusdatassa; sigmaIndeksi++) {
                for (int pikseli = 0; pikseli < kuvavektorinPituus; pikseli++) {
                    eigenfaces[eigenface][pikseli] += ominaisvektorit[sigmaIndeksi][eigenface] * opetusdataJostaVahennettyKeskiarvo[sigmaIndeksi][pikseli];
                }
            }
        }
        
        return eigenfaces;
    }
    
    /**
     * Metodi laskee Turkin ja Pentlandin artikkelin yhtälön 7 avulla jokaisen opetusdatan henkilön kasvoluokan.
     * Metodi hyödyntää tietoa siitä, että opetusdata sisältää jokaisesta henkilöstä kuviaPerHenkilo-kpl kuvaa, 
     * jotka ovat opetusdatassa peräkkäisinä rivivektoreina.
     * Opetusdatan kuvavektorin (,josta on vähennetty keskiarvokasvot) ja eigenface-kuvavektorin matriisitulo 
     * muodostaa painon, ja painojen muodostama vektori kuvastaa kunkin eigenface-kuvavektorin osuutta opetusdatan 
     * kuvan esittämisessä.
     * Koska opetusdatassa on kuviaPerHenkilo-kpl kuvaa jokaisesta henkilöstä, lasketaan kasvoluokkaan näiden painojen keskiarvo.
     * 
     * @return double[][] kasvoluokat
     */
    public double[][] laskeKasvoluokat() {
        double[][] kasvoluokat = new double[kuviaOpetusdatassa/kuviaPerHenkilo][eigenfacesMaara];
        double[] painot = new double[eigenfacesMaara];
        
        // Yhtälö 7 Turkin ja Pentlandin artikkelista
        for (int kuva = 0; kuva < kuviaOpetusdatassa; kuva++) {
            for (int eigenface = 0; eigenface < eigenfaces.length; eigenface++) {
                painot[eigenface] += Matriisi.tulomatriisinAlkio(opetusdataJostaVahennettyKeskiarvo[kuva], eigenfaces[eigenface]) / kuviaPerHenkilo;
            }
            if ((kuva + 1) % kuviaPerHenkilo == 0) {
                kasvoluokat[(kuva + 1) / kuviaPerHenkilo - 1] = painot;
                painot = new double[eigenfacesMaara];
            }
        }
        
        return kasvoluokat;
    }
    
    /**
     * Metodi laskee haettavan kuvan kuvavektorin kasvoluokan ja tarkastaa sen euklidisen etäisyyden
     * opetusdatasta muodostettuihin kasvoluokkiin.
     * Metodi palauttaa sen henkilön kasvoluokan indeksin, jonka euklidinen etäisyys haettavaan kuvavektoriin on lyhin.
     * 
     * @param int[] haettavan kuvan vektori
     * @return int kasvoluokkaa vastaava henkilö
     */
    public int lyhinEuklidinenEtaisyys(int[] haettavaKuva) {
        double[] kuvanPainot = laskeYksittaisenKuvanKasvoluokka(haettavaKuva);
        double[] etaisyydet = laskeEtaisyydet(kuvanPainot);
        return pienimmanArvonIndeksi(etaisyydet, new Sorttaus(etaisyydet.clone()).sorttausPienimmastaSuurimpaan()[0]);
    }
    
    private int pienimmanArvonIndeksi(double[] vektori, double pieninArvo) {
        for (int indeksi = 0; indeksi < vektori.length; indeksi++) {
            if (vektori[indeksi] == pieninArvo) {
                return indeksi;
            }
        }
        return -1;
    }
    
    /**
     * Metodi laskee k kasvoluokkaa, jotka ovat lähimpänä haettavaa kuvaa.
     * K-lähimmän naapurin menetelmää hyödynnetään, jos jokin kasvoluokka saa enemmistön.
     * Jos enemmistöä ei löydy, valitaan se yksittäinen kasvoluokka, joka on lähimpänä haettavaa kuvaa.
     * 
     * @param haettavaKuva
     * @param k-lähimmän naapurin menetelmä
     * @return int kasvoluokkaa vastaava henkilö
     */
    public int kLyhyintaEuklidistaEtaisyytta(int[] haettavaKuva, int k) {
        if (k % 2 == 0) {
            throw new IllegalArgumentException("Kokonaislukuparametrin tulee olla pariton");
        }
        
        double[] kuvanPainot = laskeYksittaisenKuvanKasvoluokka(haettavaKuva);
        double[] etaisyydet = laskeEtaisyydet(kuvanPainot);
        double[] etaisyydetPienimmastaSuurimpaan = new Sorttaus(etaisyydet.clone()).sorttausPienimmastaSuurimpaan();
        int[] hlot = etsiPienimpiaEtaisyyksiaVastaavatHenkilot(k, etaisyydet, etaisyydetPienimmastaSuurimpaan);
        return etsiEnemmistoaVastaavaHenkilo(k, hlot);   
    }
    
    protected int etsiEnemmistoaVastaavaHenkilo(int k, int[] hlot) {
        // lasketaan, kuinka monta kertaa kukakin alkio esiintyy aineistossa
        Hajautustaulu<Integer, Integer> frekvenssit = new Hajautustaulu<>();
        for (int i = 0; i < k; i++) {
            if (frekvenssit.hae(hlot[i]) == null) {
                frekvenssit.lisaa(hlot[i], 0);
            }
            frekvenssit.lisaa(hlot[i], frekvenssit.hae(hlot[i]) + 1);
        }
        
        // jos löytyy enemmistö, palautetaan se
        for (int i = 0; i < k; i++) {
            if (frekvenssit.hae(hlot[i]) > k / 2) {
                return hlot[i];
            }
        }
        
        // jos naapureista ei löydy enemmistöä
        return hlot[0];
    }
    
    protected int[] etsiPienimpiaEtaisyyksiaVastaavatHenkilot(int k, double[] etaisyydet, double[] etaisyydetPienimmastaSuurimpaan) {
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
        return hlot;
    }
    
    private double[] laskeEtaisyydet(double[] kuvanPainot) {
        // Yhtälö 8 Turkin ja Pentlandin artikkelista
        double[] etaisyydet = new double[kasvoluokat.length];
        for (int luokka = 0; luokka < kasvoluokat.length; luokka++) {
            double etaisyys = 0;
            for (int paino = 0; paino < eigenfacesMaara; paino++) {
                etaisyys += Math.pow((kasvoluokat[luokka][paino] - kuvanPainot[paino]), 2);
            }
            etaisyydet[luokka] = etaisyys;
        }
        return etaisyydet;
    }
    
    private double[] laskeYksittaisenKuvanKasvoluokka(int[] kuva) {
        double[] kuvavektori = vahennaKeskiarvokasvot(kuva);
        
        // laske haettavan kuvan painot
        double[] kuvanPainot = new double[eigenfacesMaara];
        for (int i = 0; i < eigenfacesMaara; i++) {
            kuvanPainot[i] = Matriisi.tulomatriisinAlkio(kuvavektori, eigenfaces[i]);
        }
        
        return kuvanPainot;
    }
}