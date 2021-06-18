# Viikkoraportti 6

Tällä viikolla tehtyä ja opittua:

- korvasin Javan HashMapin ja Arrays.sortin omilla tietorakenteilla
- olen kirjoittanut yhden toteutuksen Hajautustaulu- ja Lista-luokista jo ohjelmoinnin jatkokurssilla - oli mukavaa tarkastella silloin kirjoitettua koodia tirakirjan opeista viisastuneena
- HashMapin käyttöhän on tässä totetuksessa melkoisen turhaa (vastaavan esiintymiskertojen laskun voisi toteuttaa helpommin taulukolla, kun kyseessä on kokonaisluvut väliltä 0-9), mutta ajatettelin, että olisi mukavaa saada projektiin joku oma tietorakenne

Epäselväksi jäi:

- miltä kantilta minun kannattaisi lähestyä suorituskykytestejä, kun kuva-aineisto on rajallinen? Turkin ja Pentlandin artikkelissa sanotaan näin:

> Designing a practical system for face recognition
> within this framework requires assessing the tradeoffs
> between generality, required accuracy, and speed. If the
> face recognition task is restricted to a small set of people
> (such as the members of a family or a small company),
> a small set of eigenfaces is adequate to span the faces of
> interest. If the system is to learn new faces or represent
> many people, a larger basis set of eigenfaces will be
> required. The results of Sirovich and Kirby (1987) and
> Kirby and Sirovich (1990) for coding of face images gives
> some evidence that even if it were necessary to represent
> a large segment of the population, the number of eigen-
> faces needed would still be relatively small.

- kokeilemalla vaikuttaisi siltä, että kymmenellä eigenfaces-vektorilla saadaan hyvät tulokset kaikkien 40 henkilön tunnistamiseen (kun aikaisemmin on testattu 10 henkilön tunnistamista)

Mitä teen seuraavaksi:

- erityisesti k-lähimmän naapurin etsimisestä vastaavan metodin, mutta myös muun eigenfaces-luokan toiminnallisuuden pilkkominen, jotta single responsibility toteutuu
- en ehtinyt eriyttää tunnistuksen onnistumista mittaavia testejä vielä omaksi testiohjelmakseen tällä viikolla, ensi viikolla keskityn testaamisen ja dokumentaation viimeistelemiseen

Tuntimäärä: 15 tuntia
