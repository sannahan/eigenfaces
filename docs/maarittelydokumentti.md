# Määrittelydokumentti

Toteutan labrassa Eigenfaces-algoritmin, jota voidaan käyttää kasvojentunnistukseen. Algoritmin avulla voidaan:
- tarkistaa, vastaako annettu kasvokuva opetusdatan kasvoja 
- tarkistaa, onko annettu kuva ylipäänsä kasvokuva

Käytän projektissani pääasiassa seuraavaa kahta lähdettä:

Coding the Matrix. Philip N. Klein (2013). Newtonian Press.
Eigenfaces for Recognition. Matthew Turk and Alex Pentland (1991). Massachusetts Insitute of Technology.

Näistä jälkimmäinen on Eigenfaces-algoritmin kehittäjien laatima artikkeli, joka kuvaa algoritmin vaiheet ja käyttökohteet. Coding the Matrix -kirja esittelee, miten lineaarialgebraa voi hyödyntää tietojenkäsittelytieteen sovelluksissa. Kirjasta on (toivottavasti) hyötyä algoritmin käytännön toteutuksessa.

Valitaan opetusdataksi artikkelissa ehdotettu 40 kappaletta 256 x 256 mustavalkokuvia, 4 kuvaa per henkilö. Koska kuva on mustavalkokuva, yksi pikseli saa arvon 0-255, joten opetusdata vaatii 40 x 256 x 256 tavua säilytystilaa. Algoritmin syötteet ovat niinikään mustavalkokuvia, joiden kooksi valitaan 256 x 256. Turkin ja Pentlandin artikkeli kuvaa myös tapoja löytää kasvot isommasta kuvasta – aion kuitenkin käyttää syötteenä tarkasti kasvoihin rajattuja kuvia ja keskittyä tähän kasvojenetsimistoiminnallisuuteen vain ajan sen salliessa.  

Eigenfaces-algoritmin tarkoituksena on vähentää aikavaatimus O(n²) O(m), jossa n = 256 (pikseleiden määrä yhdellä rivillä) ja m = opetusdatan kuvien määrä. Tilavaativuus on O(n²).

Algoritmissa on paljon matriisien ja vektoreiden käsittelyä edellyttävää toiminnallisuutta, joten voisi olla aiheellista tehdä omat, taulukoita ja taulukoiden taulukoita käyttävät tietorakenteet.

Opinto-ohjelmani on tietojenkäsittelytieteen kandidaatti, projektin dokumentaatiossa käytetty kieli on suomi ja ohjelmointikieli on Java.
