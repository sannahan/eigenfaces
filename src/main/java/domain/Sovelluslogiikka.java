package domain;

import dao.Kuvanlukija;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * Rajapinta käyttöliittymän ja sovelluksen muun toiminnallisuuden välillä
 */
public class Sovelluslogiikka {
    private Eigenfaces eigenfaces;
    private Kuvanlukija kuvanlukija;
    private static final int LEVEYS = 92;
    private static final int KORKEUS = 112;
    
    /**
     * Sovelluslogiikka saa konstruktorissa eigenfaces- ja kuvanlukija-luokat. 
     * Opetusdata luetaan kuvanlukijan avulla ja annetaan eigenfaces-luokalle. 
     */
    public Sovelluslogiikka() {
        this.kuvanlukija = new Kuvanlukija(LEVEYS, KORKEUS);
        
        kuvanlukija.lueOpetusdata();
        
        this.eigenfaces = new Eigenfaces(kuvanlukija.getOpetusdata(), LEVEYS, KORKEUS);
    }
    
    /**
     * Metodi kirjoittaa eigenfaces-luokassa lasketun keskiarvokasvovektorin ("the average face") kuvaksi
     * 
     * @return Image-kuva
     */
    public Image kirjoitaKeskiarvokasvot() {
        return kirjoitaPikselitKuvaksi(eigenfaces.laskeKeskiarvoKasvot());
    }
    
    /**
     * Metodi kirjoittaa eigenfaces-luokassa lasketun eigenface-vektorin kuvaksi
     * 
     * @param valittuEigenface mikä kymmenestä eigenfaces-kuvasta kirjoitetaan
     * @return Image-kuva
     */
    public Image kirjoitaEigenface(int valittuEigenface) {
        return kirjoitaEigenfaceKuvaksi(eigenfaces.laskeEigenfaces(), valittuEigenface);
    }
    
    /**
     * Metodi kirjoittaa kuvavektorin kuvaksi, joka voidaan näyttää JavaFX:n avulla, kun kuvavektori on int[]
     * 
     * @param int[] kuvavektori
     * @return Image-kuva
     */
    public Image kirjoitaPikselitKuvaksi(int[] kuvavektori) {
        WritableImage kohdekuva = new WritableImage(LEVEYS, KORKEUS);
        PixelWriter kirjoittaja = kohdekuva.getPixelWriter();
        
        int pikselinIndeksi = 0;
        for (int rivi = 0; rivi < KORKEUS; rivi++) {
            for (int sarake = 0; sarake < LEVEYS; sarake++) {
                Color uusiVari = Color.grayRgb(kuvavektori[pikselinIndeksi]);
                pikselinIndeksi++;
                kirjoittaja.setColor(sarake, rivi, uusiVari);
            }
        }
        
        return kohdekuva;
    }
    
    /**
     * Metodi kirjoittaa kuvavektorin kuvaksi, joka voidaan näyttää JavaFX:n avulla, kun kuvavektori on double[].
     * 
     * @param double[] kuvavektori
     * @return Image-kuva
     */
    public Image kirjoitaPikselitKuvaksi(double[] kuvavektori) {
        int[] kokonaislukuKuvavektori = new int[kuvavektori.length];
        for (int indeksi = 0; indeksi < kuvavektori.length; indeksi++) {
            kokonaislukuKuvavektori[indeksi] = (int) kuvavektori[indeksi];
        }
        return kirjoitaPikselitKuvaksi(kokonaislukuKuvavektori);
    }
    
    /**
     * Metodi kirjoittaa valitun eigenface-vektorin kuvaksi.
     * Vektori voi sisältää negatiivisia arvoja, joten metodi muuttaa arvot vastaamaan asteikkoa 0-255.
     * 
     * @param eigenfaces-matriisi
     * @param valitunEigenfacenIndeksi
     * @return Image-kuva
     */
    private Image kirjoitaEigenfaceKuvaksi(double[][] eigenfaces, int valitunEigenfacenIndeksi) {
        double max = Double.MIN_VALUE;
        double min = Double.MAX_VALUE;
        for (int i = 0; i < LEVEYS*KORKEUS; i++) {
            if (eigenfaces[valitunEigenfacenIndeksi][i] > max) {
                max = eigenfaces[valitunEigenfacenIndeksi][i];
            }
            if (eigenfaces[valitunEigenfacenIndeksi][i] < min) {
                min = eigenfaces[valitunEigenfacenIndeksi][i];
            }
        }
        
        double[] kirjoitettavaEigenface = new double[LEVEYS*KORKEUS];
        
        for (int i = 0; i < LEVEYS*KORKEUS; i++) {
            eigenfaces[valitunEigenfacenIndeksi][i] += Math.abs(min);
            double kerroin = 255 / (max + Math.abs(min));
            eigenfaces[valitunEigenfacenIndeksi][i] *= kerroin;
            kirjoitettavaEigenface[i] = eigenfaces[valitunEigenfacenIndeksi][i];
        }
        
        return kirjoitaPikselitKuvaksi(kirjoitettavaEigenface);
    }
}