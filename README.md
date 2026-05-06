# EasyTournament

**EasyTournament** è un'applicazione desktop sviluppata in JavaFX per la gestione completa di tornei sportivi e competitivi.  
Il sistema consente di creare tornei personalizzati, gestire squadre e incontri, aggiornare i risultati e visualizzare l'andamento della competizione tramite un'interfaccia grafica semplice e intuitiva.

Il progetto è stato realizzato seguendo il pattern architetturale **MVC (Model-View-Controller)**, con persistenza locale dei dati tramite **SQLite** e gestione sicura delle credenziali utente tramite **BCrypt**.

---

## Funzionalità principali

- **Registrazione e login utenti**
  - Sistema di autenticazione con credenziali cifrate tramite BCrypt.
  - Accesso sicuro alle funzionalità dell'applicazione.

- **Gestione dei tornei**
  - Creazione, modifica ed eliminazione di tornei.
  - Configurazione dei partecipanti e della modalità di competizione.

- **Modalità di competizione**
  - **Girone all'italiana**: ogni squadra affronta tutte le altre partecipanti.
  - **Eliminazione diretta**: gestione di tabelloni a scontro singolo.

- **Gestione squadre e incontri**
  - Inserimento e organizzazione delle squadre partecipanti.
  - Aggiornamento dei risultati degli incontri.
  - Visualizzazione dello stato di avanzamento del torneo.

- **Statistiche e riepiloghi**
  - Consultazione dei dettagli dei tornei.
  - Visualizzazione dei risultati e dell'andamento della competizione.

---

## Tecnologie utilizzate

- **Java 25**
- **JavaFX 23.0.1** per l'interfaccia grafica desktop
- **SQLite** per la persistenza locale dei dati
- **JDBC** per l'interazione con il database
- **jBCrypt** per l'hashing sicuro delle password
- **Apache Maven** per la gestione delle dipendenze e del ciclo di build
- **Pattern MVC** per separare logica applicativa, interfaccia grafica e gestione dei dati

---

## Architettura

Il progetto segue il pattern **Model-View-Controller**:

- **Model**: contiene le classi che rappresentano le entità principali dell'applicazione, come utenti, tornei, squadre e incontri.
- **View**: gestisce l'interfaccia grafica realizzata con JavaFX.
- **Controller**: coordina l'interazione tra interfaccia utente, logica applicativa e persistenza dei dati.
- **Database layer**: gestisce le operazioni di lettura e scrittura su SQLite tramite JDBC.

Questa struttura rende il codice più organizzato, manutenibile e facilmente estendibile.

---

## Obiettivo del progetto

EasyTournament nasce con l'obiettivo di semplificare la gestione di competizioni strutturate, evitando la gestione manuale di calendari, risultati e classifiche.

Il progetto mi ha permesso di approfondire:

- sviluppo di applicazioni desktop in Java;
- progettazione di interfacce grafiche con JavaFX;
- gestione della persistenza con SQLite e JDBC;
- applicazione del pattern MVC;
- sicurezza di base nella gestione delle password;
- modellazione della logica di business per tornei e competizioni.

---

## Possibili sviluppi futuri

- Esportazione dei risultati in PDF o CSV.
- Miglioramento della dashboard statistica.
- Supporto a più ruoli utente, come amministratore e partecipante.
- Migrazione verso un database server-side per uso multiutente.
