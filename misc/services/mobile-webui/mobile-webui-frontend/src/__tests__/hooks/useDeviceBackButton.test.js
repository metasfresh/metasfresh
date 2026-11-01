import React from 'react';
import { act, render } from '@testing-library/react';
import { Router } from 'react-router-dom';
import { createMemoryHistory } from 'history';
import { useDeviceBackButton, SENTINEL_BUFFER } from '../../hooks/useDeviceBackButton';

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

describe('useDeviceBackButton (device/browser Back is a pure no-op, robust to mashing)', () => {
  let pushStateSpy;

  beforeEach(() => {
    jest.clearAllMocks();
    pushStateSpy = jest.spyOn(window.history, 'pushState');
  });

  afterEach(() => {
    pushStateSpy.mockRestore();
  });

  it('primes a BUFFER of sentinel history entries on mount (so a burst of Back presses has plenty to absorb)', () => {
    renderTrap();
    expect(pushStateSpy).toHaveBeenCalledTimes(SENTINEL_BUFFER);
    // jsdom's window.history.state is always null, so the cloned state is null here. In a real browser
    // it is the connected-react-router location state (so a popped sentinel keeps the same location
    // key) — that state-clone behaviour is covered by the e2e spec, not assertable in jsdom.
    expect(pushStateSpy).toHaveBeenLastCalledWith(null, '', window.location.href);
  });

  it('on device/browser Back, refills one sentinel and does NOT navigate (pure no-op)', () => {
    renderTrap();
    pushStateSpy.mockClear();

    firePopState();

    // exactly one sentinel re-primed, nothing else
    expect(pushStateSpy).toHaveBeenCalledTimes(1);
    expect(pushStateSpy).toHaveBeenCalledWith(null, '', window.location.href);
  });

  it('never calls window.history.go / back (no in-app navigation is replayed)', () => {
    const goSpy = jest.spyOn(window.history, 'go');
    const backSpy = jest.spyOn(window.history, 'back');
    renderTrap();

    firePopState();
    firePopState();

    expect(goSpy).not.toHaveBeenCalled();
    expect(backSpy).not.toHaveBeenCalled();
    goSpy.mockRestore();
    backSpy.mockRestore();
  });

  it('refills one sentinel per Back press, so the buffer never depletes under repeated/rapid mashing', () => {
    renderTrap();
    pushStateSpy.mockClear();

    for (let i = 0; i < 10; i++) firePopState();

    expect(pushStateSpy).toHaveBeenCalledTimes(10);
  });

  it('rebuilds the full sentinel buffer after a real forward navigation (PUSH / REPLACE)', () => {
    const { history } = renderTrap();
    pushStateSpy.mockClear();

    act(() => history.push('/screen/b'));
    expect(pushStateSpy).toHaveBeenCalledTimes(SENTINEL_BUFFER);

    pushStateSpy.mockClear();
    act(() => history.replace('/screen/c'));
    expect(pushStateSpy).toHaveBeenCalledTimes(SENTINEL_BUFFER);
  });

  it('does NOT re-prime on a POP action via the history listener (the popstate handler owns that)', () => {
    const { history } = renderTrap();
    act(() => history.push('/screen/b')); // PUSH → primes buffer
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
