# 🚌 Bus Tracker FVG

**Bus Tracker FVG** è un'applicazione Android moderna sviluppata in **Kotlin** e **Jetpack Compose** che permette agli utenti del trasporto pubblico locale (FVG) di monitorare gli orari dei bus in tempo reale.

L'app risolve il problema di dover cercare ogni volta la fermata sul sito web: permette di scansionare i QR Code fisici alle fermate, salvare i codici in un database locale e richiamare la tabella oraria con un solo tocco.

## ✨ Funzionalità Principali

*   **Scanner QR Intelligente:** Scansiona i codici alle fermate usando la fotocamera integrata. L'app estrae automaticamente il codice fermata (es. `UD123`) dall'URL.
*   **Database Locale:** Salva le tue fermate preferite. I dati rimangono sul telefono (persistenza dati).
*   **Orari in Tempo Reale:** Integrazione tramite WebView ottimizzata con il servizio *Realtime TPL FVG*.
*   **Gestione Fermate:** Aggiungi (via QR o manuale) ed elimina le fermate dalla tua lista.
*   **Interfaccia Moderna:** UI pulita basata su Material Design 3 e Jetpack Compose.

## 🛠 Tecnologia e Librerie

Il progetto è costruito seguendo le best practices moderne di sviluppo Android:

*   **Linguaggio:** Kotlin
*   **UI Toolkit:** Jetpack Compose (Material 3)
*   **Architettura:** MVVM (Model-View-ViewModel)
*   **Database:** Room Database (SQLite)
*   **Fotocamera:** CameraX
*   **Visione Artificiale:** Google ML Kit (Barcode Scanning)
*   **Navigazione:** Navigation Compose
*   **Web:** Android WebView (con supporto DOM Storage abilitato)

## 📱 Requisiti

*   Android SDK 35 (Compile SDK)
*   Minimo Android 8.0 (API 26)
*   Permessi richiesti: Fotocamera (per scansione), Internet (per orari).

## 🚀 Installazione e Setup

1.  Clona questo repository o scarica il codice.
2.  Apri il progetto in **Android Studio** (versione Koala/Ladybug o superiore raccomandata).
3.  Attendi la sincronizzazione di Gradle.
4.  Collega un dispositivo fisico (o usa un emulatore con fotocamera configurata).
5.  Esegui l'app (`Run`).

## 📖 Come si usa

1.  **Home:** Vedi la lista delle fermate salvate. Clicca su una card per vedere gli orari.
2.  **Aggiungi (+):** Premi il tasto "+" in basso a destra.
3.  **Scansiona:** Premi l'icona del QR Code per aprire la fotocamera e inquadra il codice alla fermata. Il campo codice si compilerà da solo.
4.  **Salva:** Dai un nome alla fermata (es. "Casa", "Lavoro") e salva.

## 📄 Note Sviluppatore

Questo progetto è stato creato a scopo didattico ed è pienamente funzionante. Utilizza le API pubbliche/Web View del servizio TPL FVG per la visualizzazione dei dati.

---
*Developed with ❤️ in Android Studio*
