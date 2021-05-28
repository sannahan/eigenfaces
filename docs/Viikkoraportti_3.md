# Viikkoraportti 3
Tällä viikolla tehtyä ja opittua:
- laskin eigenfaces-vektorit (koodi hyödyntää toistaiseksi vielä apachen math3-kirjastoa ominaisarvojen ja -vektoreiden laskemiseen). JavaFX:llä näytetään nyt keskiarvokasvot ja 10 kpl eigenfaces-vektoreita kuvina.
- opin kirjaston käyttöönotossa lisää build.gradlen rakentamisesta. 

Epäselväksi jäi:
- en saanut vieläkään hyvää ideaa ominaisarvojen ja -vektoreiden laskemisesta, enkä keksinyt vielä, miten laajentaisin projektia muilta osin, jos en saa näitä ominaisuuksia toteutettua. Puolet ajasta meni tällä viikolla tuloksettomaan googlailuun, mikä oli vähän turhauttavaa.
- varasin nyt kirjastosta Wilkinsonin ja Reinschin Linear Algebra -kirjan, jolle matlabkin käsittääkseni pohjaa
- eigenfaces-luokkaan jäi nyt yksi 4 miljoonan alkion for-loop (rivit 78-84), jolle en keksinyt tehokkaampaa laskutapaa:

```Java
    // yhtälö 6 Turkin ja Pentlandin artikkelista
    for (int kuva = 0; kuva < 10; kuva++) {
      for (int sigmaIndeksi = 0; sigmaIndeksi < 40; sigmaIndeksi++) {
        for (int pikseli = 0; pikseli < kuvavektorinPituus; pikseli++) {
          eigenfaces[kuva][pikseli] += ominaisvektorit[sigmaIndeksi][kuva] * opetusdata[sigmaIndeksi][pikseli];
        }
      }
    }
```

Mitä teen seuraavaksi:

Ominaisarvojen ja -vektoreiden selvittäminen jatkuu

Tuntimäärä: 18 tuntia
