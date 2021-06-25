package utils;

import domain.*;
import dao.*;

/**
 * Luokka testaa kasvotunnistuksen onnistuneisuutta.
 */
public class OnnistumisprosenttiTestaaja implements Testaaja {
    private final int ensimmainenHlo;
    private final int ensimmainenKuva;
    private final int henkiloMaara;
    private final double[] onnistumisprosentit = new double[3];
    private Kuvanlukija kuvanlukija;
    
    public OnnistumisprosenttiTestaaja(Kuvanlukija kuvanlukija, int ensimmainenHlo, int ensimmainenKuva, int henkiloMaara) {
        this.kuvanlukija = kuvanlukija;
        this.ensimmainenHlo = ensimmainenHlo;
        this.ensimmainenKuva = ensimmainenKuva;
        this.henkiloMaara = henkiloMaara;
    }
    
    /**
     * Metodi käynnistää testaustoiminnallisuuden.
     */
    public void testaa() {
        onnistumisprosentit[0] = testaaOnnistumisprosenttiOpetusdatastaJossaYksiNeljanKuvanLuokkaPerHenkilo();
        onnistumisprosentit[1] = testaaOnnistumisprosenttiOpetusdatastaJossaKaksiNeljanKuvanLuokkaaPerHenkilo();
        onnistumisprosentit[2] = testaaOnnistumisprosenttiOpetusdatastaJossaKolmeKolmenKuvanLuokkaaPerHenkilo();
    }
    
    /**
     * Metodi palauttaa testitulokset puolipisteellä eroteltuna.
     * 
     * @return String tulokset 
     */
    public String lue() {
        StringBuilder sb = new StringBuilder();
        sb.append("4 opetuskuvaa, 6 testikuvaa / henkilö;");
        sb.append("2 x 4 opetuskuvaa, 2 testikuvaa / henkilö;");
        sb.append("3 x 3 opetuskuvaa, 1 testikuva / henkilö;");
        sb.append(onnistumisprosentit[0] + ";");
        sb.append(onnistumisprosentit[1] + ";");
        sb.append(onnistumisprosentit[2]);
        return sb.toString();
    }
    
    /**
     * Opetusdataan luetaan neljä kuvaa / henkilö.
     * Jäljellejäävää 6 kuvaa / henkilö käytetään testikuvina.
     * 
     * @return double onnistumisprosentti henkilöiden tunnistukselle
     */
    private double testaaOnnistumisprosenttiOpetusdatastaJossaYksiNeljanKuvanLuokkaPerHenkilo() {
        Eigenfaces ef = luoOpetusdataTestiEigenfacesLuokalle(false);
        int oikein = oikeinTunnistettujaKuvia(ef, 5, 10);
        int testikuvaMaara = 60;
        return (double) oikein / testikuvaMaara * 100;
    }
    
    /**
     * Opetusdataan luetaan kahdeksan kuvaa / henkilö niin, että jokaista henkilöä vastaa kaksi kasvoluokkaa.
     * Hypoteesi on, että kaksi luokkaa tekee henkilöiden tunnistuksesta täsmällisempää.
     * 
     * @return double onnistumisprosentti henkilöiden tunnistukselle
     */
    private double testaaOnnistumisprosenttiOpetusdatastaJossaKaksiNeljanKuvanLuokkaaPerHenkilo() {
        Eigenfaces ef = luoOpetusdataTestiEigenfacesLuokalle(true);
        int oikein = oikeinTunnistettujaKuvia(ef, 9, 10);
        int testikuvaMaara = 20;
        return (double) oikein / testikuvaMaara * 100;
    }
    
    private Eigenfaces luoOpetusdataTestiEigenfacesLuokalle(boolean kaksiLuokkaaPerHenkilo) {
        int kuviaKasvoluokassa = 4;
        int[][] opetusdataEnsimmaiselleLuokalle = kuvanlukija.lueOpetusdata(ensimmainenHlo, ensimmainenKuva, henkiloMaara, kuviaKasvoluokassa);
        if (kaksiLuokkaaPerHenkilo) {
            int[][] opetusdataToiselleLuokalle = kuvanlukija.lueOpetusdata(ensimmainenHlo, ensimmainenKuva + kuviaKasvoluokassa, henkiloMaara, kuviaKasvoluokassa);
            return new Eigenfaces(kuviaKasvoluokassa, opetusdataEnsimmaiselleLuokalle, opetusdataToiselleLuokalle);
        }
        return new Eigenfaces(kuviaKasvoluokassa, opetusdataEnsimmaiselleLuokalle);
    }
    
    private int oikeinTunnistettujaKuvia(Eigenfaces ef, int ensimmainenTestikuva, int viimeinenTestikuva) {
        int oikein = 0;
        for (int henkilo = ensimmainenHlo; henkilo < ensimmainenHlo + henkiloMaara; henkilo++) {
            for (int testikuva = ensimmainenTestikuva; testikuva <= viimeinenTestikuva; testikuva++) {
                int[] kuvavektori = kuvanlukija.lueKuva("att_faces/s" + henkilo + "/" + testikuva + ".pgm");
                int hloTulos = ef.lyhinEuklidinenEtaisyys(kuvavektori) + ensimmainenHlo;
                if (hloTulos == henkilo) {
                    oikein++;
                }
                // jos jokaisesta henkilöstä on kaksi kasvoluokkaa, tarkastetaan myös jälkimmäinen kasvoluokka
                if (hloTulos - ensimmainenHlo + 1 > henkiloMaara && hloTulos - henkiloMaara == henkilo) {
                    oikein++;
                }
            }
        }
        return oikein;
    }
    
    /**
     * Opetusdataan luetaan yhdeksän kuvaa / henkilö.
     * Jokaista kuvaa käytetään vuorollaan testikuvana (testikuvaa ei lueta opetusdataan).
     * Lasketaan onnistumisprosenttien keskiarvo.
     * 
     * @return double onnistumisprosentti henkilöiden tunnistukselle
     */
    private double testaaOnnistumisprosenttiOpetusdatastaJossaKolmeKolmenKuvanLuokkaaPerHenkilo() {
        double keskiarvo = 0;
        
        int testikuvaMaara = 10;
        for (int testikuva = 1; testikuva <= testikuvaMaara; testikuva++) {
            int[][] opetusdata = kuvanlukija.lueOpetusdataTestikuvaaVaihdellen(ensimmainenHlo, ensimmainenKuva, henkiloMaara, testikuvaMaara, testikuva);
            Eigenfaces ef = new Eigenfaces(3, opetusdata);
            int oikein = 0;
            for (int henkilo = ensimmainenHlo; henkilo < ensimmainenHlo + henkiloMaara; henkilo++) {
                int[] kuvavektori = kuvanlukija.lueKuva("att_faces/s" + henkilo + "/" + testikuva + ".pgm");
                int tarkasteltaviaNaapureita = 3;
                int hloTulos = ef.kLyhyintaEuklidistaEtaisyytta(kuvavektori, tarkasteltaviaNaapureita) + ensimmainenHlo;
                if (hloTulos == henkilo) {
                    oikein++;
                }
            }
            keskiarvo += (double) oikein / testikuvaMaara * 100;
        }
        
        return keskiarvo / testikuvaMaara;
    }
}