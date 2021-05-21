package dao;

import java.io.DataInputStream;
import java.io.FileInputStream;

/**
 * Luokka lukee opetusdatan PGM-kuvien pikseleistä, jotka ovat arvoiltaan kokonaislukuja välillä 0-255 
 */
public class Kuvanlukija {
    private int[][] opetusdata;
    private int leveys, korkeus, lisattavanKuvanIndeksi;
    
    /**
     * Konstruktori luo 40x10304-matriisin opetusdatalle.
     * Opetusdatassa käytettyjä kuvia on 40 kappaletta.
     * Jokainen kuva ilmaistaan yhtenä rivivektorina.
     * 
     * @param leveys  kuvan leveys
     * @param korkeus kuvan korkeus
     */
    public Kuvanlukija(int leveys, int korkeus) {
        int opetusdataanLuettavienKuvienMaara = 40;
        
        this.leveys = leveys;
        this.korkeus = korkeus;
        
        this.opetusdata = new int[opetusdataanLuettavienKuvienMaara][leveys*korkeus];
    }
    
    /**
     * Opetusdataan on valittu henkilöt 20-29 ja jokaiselta henkilöltä kuvat 1-4
     * AT&T:n kasvodatassa on 40 henkilöä, 10 kuvaa / henkilö.
     * Muita kuvia käytetään testidatana.
     */
    public void lueOpetusdata() {
        for (int henkilo = 20; henkilo < 30; henkilo++) {
            for (int kuva = 1; kuva < 5; kuva++) {
                String tiedostoNimi = "att_faces/s" + String.valueOf(henkilo) + "/" + String.valueOf(kuva) + ".pgm";
                lueKuva(tiedostoNimi);
            }
        }
    }
    
    /**
     * Metodi muodostaa vektorin kuvan pikseleistä ja lisää sen opetusdatamatriisiin
     * 
     * @param tiedostoNimi  kuvatiedosto
     */
    public void lueKuva(String tiedostoNimi) {
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
        
        lisaaKuvavektoriMatriisiin(kuvavektori);
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
    
    private void lisaaKuvavektoriMatriisiin(int[] kuvavektori) {
        opetusdata[lisattavanKuvanIndeksi] = kuvavektori;
        lisattavanKuvanIndeksi++;
    }
    
    /**
     * Metodi palauttaa opetusdatamatriisin
     * 
     * @return opetusdatamatriisi 
     */
    public int[][] getOpetusdata() {
        return opetusdata;
    }
}