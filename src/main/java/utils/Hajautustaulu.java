package utils;

/**
 * HashMapia vastaava toiminnallisuus
 * 
 * @param <K> avaimen tyyppiparametri
 * @param <V> arvon tyyppiparametri
 */
public class Hajautustaulu<K,V> {
    private Lista<Pari<K,V>>[] hajautustaulu;
    private int arvoja;
    
    public Hajautustaulu() {
        this.hajautustaulu = new Lista[11];
        this.arvoja = 0;
    }
    
    /**
     * Metodi palauttaa avainta vastaavan arvon, jos se löytyy.
     * 
     * @param avain
     * @return arvo, jos se löytyy; null, jos se ei löydy
     */
    public V hae(K avain) {
        int hajautusarvo = laskeHajautusarvo(avain, this.hajautustaulu.length);
        
        Lista<Pari<K,V>> arvotIndeksissa = this.hajautustaulu[hajautusarvo];
        if (arvotIndeksissa == null) {
            return null;
        }
        
        for (int i = 0; i < arvotIndeksissa.koko(); i++) {
            Pari<K,V> pari = arvotIndeksissa.anna(i);
            if (pari.getAvain().equals(avain)) {
                return pari.getArvo();
            }
        }
        
        return null;
    }
    
    private int laskeHajautusarvo(K avain, int pituus) {
        return Math.abs(avain.hashCode() % pituus);
    }
    
    /**
     * Metodi lisää avain-arvoparin hajautustauluun.
     * Hajautustaulun kokoa kasvatetaan tarvittaessa.
     * 
     * @param avain
     * @param arvo 
     */
    public void lisaa(K avain, V arvo) {
        Lista<Pari<K,V>> arvotIndeksissa = getLista(avain);
        
        int indeksi = getIndeksi(arvotIndeksissa, avain);
        if (indeksi < 0) {
            arvotIndeksissa.lisaa(new Pari<>(avain, arvo));
            this.arvoja++;
        } else {
            arvotIndeksissa.anna(indeksi).setArvo(arvo);
        }
        
        if (1.0 * this.arvoja / this.hajautustaulu.length > 0.75) {
            kasvata();
        }
    }
    
    private Lista<Pari<K,V>> getLista(K avain) {
        int hajautusarvo = laskeHajautusarvo(avain, this.hajautustaulu.length);
        
        if (this.hajautustaulu[hajautusarvo] == null) {
            this.hajautustaulu[hajautusarvo] = new Lista<>();
        }
        
        return this.hajautustaulu[hajautusarvo];
    }
    
    private int getIndeksi(Lista<Pari<K,V>> lista, K avain) {
        for (int i = 0; i < lista.koko(); i++) {
            if (lista.anna(i).getAvain().equals(avain)) {
                return i;
            }
        }
        return -1;
    }
    
    private void kasvata() {
        Lista<Pari<K,V>>[] uusi = new Lista[this.hajautustaulu.length * 2];
        for (int i = 0; i < this.hajautustaulu.length; i++) {
            kopioi(uusi, i);
        }
        this.hajautustaulu = uusi;
    }
    
    private void kopioi(Lista<Pari<K,V>>[] uusi, int indeksista) {
        Lista<Pari<K,V>> paritIndeksista = this.hajautustaulu[indeksista];
        
        for (int i = 0; i < paritIndeksista.koko(); i++) {
            Pari<K,V> pari = paritIndeksista.anna(i);
            int uusiHajautusarvo = laskeHajautusarvo(pari.getAvain(), uusi.length);
            
            if (uusi[uusiHajautusarvo] == null) {
                uusi[uusiHajautusarvo] = new Lista<>();
            }
            
            uusi[uusiHajautusarvo].lisaa(pari);
        }
    }
    
    /**
     * Metodi poistaa avain-arvoparin hajautustaulusta.
     * 
     * @param avain
     * @return poistetun avain-arvoparin arvo, jos löytyy; null, jos ei löydy
     */
    public V poista(K avain) {
        Lista<Pari<K,V>> arvotIndeksissa = getLista(avain);
        
        if (arvotIndeksissa.koko() == 0) {
            return null;
        }
        
        int indeksi = getIndeksi(arvotIndeksissa, avain);
        
        if (indeksi < 0) {
            return null;
        }
        
        Pari<K,V> pari = arvotIndeksissa.anna(indeksi);
        arvotIndeksissa.poista(pari);
        return pari.getArvo();
    }
}