import { normalizeDecimalNumberString, isValidDecimalNumberString } from '../../utils/decimalInput';

// Shared vectors with the desktop frontend (frontend/src/__tests__/utils/decimalNumberInput.test.js).
// This parser is a COPY of the desktop one (separate build), these vectors keep it honest.

describe('normalizeDecimalNumberString', () => {
  it('reads the comma as the decimal separator', () => {
    expect(normalizeDecimalNumberString('3,57')).toEqual('3.57');
    expect(normalizeDecimalNumberString('1000,50')).toEqual('1000.50');
    expect(normalizeDecimalNumberString('0,5')).toEqual('0.5');
  });

  it('reads the dot as the decimal separator (no thousands grouping on input)', () => {
    expect(normalizeDecimalNumberString('3.57')).toEqual('3.57');
    expect(normalizeDecimalNumberString('37.5')).toEqual('37.5');
    expect(normalizeDecimalNumberString('56.525')).toEqual('56.525');
    expect(normalizeDecimalNumberString('1.000')).toEqual('1.000');
  });

  it('keeps the minus sign', () => {
    expect(normalizeDecimalNumberString('-3,5')).toEqual('-3.5');
    expect(normalizeDecimalNumberString('-3.5')).toEqual('-3.5');
  });

  it('a value holding both separators: the last one is the decimal', () => {
    expect(normalizeDecimalNumberString('1,234.56')).toEqual('1234.56');
    expect(normalizeDecimalNumberString('1.234,56')).toEqual('1234.56');
    expect(normalizeDecimalNumberString('1.234.567,8')).toEqual('1234567.8');
  });

  it('reads repeated separators with the last one as the decimal point', () => {
    expect(normalizeDecimalNumberString('1.234.567')).toEqual('1234.567');
    expect(normalizeDecimalNumberString('1,2,3')).toEqual('12.3');
  });

  it('drops a trailing separator', () => {
    expect(normalizeDecimalNumberString('1.5.')).toEqual('1.5');
    expect(normalizeDecimalNumberString('12,50,')).toEqual('12.50');
    expect(normalizeDecimalNumberString('3.')).toEqual('3');
    expect(normalizeDecimalNumberString('3,')).toEqual('3');
    expect(normalizeDecimalNumberString('1.2.3.')).toEqual('12.3');
  });

  it('drops blanks, no-break spaces and narrow no-break spaces', () => {
    expect(normalizeDecimalNumberString('1 234,56')).toEqual('1234.56');
    expect(normalizeDecimalNumberString('1\u00A0234,56')).toEqual('1234.56');
    expect(normalizeDecimalNumberString('1\u202F234,56')).toEqual('1234.56');
  });

  it('trims surrounding blanks', () => {
    expect(normalizeDecimalNumberString(' 3,57 ')).toEqual('3.57');
  });

  it('leaves an empty or missing value untouched', () => {
    expect(normalizeDecimalNumberString('')).toEqual('');
    expect(normalizeDecimalNumberString(null)).toEqual(null);
    expect(normalizeDecimalNumberString(undefined)).toEqual(undefined);
  });

  it('reads a text without any digit as empty', () => {
    expect(normalizeDecimalNumberString('-')).toEqual('');
    expect(normalizeDecimalNumberString(',')).toEqual('');
    expect(normalizeDecimalNumberString('.')).toEqual('');
  });

  it('returns a text that is no number as typed', () => {
    expect(normalizeDecimalNumberString('3,5a')).toEqual('3,5a');
    expect(normalizeDecimalNumberString('1e5')).toEqual('1e5');
    expect(normalizeDecimalNumberString('Infinity')).toEqual('Infinity');
    expect(normalizeDecimalNumberString('abc')).toEqual('abc');
    expect(normalizeDecimalNumberString('٣,٥')).toEqual('٣,٥');
  });
});

describe('isValidDecimalNumberString', () => {
  it('accepts a number typed with either the comma or the dot', () => {
    [
      '3,57',
      '3.57',
      '1.000',
      '1.234,56',
      '1,234.56',
      '-5',
      '',
      '-',
      ' 1 234,5 ',
      '-1.234,56',
      ' -3,5',
      '1 234,56',
      '1\u00A0234,56',
    ].forEach((text) => expect(isValidDecimalNumberString(text)).toBe(true));
    expect(isValidDecimalNumberString(undefined)).toBe(true);
  });

  it('refuses a text that is no number', () => {
    ['3,5a', '1e5', '1,2,3x', '12x9', 'Infinity', 'abc', '٣,٥', '3-5', "1'234.56"].forEach((text) =>
      expect(isValidDecimalNumberString(text)).toBe(false)
    );
  });

  it('refuses a digit string too long to be a number (overflow)', () => {
    expect(isValidDecimalNumberString('1' + '0'.repeat(400))).toBe(false);
    expect(isValidDecimalNumberString('1' + '0'.repeat(300))).toBe(true);
  });
});
