import axios from 'axios';

// The trace POST must carry an explicit timeout.
//
// axios 0.21's default is 0 (none), and the sync task holds a single in-flight promise so the
// periodic task and the `online` listener cannot post the same batch twice. A POST that never
// settles would leave that promise pending forever, so every later sync from either trigger would
// join a dead promise and do nothing — ui-trace would go permanently silent on the device with no
// error and no log. The timeout is what makes that unreachable, so it is pinned here.

jest.mock('axios', () => ({ post: jest.fn() }));

const { postEventsToBackend, TRACE_POST_TIMEOUT_MILLIS } = require('../ui_trace');

beforeEach(() => {
  axios.post.mockImplementation(async () => ({ data: {} }));
});

describe('postEventsToBackend', () => {
  it('sends a finite timeout so a hung request rejects instead of hanging forever', async () => {
    await postEventsToBackend([{ id: 'evt-1' }]);

    expect(axios.post).toHaveBeenCalledTimes(1); // non-vacuity
    const [, , config] = axios.post.mock.calls[0];
    expect(config?.timeout).toBe(TRACE_POST_TIMEOUT_MILLIS);
    expect(Number.isFinite(TRACE_POST_TIMEOUT_MILLIS)).toBe(true);
    expect(TRACE_POST_TIMEOUT_MILLIS).toBeGreaterThan(0);
  });

  it('posts the events under an `events` key', async () => {
    const events = [{ id: 'evt-1' }, { id: 'evt-2' }];
    await postEventsToBackend(events);

    const [, body] = axios.post.mock.calls[0];
    expect(body).toEqual({ events });
  });
});
