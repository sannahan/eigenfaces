# Toteutusdokumentti

## Yleisrakenne

Ohjelmassa on neljä pakkausta: dao, domain, ui ja utils. Dao sisältää kuvien lukemiseen tarvittavan toiminnallisuuden. Domain sisältää eigenfaces-algoritmin toiminnallisuuden ja ui javafx-käyttöliittymän, joka käynnistetään Main-luokasta käsin. Utils sisältää tarvittavat tietorakenteet.

## Aika- ja tilavaativuudet

### Algoritmit

Järjestämistoiminnallisuus on toteutettu lomitusjärjestämisen avulla. Sen aikavaativuus on O(n log n) ja tilavaativuus O(n).

### Tietorakenteet

Listan toteutuksessa tuplataan listan koko tarvittaessa, jolloin listalle lisäämisen aikavaativuus on keskimäärin O(1). Poistamisessa käytetään myös
samaa logiikkaa: taulukon koko puolitetaan, kun käytössä on enää yksi neljännes, jolloin aikavaativuus on keskimäärin O(1).

Hajautustaulun toteutuksessa on käytetty ketjuhajautusta. Avain-arvoparin lisääminen tai poistaminen hajautustaulusta vie aikaa O(m), missä m on tutkittavan listan pituus. Hajautusarvo lasketaan kaavalla Object.hashCode() % taulukonKoko.

## Parannusehdotukset

- Kuvan mittasuhteet määritellään tällä hetkellä sovelluslogiikka-luokassa. Jos ohjelmaa haluttaisiin laajentaa käsittelemään myös muun kokoista kuvadataa, tulisi leveys ja korkeus lukea kuvan otsakkeesta.
- Hajautustaulua käytetetään tässä ohjelmassa hyvin rajatusti, mutta jos hajautustaulun avulla käsiteltäisiin suurempia tietomääriä, voisi avainten jakautumista tasaisesti edistää alkuluvulla jakamalla saadulla jakojäännöksellä.

## Lähteet
