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
