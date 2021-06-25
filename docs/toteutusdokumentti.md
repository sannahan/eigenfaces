# Toteutusdokumentti

## Yleisrakenne

Ohjelmassa on neljä pakkausta: dao, domain, ui ja utils. Dao sisältää kuvien lukemiseen tarvittavan toiminnallisuuden. Domain sisältää eigenfaces-algoritmin toiminnallisuuden ja sovelluslogiikan. Ui sisältää javafx-käyttöliittymän, joka käynnistetään Main-luokasta käsin. Utils sisältää tarvittavat tietorakenteet ja raskaan testaustoiminnallisuuden, jota ei voida toteuttaa JUnit-testeinä.

![Luokkakaavio](/images/luokkakaavio.png)

## Aika- ja tilavaativuudet

### Algoritmien aika- ja tilavaativuudet

Sovellus käyttää tunnistukseen kasvokuvia, joiden koko on 92 x 112. Yhden kuvavektorin pituus on siis 92 * 112 > 10 000 tavua (yksi pikseli koostuu kokonaislukuarvosta 0-255 ja vastaa yhtä tavua). Turkin ja Pentlandin merkintöjä seuraten kun opetusdatamatriisi A = [kuvavektori1 kuvavektori 2 ... kuvavektori n], kovarianssimatriisisissa C = A * transpoosi(A) olisi yli 10 000 * 10 000 tavua. Koska näin ison matriisin käsittely olisi hyvin laskennallisesti työlästä, tarkastellaan sen sijaan eigenfaces-vektorien virittämää aliavaruutta M'.

Suorituskykyä testaava koodi löytyy luokasta `SuorituskykyTestaus.java`. Suorituskyky on testattu opetusdatalla, jossa on 10, 100 tai 1000 kuvaa. Eigenfaces-luokan alustamiseen eli siihen, että opetusdatasta vähennetään kasvojen keskiarvo, lasketaan eigenfaces-vektorit ja kuvien henkilöitä vastaavat kasvoluokat, kuluu aikaa eksponentiaalisesti:

![Eigenfaces-luokan alustamiseen kuluvat ajat](/images/init.png)

Tuloksista voidaan tulkita, ettei eigenfaces-algoritmi sovellu kovin suurten kuva-aineistojen käsittelyyn. Kun opetusdata sisältää 1000 kuvaa, kuluu sen käsittelyyn jo n. 20 sekuntia. Esimerkiksi eigenfaces-vektorien laskeminen edellyttää tällöin seuraavaa yli sadan miljoonan alkion loopia:

```Java
for eigenface = 0 to 10
    for sigmaIndeksi = 0 to 1000
        for pikseli = 0 to 92 * 112
            eigenfaces[eigenface][pikseli] += ominaisvektorit[sigmaIndeksi][eigenface] * opetusdataJostaVahennettyKeskiarvo[sigmaIndeksi][pikseli];
```

Kasvojentunnistus puolestaan tapahtuu lineaarisessa ajassa O(n) millisekuntin kymmenesosissa:

![Kasvojentunnistukseen kuluvat ajat](/images/tunnistus.png)

Tulkinta, että kasvojentunnistuksen aikavaativuus on O(n) on tarkoituksella hieman löyhä. Eigenfaces-luokkassa lasketaan tällä hetkellä opetusdatalle 10 eigenfaces-vektoria opetusdatan koosta riippumatta, vaikka esimerkiksi 1000 kuvan opetusdatan edustamiseen voisi olla syytä käyttää useampaa eigenfaces-vektoria. Turk ja Pentland (1991) kuitenkin esittävät Kirbyn ja Sirovichin (1990) tukimuksen perusteella, että 

>> even if it were necessary to represent a large segment of the population, the number of eigenfaces needed would still be relatively small

Eigenfaces-vektoreiden määrän lisääminen ei siis hidastane tunnistusta merkittävästi. 

Järjestämistoiminnallisuus `Sorttaus.java`-luokassa on toteutettu lomitusjärjestämisen avulla. Sen aikavaativuus on O(n log n) ja tilavaativuus O(n).

### Tietorakenteiden aika- ja tilavaativuudet

Listan toteutuksessa tuplataan listan koko tarvittaessa, jolloin listalle lisäämisen aikavaativuus on keskimäärin O(1). Poistamisessa käytetään myös
samaa logiikkaa: taulukon koko puolitetaan, kun käytössä on enää yksi neljännes, jolloin aikavaativuus on keskimäärin O(1).

Hajautustaulun toteutuksessa on käytetty ketjuhajautusta. Avain-arvoparin lisääminen tai poistaminen hajautustaulusta vie aikaa O(m), missä m on tutkittavan listan pituus. Hajautusarvo lasketaan kaavalla Object.hashCode() % taulukonKoko.

## Parannusehdotukset

- Kuvan mittasuhteet määritellään tällä hetkellä sovelluslogiikka-luokassa. Jos ohjelmaa haluttaisiin laajentaa käsittelemään myös muun kokoista kuvadataa, tulisi leveys ja korkeus lukea kuvan otsakkeesta. Otsakkeen lukemiselle on jo oma metodinsa, joten laajennus ei vaatisi suurta lisätyötä.
- Onnistumisprosentti voidaan tällä hetkellä laskea vain 10 henkilön testikuvista kerrallaan, sillä testikuvien määrä on koodissa määritelty vakio. Jos onnistumisprosentin testausta haluttaisiin laajentaa esimerkiksi sen tutkimiseen, kuinka monta eigenfaces-vektoria riittäisi kuvaamaan suurempaa opetusdataa, tulisi koodia muokata jonkin verran. 
- Hajautustaulua käytetetään tässä ohjelmassa hyvin rajatusti, mutta jos hajautustaulun avulla käsiteltäisiin suurempia tietomääriä, voisi avainten jakautumista tasaisesti edistää alkuluvulla jakamalla saadulla jakojäännöksellä.

## Lähteet

- Eigenfaces for Recognition. Matthew Turk and Alex Pentland (1991). Massachusetts Insitute of Technology.
- Tietorakenteet ja algoritmit. Antti Laaksonen (2021). Helsingin Yliopisto. [Linkki tirakirjaan](https://www.cs.helsinki.fi/u/ahslaaks/tirakirja/)
