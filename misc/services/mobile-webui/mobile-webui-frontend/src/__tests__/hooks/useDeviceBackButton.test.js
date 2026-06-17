import React from 'react';
import { act, render } from '@testing-library/react';
import { Router } from 'react-router-dom';
import { createMemoryHistory } from 'history';
import { useDeviceBackButton } from '../../hooks/useDeviceBackButton';
import { useMobileNavigation } from '../../hooks/useMobileNavigation';
import { useBackLocationFromHeaders } from '../../reducers/headers';

jest.mock('../../hooks/useMobileNavigation', () => ({
  useMobileNavigation: jest.fn(),
}));
jest.mock('../../reducers/headers', () => ({
  useBackLocationFromHeaders: jest.fn(),
}));

const goTo = jest.fn();

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

describe('useDeviceBackButton', () => {
  let pushStateSpy;

  beforeEach(() => {
    jest.clearAllMocks();
    useMobileNavigation.mockReturnValue({ goTo });
    pushStateSpy = jest.spyOn(window.history, 'pushState');
  });

  afterEach(() => {
    pushStateSpy.mockRestore();
  });

  it('primes a sentinel history entry on mount at the current URL (so Back has something to absorb)', () => {
    useBackLocationFromHeaders.mockReturnValue('/some/back');
    renderTrap();
    expect(pushStateSpy).toHaveBeenCalledTimes(1);
    expect(pushStateSpy).toHaveBeenCalledWith(null, '', window.location.href);
  });

  it('on device Back, navigates to the screen-declared back location (NOT the browser stack)', () => {
    useBackLocationFromHeaders.mockReturnValue('/some/back');
    renderTrap();
    pushStateSpy.mockClear();

    firePopState();

    expect(goTo).toHaveBeenCalledTimes(1);
    expect(goTo).toHaveBeenCalledWith('/some/back');
  });

  it('on device Back at the top of the stack (no declared back), re-primes the sentinel only — a deliberate no-op', () => {
    useBackLocationFromHeaders.mockReturnValue(null);
    renderTrap();
    pushStateSpy.mockClear();

    firePopState();

    expect(pushStateSpy).toHaveBeenCalledTimes(1);
    expect(pushStateSpy).toHaveBeenCalledWith(null, '', window.location.href);
    expect(goTo).not.toHaveBeenCalled();
  });

  it('never falls back to history.go(-1) (the browser-stack back it is neutralizing)', () => {
    const goSpy = jest.spyOn(window.history, 'go');
    useBackLocationFromHeaders.mockReturnValue(null);
    renderTrap();

    firePopState();

    expect(goSpy).not.toHaveBeenCalled();
    goSpy.mockRestore();
  });

  it('re-primes the sentinel after a real forward navigation (PUSH / REPLACE)', () => {
    useBackLocationFromHeaders.mockReturnValue('/some/back');
    const { history } = renderTrap();
    pushStateSpy.mockClear();

    act(() => history.push('/screen/b'));
    expect(pushStateSpy).toHaveBeenCalledTimes(1);

    act(() => history.replace('/screen/c'));
    expect(pushStateSpy).toHaveBeenCalledTimes(2);
  });

  it('does NOT re-prime on a POP action (would stack up spurious sentinel entries)', () => {
    useBackLocationFromHeaders.mockReturnValue('/some/back');
    const { history } = renderTrap();
    act(() => history.push('/screen/b')); // PUSH → primes
    pushStateSpy.mockClear();

    act(() => history.goBack()); // POP → must NOT prime via the listener

    expect(pushStateSpy).not.toHaveBeenCalled();
  });

  it('removes the popstate listener and the history listener on unmount', () => {
    const removeEventListenerSpy = jest.spyOn(window, 'removeEventListener');
    useBackLocationFromHeaders.mockReturnValue(null);
    const { unmount, history } = renderTrap();

    unmount();
    expect(removeEventListenerSpy).toHaveBeenCalledWith('popstate', expect.any(Function));
    removeEventListenerSpy.mockRestore();

    // history listener is detached too: a post-unmount navigation must not prime.
    pushStateSpy.mockClear();
    act(() => history.push('/after/unmount'));
    expect(pushStateSpy).not.toHaveBeenCalled();
  });
});
