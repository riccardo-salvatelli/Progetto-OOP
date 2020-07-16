<h1 align="center">Dropbox Decrypter </h1>
<p align="center">
  Applicazione che permette di scaricare e decriptare file caricati su cartella Dropbox e conseguentemente effettuare varie statistiche
</p>

## Introduzione
Il programma Dropbox Decrypter è un potente strumento per tenere al sicuro i file all'interno della propria cartella Dropbox. 

Attraverso l'algoritmo di criptazione del chaos XOR (byte a byte) derivato dall'oscillatore di Van der Pol i file caricati sono illeggibili a chiunque. Grazie a Dropbox Decrypter è possibile scaricarli in locale e decriptarli inserendo il codice personale utilizzato durante la criptazione.
Una volta scaricato il file è disponibile all'utente in locale ed è pronto per la sua fruizione.

Un'ulteriore caratteristica dell'applicazione è la possibilità di effettuare statistiche sulle tipologie più note di file come testi (txt) o immagini (jpg, png, jpeg).

## Statistiche

Le statistiche comprendono:

1) Statistiche per singolo file
  - Numero di caratteri contenuti nel file di testo
  - Numero di parole contenute nel file di testo
  - Numero di frasi contenute nel file di testo
  - Numero di pixel contenuti in un'immagine

2) Statistiche generali
  - Numero di file di testo scaricati
  - Numero di immagini scaricate
  - Media di parole contenute in tutti i file di testo
  - Media di frasi per ogni file di testo
  - Dimensione media delle immagini (media pixel)
  - Dimensione media delle immagini (largh x alt)

3) Statistiche sulla pubblicazione
  - Percentuale di caricamento degli autori dei file
  - Filtrazione per data di ultima modifica del file

4) Altro
  - Nome autore
  - Dimensione del file
  - Data ultima modifica

## Metodi
Attraverso il programma Postman è possibile fare le richieste al web-server locale.
Una volta avviato il programma è disponibile per l'utilizzo all'indirizzo
```
 localhost:8080/
```
La prima lista di metodi disponibili sono:
- `GET /getListaFIle`
Restituisce la lista di file contenuta nella cartella Dropbox
- `POST /scarica`
Permette di scaricare in locale il file desiderato specificando nel body della richiesta, in formato JSON, l'id del file e il codice per decriptare il file

Esempio di JSON per il body:
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
## Documentazione
Il codice java è interamente documentato in [Javadoc](ProgettoOOP/doc/index.html)


## Diagrammi UML
- Diagramma delle classi
//immagine
- Diagramma dei casi d'uso
//immagine
- Diagramma delle seuqenze
//immagine


### Autori
Progetto realizzato da:
- Alessandro Muscatello
- Riccardo Salvatelli
- Alessandro Zamponi
