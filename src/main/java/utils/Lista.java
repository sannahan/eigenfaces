package utils;

import java.util.Objects;

/**
 * ArrayListiä vastaava toiminnallisuus
 * 
 * @param <T> tyyppiparametri 
 */
public class Lista<T> {
    private T[] lista;
    private int alkioita;
    
    public Lista() {
        this.lista = (T[]) new Object[10];
        this.alkioita = 0;
    }
    
    /**
     * Metodi lisää arvon listaan.
     * Listan kokoa kasvatetaan tarvittaessa.
     * 
     * @param arvo (arvo ei saa olla null) 
     */
    public void lisaa(T arvo) {
        if (Objects.isNull(arvo)) {
            throw new IllegalArgumentException("Listalle lisättävä arvo ei saa olla null");
        }
        
        if (this.alkioita == this.lista.length) {
            kasvata();
        }
        
        this.lista[this.alkioita] = arvo;
        this.alkioita++;
    }
    
    private void kasvata() {
        T[] uusiTaulukko = (T[]) new Object[this.lista.length * 2];
        for (int i = 0; i < this.alkioita; i++) {
            uusiTaulukko[i] = this.lista[i];
        }
        this.lista = uusiTaulukko;
    }
    
    private void kutista() {
        T[] uusiTaulukko = (T[]) new Object[this.lista.length / 2];
        for (int i = 0; i < this.alkioita; i++) {
            uusiTaulukko[i] = this.lista[i];
        }
        this.lista = uusiTaulukko;
    }
    
    /**
     * Metodi tarkistaa, löytyykö annettu arvo listalta.
     * 
     * @param arvo
     * @return true, jos arvo löytyy listalta; false, jos ei
     */
    public boolean sisaltaa(T arvo) {
        return arvonIndeksi(arvo) >= 0;
    }
    
    /**
     * Jos arvo löytyy listalta, metodi palauttaa sen indeksin.
     * 
     * @param arvo
     * @return indeksi >= 0, jos arvo löytyy listalta; -1, jos arvoa ei löydy
     */
    public int arvonIndeksi(T arvo) {
        for (int i = 0; i < this.alkioita; i++) {
            if (this.lista[i].equals(arvo)) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Metodi poistaa annetun arvon listalta.
     * Metodi kutistaa listaa tarvittaessa.
     * 
     * @param arvo 
     */
    public void poista(T arvo) {
        int indeksista = arvonIndeksi(arvo);
        if (indeksista < 0) {
            return;
        }
        
        siirraVasemmalle(indeksista);
        this.alkioita--;
        
        if (this.alkioita <= this.lista.length / 4) {
            kutista();
        }
    }
    
    private void siirraVasemmalle(int indeksista) {
        for (int i = indeksista; i < this.alkioita - 1; i++) {
            this.lista[i] = this.lista[i + 1];
        }
    }
    
    /**
     * Metodi palauttaa listan koon.
     * 
     * @return int listan koko 
     */
    public int koko() {
        return this.alkioita;
    }
    
    /**
     * Metodi palauttaa listan sisältävän taulukon koon.
     * 
     * @return int taulukon koko 
     */
    protected int taulukonKoko() {
        return this.lista.length;
    }
    
    /**
     * Metodi palauttaa annetusta indeksistä löydetyn arvon.
     * 
     * @param indeksi (indeksin tulee olla listan rajojen sisällä)
     * @return indeksistä löydetty arvo
     */
    public T anna(int indeksi) {
        if (indeksi < 0 || indeksi >= this.alkioita) {
            throw new ArrayIndexOutOfBoundsException("Indeksin tulee olla välillä [0, " + this.alkioita + "[");
        }
        return this.lista[indeksi];
    }
}