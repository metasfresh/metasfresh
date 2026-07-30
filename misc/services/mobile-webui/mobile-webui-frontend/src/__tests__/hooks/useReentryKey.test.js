import React from 'react';
import { render, screen, act } from '@testing-library/react';
import { useReentryKey } from '../../hooks/useReentryKey';

const Probe = () => <span data-testid="key">{useReentryKey()}</span>;
const currentKey = () => Number(screen.getByTestId('key').textContent);

describe('useReentryKey', () => {
  it('starts at 0', () => {
    render(<Probe />);
    expect(currentKey()).toBe(0);
  });

  it('increases when the window regains focus', () => {
    render(<Probe />);
    const before = currentKey();
    act(() => {
      window.dispatchEvent(new Event('focus'));
    });
    expect(currentKey()).toBe(before + 1);
  });

  it('increases when the document becomes visible again', () => {
    render(<Probe />);
    const before = currentKey();
    act(() => {
      document.dispatchEvent(new Event('visibilitychange'));
    });
    expect(currentKey()).toBe(before + 1);
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
});
