import React from 'react';
import { act, render } from '@testing-library/react';
import { Router } from 'react-router-dom';
import { createMemoryHistory } from 'history';
import { useDeviceBackButton } from '../../hooks/useDeviceBackButton';

function renderTrap({ history = createMemoryHistory() } = {}) {
  function TestComponent() {
    useDeviceBackButton();
    return null;
  }
  const utils = render(
    <Router history={history}>
      <TestComponent />
    </Router>
  );
  return { ...utils, history };
}

function firePopState() {
  act(() => {
    window.dispatchEvent(new PopStateEvent('popstate'));
  });
}

describe('useDeviceBackButton (browser Back fully neutralized)', () => {
  let pushStateSpy;

  beforeEach(() => {
    pushStateSpy = jest.spyOn(window.history, 'pushState');
  });

  afterEach(() => {
    pushStateSpy.mockRestore();
  });

  it('primes a sentinel history entry on mount at the current URL (so Back has something to absorb)', () => {
    renderTrap();
    expect(pushStateSpy).toHaveBeenCalledTimes(1);
    expect(pushStateSpy).toHaveBeenCalledWith(null, '', window.location.href);
  });

  it('on device/browser Back, re-primes the sentinel and does NOT navigate (pure no-op, every screen)', () => {
    const { history } = renderTrap();
    const before = history.location.pathname;
    pushStateSpy.mockClear();

    firePopState();

    expect(pushStateSpy).toHaveBeenCalledTimes(1); // re-primed
    expect(pushStateSpy).toHaveBeenCalledWith(null, '', window.location.href);
    expect(history.location.pathname).toBe(before); // did not navigate
  });

  it('never calls history.go(-1) (the browser-stack back it is neutralizing)', () => {
    const goSpy = jest.spyOn(window.history, 'go');
    renderTrap();
    firePopState();
    expect(goSpy).not.toHaveBeenCalled();
    goSpy.mockRestore();
  });

  it('re-primes the sentinel after a real forward navigation (PUSH / REPLACE)', () => {
    const { history } = renderTrap();
    pushStateSpy.mockClear();

    act(() => history.push('/screen/b'));
    expect(pushStateSpy).toHaveBeenCalledTimes(1);

    act(() => history.replace('/screen/c'));
    expect(pushStateSpy).toHaveBeenCalledTimes(2);
  });

  it('does NOT re-prime on a POP action (would stack up spurious sentinel entries)', () => {
    const { history } = renderTrap();
    act(() => history.push('/screen/b')); // PUSH → primes
    pushStateSpy.mockClear();

    act(() => history.goBack()); // POP → must NOT prime via the listener

    expect(pushStateSpy).not.toHaveBeenCalled();
  });

  it('removes the exact popstate listener it added, and detaches the history listener, on unmount', () => {
    const addEventListenerSpy = jest.spyOn(window, 'addEventListener');
    const removeEventListenerSpy = jest.spyOn(window, 'removeEventListener');
    const { unmount, history } = renderTrap();

    const addedHandler = addEventListenerSpy.mock.calls.find(([type]) => type === 'popstate')[1];

    unmount();
    expect(removeEventListenerSpy).toHaveBeenCalledWith('popstate', addedHandler);
    addEventListenerSpy.mockRestore();
    removeEventListenerSpy.mockRestore();

    pushStateSpy.mockClear();
    act(() => history.push('/after/unmount'));
    expect(pushStateSpy).not.toHaveBeenCalled();
  });
});
