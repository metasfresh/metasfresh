import { countDecimalPlaces } from '../../utils/qtys';

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
});
