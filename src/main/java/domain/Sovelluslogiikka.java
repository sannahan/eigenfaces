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
     * Metodi kirjoittaa kuvavektorin kuvaksi, joka voidaan näyttää JavaFX:n avulla
     * 
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
}