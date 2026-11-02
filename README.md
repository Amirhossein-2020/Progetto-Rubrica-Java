# Progetto Rubrica (Java Swing + MySQL)

Software di gestione contatti con interfaccia grafica e persistenza su database.

## Funzionalità
* **CRUD Completo:** Creazione, Lettura, Aggiornamento ed Eliminazione contatti.
* **Persistenza MySQL:** Utilizzo di JDBC per salvare i dati.
* **Login System:** Accesso protetto tramite username/password.
* **Interfaccia:** Java Swing con Look & Feel "Nimbus".

## Requisiti
* Java JDK 8+
* MySQL Server

## Installazione e Configurazione
1. Eseguire lo script `schema_database.sql` nel proprio client MySQL per creare il database e l'utente di test.
2. Modificare il file `credenziali_database.properties` inserendo le proprie credenziali MySQL locali.
3. Compilare ed eseguire il progetto (classe Main: `rubrica.RubricaFrame`).
