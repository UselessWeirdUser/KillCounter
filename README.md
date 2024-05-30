# KillCounter

### Configurazione

Il plugin, al primo avvio, genera un file di configurazione di default. All'interno di tale file &egrave;
possibile specificare le informazioni necessarie all'accesso del database. La configurazione di default
&egrave;:

```yml
database-server: localhost
database-port: 3306
database-name: database
database-user: root
database-password: password
```

&Egrave; possibile modificare i vari parametri, e in particolare:
- sotto la voce `database-server` bisogna indicare l'indirizzo del server MySQL;
- nel campo `database-port` &egrave; possibile specificare la porta di comunicazione del server;
- con `database-name` si pu&ograve; scegliere il database all'interno del quale scrivere i dati;
- tramite i campi `database-user` e `database-password` &egrave; possibile specificare, rispettiavmente,
username e password per accedere al server MySQL.

### Comandi forniti

Il plugin aggiunge un comando al server, `/stats` (accessibile anche tramite `/killcounter:stats`).
L'utilizzo del comando &egrave; semplice e intuitivo: basta eseguirlo per ottenere un resoconto delle
proprie morti, del numero di uccisioni effettuate e dell'attuale kill streak. Pu&ograve; essere eseguito
da qualsiasi player online nel server.

### Hook di PlaceholderAPI

Il plugin fornisce un'espansione interna per il plugin PlaceholderAPI, offrendo i seguenti placeholder:
- `%killcounter_kills%`, che corrisponde al numero di kill effettuate dal player;
- `%killcounter_deaths%`, che indica il numero di morti del player;
- `%killcounter_killstreak%`, che indica l'attuale _kill streak_ del player.

> Il parsing dei placeholder pu&ograve; essere effettuato esclusivamente su player online.
