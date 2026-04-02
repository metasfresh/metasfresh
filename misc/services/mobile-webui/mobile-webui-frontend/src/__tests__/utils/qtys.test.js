import { countDecimalPlaces, formatQtyToHumanReadable } from '../../utils/qtys';

describe('qtys tests', () => {
  describe('countDecimalPlaces', () => {
    const expectations = [
      // [ input, expected ]

      // null/undefined/NaN/Infinity
      [null, 0],
      [undefined, 0],
      [NaN, 0],
      [Infinity, 0],
      [-Infinity, 0],

      // integers
      [0, 0],
      [1, 0],
      [10, 0],
      [100, 0],

      // basic decimals
      [0.1, 1],
      [0.01, 2],
      [0.001, 3],
      [3.84, 2],
      [19.125, 3],

      // small quantities (the actual use case)
      [0.00384, 5],
      [0.01913, 5],
      [0.019125, 6],

      // JS strips trailing zeros from numbers, so these are equivalent
      [1.1, 1], // String(1.10) = "1.1"
      [0.019125, 6], // String(0.01912500) = "0.019125"

      // string inputs with trailing zeros (from backend JSON)
      ['0.01912500', 6],
      ['1.0', 0],
      ['1.00', 0],
      ['10.50', 1],
      ['10.050', 2],
      ['100', 0],
      ['0.100', 1],
    ];

    for (const [input, expected] of expectations) {
      it(JSON.stringify(input) + ' -> ' + expected, () => expect(countDecimalPlaces(input)).toBe(expected));
    }
  });

  describe('formatQtyToHumanReadable - precision derived from qty decimal places', () => {
    const expectations = [
      // [ { qty, uom, precision }, expectedQtyStr, expectedUom ]

      // Small kg quantities auto-converted to grams — precision derived from qty decimals minus shift
      [{ qty: 0.019125, uom: 'kg' }, '19.125', 'g'], // 6 decimals - 3 shift = 3
      [{ qty: 0.01913, uom: 'kg' }, '19.13', 'g'], // 5 decimals - 3 shift = 2
      [{ qty: 0.00384, uom: 'kg' }, '3.84', 'g'], // 5 decimals - 3 shift = 2
      [{ qty: 0.00765, uom: 'kg' }, '7.65', 'g'], // 5 decimals - 3 shift = 2
      [{ qty: 0.1, uom: 'kg' }, '100', 'g'], // 1 decimal - 3 shift = 0 (but >=1 so no conversion) → actually 0.1 < 1 → converts to 100 g, precision max(1-3,0)=0

      // Very small kg → mg conversion
      [{ qty: 0.0000012, uom: 'kg' }, '1.2', 'mg'], // 7 decimals, 2 shifts (kg→g→mg) = 7-6=1

      // Normal kg quantities (no conversion, qty >= 1)
      [{ qty: 100, uom: 'kg' }, '100', 'kg'], // 0 decimals, no shift
      [{ qty: 1.5, uom: 'kg' }, '1.5', 'kg'], // 1 decimal, no shift
      [{ qty: 2.25, uom: 'kg' }, '2.25', 'kg'], // 2 decimals, no shift

      // Non-kg UOM (no conversion possible)
      [{ qty: 0.5, uom: 'PCE' }, '0.5', 'PCE'], // 1 decimal, no shift, default precision max(1,4)=4 → but only 1 significant decimal

      // Explicit precision overrides derived precision
      [{ qty: 0.019125, uom: 'kg', precision: 999 }, '19.125', 'g'], // explicit 999 → shows all
      [{ qty: 0.019125, uom: 'kg', precision: 1 }, '19.1', 'g'], // explicit 1 → truncates

      // Rounding behavior (toFixed uses standard rounding: half-up for most cases)
      [{ qty: 0.019155, uom: 'kg', precision: 1 }, '19.2', 'g'], // 19.155 rounded to 1 decimal → 19.2 (rounds up)
      [{ qty: 0.019145, uom: 'kg', precision: 1 }, '19.1', 'g'], // 19.145 rounded to 1 decimal → 19.1 (rounds down)
      [{ qty: 0.019195, uom: 'kg', precision: 1 }, '19.2', 'g'], // 19.195 rounded to 1 decimal → 19.2 (rounds up)
      [{ qty: 1.555, uom: 'kg', precision: 2 }, '1.55', 'kg'], // 1.555 → 1.55 (JS floating-point: 1.555 is stored as 1.554999... so toFixed rounds down)
      [{ qty: 1.554, uom: 'kg', precision: 2 }, '1.55', 'kg'], // 1.554 rounded to 2 decimals → 1.55 (rounds down)
      [{ qty: 0.009999, uom: 'kg', precision: 2 }, '10', 'g'], // 9.999 g rounded to 2 decimals → 10.00 → "10" (rounds up across boundary)
    ];

    for (const [input, expectedQtyStr, expectedUom] of expectations) {
      const label = `qty=${input.qty} uom=${input.uom}${
        input.precision != null ? ' precision=' + input.precision : ''
      } -> "${expectedQtyStr} ${expectedUom}"`;
      it(label, () => {
        const result = formatQtyToHumanReadable(input);
        expect(result.qtyEffectiveStr).toBe(expectedQtyStr);
        expect(result.uomEffective).toBe(expectedUom);
      });
    }
  });
});
