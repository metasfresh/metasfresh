import qhistory from 'qhistory';
import queryString from 'query-string';
import { createBrowserHistory, createMemoryHistory } from 'history';
import { logDiagEvent } from '../utils/diagnostics';

const isTest = process.env.NODE_ENV === 'test';
const history = isTest
  ? createMemoryHistory({ initialEntries: ['/'] })
  : createBrowserHistory();

const wrappedHistory = qhistory(
  history,
  queryString.stringify,
  queryString.parse
);

// Wrap push/replace to log programmatic route changes for diagnostics
// (direct window.location changes are not captured here)
const originalPush = wrappedHistory.push.bind(wrappedHistory);
const originalReplace = wrappedHistory.replace.bind(wrappedHistory);

wrappedHistory.push = (...args) => {
  try {
    logDiagEvent('navigation', { action: 'push', to: args[0] });
  } catch (e) {
    // Never let diagnostic logging break navigation
  }
  return originalPush(...args);
};

wrappedHistory.replace = (...args) => {
  try {
    logDiagEvent('navigation', { action: 'replace', to: args[0] });
  } catch (e) {
    // Never let diagnostic logging break navigation
  }
  return originalReplace(...args);
};

export default wrappedHistory;
