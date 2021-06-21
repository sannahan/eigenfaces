package domain;

/**
 * Lomitusjärjestäminen
 */
public class Sorttaus {
    private double[] sortattavaTaulukko;
    
    public Sorttaus(double[] sortattavaTaulukko) {
        this.sortattavaTaulukko = sortattavaTaulukko;
    }
    
    /**
     * Metodi järjestää taulukon alkiot suuruusjärjestykseen
     * @return int[], jonka alkiot ovat järjestyksessä pienimmästä suurimpaan
     */
    public double[] sorttausPienimmastaSuurimpaan() {
        jarjesta(0, this.sortattavaTaulukko.length - 1);
        return this.sortattavaTaulukko;
    }
    
    private void jarjesta(int a, int b) {
        if (a == b) {
            return;
        }
        int k = (a + b) / 2;
        jarjesta(a, k);
        jarjesta(k + 1, b);
        lomita(a, k, k + 1, b);
    }
    
    private void lomita(int alku1, int loppu1, int alku2, int loppu2) {
        int alku = alku1;
        int loppu = loppu2;
        double[] aputaulukko = new double[loppu2 + 1];
        
        for (int i = alku; i <= loppu; i++) {
            if (alku2 > loppu2 || ( alku1 <= loppu1 && sortattavaTaulukko[alku1] <= sortattavaTaulukko[alku2])) {
                aputaulukko[i] = sortattavaTaulukko[alku1];
                alku1++;
            } else {
                aputaulukko[i] = sortattavaTaulukko[alku2];
                alku2++;
            }
        }
        
        for (int i = alku; i <= loppu; i++) {
            sortattavaTaulukko[i] = aputaulukko[i];
        }
    }
}