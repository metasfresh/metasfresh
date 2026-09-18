import { postEventsToBackend } from '../../api/ui_trace';
import { deleteEvents, getEventsBatch, trimOldestEvents } from './db';
import { useEventListener } from '../../hooks/useEventListener';
import { usePeriodicTask } from '../../hooks/usePeriodicTask';
import { MAX_EVENTS_PER_SYNC, MAX_STORED_EVENTS } from './constants';

const SYNC_INTERVAL_MILLIS = 1000;

// The POST has its own timeout; these are for the IndexedDB calls. A db.open() blocked by a stale
// tab on an older schema never settles, and since the mutex below makes one stuck await poison every
// later sync, the store operations need the same ceiling the POST has. Generous on purpose: this is
// a deadlock breaker, not a latency budget.
export const DB_OP_TIMEOUT_MILLIS = 10000;

const withTimeout = (promise, label) =>
  new Promise((resolve, reject) => {
    const timer = setTimeout(() => reject(new Error(`ui_trace: ${label} timed out`)), DB_OP_TIMEOUT_MILLIS);
    promise.then(resolve, reject).finally(() => clearTimeout(timer));
  });

// The periodic task and the `online` listener both call this and are not mutually exclusive
// (usePeriodicTask serialises only its own re-invocations), so without the mutex an `online` event
// during a POST reads the same undeleted batch and posts it twice. The backend does not dedupe -
// UI_Trace's ExternalId index is non-unique - so that lands as duplicate rows. Not exactly-once, and
// the gaps are not rare: a tab closing between POST and delete resends, and this mutex is per-tab
// while IndexedDB is shared - so the documented PWA-plus-browser-tab setup duplicates systematically.
// Closing that needs a unique constraint on ExternalId server-side.
let inFlightSync = null;

const syncEventsToBackend = (reason = 'programmatic') => {
  // Join the in-flight sync rather than queueing: it is already draining the same store.
  if (inFlightSync) return inFlightSync;
  inFlightSync = doSyncEventsToBackend(reason).finally(() => {
    inFlightSync = null;
  });
  return inFlightSync;
};

const doSyncEventsToBackend = async (reason) => {
  try {
    // Before the offline check: out of coverage is exactly when the backlog grows unchecked.
    await withTimeout(trimOldestEvents(MAX_STORED_EVENTS), 'trimOldestEvents');

    if (!navigator.onLine) {
      console.log(`Skip syncing because not online (${reason})`);
      return;
    }

    const events = await withTimeout(getEventsBatch(MAX_EVENTS_PER_SYNC), 'getEventsBatch');
    if (!events?.length) return;

    await postEventsToBackend(events);

    // Only what was posted: anything saved during the POST is still unposted and must survive.
    await withTimeout(deleteEvents(events.map((event) => event.id)), 'deleteEvents');
  } catch (error) {
    console.error('Error syncing events:', error);
  }
};

export const useUIEventsTracing = () => {
  useEventListener('online', () => syncEventsToBackend('back online'));
  usePeriodicTask('sync UI events to backend', SYNC_INTERVAL_MILLIS, () => syncEventsToBackend('periodic task'));
};
