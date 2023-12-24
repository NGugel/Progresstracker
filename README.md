# MobileAppEntwicklung:Progresstracker

Kurs: TIF21B
Gruppe: F
Entwickler: NiclasGugel,PatrickFurtwängler

Repository: https://github.com/NGugel/Progresstracker

Entwicklungsansatz:KotlinmitXMLLayouts

BeschreibungderApp:

DieProgresstrackerAppbietetzweiModi:

```
● EinmaldenLernmodusumsichselbstverschiedeneKategorienanzulegen
unddenenFragenzuzuordnen,diesekönnendannzumlernen
durchgearbeitetwerden.
● UnddenFreizeitmodus,hierkönnenverschiedeneFreizeitaktivitätenangelegt
werdenundihnenAktionenoderAktivitätenunterordnenumsich
beispielsweisebeimSportanseineangabenzuhaltenundsicheinen
ÜberblicküberseinenFortschrittzuverschaffen.
```
MUSTHAVEAnforderungen:

```
● ListevonKategoriendiesestehenfürbeliebigeThemenoder
Vorlesungsinhalte,diejemandlernenmöchte(Beispiele:Englisch,
Geographie,Marketing,Kotlin,Algorithmen,etc.).Kategorienenthalten
beliebigvieleFragen.
```
```
● Verwaltungsmodus:
○ Kategorienkönnenerstellt,bearbeitetundgelöschtwerden.
○ FragenzueinerKategoriekönnenerstellt,bearbeitetundgelöscht
werden.
○ DreiunterschiedlicheFragetypen
■ „MultipleChoicefürdieAuswahleinerAntwortaus 4
Möglichkeiten
■ FreiformulierbareAntwortineinerTexteingabe
■ VorgegebeneAntwortmitFehlernmussineinerTexteingabe
korrigiertwerden
```

```
● Anwendungsmodus
○ DieAuswahleinerKategorieoderallenKategorienführtzueinerListe
vonFragen.
○ DieFragenkönnenalsQuizzumLernendurchgearbeitetwerden:
■ Eswirdgefragt,wievieleFragenzudemThemabeantwortet
werdensollen.
■ FürdieseAnzahlwerdenausdemThemazufälligeFragen
gewählt.
■ HierbeiwerdennacheinanderdieeinzelnenFragengestelltund
dazukönnenAntworteneingegebenwerden.ZudenAntworten
gibtespassendeRückmeldungen
```
## NICETOHAVES:

```
● Freizeitmodus
○ ErstellenvonFreizeitaktivitätenmituntergeordnetenAktivitäten
○ GenerischeImplementierungvonbeliebigvielenBildschirmenmit
jeweilsdazugehörigen,eigenenSharedPreferencesundselbst
implementiertemSwipeListener
○ ImplementierungmittelsSharedpreferences,persistenteLokale
speicherung
```
```
● ImplementierungeinerlokalenDatenbankfürdenLernmodus
○ Roomdatenbankimplementiert,
TabelleKategorien,TabelleFragen
JedeFrageisteinerKategoriemittelsForeignkeyzugewiesen
```
```
○ AppRepositoryschnittstelleumzukünftigCloudspeichermöglichkeiten
leichtzuimplementieren
```

