package utils;

import domain.Eigenfaces;
import java.util.Arrays;
import java.util.Random;

/**
 * Luokka suorituskykytesteille. Testaa sekä Eigenfaces-luokan alustamisen että kuvantunnistuksen.
 */
public class SuorituskykyTestaaja implements Testaaja {
    private final int[] testikoot = {10, 100, 1000};
    private final int kokoja = testikoot.length;
    private final int testeja = 100;
    private final int pikseleita = 92 * 112;
    private final double[] initEigenfaces = new double[kokoja];
    private final double[] tunnistaKasvot = new double[kokoja];
    private Random random;
    
    public SuorituskykyTestaaja() {
        random = new Random();
    }
    
    /**
     * Metodi käynnistää testaustoiminnallisuuden.
     */
    public void testaa() {
        testaaInit();
        testaaTunnistus();
    }
    
    /**
     * Metodi palauttaa testitulokset pilkulla eroteltuina.
     * 
     * @return String tulokset
     */
    public String lue() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < kokoja; i++) {
            sb.append(testikoot[i]);
            sb.append(",");
        }
        for (int i = 0; i < kokoja; i++) {
            sb.append(initEigenfaces[i]);
            sb.append(",");
        }
        for (int i = 0; i < kokoja; i++) {
            sb.append(tunnistaKasvot[i]);
            sb.append(",");
        }
        return sb.toString();
    }
    
    private void testaaTunnistus() {
        for (int i = 0; i < kokoja; i++) {
            Eigenfaces eigenfaces = new Eigenfaces(4, luoKuvitteellinenOpetusdata(testikoot[i]));
            
            int generoitujaTestikuvia = testeja;
            long[] aikaTestitapauksiin = new long[generoitujaTestikuvia];

            for (int kuva = 0; kuva < generoitujaTestikuvia; kuva++) {
                int[] kuvavektori = luoRandomKuvavektori();
                long aikaKaikkiin = 0;
                long aikaYhteen = 0;
                for (int testi = 0; testi < testeja; testi++) {
                    aikaYhteen = System.nanoTime();
                    eigenfaces.lyhinEuklidinenEtaisyys(kuvavektori);
                    aikaKaikkiin += System.nanoTime() - aikaYhteen;
                }
                aikaTestitapauksiin[kuva] = aikaKaikkiin / testeja;
            }
            tunnistaKasvot[i] = (Arrays.stream(aikaTestitapauksiin).sum() / generoitujaTestikuvia) / 1000000.0;
        }
    }
    
    private void testaaInit() {
        Eigenfaces eigenfaces;
        
        for (int i = 0; i < kokoja; i++) {
            long[] suoritusAjat = new long[testeja];
            int[][] opetusdata = luoKuvitteellinenOpetusdata(testikoot[i]);
            
            for (int testi = 0; testi < testeja; testi++) {
                long aika = System.nanoTime();
                eigenfaces = new Eigenfaces(4, opetusdata);
                aika = System.nanoTime() - aika;
                suoritusAjat[testi] = aika;
            }
            Arrays.sort(suoritusAjat);
            initEigenfaces[i] = suoritusAjat[suoritusAjat.length / 2] / 1000000.0;
        }
    }
    
    private int[][] luoKuvitteellinenOpetusdata(int kuvia) {
        int[][] opetusdata = new int[kuvia][pikseleita];
        for (int kuva = 0; kuva < kuvia; kuva++) {
            opetusdata[kuva] = luoRandomKuvavektori();
        }
        return opetusdata;
    }
    
    private int[] luoRandomKuvavektori() {
        int[] kuvavektori = new int[pikseleita];
        for (int i = 0; i < pikseleita; i++) {
            kuvavektori[i] = random.nextInt(256);
        }
        return kuvavektori;
    }
}