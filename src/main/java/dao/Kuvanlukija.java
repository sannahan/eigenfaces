package dao;

import java.io.DataInputStream;
import java.io.FileInputStream;

/**
 * Luokka lukee opetusdatan PGM-kuvien pikseleistä, jotka ovat arvoiltaan kokonaislukuja välillä 0-255 
 */
public class Kuvanlukija {
    private int leveys, korkeus;
    
    /**
     * Konstruktori luo 40x10304-matriisin opetusdatalle.
     * Opetusdatassa käytettyjä kuvia on 40 kappaletta.
     * Jokainen kuva ilmaistaan yhtenä rivivektorina.
     * 
     * @param leveys  kuvan leveys
     * @param korkeus kuvan korkeus
     */
    public Kuvanlukija(int leveys, int korkeus) {
        this.leveys = leveys;
        this.korkeus = korkeus;
    }
    
    /**
     * Opetusdataan luetaan parametrien mukainen määrä kuvia.
     * AT&T:n kasvodatassa on 40 henkilöä, 10 kuvaa / henkilö.
     * Muita kuvia käytetään testidatana.
     * 
     * @param alkaenHenkilo ensimmäinen testidataan luettava henkilö
     * @param alkaenKuva ensimmäinen testidataan luettava kuva henkilön kuvista
     * @param luettaviaHenkiloita kuinka monen henkilön kuvia luetaan opetusdataan
     * @param luettaviaKuvia kuinka monta kuvaa / henkilö luetaan opetusdataan
     * @return int[][] opetusdatamatriisi
     */
    public int[][] lueOpetusdata(int alkaenHenkilo, int alkaenKuva, int luettaviaHenkiloita, int luettaviaKuvia) {
        int[][] opetusdata = new int[luettaviaHenkiloita*luettaviaKuvia][leveys*korkeus];
        
        int ensimmainenHenkilo = alkaenHenkilo;
        int ensimmainenKuva = alkaenKuva;
        int kuviaLuettu = 0;
        for (int henkilo = ensimmainenHenkilo; henkilo < ensimmainenHenkilo + luettaviaHenkiloita; henkilo++) {
            for (int kuva = ensimmainenKuva; kuva < ensimmainenKuva + luettaviaKuvia; kuva++) {
                String tiedostoNimi = "att_faces/s" + String.valueOf(henkilo) + "/" + String.valueOf(kuva) + ".pgm";
                int[] kuvavektori = lueKuva(tiedostoNimi);
                opetusdata[kuviaLuettu++] = kuvavektori;
            }
        }
        
        return opetusdata;
    }
    
    /**
     * Opetusdataan luetaan parametrien mukainen määrä kuvia.
     * AT&T:n kasvodatassa on 40 henkilöä, 10 kuvaa / henkilö.
     * Testikuvaa ei lueta opetusdataan.
     * 
     * @param alkaenHenkilo ensimmäinen testidataan luettava henkilö
     * @param alkaenKuva ensimmäinen testidataan luettava kuva henkilön kuvista
     * @param luettaviaHenkiloita kuinka monen henkilön kuvia luetaan opetusdataan
     * @param luettaviaKuvia kuinka monta kuvaa / henkilö luetaan opetusdataan
     * @param testikuva kuva, jota ei lueta opetusdataan
     * @return int[][] opetusdatamatriisi
     */
    public int[][] lueOpetusdataTestikuvaaVaihdellen(int alkaenHenkilo, int alkaenKuva, int luettaviaHenkiloita, int luettaviaKuvia, int testikuva) {
        int[][] opetusdata = new int[luettaviaHenkiloita*(luettaviaKuvia-1)][leveys*korkeus];
        
        int kuviaLuettu = 0;
        for (int henkilo = alkaenHenkilo; henkilo < alkaenHenkilo + luettaviaHenkiloita; henkilo++) {
            for (int kuva = alkaenKuva; kuva < alkaenKuva + luettaviaKuvia; kuva++) {
                if (kuva == testikuva) {
                    continue;
                }
                String tiedostoNimi = "att_faces/s" + String.valueOf(henkilo) + "/" + String.valueOf(kuva) + ".pgm";
                int[] kuvavektori = lueKuva(tiedostoNimi);
                opetusdata[kuviaLuettu++] = kuvavektori;
            }
        }
        
        return opetusdata;
    }
    
    /**
     * Metodi muodostaa vektorin kuvan pikseleistä
     * 
     * @param tiedostoNimi  kuvatiedoston polku
     */
    public int[] lueKuva(String tiedostoNimi) {
        int[] kuvavektori = new int[leveys*korkeus];
            
        try {
            DataInputStream syoteVirta = new DataInputStream(new FileInputStream(tiedostoNimi));

            ohitaOtsake(syoteVirta);
            
            for (int pikseli = 0; pikseli < leveys*korkeus; pikseli++) {
                kuvavektori[pikseli] = syoteVirta.readUnsignedByte();            
            }
        } catch (Exception e) {
            System.out.println("Kuvan lukeminen ei onnistunut!");
            e.printStackTrace();
        }
        
        return kuvavektori;
    }
    
    private void ohitaOtsake(DataInputStream syoteVirta) throws Exception {
        int otsakkeenKokoRiveina = 3;
        while (otsakkeenKokoRiveina > 0) {
            char c;
            do {
                c = (char)(syoteVirta.readUnsignedByte());
            } while (c != '\n');
            otsakkeenKokoRiveina--;
        }
    }
}