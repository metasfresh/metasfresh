/**
 * Diagnostic error logging for white screen issues.
 *
 * Captures JavaScript errors, unhandled promise rejections,
 * navigation events, and process actions.
 * Customer can type hilfe() in browser console to download a diagnostic report.
 *
 * Usage:
 *   1. Import this file in index.jsx: import './utils/diagnostics';
 *   2. When white screen occurs, customer opens console (F12) and types: hilfe()
 *   3. A JSON file is downloaded containing error logs, navigation events, and browser info
 */

const MAX_ENTRIES = 100;
const errors = [];

const MAX_EVENTS = 200;
const events = [];

/**
 * Log a diagnostic event (navigation, processAction, redirect, popstate).
 * Other modules import this to record events for the hilfe() report.
 * Wrapped in try-catch so diagnostic logging never breaks the application.
 */
export function logDiagEvent(type, data) {
  try {
    events.push({
      timestamp: new Date().toISOString(),
      type,
      url: window.location.href,
      ...data,
    });
    if (events.length > MAX_EVENTS) {
      events.shift();
    }
  } catch (e) {
    // Diagnostic logging must never break the application
  }
}

// Capture JavaScript errors
window.onerror = (message, source, lineno, colno, error) => {
  errors.push({
    timestamp: new Date().toISOString(),
    type: 'error',
    message,
    source,
    lineno,
    colno,
    stack: error?.stack,
    url: window.location.href,
  });
  if (errors.length > MAX_ENTRIES) {
    errors.shift();
  }
};

// Capture unhandled promise rejections
window.addEventListener('unhandledrejection', (event) => {
  errors.push({
    timestamp: new Date().toISOString(),
    type: 'unhandledrejection',
    reason: String(event.reason),
    stack: event.reason?.stack,
    url: window.location.href,
  });
  if (errors.length > MAX_ENTRIES) {
    errors.shift();
  }
});

/**
 * Download diagnostic report as JSON file.
 * Customer types hilfe() in browser console.
 */
window.hilfe = () => {
  try {
    const report = {
      exportTime: new Date().toISOString(),
      // eslint-disable-next-line no-undef
      buildHash: typeof COMMIT_HASH !== 'undefined' ? COMMIT_HASH : 'unknown',
      browserInfo: {
        userAgent: navigator.userAgent,
        language: navigator.language,
        url: window.location.href,
        screen: `${window.screen.width}x${window.screen.height}`,
        window: `${window.innerWidth}x${window.innerHeight}`,
      },
      errors: errors,
      events: events,
    };

    let jsonString;
    try {
      jsonString = JSON.stringify(report, null, 2);
    } catch (e) {
      // Fallback: handle circular references gracefully
      const seen = new WeakSet();
      jsonString = JSON.stringify(
        report,
        (key, value) => {
          if (typeof value === 'object' && value !== null) {
            if (seen.has(value)) return '[Circular]';
            seen.add(value);
          }
          return value;
        },
        2
      );
    }

    const blob = new Blob([jsonString], {
      type: 'application/json',
    });
    const link = document.createElement('a');
    link.href = URL.createObjectURL(blob);
    link.download = `metasfresh-diagnose-${Date.now()}.json`;
    link.click();
    URL.revokeObjectURL(link.href);

    // eslint-disable-next-line no-console
    console.info(
      '%cDiagnose-Datei wurde heruntergeladen. Bitte senden Sie diese Datei an den Support.',
      'color: green; font-weight: bold;'
    );
  } catch (e) {
    // eslint-disable-next-line no-console
    console.error(
      '%cFehler beim Erstellen der Diagnose-Datei. Bitte machen Sie einen Screenshot dieser Fehlermeldung und senden Sie ihn an den Support.',
      'color: red; font-weight: bold;'
    );
    // eslint-disable-next-line no-console
    console.error(e);
  }
};

// Show hint in console on page load
// eslint-disable-next-line no-console
console.info(
  '%cBei Problemen: Tippen Sie hilfe() ein und drücken Sie Enter, um eine Diagnose-Datei herunterzuladen.',
  'color: gray;'
);
