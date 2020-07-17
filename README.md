<h1 align="center">Dropbox Decrypter </h1>
<p align="center">
  Applicazione che permette di scaricare e decriptare file caricati su cartella Dropbox e conseguentemente effettuare varie statistiche
</p>

### Tabella dei contenuti
* [Introduzione](#intro)
* [Installazione](#install)
* [Configurazione](#config)
* [Statistiche](#stats)
* [Metodi](#metod)
* [Struttura progetto](#strutt)
* [Documentazione](#doc)
* [Diagrammi UML](#uml)

<a name="intro"></a>
## Introduzione
Il programma Dropbox Decrypter è un potente strumento per tenere al sicuro i file all'interno della propria cartella Dropbox. 

Attraverso l'algoritmo di criptazione del chaos XOR (byte a byte) derivato dall'oscillatore di Van der Pol i file caricati sono illeggibili a chiunque. Grazie a Dropbox Decrypter è possibile scaricarli in locale e decriptarli inserendo il codice personale utilizzato durante la criptazione.
Una volta scaricato il file è disponibile all'utente in locale ed è pronto per la sua fruizione.

Un'ulteriore caratteristica dell'applicazione è la possibilità di effettuare statistiche sulle tipologie più note di file come testi (txt) o immagini (jpg, png, jpeg).

<a name="install"></a>
## Installazione
Dropbox Decrypter è installabile semplicemente eseguendo:
```git clone https://github.com/riccardo-salvatelli/Progetto-OOP```

<a name="config"></a>
## Configurazione
Per configurare Dropbox Decrypter è necessario modificare la variabile ```token``` all'interno di [ServizioFileImpl.java](ProgettoOOP/src/main/java/it/progettoOOP/service/ServizioFileImpl.java). Una volta fatto ciò basta avviare il web-server eseguendo [DropboxDecrypterApplication.java](ProgettoOOP/src/main/java/it/progettoOOP/DropboxDecrypterApplication.java)

<a name="stats"></a>
## Statistiche

Le statistiche comprendono:

* Statistiche per singolo file
  * Numero di caratteri contenuti nel file di testo
  * Numero di parole contenute nel file di testo
  * Numero di frasi contenute nel file di testo
  * Numero di pixel contenuti in un'immagine
  
* Statistiche generali
  * Numero di file di testo scaricati
  * Numero di immagini scaricate
  * Media di parole contenute in tutti i file di testo
  * Media di frasi per ogni file di testo
  * Dimensione media delle immagini (media pixel)
  * Dimensione media delle immagini (largh x alt)

* Statistiche sulla pubblicazione
  * Percentuale di caricamento degli autori dei file
  * Filtrazione per data di ultima modifica del file

* Altro
  * Nome autore
  * Dimensione del file
  * Data ultima modifica

<a name="metod"></a>
## Metodi
Attraverso richieste HTTP è possibile interagire con il web-server locale specificando i corretti endpoint qui sotto elencati.
L'indirizzo associato al web-server è
```
localhost:8080/
```
Gli endpoint definiti sono:
- `GET /getListaFIle`
Restituisce la lista di file contenuta nella cartella Dropbox
- `POST /scarica`
Permette di scaricare in locale il file desiderato passando nel body della richiesta, in formato JSON, un oggetto ```Credenziali``` per decriptare il file

Parametri del body:
```
{
    "id": "id:udSkk_E_5_XXXXXXXXXXXX",
    "chiavi": [1,2]
}
```

Una volta scaricati i primi file è possibile effettuare anche i seguenti metodi:
- ` GET /listaLocale` Restituisce la lista scaricata localmente dal precedente endpoint.
- ` GET /numeroTxt` Restituisce il numero di file di testo scaricate localmente.
- ` GET /numeroImm` Restituisce il numero di immagini scaricate localmente.
- ` GET /mediaParole` Restituisce la media di parole contenute nei file scaricati.
- ` GET /mediaFrasi` Restituisce la media di frasi contenute nei file scaricati.
- ` GET /mediaPixel` Restituisce la media di pixel totali delle immagini.
- ` GET /mediaDimImm` Restituisce la media delle dimensioni delle immagini (lar x alt).
- ` GET /statAutori` Restituisce in percentuale quanti file ha caricato.
- ` POST /filtraData` Restituisce i file scaricati in un'arco temportale specificato nel body della richiesta.

Esempio di JSON per il body:
```
{
    "dataInizio" : 0,
    "dataFine" : {
        "data" : "15/07/2020",
        "ora" : "12:00:00"
    }
}
```
<a name="doc"></a>
## Documentazione
Il codice java è interamente documentato in [Javadoc](ProgettoOOP/doc/index.html)

<a name="uml"></a>
## Diagrammi UML
![alt text](https://raw.githubusercontent.com/riccardo-salvatelli/Progetto-OOP/master/UML/ClassDiagram.png)
*Diagramma delle classi*
![alt text](https://raw.githubusercontent.com/riccardo-salvatelli/Progetto-OOP/master/UML/Diagramma_dei_casi_d'uso.jpg)
*Diagramma dei casi d'uso*
![alt_text](https://raw.githubusercontent.com/riccardo-salvatelli/Progetto-OOP/master/UML/Diagramma%20delle%20seuqenze.png)
*Diagramma delle seuqenze*

<a name="strutt"></a>
## Struttura progetto
```
.
├── 1
├── ProgettoOOP
│   ├── doc
│   │   ├── allclasses-index.html
│   │   ├── allpackages-index.html
│   │   ├── constant-values.html
│   │   ├── deprecated-list.html
│   │   ├── element-list
│   │   ├── help-doc.html
│   │   ├── index-files
│   │   │   ├── index-10.html
│   │   │   ├── index-1.html
│   │   │   ├── index-2.html
│   │   │   ├── index-3.html
│   │   │   ├── index-4.html
│   │   │   ├── index-5.html
│   │   │   ├── index-6.html
│   │   │   ├── index-7.html
│   │   │   ├── index-8.html
│   │   │   └── index-9.html
│   │   ├── index.html
│   │   ├── it
│   │   │   └── progettoOOP
│   │   │       ├── class-use
│   │   │       │   └── DropboxDecrypterApplication.html
│   │   │       ├── controller
│   │   │       │   ├── class-use
│   │   │       │   │   └── DropboxDecrypterController.html
│   │   │       │   ├── DropboxDecrypterController.html
│   │   │       │   ├── package-summary.html
│   │   │       │   ├── package-tree.html
│   │   │       │   └── package-use.html
│   │   │       ├── DropboxDecrypterApplication.html
│   │   │       ├── exception
│   │   │       │   ├── ChiaviErrateException.html
│   │   │       │   ├── ChiaviNullException.html
│   │   │       │   ├── class-use
│   │   │       │   │   ├── ChiaviErrateException.html
│   │   │       │   │   ├── ChiaviNullException.html
│   │   │       │   │   └── DivZeroException.html
│   │   │       │   ├── DivZeroException.html
│   │   │       │   ├── package-summary.html
│   │   │       │   ├── package-tree.html
│   │   │       │   └── package-use.html
│   │   │       ├── model
│   │   │       │   ├── class-use
│   │   │       │   │   ├── Credenziali.html
│   │   │       │   │   ├── Decriptazione.html
│   │   │       │   │   ├── File.html
│   │   │       │   │   ├── Immagine.html
│   │   │       │   │   └── Testo.html
│   │   │       │   ├── Credenziali.html
│   │   │       │   ├── Decriptazione.html
│   │   │       │   ├── File.html
│   │   │       │   ├── Immagine.html
│   │   │       │   ├── package-summary.html
│   │   │       │   ├── package-tree.html
│   │   │       │   ├── package-use.html
│   │   │       │   └── Testo.html
│   │   │       ├── package-summary.html
│   │   │       ├── package-tree.html
│   │   │       ├── package-use.html
│   │   │       └── service
│   │   │           ├── class-use
│   │   │           │   ├── ServizioFile.html
│   │   │           │   └── ServizioFileImpl.html
│   │   │           ├── package-summary.html
│   │   │           ├── package-tree.html
│   │   │           ├── package-use.html
│   │   │           ├── ServizioFile.html
│   │   │           └── ServizioFileImpl.html
│   │   ├── member-search-index.js
│   │   ├── member-search-index.zip
│   │   ├── overview-summary.html
│   │   ├── overview-tree.html
│   │   ├── package-search-index.js
│   │   ├── package-search-index.zip
│   │   ├── resources
│   │   │   ├── glass.png
│   │   │   └── x.png
│   │   ├── script-dir
│   │   │   ├── external
│   │   │   │   └── jquery
│   │   │   │       └── jquery.js
│   │   │   ├── images
│   │   │   │   ├── ui-bg_glass_55_fbf9ee_1x400.png
│   │   │   │   ├── ui-bg_glass_65_dadada_1x400.png
│   │   │   │   ├── ui-bg_glass_75_dadada_1x400.png
│   │   │   │   ├── ui-bg_glass_75_e6e6e6_1x400.png
│   │   │   │   ├── ui-bg_glass_95_fef1ec_1x400.png
│   │   │   │   ├── ui-bg_highlight-soft_75_cccccc_1x100.png
│   │   │   │   ├── ui-icons_222222_256x240.png
│   │   │   │   ├── ui-icons_2e83ff_256x240.png
│   │   │   │   ├── ui-icons_454545_256x240.png
│   │   │   │   ├── ui-icons_888888_256x240.png
│   │   │   │   └── ui-icons_cd0a0a_256x240.png
│   │   │   ├── jquery-3.4.1.js
│   │   │   ├── jquery-ui.css
│   │   │   ├── jquery-ui.js
│   │   │   ├── jquery-ui.min.css
│   │   │   ├── jquery-ui.min.js
│   │   │   ├── jquery-ui.structure.css
│   │   │   ├── jquery-ui.structure.min.css
│   │   │   ├── jszip
│   │   │   │   └── dist
│   │   │   │       ├── jszip.js
│   │   │   │       └── jszip.min.js
│   │   │   └── jszip-utils
│   │   │       └── dist
│   │   │           ├── jszip-utils-ie.js
│   │   │           ├── jszip-utils-ie.min.js
│   │   │           ├── jszip-utils.js
│   │   │           └── jszip-utils.min.js
│   │   ├── script.js
│   │   ├── search.js
│   │   ├── serialized-form.html
│   │   ├── stylesheet.css
│   │   ├── system-properties.html
│   │   ├── type-search-index.js
│   │   └── type-search-index.zip
│   ├── DropboxDecrypter.iml
│   ├── fileScaricati
│   ├── mvnw
│   ├── mvnw.cmd
│   ├── pom.xml
│   ├── src
│   │   ├── main
│   │   │   ├── java
│   │   │   │   └── it
│   │   │   │       ├── progettoDecrypterApplication
│   │   │   │       │   └── java
│   │   │   │       │       └── model
│   │   │   │       └── progettoOOP
│   │   │   │           ├── controller
│   │   │   │           │   └── DropboxDecrypterController.java
│   │   │   │           ├── DropboxDecrypterApplication.java
│   │   │   │           ├── exception
│   │   │   │           │   ├── ChiaviErrateException.java
│   │   │   │           │   ├── ChiaviNullException.java
│   │   │   │           │   └── DivZeroException.java
│   │   │   │           ├── model
│   │   │   │           │   ├── Credenziali.java
│   │   │   │           │   ├── Decriptazione.java
│   │   │   │           │   ├── File.java
│   │   │   │           │   ├── Immagine.java
│   │   │   │           │   └── Testo.java
│   │   │   │           └── service
│   │   │   │               ├── ServizioFileImpl.java
│   │   │   │               └── ServizioFile.java
│   │   │   └── resources
│   │   │       └── application.properties
│   │   └── test
│   │       └── java
│   │           └── it
│   │               └── progettoOOP
│   │                   ├── DropboxDecrypter
│   │                   │   └── DropboxDecrypterApplicationTests.java
│   │                   ├── model
│   │                   │   └── FileTest.java
│   │                   ├── service
│   │                   │   └── ServizioFileImplTest.java
│   │                   └── test
│   └── target
│       ├── classes
│       │   ├── application.properties
│       │   └── it
│       │       └── progettoOOP
│       │           ├── controller
│       │           │   └── DropboxDecrypterController.class
│       │           ├── DropboxDecrypterApplication.class
│       │           ├── exception
│       │           │   ├── ChiaviErrateException.class
│       │           │   ├── ChiaviNullException.class
│       │           │   └── DivZeroException.class
│       │           ├── model
│       │           │   ├── Credenziali.class
│       │           │   ├── Decriptazione.class
│       │           │   ├── File.class
│       │           │   ├── Immagine.class
│       │           │   └── Testo.class
│       │           └── service
│       │               ├── ServizioFile.class
│       │               └── ServizioFileImpl.class
│       ├── generated-sources
│       │   └── annotations
│       ├── generated-test-sources
│       │   └── test-annotations
│       └── test-classes
│           └── it
│               └── progettoOOP
│                   ├── DropboxDecrypter
│                   │   └── DropboxDecrypterApplicationTests.class
│                   ├── model
│                   │   └── FileTest.class
│                   └── service
│                       └── ServizioFileImplTest.class
├── README.md
└── UML
    ├── ClassDiagram.png
    └── ProgettoOOP_Model Use Case Diagram.jpg

64 directories, 136 files
```

### Autori
Progetto realizzato da:
- Alessandro Muscatello
- Riccardo Salvatelli
- Alessandro Zamponi
