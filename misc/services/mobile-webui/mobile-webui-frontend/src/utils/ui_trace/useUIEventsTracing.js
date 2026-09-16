import { postEventsToBackend } from '../../api/ui_trace';
import { deleteEvents, getEventsBatch, trimOldestEvents } from './db';
import { useEventListener } from '../../hooks/useEventListener';
import { usePeriodicTask } from '../../hooks/usePeriodicTask';
import { MAX_EVENTS_PER_SYNC, MAX_STORED_EVENTS } from './constants';

const SYNC_INTERVAL_MILLIS = 1000;

// The periodic task and the `online` listener both call this and are not mutually exclusive
// (usePeriodicTask serialises only its own re-invocations), so without the mutex an `online` event
// during a POST reads the same undeleted batch and posts it twice. The backend does not dedupe -
// UI_Trace's ExternalId index is non-unique - so that lands as duplicate rows. Not exactly-once: a
// tab closing between POST and delete still resends, and this is per-tab while IndexedDB is not.
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
    await trimOldestEvents(MAX_STORED_EVENTS);

    if (!navigator.onLine) {
      console.log(`Skip syncing because not online (${reason})`);
      return;
    }

    const events = await getEventsBatch(MAX_EVENTS_PER_SYNC);
    if (!events?.length) return;

    await postEventsToBackend(events);

    // Only what was posted: anything saved during the POST is still unposted and must survive.
    await deleteEvents(events.map((event) => event.id));
  } catch (error) {
    console.error('Error syncing events:', error);
  }
};

export const useUIEventsTracing = () => {
  useEventListener('online', () => syncEventsToBackend('back online'));
  usePeriodicTask('sync UI events to backend', SYNC_INTERVAL_MILLIS, () => syncEventsToBackend('periodic task'));
};
