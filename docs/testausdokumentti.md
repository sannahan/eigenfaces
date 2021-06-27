# Testausdokumentti

## JUnit

Dao-, domain- ja utils-pakkkauksille on omat testipakkaukset. Käyttöliittymä ja sovelluslogiikka on eriytetty; sovelluslogiikkaluokka sisältää eigenfaces- ja kuvanlukijaoliot, joten sen testaamisessa on hyödynnetty Mockitoa.

Testit voi suorittaa komennolla 
```
./gradlew test 
```
ja rivikattavuusraportin voi muodostaa komennolla
```
./gradlew test jacocoTestReport
```
Raporttia voi tarkastella avaamalla tiedoston `build/reports/jacoco/test/html/index.html`.

![Testikattavuus](/images/testikattavuus.png)

Rivikattavuus on 93 prosenttia ja haarautumakattavuus on 89 prosenttia.

## Onnistumisprosentit

Sovellusta voi testata graafisen käyttöliittymän kautta kolmella tavalla: valitsemalla testikuvaksi henkilön 20-29 ja kuvan 5-10, testaamalla onnistumisprosentit tai käynnistämällä suorituskykytestit.

![Kuva graafisesta käyttöliittymästä](/images/sovellus.png)

Tunnistuksen onnistuneisuutta testattiin tavalla, jossa...

1) opetusdata muodostuu 10 henkilön kuvista 1-4, ja kuvia 5-10 käytetään testikuvina.
2) opetusdata muodostuu 10 henkilön kuvista 1-4 ja 5-8, ja kuvia 9-10 käytetään testikuvina
3) opetusdata muodostuu 10 henkilön kuvista. Opetusdataan valitaan 3 x 3 kuvaa ja jäljelle jäänyttä kuvaa käytetään testikuvana niin, että jokainen kymmenestä kuvasta on vuorollaan testikuva. Kuvan tunnistamisessa käytetään lisäksi k-lähimmän naapurin menetelmää, kun k = 3

Onnistumisprosentit:

----- | Henkilöt 1-10 | Henkilöt 11-20 | Henkilöt 21-30 | Henkilöt 31-40
----- | ----- | ----- | ----- | ------
Tapa 1 | 95 % | 88 % | 90 % | 80 %
Tapa 2 | 95 % | 85 % | 100 % | 95 %
Tapa 3 | 95 % | 91 % | 96 % | 91 %

Onnistuneisuus oli jokaisessa tapauksessa >= 80 %. Näiden tulosten perusteella ei löydetty tapaa, joka tuottaisi kaikkien henkilöiden osalta muita paremman onnistumisprosentin.

Ohjelman "Testaa valitsemalla testikuvaksi hlo 20-29 ja kuva 5-10" -toiminnallisuus perustuu tapaan 1. Tuloksena näytetään hakutuloksena saadun henkilön kuva 1. Tavan 1 kuvamäärät pohjautuvat Turkin ja Pentlandin (1991) alkuperäiseen eigenfaces-artikkeliin. 

## Suorituskykytestaus

Suorituskykyä testattiin 10, 100 ja 1000 kuvan opetusdatoilla. Koska Java on JIT-kieli (koodi käännetään 'just in time') ja koska Javan roskienkeräily ja käyttöjärjestelmän tekemä vuoronnus voivat vaikuttaa testituloksiin,
- testit tehdään useasti (eigenfaces-luokan alustaminen tehdään 100 kertaa ja kuvantunnistus tehdään 100 kertaa 100 erilaisella kuvavektorilla)
- kerätyistä arvoista valitaan alustamisen yhteydessä mediaani ja kuvantunnistuksessa keskiarvo

Suorituskykytestauksen syötteistä ja tuloksista löytyy tietoa [toteutusdokumentista](/docs/toteutusdokumentti.md).
