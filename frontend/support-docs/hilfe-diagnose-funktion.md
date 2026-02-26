# Diagnose-Funktion bei Anwendungsproblemen (hilfe)

Bei technischen Problemen wie einem "weißen Bildschirm" oder unerwartetem Verhalten können Sie eine Diagnosedatei erstellen, die dem Support bei der Fehleranalyse hilft.

## Wann verwenden?

Nutzen Sie diese Funktion, wenn:
- Die Anwendung einen weißen Bildschirm zeigt
- Unerwartete Fehler auftreten
- Funktionen nicht wie erwartet arbeiten
- Der Support Sie um diese Datei bittet

## Schritt-für-Schritt Anleitung

### Schritt 1: Entwicklertools öffnen und Konsole auswählen

Drücken Sie **F12** auf Ihrer Tastatur, um die Browser-Entwicklertools zu öffnen. Klicken Sie dann auf den Reiter **"Console"** (Konsole).

![Entwicklertools mit Konsole](images/devtools-console.png)

Beim Laden der Seite sehen Sie bereits einen Hinweis:
```
Bei Problemen: Tippen Sie hilfe() ein und drücken Sie Enter, um eine Diagnose-Datei herunterzuladen.
```

### Schritt 2: Befehl eingeben

Tippen Sie in der Konsole den folgenden Befehl ein:

```
hilfe()
```

Drücken Sie dann **Enter**.

![hilfe() eingeben](images/hilfe-command.png)

### Schritt 3: Datei herunterladen

Der Browser lädt automatisch eine Datei herunter mit dem Namen (je nach Browser-Einstellung erscheint ein Speichern-Dialog):
```
metasfresh-diagnose-[Zeitstempel].json
```

In der Konsole erscheint eine Bestätigung in grüner Schrift:
```
Diagnose-Datei wurde heruntergeladen. Bitte senden Sie diese Datei an den Support.
```

![Download bestätigt](images/download-confirmed.png)

### Schritt 4: Datei an Support senden

Senden Sie die heruntergeladene JSON-Datei an den metasfresh-Support per E-Mail oder über Ihr Ticket-System.

## Was enthält die Diagnosedatei?

Die Datei enthält technische Informationen zur Fehleranalyse:

| Information | Beschreibung |
|-------------|--------------|
| **exportTime** | Zeitpunkt der Diagnoseerstellung |
| **buildHash** | Version der Anwendung |
| **browserInfo** | Browser, Sprache, Bildschirmgröße |
| **errors** | Liste der aufgetretenen JavaScript-Fehler |
| **events** | Protokoll der Navigations- und Prozessereignisse (bis zu 200 Einträge) |

Die **events**-Liste zeichnet auf, welche Seitenwechsel und Aktionen in der Anwendung stattgefunden haben. Dies hilft dem Support insbesondere bei der Analyse von unerwarteten Weiterleitungen oder Seitenwechseln.

| Ereignistyp | Beschreibung |
|-------------|--------------|
| **navigation** | Jede URL-Änderung (Seitenwechsel) |
| **processAction** | Ergebnis einer Prozessausführung (z.B. Beleg öffnen, Ansicht öffnen) |
| **redirect** | Weiterleitung durch die Anwendung (z.B. automatische Umleitung, Verwerfen-Dialog) |
| **popstate** | Browser Vor-/Zurück-Navigation |

### Beispiel einer Diagnosedatei

```json
{
  "exportTime": "2026-01-16T14:31:22.659Z",
  "buildHash": "7fe58624e7c80f1757d3",
  "browserInfo": {
    "userAgent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) ...",
    "language": "de-DE",
    "url": "http://localhost/login",
    "screen": "1920x1080",
    "window": "1200x800"
  },
  "errors": [
    {
      "timestamp": "2026-01-16T14:26:59.519Z",
      "type": "error",
      "message": "Cannot read property 'data' of undefined",
      "source": "http://localhost/bundle.js",
      "lineno": 12345,
      "stack": "Error: Cannot read property..."
    }
  ],
  "events": [
    {
      "timestamp": "2026-01-16T14:30:01.123Z",
      "type": "navigation",
      "action": "push",
      "to": "/window/143/1000000",
      "url": "http://localhost/window/143"
    },
    {
      "timestamp": "2026-01-16T14:30:15.456Z",
      "type": "processAction",
      "processId": "WEBUI_Shipment_Schedule",
      "pinstanceId": "12345",
      "actionType": "openView",
      "windowId": "540674",
      "viewId": "540674-aabbcc",
      "url": "http://localhost/window/143/1000000"
    }
  ]
}
```

## Datenschutz

Die Diagnosedatei enthält **keine** persönlichen Daten, Passwörter oder Geschäftsdaten. Es werden ausschließlich technische Informationen über den Browser, aufgetretene Fehler und Navigations-/Prozessereignisse erfasst. Die Ereignisse enthalten URL-Pfade, Prozess-IDs und Fenster-/Ansichts-Kennungen — keine Dokumentinhalte oder Geschäftsdaten.

## Häufige Fragen

### Die Funktion hilfe() wird nicht erkannt?

Stellen Sie sicher, dass Sie die Konsole im richtigen Browser-Tab geöffnet haben (dort wo metasfresh läuft). Laden Sie die Seite neu (F5) und versuchen Sie es erneut.

### Es werden keine Fehler in der Datei angezeigt?

Wenn keine Fehler erfasst wurden, ist das Feld "errors" leer. Auch in diesem Fall enthält die Datei wertvolle Informationen im "events"-Bereich, der alle Seitenwechsel und Aktionen protokolliert. Bitte senden Sie die Datei trotzdem an den Support und beschreiben Sie das Problem so genau wie möglich in Ihrem Support-Ticket.

### Wo finde ich die heruntergeladene Datei?

Die Datei befindet sich in Ihrem Browser-Download-Ordner (üblicherweise "Downloads" in Ihrem Benutzerverzeichnis).
