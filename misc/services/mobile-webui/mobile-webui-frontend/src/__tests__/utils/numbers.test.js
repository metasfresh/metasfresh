import { toNumberOrNaN } from '../../utils/numbers';

describe('numbers tests', () => {
  describe('toNumberOrNaN', () => {
    const expectations = [
      // [ input, expected ]
      [null, Number.NaN],
      [undefined, Number.NaN],
      ['', 0],
      ['.3', Number(0.3)],
      [',3', Number(0.3)],
      ['0.3', Number(0.3)],
      ['0,3', Number(0.3)],
      ['0.3', Number(0.3)],
      ['    0.3     ', Number(0.3)],
      ['    0,3     ', Number(0.3)],
      [',', Number.NaN],
      ['.', Number.NaN],
      ['0,300,200', Number.NaN],
      [Number('123.456'), Number('123.456')],
    ];

    for (const [input, expected] of expectations) {
      it('"' + input + '"(' + typeof input + ') -> ' + expected, () => expect(toNumberOrNaN(input)).toBe(expected));
    }
  });
});
