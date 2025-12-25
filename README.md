# EasyTournament

**EasyTournament** è un'applicazione desktop progettata per semplificare la gestione, l'organizzazione e la visualizzazione di tornei sportivi o competitivi. Il sistema permette di gestire diverse modalità di gioco, come gironi all'italiana o eliminazione diretta, offrendo un'interfaccia intuitiva per utenti e amministratori.

## 🚀 Funzionalità Principali

* **Autenticazione Sicura:** Sistema di registrazione e login per gli utenti con cifratura delle password tramite BCrypt.
* **Gestione Tornei:** Creazione, modifica ed eliminazione di tornei personalizzati.
* **Modalità di Gioco:** * **Girone all'italiana:** Gestione di scontri dove tutti i partecipanti si affrontano tra loro.
    * **Eliminazione Diretta:** Gestione di tabelloni a scontro unico.
* **Gestione Squadre ed Incontri:** Inserimento dei partecipanti e aggiornamento dei risultati in tempo reale.
* **Dashboard Statistiche:** Visualizzazione dell'andamento dei tornei e dei dettagli di ogni incontro.

## 🛠️ Tecnologie Utilizzate

Il progetto è sviluppato in **Java** seguendo l'architettura **MVC** (Model-View-Controller) e utilizza i seguenti strumenti:

* **Linguaggio:** Java 25.
* **Interfaccia Grafica:** [JavaFX](https://openjfx.io/) (versione 23.0.1) per un'esperienza utente desktop moderna.
* **Database:** [SQLite](https://www.sqlite.org/) tramite driver JDBC per la persistenza locale dei dati.
* **Sicurezza:** [jBCrypt](https://www.mindrot.org/projects/jBCrypt/) per l'hashing sicuro delle credenziali.
* **Build Automation:** [Apache Maven](https://maven.apache.org/) per la gestione delle dipendenze e del ciclo di vita del software.
