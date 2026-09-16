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

const syncEventsToBackend = async (reason = 'programmatic') => {
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
