import React from 'react';
import { render, screen } from '@testing-library/react';
import HUButton from '../../components/huSelector/HUButton';

const hu = {
  id: '1000021',
  jsonHUType: 'LU',
  products: [],
  attributes2: { list: [{ code: 'Lot-Nummer', caption: 'Lot', value: 'L1' }] },
};

describe('HUButton statusCaption (opt-in)', () => {
  it('shows the status caption and is not tappable when disabled', () => {
    render(<HUButton handlingUnitInfo={hu} disabled={true} statusCaption="Versendet" onClick={() => {}} />);
    expect(screen.getByText('Versendet')).toBeTruthy();
    expect(screen.getByRole('button').disabled).toBe(true);
  });

  it('renders no status line without a caption (other callers unchanged)', () => {
    const { container } = render(<HUButton handlingUnitInfo={hu} onClick={() => {}} />);
    expect(container.querySelector('.hu-button-status')).toBeNull();
  });
});
