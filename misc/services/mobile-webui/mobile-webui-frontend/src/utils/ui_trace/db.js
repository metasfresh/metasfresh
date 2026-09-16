import Dexie from 'dexie';
import { v4 as uuidv4 } from 'uuid';

import { MAX_STORED_EVENTS } from './constants';

const db = new Dexie('uiTraceEvents');
db.version(1).stores({
  props: 'key,value',
  events: 'id, event',
});

// Above this many records the v1->v2 upgrade clears instead of backfilling: .modify() rewrites every
// row in one versionchange transaction that cannot be split, and if it aborts nothing commits, so a
// huge backlog would retry the same doomed rewrite on every launch. Nothing of value is lost - v1 has
// no order, so the newest cannot be spared anyway, and trimOldestEvents cuts to this same cap next
// cycle. v2 adds the ts index; v1 had none usable (its only one was on `event`, a plain object, which
// IndexedDB cannot index at all).
db.version(2)
  .stores({
    props: 'key,value',
    events: 'id, ts',
  })
  .upgrade((tx) => {
    const events = tx.table('events');
    return events.count().then((count) => {
      if (count > MAX_STORED_EVENTS) return events.clear();
      // Without the backfill Dexie leaves ts-less v1 records out of the index entirely: never sent,
      // never trimmed.
      return events.toCollection().modify((record) => {
        record.ts = record.event?.timestamp ?? 0;
      });
    });
  });

// A stale tab holding a v1 connection blocks a new tab's v2 upgrade indefinitely, and this app does
// run as two instances on one handheld. Anything queued behind that blocked open() never settles,
// which wedges the sync mutex for the life of the tab. Dexie re-opens on the next operation.
db.on('versionchange', () => {
  db.close();
});

export const saveEvent = async (event) => {
  try {
    const record = { id: event.id, ts: event.timestamp ?? 0, event };
    await db.events.add(record);
  } catch (error) {
    console.error('Error saving event', { error, event });
  }
};

// Bounded so one sync cycle costs the same whatever the backlog: reading the whole table every
// second made a degraded backend quadratic.
export const getEventsBatch = async (limit) => {
  const records = await db.events.orderBy('ts').limit(limit).toArray();
  return records.map((record) => record.event);
};

// Only the posted ids - the previous clearEvents() emptied the table and took with it anything saved
// while the POST was in flight.
export const deleteEvents = async (ids) => {
  if (!ids?.length) return;
  await db.events.bulkDelete(ids);
};

// Nothing is deleted unless a POST succeeds, so an unreachable backend needs this ceiling. Oldest
// go first: a trace is read to see what the device is doing now.
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
