/**
 * Storage policy for the UI-trace event store.
 *
 * These live in their own leaf module because both db.js and useUIEventsTracing.js need them and
 * useUIEventsTracing.js already imports db.js — so putting them in either would either duplicate the
 * literal or create an import cycle. Duplicating is the real hazard: the db.js migration uses
 * MAX_STORED_EVENTS as the threshold above which it bulk-clears instead of backfilling, so a copy
 * that fell out of sync with the runtime cap would wipe the backlog of a device sitting at a
 * perfectly legitimate steady-state size.
 */

// How many events one sync cycle may send. The sync task runs every second for the entire life of
// the tab, so its work must not scale with the backlog.
export const MAX_EVENTS_PER_SYNC = 200;

// Hard ceiling on the stored backlog. Nothing is deleted unless a POST succeeds, so without this a
// tab running for days against an unreachable backend grows the store without bound.
export const MAX_STORED_EVENTS = 5000;
