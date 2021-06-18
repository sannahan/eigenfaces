package utils;

/**
 * Luokka kuvaa avain-arvoparia.
 * 
 * @param <K> avaimen tyyppiparametri
 * @param <V> arvon tyyppiparametri
 */
public class Pari<K,V> {
    private K avain;
    private V arvo;
    
    /**
     * Konstruktori saa parametrinaan avaimen ja arvon.
     * 
     * @param avain
     * @param arvo 
     */
    public Pari(K avain, V arvo) {
        this.avain = avain;
        this.arvo = arvo;
    }
    
    /**
     * Metodi palauttaa avaimen.
     * 
     * @return avain
     */
    public K getAvain() {
        return this.avain;
    }
    
    /**
     * Metodi palauttaa arvon.
     * 
     * @return arvo 
     */
    public V getArvo() {
        return this.arvo;
    }
    
    /**
     * Metodi asettaa arvon.
     * 
     * @param arvo 
     */
    public void setArvo(V arvo) {
        this.arvo = arvo;
    }
}
