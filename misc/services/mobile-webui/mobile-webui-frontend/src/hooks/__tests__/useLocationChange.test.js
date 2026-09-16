import React from 'react';
import { render, act } from '@testing-library/react';
import { Router } from 'react-router';
import { createBrowserHistory } from 'history';

import { useLocationChange } from '../useLocationChange';

// useLocationChange must fire on EVERY navigation, including history.push/replace.
//
// It used to listen only for the native 'popstate'/'hashchange' events, which fire for browser
// back/forward but NOT for pushState/replaceState — and useMobileNavigation routes essentially all
// in-app navigation through history.replace(). What actually drove the callback was the fact that
// its host component remounted on each navigation, re-running the mount-time trackLocation().
//
// That made the hook silently dependent on being mounted per screen. ScreenToaster relies on it to
// dismiss toasts on navigation (toast.dismiss()), so once ScreenToaster was mounted once at the app
// root instead, a toast raised on one screen would persist onto the next.
//
// These tests pin the behaviour the hook is supposed to provide, independent of remounting.

const Probe = ({ onChange }) => {
  useLocationChange(onChange);
  return null;
};

beforeEach(() => {
  // Both the stored marker and the URL carry over between tests in jsdom; reset both so each test
  // starts from a known location (the hook compares window.location.href against the marker).
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
