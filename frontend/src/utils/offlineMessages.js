/**
 * These are used as fallback when the app is in offline mode. (no messages api fetched from the BE)
 */
module.exports = {
  offline: {
    de_DE: {
      OFFLINE_MESSAGE_LINE1: 'Verbindung unterbrochen',
      OFFLINE_MESSAGE_LINE2:
        'Es treten Verbindungsprobleme auf. Bitte kontrolliere die Verbindung und lade die Seite neu.',
      BAD_GATEWAY_LINE1: 'Instanz ist nicht verfügbar',
      BAD_GATEWAY_LINE2: 'Es treten Verbindungsprobleme auf.',
    },
    en_US: {
      OFFLINE_MESSAGE_LINE1: 'Connection lost.',
      OFFLINE_MESSAGE_LINE2:
        'There are some connection issues. Check connection and try to refresh the page',
      BAD_GATEWAY_LINE1: 'Instance is not available',
      BAD_GATEWAY_LINE2: 'There are some connection issues.',
    },
  },
};
