# Viikkoraportti 5

Tällä viikolla tehtyä ja opittua:

- tein metodit, joilla voi testata algoritmin onnistumista kasvojentunnistuksessa yhden, kahden ja kolmen kasvoluokan tapauksessa (3 kasvoluokan tapauksessa hyödynnetään 3-lähimmän naapurin menetelmää)
- tulokset olivat jossain määrin yllättäviä: melko usein kahden ja kolmen luokan käyttö nosti onnistumisprosenttia hieman yhteen luokkaan verrattuna, mutta ei aina
- myös siinä oli eroavaisuutta, saadaanko kahden vai kolmen luokan avulla parempi tulos
- esimerkiksi kuvien 20-29 kohdalla yhtä luokkaa käytettäessä onnistumisprosentti on 87 prosenttia, kahta luokkaa käytettäessä 100 prosenttia ja kolmea luokkaa käytettäessä 94 prosenttia
- eigenfaces-algoritmin vahvuudet ovat siinä, että matriisi saatetaan sellaiseen muotoon, että eigenfaces-vektoreiden laskeminen on mahdollista, ja siinä, että paljon tilaa vaativat kuvatiedostot voidaan esittää eigenfaces-vektoreiden avulla. Algoritmin testaaminen opetusdataa vaihtaen on puolestaan raskasta, sillä opetusdatan vaihtuessa kaikki laskenta tulee tehdä uudestaan. Sovelluksen käynnistäminen kestääkin tällä hetkellä huomattavasti aikaisempaa pidempään (n. 30 sekuntia) testien raskaudesta johtuen
- refaktoroin koodia siten, että opetusdataan luettavat henkilöt voidaan valita kätevämmin sovelluslogiikka-luokasta käsin

Epäselväksi jäi:

- erityisesti eigenfaces-luokan metodeita olisi edelleen syytä pilkkoa, jotta kattavampi testaaminen mahdollistuisi

Mitä teen seuraavaksi:

- suorituskykytestaamiseen tutustuminen
- k-lähimmän naapurin etsimisestä vastaavan metodin pilkkominen ja selventäminen (olisi myös mukavaa toteuttaa metodin hyödyntämä järjestämistoiminnallisuus ja HashMap itse)
- demoa silmälläpitäen olisi hyvä saada graafinen näkymä ensin ilman testejä näkyviin, jotta ohjelman käyttäminen nopeutuisi

Tuntimäärä: 14 tuntia
