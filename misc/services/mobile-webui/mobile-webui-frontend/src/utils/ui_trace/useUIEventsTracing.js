import { postEventsToBackend } from '../../api/ui_trace';
import { deleteEvents, getEventsBatch, trimOldestEvents } from './db';
import { useEventListener } from '../../hooks/useEventListener';
import { usePeriodicTask } from '../../hooks/usePeriodicTask';

const SYNC_INTERVAL_MILLIS = 1000;

// How many events one sync cycle may send. This task runs every second for the entire life of the
// tab, so the work it does must not scale with the backlog: previously it read and serialised the
// WHOLE store each time, which turned a degraded backend into steadily growing per-second work.
export const MAX_EVENTS_PER_SYNC = 200;

// Hard ceiling on the stored backlog. Nothing is deleted unless a POST succeeds, so without this a
// tab running for days against an unreachable backend grows the store without bound.
export const MAX_STORED_EVENTS = 5000;

// The two triggers at the bottom of this file - the periodic task AND the `online` listener - are
// NOT mutually exclusive: usePeriodicTask serialises only its OWN re-invocations. Without this mutex,
// an `online` event arriving while a POST is in flight starts a second sync that reaches
// getEventsBatch() before the first reaches deleteEvents(), so it reads the SAME still-undeleted
// batch and POSTs it again. The backend does not dedupe (UI_Trace's ExternalId index is non-unique
// and the primary key is server-generated), so that lands as duplicate rows, which then double-count
// in the api_request_audit joins used to reconstruct a device session.
// This does NOT give exactly-once delivery: a tab closing between a successful POST and deleteEvents
// still resends, and the mutex is per-tab while IndexedDB is shared across tabs of the same origin.
// Closing those requires a server-side unique constraint on ExternalId.
let inFlightSync = null;

const syncEventsToBackend = (reason = 'programmatic') => {
  // Coalesce rather than queue: a sync already in flight is draining the same store, so the right
  // response to a second trigger is to join it, not to start a competing pass over the same events.
  if (inFlightSync) return inFlightSync;
  inFlightSync = doSyncEventsToBackend(reason).finally(() => {
    inFlightSync = null;
  });
  return inFlightSync;
};

const doSyncEventsToBackend = async (reason) => {
  try {
    // Enforced FIRST, every cycle, so the offline early-return below is not a path that skips it —
    // a device out of Wi-Fi coverage is exactly the case where the backlog grows unchecked.
    await trimOldestEvents(MAX_STORED_EVENTS);

    if (!navigator.onLine) {
      console.log(`Skip syncing because not online (${reason})`);
      return;
    }

    const events = await getEventsBatch(MAX_EVENTS_PER_SYNC);
    if (!events?.length) return;

    await postEventsToBackend(events);

    // Delete exactly what was posted — never the whole table. Any event saved while the POST above
    // was in flight is still in the store, unposted, and must survive to be sent on a later cycle.
    await deleteEvents(events.map((event) => event.id));
  } catch (error) {
    console.error('Error syncing events:', error);
  }
};

export const useUIEventsTracing = () => {
  useEventListener('online', () => syncEventsToBackend('back online'));
  usePeriodicTask('sync UI events to backend', SYNC_INTERVAL_MILLIS, () => syncEventsToBackend('periodic task'));
};
