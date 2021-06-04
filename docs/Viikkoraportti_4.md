# Viikkoraportti 4

Tällä viikolla tehtyä ja opittua:

- lisäsin graafiseen käyttöliittymään mahdollisuuden testata kasvojentunnistusta kuvilla, jotka kuvaavat opetusdatan henkilöitä, mutta eivät kuulu opetusdataan
- syötettä ei toistaiseksi tarkisteta, eli ohjelma todennäköisesti kaatuu, ellei syöte ole ohjeiden mukainen
- kuvia, jotka kuvaavat opetusdatan henkilöitä, mutta eivät kuulu opetusdataan, on 60. Kasvot tunnistetaan 52 / 60 todennäköisyydellä (noin 87 prosenttia) 

Epäselväksi jäi:

- eigenfaces-luokan metodien vastuita tulisi vähentää, jotta niiden testaaminen olisi mahdollista. Muutamasta suuresta metodista johtuen testikattavuus on nyt melko alhainen
- tein sovelluslogiikkaluokkaan toisen konstruktorin mock-olioilla testaamista varten, koska en keksinyt muuta selkeää tapaa riippuvuuksien injektoinnille javafx-toiminnallisuutta toteuttavasta luokasta käsin
- olisi mielenkiintoista testata, kuinka monta eigenfaces-kuvavektoria vaadittaisiin onnistumisprosenttin kasvattamiseksi lähes 100 prosenttiin

Mitä teen seuraavaksi:

- en vielä tiedä, mitä teen seuraavaksi. Sovitaan tosiaan tapaaminen ensi viikon alkuun!

Tuntimäärä: 10 tuntia
