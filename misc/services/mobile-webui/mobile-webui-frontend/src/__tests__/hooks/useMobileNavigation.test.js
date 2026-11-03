import React from 'react';
import { render } from '@testing-library/react';
import { useHistory, useLocation } from 'react-router-dom';
import { useMobileNavigation } from '../../hooks/useMobileNavigation';
import { useBackLocationFromHeaders } from '../../reducers/headers';
import { useMobileLocation } from '../../hooks/useMobileLocation';

jest.mock('react-router-dom', () => ({
  useHistory: jest.fn(),
  useLocation: jest.fn(),
}));
jest.mock('../../reducers/headers', () => ({ useBackLocationFromHeaders: jest.fn() }));
jest.mock('../../hooks/useMobileLocation', () => ({ useMobileLocation: jest.fn() }));

const replace = jest.fn();

function renderNav({ backLocation = null } = {}) {
  useHistory.mockReturnValue({ replace, go: jest.fn() });
  useLocation.mockReturnValue({ state: {} });
  useBackLocationFromHeaders.mockReturnValue(backLocation);
  useMobileLocation.mockReturnValue({});
  const api = { current: null };
  function TestComponent() {
    api.current = useMobileNavigation();
    return null;
  }
  render(<TestComponent />);
  return api;
}

describe('useMobileNavigation back navigation (no browser-stack dependency)', () => {
  beforeEach(() => jest.clearAllMocks());

  it('goBack() with no declared backLocation goes Home (history.replace("/")), never history.go(-1)', () => {
    const { current } = renderNav({ backLocation: null });
    current.goBack();
    expect(replace).toHaveBeenCalledTimes(1);
    expect(replace).toHaveBeenCalledWith('/');
  });

  it('goBack() with a declared backLocation navigates there', () => {
    const { current } = renderNav({ backLocation: '/some/back' });
    current.goBack();
    expect(replace).toHaveBeenCalledWith('/some/back');
  });

  it('go(-1) delegates to goBack() — Home when no backLocation', () => {
    const { current } = renderNav({ backLocation: null });
    current.go(-1);
    expect(replace).toHaveBeenCalledWith('/');
  });

  it('go(string) navigates to that location (unchanged)', () => {
    const { current } = renderNav();
    current.go('/explicit/path');
    expect(replace).toHaveBeenCalledWith('/explicit/path');
  });

  it('go(delta) with a positive delta (forward) goes Home, never history.go(delta)', () => {
    const historyGo = jest.fn();
    useHistory.mockReturnValue({ replace, go: historyGo });
    useLocation.mockReturnValue({ state: {} });
    useBackLocationFromHeaders.mockReturnValue(null);
    useMobileLocation.mockReturnValue({});
    const api = { current: null };
    function C() {
      api.current = useMobileNavigation();
      return null;
    }
    render(<C />);

    api.current.go(1);

    expect(historyGo).not.toHaveBeenCalled();
    expect(replace).toHaveBeenCalledWith('/');
  });

  it('go(delta) with |delta|>1 goes Home, never history.go(delta)', () => {
    const historyGo = jest.fn();
    useHistory.mockReturnValue({ replace, go: historyGo });
    useLocation.mockReturnValue({ state: {} });
    useBackLocationFromHeaders.mockReturnValue(null);
    useMobileLocation.mockReturnValue({});
    const api = { current: null };
    function C() {
      api.current = useMobileNavigation();
      return null;
    }
    render(<C />);

    api.current.go(-3);

    expect(historyGo).not.toHaveBeenCalled();
    expect(replace).toHaveBeenCalledWith('/');
  });
});
