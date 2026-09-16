import React from 'react';
import { render } from '@testing-library/react';
import { act } from 'react-dom/test-utils';

// UI-trace sync must stay bounded and must not destroy events.
//
// Two defects are covered here. Each test below was written against the previous implementation
// and observed to FAIL there before the fix landed:
//
//   1. clearEvents() dropped the WHOLE table after a successful POST, so any event saved while
//      that POST was in flight was destroyed — silent trace loss, worst exactly when the device
//      is busy scanning and events arrive fastest.
//   2. A failing POST cleared nothing, while the periodic task kept reading the ENTIRE store
//      every second. On a tab that lives for days with a degraded backend, each cycle re-read and
//      re-serialised a strictly larger backlog, forever.
//
// NOTE: react-scripts sets jest `resetMocks: true`, which strips the implementation passed to
// `jest.fn(impl)` before each test. Every mock implementation must therefore be assigned in
// beforeEach via mockImplementation — otherwise the mocks return undefined and these tests pass
// VACUOUSLY (no POST ever fires). Each test below carries an explicit non-vacuity assertion.

jest.mock('../../../api/ui_trace', () => ({ postEventsToBackend: jest.fn() }));
jest.mock('../db', () => ({
  getEventsBatch: jest.fn(),
  deleteEvents: jest.fn(),
  trimOldestEvents: jest.fn(),
  saveEvent: jest.fn(),
  getOrCreateDeviceId: jest.fn(),
}));

const { postEventsToBackend } = require('../../../api/ui_trace');
const { getEventsBatch, deleteEvents, trimOldestEvents, getOrCreateDeviceId } = require('../db');
const { useUIEventsTracing } = require('../useUIEventsTracing');
const { MAX_EVENTS_PER_SYNC, MAX_STORED_EVENTS } = require('../constants');

const SYNC_INTERVAL_MILLIS = 1000;

let store;
let seq;

const pushEvent = (n = 1) => {
  for (let i = 0; i < n; i++) {
    seq++;
    store.push({
      id: `evt-${seq}`,
      eventName: 'barcodeScanned',
      timestamp: 1700000000000 + seq,
      device: { deviceId: 'test-device', tabId: 'tab-1', userAgent: 'test-agent' },
    });
  }
};

function mountTracing() {
  function TestComponent() {
    useUIEventsTracing();
    return null;
  }
  return render(<TestComponent />);
}

// Flush microtasks only: setTimeout is frozen by fake timers, so awaiting one would deadlock.
const settle = async () => {
  await act(async () => {
    for (let i = 0; i < 20; i++) await Promise.resolve();
  });
};

const runOneCycle = async () => {
  await act(async () => {
    jest.advanceTimersByTime(SYNC_INTERVAL_MILLIS);
  });
  await settle();
};

const payloadSizes = () => postEventsToBackend.mock.calls.map(([events]) => events.length);

beforeEach(() => {
  jest.useFakeTimers();
  store = [];
  seq = 0;
  // Implementations MUST be set here — see the resetMocks note above.
  // Ordered by ts, like the real getEventsBatch (db.js does `.orderBy('ts')`). Slicing the array in
  // insertion order would coincide with that only because pushEvent happens to append increasing
  // timestamps, making the fake agree with the real one by accident rather than by construction.
  // NOTE: db.js itself is mocked away here, so its ordering and its v1->v2 migration are NOT covered
  // by this suite - that needs fake-indexeddb against a real Dexie instance.
  const byTs = () => [...store].sort((a, b) => a.timestamp - b.timestamp);
  getEventsBatch.mockImplementation(async (limit) => byTs().slice(0, limit));
  deleteEvents.mockImplementation(async (ids) => {
    const drop = new Set(ids);
    store = store.filter((event) => !drop.has(event.id));
  });
  trimOldestEvents.mockImplementation(async (max) => {
    if (store.length <= max) return 0;
    const excess = store.length - max;
    const oldest = new Set(
      byTs()
        .slice(0, excess)
        .map((event) => event.id)
    );
    store = store.filter((event) => !oldest.has(event.id));
    return excess;
  });
  getOrCreateDeviceId.mockImplementation(async () => 'test-device');
});

afterEach(() => {
  jest.useRealTimers();
});

