import React from 'react';
import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
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

function renderTrap() {
  function TestComponent() {
    useDeviceBackButton();
    return null;
  }
  return render(
    <MemoryRouter>
      <TestComponent />
    </MemoryRouter>
  );
}

function firePopState() {
  window.dispatchEvent(new PopStateEvent('popstate'));
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

  it('primes a sentinel history entry on mount (so Back has something to absorb)', () => {
    useBackLocationFromHeaders.mockReturnValue('/some/back');
    renderTrap();
    expect(pushStateSpy).toHaveBeenCalledTimes(1);
  });

  it('on device Back, re-primes the sentinel and navigates to the screen-declared back location', () => {
    useBackLocationFromHeaders.mockReturnValue('/some/back');
    renderTrap();
    pushStateSpy.mockClear();

    firePopState();

    // sentinel re-primed (stack/URL stays put) ...
    expect(pushStateSpy).toHaveBeenCalledTimes(1);
    // ... and we navigate to the declared back, NOT the browser stack
    expect(goTo).toHaveBeenCalledTimes(1);
    expect(goTo).toHaveBeenCalledWith('/some/back');
  });

  it('on device Back at the top of the stack (no declared back), re-primes only — a deliberate no-op', () => {
    useBackLocationFromHeaders.mockReturnValue(null);
    renderTrap();
    pushStateSpy.mockClear();

    firePopState();

    expect(pushStateSpy).toHaveBeenCalledTimes(1);
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
});
