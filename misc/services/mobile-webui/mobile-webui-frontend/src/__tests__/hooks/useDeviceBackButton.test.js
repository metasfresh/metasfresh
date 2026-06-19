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

describe('useDeviceBackButton (device/browser Back is a pure no-op)', () => {
  let pushStateSpy;

  beforeEach(() => {
    jest.clearAllMocks();
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

  it('on device/browser Back, ONLY re-primes the sentinel — it does not navigate (pure no-op)', () => {
    renderTrap();
    pushStateSpy.mockClear();

    firePopState();

    // sentinel re-primed so the stack never escapes the app, and nothing else happens
    expect(pushStateSpy).toHaveBeenCalledTimes(1);
    expect(pushStateSpy).toHaveBeenCalledWith(null, '', window.location.href);
  });

  it('never calls window.history.go / back (no in-app navigation is replayed)', () => {
    const goSpy = jest.spyOn(window.history, 'go');
    const backSpy = jest.spyOn(window.history, 'back');
    renderTrap();

    firePopState();

    expect(goSpy).not.toHaveBeenCalled();
    expect(backSpy).not.toHaveBeenCalled();
    goSpy.mockRestore();
    backSpy.mockRestore();
  });

  it('keeps a sentinel on top across repeated Back presses (so a 2nd/3rd Back can never pop a real entry)', () => {
    renderTrap();
    pushStateSpy.mockClear();

    firePopState();
    firePopState();
    firePopState();

    // one re-prime per Back press → there is always a fresh sentinel on top
    expect(pushStateSpy).toHaveBeenCalledTimes(3);
  });

  it('re-primes the sentinel after a real forward navigation (PUSH / REPLACE)', () => {
    const { history } = renderTrap();
    pushStateSpy.mockClear();

    act(() => history.push('/screen/b'));
    expect(pushStateSpy).toHaveBeenCalledTimes(1);

    act(() => history.replace('/screen/c'));
    expect(pushStateSpy).toHaveBeenCalledTimes(2);
  });

  it('does NOT re-prime on a POP action via the history listener (the popstate handler owns that)', () => {
    const { history } = renderTrap();
    act(() => history.push('/screen/b')); // PUSH → primes
    pushStateSpy.mockClear();

    act(() => history.goBack()); // memory-history POP → must NOT prime via the listener

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
