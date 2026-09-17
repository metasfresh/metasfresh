import React from 'react';
import { render, act } from '@testing-library/react';
import { Router } from 'react-router';
import { createBrowserHistory } from 'history';

import { useLocationChange } from '../useLocationChange';

// The hook used to fire only via popstate/hashchange, which pushState/replaceState never emit - it
// worked only because its caller remounted on each navigation. These pin it without that accident.

const Probe = ({ onChange }) => {
  useLocationChange(onChange);
  return null;
};

beforeEach(() => {
  // jsdom carries both over between tests, and the hook compares one against the other.
  sessionStorage.clear();
  window.history.replaceState({}, '', '/');
});

describe('useLocationChange, mounted once for the app lifetime', () => {
  it('fires on history.push', () => {
    const history = createBrowserHistory();
    history.replace('/a');
    const onChange = jest.fn();

    render(
      <Router history={history}>
        <Probe onChange={onChange} />
      </Router>
    );
    const afterMount = onChange.mock.calls.length;
    expect(afterMount).toBeGreaterThan(0); // non-vacuity: the mount call itself fires

    act(() => history.push('/b'));
    expect(onChange.mock.calls.length).toBe(afterMount + 1);

    act(() => history.push('/c'));
    expect(onChange.mock.calls.length).toBe(afterMount + 2);
  });

  it('fires on history.replace, which is what in-app navigation actually uses', () => {
    const history = createBrowserHistory();
    history.replace('/a');
    const onChange = jest.fn();

    render(
      <Router history={history}>
        <Probe onChange={onChange} />
      </Router>
    );
    const afterMount = onChange.mock.calls.length;

    act(() => history.replace('/b'));
    expect(onChange.mock.calls.length).toBe(afterMount + 1);
  });

  it('does not fire again for a navigation to the same location', () => {
    const history = createBrowserHistory();
    history.replace('/a');
    const onChange = jest.fn();

    render(
      <Router history={history}>
        <Probe onChange={onChange} />
      </Router>
    );
    act(() => history.push('/b'));
    const afterFirst = onChange.mock.calls.length;

    act(() => history.push('/b')); // same location again
    expect(onChange.mock.calls.length).toBe(afterFirst);
  });

  // The hook subscribes to a location change by TWO routes: its own
  // window.addEventListener('popstate', ...) AND history.listen - and the history v4 library
  // itself listens to window popstate and redispatches to its listeners. So one real back button
  // reaches trackLocation TWICE, and must still yield exactly ONE callback.
  //
  // Driven by mutating the URL and raising popstate, NOT by history.goBack(): jsdom does not
  // update window.location synchronously for goBack, so that route would assert against jsdom's
  // history implementation rather than against this hook.
  it('fires exactly once for a browser back navigation, despite two subscriptions', () => {
    const history = createBrowserHistory();
    history.replace('/a');
    const onChange = jest.fn();

    render(
      <Router history={history}>
        <Probe onChange={onChange} />
      </Router>
    );
    act(() => history.push('/b'));
    const beforeBack = onChange.mock.calls.length;
    expect(beforeBack).toBeGreaterThan(0); // non-vacuity: push is observed

    act(() => {
      // What a real back button does: the URL becomes the previous entry, then popstate fires.
      window.history.replaceState({}, '', '/a');
      window.dispatchEvent(new PopStateEvent('popstate'));
    });

    expect(onChange.mock.calls.length).toBe(beforeBack + 1);
    expect(onChange).toHaveBeenLastCalledWith(
      expect.objectContaining({
        currentLocation: expect.stringContaining('/a'),
        prevLocation: expect.stringContaining('/b'),
      })
    );
  });

  it('stops firing once unmounted', () => {
    const history = createBrowserHistory();
    history.replace('/a');
    const onChange = jest.fn();

    const { unmount } = render(
      <Router history={history}>
        <Probe onChange={onChange} />
      </Router>
    );
    act(() => history.push('/b'));
    const beforeUnmount = onChange.mock.calls.length;
    expect(beforeUnmount).toBeGreaterThan(0); // non-vacuity

    unmount();
    act(() => history.push('/c'));
    expect(onChange.mock.calls.length).toBe(beforeUnmount);
  });
});

// The app mounts two consumers: ScreenToaster dismisses stale toasts, useUITraceLocationChange
// emits the locationChanged event and sets the application id. Both must see every navigation.
describe('useLocationChange, with more than one consumer mounted', () => {
  it('delivers every navigation to both consumers', () => {
    const history = createBrowserHistory();
    history.replace('/a');
    const onFirst = jest.fn();
    const onSecond = jest.fn();

    render(
      <Router history={history}>
        <>
          <Probe onChange={onFirst} />
          <Probe onChange={onSecond} />
        </>
      </Router>
    );
    const firstAfterMount = onFirst.mock.calls.length;
    expect(firstAfterMount).toBeGreaterThan(0); // non-vacuity: the mount call itself fires
    expect(onSecond.mock.calls.length).toBe(firstAfterMount);

    act(() => history.push('/b'));
    act(() => history.push('/c'));

    expect(onFirst.mock.calls.length).toBe(firstAfterMount + 2);
    expect(onSecond.mock.calls.length).toBe(firstAfterMount + 2);
  });

  // Each consumer still de-duplicates for itself: one back navigation reaches trackLocation twice
  // per instance (own popstate listener + history.listen), and must still yield one callback.
  it('fires exactly once per consumer for a browser back navigation', () => {
    const history = createBrowserHistory();
    history.replace('/a');
    const onFirst = jest.fn();
    const onSecond = jest.fn();

    render(
      <Router history={history}>
        <>
          <Probe onChange={onFirst} />
          <Probe onChange={onSecond} />
        </>
      </Router>
    );
    act(() => history.push('/b'));
    const firstBeforeBack = onFirst.mock.calls.length;
    const secondBeforeBack = onSecond.mock.calls.length;
    expect(firstBeforeBack).toBeGreaterThan(0); // non-vacuity: push is observed

    act(() => {
      window.history.replaceState({}, '', '/a');
      window.dispatchEvent(new PopStateEvent('popstate'));
    });

    expect(onFirst.mock.calls.length).toBe(firstBeforeBack + 1);
    expect(onSecond.mock.calls.length).toBe(secondBeforeBack + 1);
  });

  // sessionStorage is what carries the last location across a page reload, so a consumer mounting
  // into an already-tracked location must not re-announce it.
  it('does not re-announce the current location to a consumer mounted later', () => {
    const history = createBrowserHistory();
    history.replace('/a');
    const onFirst = jest.fn();
    const onLate = jest.fn();

    const { rerender } = render(
      <Router history={history}>
        <Probe onChange={onFirst} />
      </Router>
    );
    act(() => history.push('/b'));
    expect(onFirst.mock.calls.length).toBeGreaterThan(0); // non-vacuity

    rerender(
      <Router history={history}>
        <>
          <Probe onChange={onFirst} />
          <Probe onChange={onLate} />
        </>
      </Router>
    );
    expect(onLate).not.toHaveBeenCalled();

    act(() => history.push('/c'));
    expect(onLate.mock.calls.length).toBe(1);
  });
});