describe('UI-trace sync — an event saved during an in-flight POST', () => {
  it('survives the POST and is delivered on a later cycle', async () => {
    let releasePost;
    postEventsToBackend.mockImplementation(
      () =>
        new Promise((resolve) => {
          releasePost = resolve;
        })
    );

    pushEvent(2);
    const { unmount } = mountTracing();
    await settle();

    expect(postEventsToBackend).toHaveBeenCalledTimes(1); // non-vacuity
    expect(postEventsToBackend.mock.calls[0][0]).toHaveLength(2);

    pushEvent(1); // arrives WHILE the POST is in flight
    const inFlightEventId = store[store.length - 1].id;

    releasePost({});
    await settle();

    // It must still be in the store: only the two posted ids may be deleted.
    expect(store.some((event) => event.id === inFlightEventId)).toBe(true);

    // And it must actually get delivered on the next cycle.
    postEventsToBackend.mockImplementation(async () => ({}));
    await runOneCycle();
    unmount();

    const everPosted = postEventsToBackend.mock.calls.flatMap(([events]) => events.map((e) => e.id));
    expect(everPosted).toContain(inFlightEventId);
    expect(store).toHaveLength(0);
  });
});

describe('UI-trace sync — a failing POST /trace', () => {
  it('keeps the stored backlog bounded instead of growing without limit', async () => {
    postEventsToBackend.mockImplementation(async () => {
      throw new Error('Network Error');
    });

    const { unmount } = mountTracing();
    await settle();

    // Far more events than the ceiling allows, while the backend stays down.
    const burst = Math.ceil(MAX_STORED_EVENTS / 4);
    for (let cycle = 0; cycle < 8; cycle++) {
      pushEvent(burst);
      await runOneCycle();
    }
    unmount();

    expect(postEventsToBackend).toHaveBeenCalled(); // non-vacuity
    expect(store.length).toBeLessThanOrEqual(MAX_STORED_EVENTS);
  });

  it('posts a bounded batch each cycle rather than the whole backlog', async () => {
    postEventsToBackend.mockImplementation(async () => {
      throw new Error('Network Error');
    });

    const { unmount } = mountTracing();
    await settle();

    for (let cycle = 0; cycle < 6; cycle++) {
      pushEvent(MAX_EVENTS_PER_SYNC * 2); // backlog outruns what one cycle may send
      await runOneCycle();
    }
    unmount();

    const sizes = payloadSizes();
    expect(sizes.length).toBeGreaterThan(1); // non-vacuity
    expect(Math.max(...sizes)).toBeLessThanOrEqual(MAX_EVENTS_PER_SYNC);
  });
});

describe('UI-trace sync — healthy backend (control)', () => {
  it('drains the store every cycle', async () => {
    postEventsToBackend.mockImplementation(async () => ({}));

    pushEvent(3);
    const { unmount } = mountTracing();
    await settle();

    for (let cycle = 0; cycle < 5; cycle++) {
      pushEvent(3);
      await runOneCycle();
    }
    unmount();

    const sizes = payloadSizes();
    expect(sizes.length).toBeGreaterThan(1); // non-vacuity
    expect(Math.max(...sizes)).toBeLessThanOrEqual(6);
    expect(store).toHaveLength(0);
  });
});

describe('UI-trace sync — offline', () => {
  it('still enforces the ceiling, so the early return cannot leak', async () => {
    const onLineSpy = jest.spyOn(window.navigator, 'onLine', 'get').mockReturnValue(false);
    try {
      const { unmount } = mountTracing();
      await settle();

      for (let cycle = 0; cycle < 6; cycle++) {
        pushEvent(MAX_STORED_EVENTS);
        await runOneCycle();
      }
      unmount();

      expect(postEventsToBackend).not.toHaveBeenCalled();
      expect(trimOldestEvents).toHaveBeenCalled(); // non-vacuity
      expect(store.length).toBeLessThanOrEqual(MAX_STORED_EVENTS);
    } finally {
      onLineSpy.mockRestore();
    }
  });
});

describe('UI-trace sync — an `online` event arriving during an in-flight POST', () => {
  it('joins the in-flight sync instead of re-posting the same batch', async () => {
    let releasePost;
    postEventsToBackend.mockImplementation(
      () =>
        new Promise((resolve) => {
          releasePost = resolve;
        })
    );

    pushEvent(2);
    const { unmount } = mountTracing();
    await settle();

    expect(postEventsToBackend).toHaveBeenCalledTimes(1); // non-vacuity: a POST is genuinely in flight

    // Connectivity returns while that POST is still unresolved. Without the in-flight mutex this
    // starts a second sync which reads the SAME undeleted batch (nothing is deleted until the first
    // POST resolves) and posts it again — duplicate rows, since the backend does not dedupe.
    await act(async () => {
      window.dispatchEvent(new Event('online'));
    });
    await settle();

    expect(postEventsToBackend).toHaveBeenCalledTimes(1);

    releasePost({});
    await settle();
    unmount();

    const postedIds = postEventsToBackend.mock.calls.flatMap(([events]) => events.map((e) => e.id));
    expect(postedIds.length).toBeGreaterThan(0); // non-vacuity
    expect(new Set(postedIds).size).toBe(postedIds.length); // no event delivered twice
  });
});
