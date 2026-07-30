import React from 'react';
import { render, screen, act } from '@testing-library/react';
import { useReentryKey } from '../../hooks/useReentryKey';

const Probe = () => <span data-testid="key">{useReentryKey()}</span>;
const currentKey = () => Number(screen.getByTestId('key').textContent);

// The operator switches away from the app: the window loses focus and the document goes hidden.
const leaveApp = () => {
  act(() => {
    window.dispatchEvent(new Event('blur'));
  });
};

describe('useReentryKey', () => {
  it('starts at 0', () => {
    render(<Probe />);
    expect(currentKey()).toBe(0);
  });

  it('increases when the window regains focus after the operator left', () => {
    render(<Probe />);
    const before = currentKey();
    leaveApp();
    act(() => {
      window.dispatchEvent(new Event('focus'));
    });
    expect(currentKey()).toBe(before + 1);
  });

  it('increases when the document becomes visible again after the operator left', () => {
    render(<Probe />);
    const before = currentKey();
    leaveApp();
    act(() => {
      document.dispatchEvent(new Event('visibilitychange'));
    });
    expect(currentKey()).toBe(before + 1);
  });

  // A real return to the app fires BOTH events. Counting each one would re-run the dependent
  // effect twice — two requests, and on failure two error toasts for a single failure.
  it('increases only ONCE when both events fire for the same return', () => {
    render(<Probe />);
    const before = currentKey();
    leaveApp();
    act(() => {
      document.dispatchEvent(new Event('visibilitychange'));
      window.dispatchEvent(new Event('focus'));
    });
    expect(currentKey()).toBe(before + 1);
  });

  it('increases again on a second, separate return', () => {
    render(<Probe />);
    const before = currentKey();
    leaveApp();
    act(() => {
      window.dispatchEvent(new Event('focus'));
    });
    leaveApp();
    act(() => {
      window.dispatchEvent(new Event('focus'));
    });
    expect(currentKey()).toBe(before + 2);
  });

  it('does not increase while the document is hidden', () => {
    render(<Probe />);
    const before = currentKey();
    const spy = jest.spyOn(document, 'visibilityState', 'get').mockReturnValue('hidden');
    act(() => {
      document.dispatchEvent(new Event('visibilitychange'));
    });
    expect(currentKey()).toBe(before);
    spy.mockRestore();
  });

  it('does not increase while the operator stays in the app', () => {
    render(<Probe />);
    const before = currentKey();
    act(() => {
      window.dispatchEvent(new Event('focus'));
      document.dispatchEvent(new Event('visibilitychange'));
    });
    expect(currentKey()).toBe(before);
  });
});
