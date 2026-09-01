import React from 'react';
import '@testing-library/jest-dom';
import { render, screen } from '@testing-library/react';

import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';

// ui_trace uses IndexedDB which is absent in jsdom.
jest.mock('../../../utils/ui_trace', () => ({
  putContext: jest.fn(),
  trace: jest.fn(),
  traceFunction: (fn) => fn,
}));

describe('ButtonWithIndicator — multiline caption', () => {
  it('applies the multiline class when the caption contains a line break', () => {
    render(<ButtonWithIndicator caption={'123 | Acme\nProduct A\nProduct B'} onClick={jest.fn()} />);
    const captionSpan = screen.getByText('Product A', { exact: false });
    expect(captionSpan.className).toMatch(/caption-multiline/);
  });

  it('does not apply the multiline class when the caption is single-line', () => {
    render(<ButtonWithIndicator caption={'123 | Acme | 2026-09-01'} onClick={jest.fn()} />);
    const captionSpan = screen.getByText('123 | Acme | 2026-09-01');
    expect(captionSpan.className).not.toMatch(/caption-multiline/);
  });
});
