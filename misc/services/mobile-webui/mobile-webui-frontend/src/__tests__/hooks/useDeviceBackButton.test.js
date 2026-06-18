import React from 'react';
import { act, render } from '@testing-library/react';
import { Router } from 'react-router-dom';
import { createMemoryHistory } from 'history';
import { useDeviceBackButton } from '../../hooks/useDeviceBackButton';
import { useMobileNavigation } from '../../hooks/useMobileNavigation';

jest.mock('../../hooks/useMobileNavigation', () => ({ useMobileNavigation: jest.fn() }));

const goBack = jest.fn();

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

describe('useDeviceBackButton (browser Back mirrors the footer Back button)', () => {
  let pushStateSpy;

  beforeEach(() => {
    jest.clearAllMocks();
    useMobileNavigation.mockReturnValue({ goBack });
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

  it('on device/browser Back, re-primes the sentinel and invokes the in-app goBack (footer Back behavior)', () => {
    renderTrap();
    pushStateSpy.mockClear();

    firePopState();

    expect(goBack).toHaveBeenCalledTimes(1); // replays the in-app Back navigation
    expect(pushStateSpy).toHaveBeenCalledTimes(1); // sentinel re-primed so the stack never escapes the app
    expect(pushStateSpy).toHaveBeenCalledWith(null, '', window.location.href);
  });

  it('delegates Back to useMobileNavigation.goBack, never window.history.go(-1)', () => {
    const goSpy = jest.spyOn(window.history, 'go');
    renderTrap();
    firePopState();
    expect(goSpy).not.toHaveBeenCalled();
    expect(goBack).toHaveBeenCalledTimes(1);
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

  it('does NOT re-prime on a POP action via the history listener (the popstate handler owns that)', () => {
    const { history } = renderTrap();
    act(() => history.push('/screen/b')); // PUSH → primes
    pushStateSpy.mockClear();
    goBack.mockClear();

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
