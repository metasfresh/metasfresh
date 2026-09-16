import axios from 'axios';

// axios 0.21 defaults to no timeout, and the sync holds one in-flight promise - a POST that never
// settles would wedge every later sync for the life of the tab. So the timeout is pinned here.

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
