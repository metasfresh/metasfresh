import React from 'react';
import '@testing-library/jest-dom';
import { render, screen } from '@testing-library/react';

import ReturnLine from '../../../apps/pos/containers/return_panel/ReturnLine';
import { formatAmountToHumanReadableStr } from '../../../utils/money';

describe('ReturnLine — precisions', () => {
  const renderLine = () =>
    render(
      <ReturnLine
        productName="Cheese"
        qty={2}
        uom="kg"
        price={1.2345}
        currencySymbol="€"
        pricePrecision={4}
        currencyPrecision={2}
        onClick={jest.fn()}
      />
    );

  it('formats the per-unit price with the terminal price precision', () => {
    renderLine();
    const expectedPriceStr = formatAmountToHumanReadableStr({ amount: 1.2345, currency: '€', precision: 4 }) + '/kg';
    expect(screen.getByTestId('pos-return-line-description')).toHaveTextContent(expectedPriceStr);
  });

  it('formats the line amount with the currency precision', () => {
    renderLine();
    const expectedAmountStr = formatAmountToHumanReadableStr({ amount: 2 * 1.2345, currency: '€', precision: 2 });
    expect(screen.getByTestId('pos-return-line-amount')).toHaveTextContent(expectedAmountStr);
  });
});
