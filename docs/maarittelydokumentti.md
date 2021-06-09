# Määrittelydokumentti

Toteutan labrassa Eigenfaces-algoritmin, jota voidaan käyttää kasvojentunnistukseen. Algoritmin avulla voidaan:
- tarkistaa, vastaako annettu kasvokuva opetusdatan kasvoja 
- tarkistaa, onko annettu kuva ylipäänsä kasvokuva

Käytän projektissani pääasiassa seuraavaa kahta lähdettä:

- Coding the Matrix. Philip N. Klein (2013). Newtonian Press.
- Eigenfaces for Recognition. Matthew Turk and Alex Pentland (1991). Massachusetts Insitute of Technology.

Näistä jälkimmäinen on Eigenfaces-algoritmin kehittäjien laatima artikkeli, joka kuvaa algoritmin vaiheet ja käyttökohteet. Coding the Matrix -kirja esittelee, miten lineaarialgebraa voi hyödyntää tietojenkäsittelytieteen sovelluksissa. Kirjasta on (toivottavasti) hyötyä algoritmin käytännön toteutuksessa.

Valitaan opetusdataksi artikkelissa ehdotettu 40 kappaletta 92 x 112 mustavalkokuvia, 4 kuvaa per henkilö. Koska kuva on mustavalkokuva, yksi pikseli saa arvon 0-255, joten opetusdata vaatii 40 x 92 x 112 tavua säilytystilaa. Algoritmin syötteet ovat niinikään mustavalkokuvia, joiden kooksi valitaan 92 x 112. Turkin ja Pentlandin artikkeli kuvaa myös tapoja löytää kasvot isommasta kuvasta – aion kuitenkin käyttää syötteenä tarkasti kasvoihin rajattuja kuvia ja keskittyä tähän kasvojenetsimistoiminnallisuuteen vain ajan sen salliessa.  

Eigenfaces-algoritmin tarkoituksena on vähentää aikavaatimus O(n²) O(m), jossa n = 92 (pikseleiden määrä yhdellä rivillä) ja m = opetusdatan kuvien määrä. Tilavaativuus on O(n²).

Algoritmissa on paljon matriisien ja vektoreiden käsittelyä edellyttävää toiminnallisuutta, joten voisi olla aiheellista tehdä omat, taulukoita ja taulukoiden taulukoita käyttävät tietorakenteet.

Opinto-ohjelmani on tietojenkäsittelytieteen kandidaatti, projektin dokumentaatiossa käytetty kieli on suomi ja ohjelmointikieli on Java.

Päivitys 9.6.2021:

Kurssin ohjaajan kanssa on sovittu, että ohjelmaa kehitetään niin, että kasvojentunnistusta testataan seuraavasti:
- valitaan 10 henkilöstä 4 kuvaa opetusdataksi ja 6 kuvaa testidataksi
- valitaan 10 henkilöstä 2 x 4 kuvaa opetusdataksi ja 2 kuvaa testidataksi
- valitaan 10 henkilöstä 3 x 3 kuvaa opetusdataksi ja 1 kuva testidataksi (vaihdellen testidatana käytettävää kuvaa ja käyttäen kolmen lähimmän naapurin menetelmää)

Näin saatuja testituloksia verrataan keskenään. Samalla on sovittu, että projektissa saa käyttää Javan kirjastoja ominaisarvojen ja -vektoreiden laskemiseen.
