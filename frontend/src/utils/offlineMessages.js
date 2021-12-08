/**
 * These are used as fallback when the app is in offline mode. (no messages api fetched from the BE)
 */
module.exports = {
  offline: {
    de: {
      badGateway: {
        title: 'Instanz ist nicht verfügbar',
        description: 'Es treten Verbindungsprobleme auf.',
      },
      noStatus: {
        title: 'Verbindung unterbrochen',
        description:
          'Es treten Verbindungsprobleme auf. Bitte kontrolliere die Verbindung und lade die Seite neu.',
      },
    },
    en: {
      badGateway: {
        title: 'Instance is not available',
        description: 'There are some connection issues.',
      },
      noStatus: {
        title: 'Connection lost.',
        description:
          'There are some connection issues. Check connection and try to refresh the page',
      },
    },
  },
};
