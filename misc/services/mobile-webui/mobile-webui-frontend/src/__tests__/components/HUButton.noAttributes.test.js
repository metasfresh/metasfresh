import React from 'react';
import { render, screen } from '@testing-library/react';
import HUButton from '../../components/huSelector/HUButton';

const huWithAttributes2 = (attributes2) => ({
  id: '1000021',
  jsonHUType: 'LU',
  products: [],
  attributes2,
});

describe('HUButton renders an HU without displayable attributes', () => {
  it.each([
    ['empty attributes list', { list: [] }],
    ['missing attributes list', {}],
    ['missing attributes2', undefined],
  ])('%s: renders the button without crashing', (_label, attributes2) => {
    const { container } = render(<HUButton handlingUnitInfo={huWithAttributes2(attributes2)} onClick={() => {}} />);
    expect(screen.getByText('1000021')).toBeTruthy();
    expect(container.querySelectorAll('.hu-row-info').length).toBe(0);
  });
});
