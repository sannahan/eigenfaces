# Eigenfaces – kasvojentunnistus

Ohjelma on tehty Helsingin yliopiston kurssille Tietorakenteet ja algoritmit -harjoitustyö. Kasvodatan lähde: AT&T Laboratories Cambridge.

## Käyttöohjeet

Aja projekti komennolla
```
./gradlew run
```
Testaus suoritetaan komennolla
```
./gradlew test
```
Testikattavuusraportti muodostetaan komennolla
```
./gradlew test jacocoTestReport
```
Checkstyle-tarkastus suoritetaan komennolla
```
./gradlew checkstyleMain
```
Jar generoidaan komennolla
```
./gradlew shadowJar
```
Jar ajetaan komennolla
```
java -jar build/libs/eigenfaces-all.jar
```
Kansion att_faces tulee löytyä juurihakemistosta jaria ajettaessa. 

Ohjelma ei tarkista annettuja syötteitä, mutta hakutoiminnallisuus toimii toivotusti syötteillä 20-29 (hlö) ja 5-10 (kuva).

## Raportit

- [Viikkoraportti 1](https://github.com/sannahan/eigenfaces/blob/master/docs/Viikkoraportti_1.md)
- [Viikkoraportti 2](https://github.com/sannahan/eigenfaces/blob/master/docs/Viikkoraportti_2.md)
- [Viikkoraportti 3](https://github.com/sannahan/eigenfaces/blob/master/docs/Viikkoraportti_3.md)
- [Viikkoraportti 4](https://github.com/sannahan/eigenfaces/blob/master/docs/Viikkoraportti_4.md)
- [Viikkoraportti 5](https://github.com/sannahan/eigenfaces/blob/master/docs/Viikkoraportti_5.md)
- [Viikkoraportti 6](https://github.com/sannahan/eigenfaces/blob/master/docs/Viikkoraportti_6.md)

## Dokumentaatio

- [Määrittelydokumentti](https://github.com/sannahan/eigenfaces/blob/master/docs/maarittelydokumentti.md)
- [Toteutusdokumentti](https://github.com/sannahan/eigenfaces/blob/master/docs/toteutusdokumentti.md)
- [Testausdokumentti](https://github.com/sannahan/eigenfaces/blob/master/docs/testausdokumentti.md)

[![codecov](https://codecov.io/gh/sannahan/eigenfaces/branch/master/graph/badge.svg?token=43PR0GWMAF)](https://codecov.io/gh/sannahan/eigenfaces)
