import Dexie from 'dexie';
import { v4 as uuidv4 } from 'uuid';

const db = new Dexie('uiTraceEvents');
db.version(1).stores({
  props: 'key,value',
  events: 'id, event',
});

// Above this many stored records the v1->v2 upgrade bulk-clears instead of rewriting every row.
// .modify() rewrites each record inside ONE atomic versionchange transaction that cannot be split.
// On precisely the devices this change targets - those whose backlog grew unbounded against a failing
// backend - that transaction is huge: it blocks the tab's DB connection while it runs, and if it
// aborts (the browser kills a long versionchange, quota is exceeded, the tab is closed) then NOTHING
// commits, the database stays at v1, and every relaunch retries the same doomed rewrite forever with
// ui-trace silently dead on that device.
// Clearing is cheap at any size and costs little here: v1 has no usable order (see below), so the
// newest records cannot be identified to be spared anyway, and trimOldestEvents cuts the store to
// MAX_STORED_EVENTS on the very next cycle regardless. Keep this equal to that cap.
const UPGRADE_BULK_CLEAR_THRESHOLD = 5000;

// v2 indexes the event timestamp. v1's primary key is a uuid and its only secondary index was on
// `event` - a plain object, which IndexedDB cannot index at all (only number/string/Date/binary and
// arrays of those), so that index held no entries and dropping it costs nothing. The store therefore
// had no usable order: neither "send the oldest first" nor "drop the oldest when full" could be
// expressed, and both are needed to keep it bounded (see getEventsBatch / trimOldestEvents below).
db.version(2)
  .stores({
    props: 'key,value',
    events: 'id, ts',
  })
  .upgrade((tx) => {
    const events = tx.table('events');
    return events.count().then((count) => {
      if (count > UPGRADE_BULK_CLEAR_THRESHOLD) return events.clear();
      // Backfill ts for records written by v1. Dexie omits records whose indexed value is undefined
      // from that index, so without this backfill the pre-upgrade backlog would be invisible to
      // every ordered query here - and therefore never sent and never trimmed.
      return events.toCollection().modify((record) => {
        record.ts = record.event?.timestamp ?? 0;
      });
    });
  });

export const saveEvent = async (event) => {
  try {
    const record = { id: event.id, ts: event.timestamp ?? 0, event };
    await db.events.add(record);
  } catch (error) {
    console.error('Error saving event', { error, event });
  }
};

/**
 * Oldest-first page of stored events, at most `limit` of them.
 *
 * The sync task runs once a second for the whole life of the tab, so the cost of one cycle must not
 * depend on how large the backlog has grown. Reading the entire table (the previous getAllEvents)
 * made a degraded backend quadratic: every second it re-read and re-serialised everything it had so
 * far failed to post.
 */
export const getEventsBatch = async (limit) => {
  const records = await db.events.orderBy('ts').limit(limit).toArray();
  return records.map((record) => record.event);
};

/**
 * Deletes exactly the given ids.
 *
 * Replaces clearEvents(), which emptied the whole table after a successful POST and so destroyed any
 * event saved while that POST was in flight — silent trace loss, worst precisely when the device is
 * scanning fastest and events arrive thickest.
 */
export const deleteEvents = async (ids) => {
  if (!ids?.length) return;
  await db.events.bulkDelete(ids);
};

/**
 * Enforces a ceiling on the stored backlog by dropping the OLDEST events, and returns how many were
 * dropped.
 *
 * Without a ceiling, a tab that lives for days with an unreachable backend grows this store without
 * bound: nothing is deleted unless a POST succeeds. Dropping the oldest (rather than refusing new
 * events) is deliberate — a UI trace is read to diagnose what the device is doing now, so the newest
 * events are the ones worth keeping.
 */
export const trimOldestEvents = async (max) => {
  const count = await db.events.count();
  if (count <= max) return 0;

  const excess = count - max;
  const oldestIds = await db.events.orderBy('ts').limit(excess).primaryKeys();
  await db.events.bulkDelete(oldestIds);
  return oldestIds.length;
};

export const getOrCreateDeviceId = async () => {
  const deviceId = await db.props.get('device_id');
  if (!deviceId || !deviceId.value) {
    const newDeviceId = uuidv4();
    await db.props.put({ key: 'device_id', value: newDeviceId });
    return newDeviceId;
  } else {
    return deviceId.value;
  }
};
