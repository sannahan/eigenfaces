# Testausdokumentti

Testit voi suorittaa komennolla 
```
./gradlew test 
```
ja rivikattavuusraportin voi muodostaa komennolla
```
./gradlew test jacocoTestReport
```
Raporttia voi tarkastella avaamalla tiedoston build/reports/jacoco/test/html/index.html selaimella.

## JUnit

Dao- ja domain-paketeille on omat testipaketit. Sovelluslogiikka-luokka toimii rajapintana ohjelman eri luokkien välillä, joten sen testaamisessa on hyödynnetty Mockitoa. Mockiton avulla varmistetaan, että luokasta kutsutaan oikeita metodeita.
