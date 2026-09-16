// Own module so db.js and useUIEventsTracing.js can share them without an import cycle. Not
// duplicated on purpose: db.js's migration uses MAX_STORED_EVENTS as its bulk-clear threshold, so a
// second copy drifting out of sync would wipe the backlog of a device at a legitimate size.

// One sync cycle's send limit - the task runs every second, so its work must not scale with the
// backlog.
export const MAX_EVENTS_PER_SYNC = 200;

// Nothing is deleted unless a POST succeeds, so an unreachable backend needs this ceiling.
export const MAX_STORED_EVENTS = 5000;
