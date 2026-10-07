import {
  initNumeralLocales,
  normalizeDecimalNumberString,
  isAllowedDecimalNumberInput,
  formatDecimalNumberForEditing,
  isValidDecimalNumberString,
} from '../../utils/locale';

// The separators a user session reports (see JSONUserSessionLocale) for a German and an English user. Only
// formatDecimalNumberForEditing (which shows a stored value for editing) depends on them; reading a typed number
// treats both the comma and the dot as the decimal separator, whatever the session.
const DE = { decimal: ',', thousands: '.' };
const EN = { decimal: '.', thousands: ',' };

afterEach(() => {
  initNumeralLocales('en', {
    numberDecimalSeparator: '.',
    numberGroupingSeparator: ',',
  });
});

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
    // accepted as the decimal number one, NOT read as the grouped 1000
    expect(normalizeDecimalNumberString('1.000')).toEqual('1.000');
  });

  it('keeps the minus sign', () => {
    expect(normalizeDecimalNumberString('-3,5')).toEqual('-3.5');
    expect(normalizeDecimalNumberString('-3.5')).toEqual('-3.5');
  });

  describe('a value holding both separators: the last one is the decimal, the earlier one(s) are stripped', () => {
    it('reads the dot as the decimal when the comma comes first', () => {
      expect(normalizeDecimalNumberString('1,234.56')).toEqual('1234.56');
    });

    it('reads the comma as the decimal when the dot comes first', () => {
      expect(normalizeDecimalNumberString('1.234,56')).toEqual('1234.56');
      expect(normalizeDecimalNumberString('1.234.567,8')).toEqual('1234567.8');
    });
  });

  it('reads repeated separators with the last one as the decimal point', () => {
    expect(normalizeDecimalNumberString('1.234.567')).toEqual('1234.567');
    expect(normalizeDecimalNumberString('1,2,3')).toEqual('12.3');
  });

  it('drops a trailing separator (it has no digit after it, so it is not the decimal point)', () => {
    expect(normalizeDecimalNumberString('1.5.')).toEqual('1.5');
    expect(normalizeDecimalNumberString('12,50,')).toEqual('12.50');
    expect(normalizeDecimalNumberString('3.')).toEqual('3');
    expect(normalizeDecimalNumberString('3,')).toEqual('3');
    expect(normalizeDecimalNumberString('1.2.3.')).toEqual('12.3');
  });

  it('drops blanks, no-break spaces and narrow no-break spaces (e.g. a pasted grouped number)', () => {
    expect(normalizeDecimalNumberString('1 234,56')).toEqual('1234.56');
    expect(normalizeDecimalNumberString('1 234,56')).toEqual('1234.56');
    expect(normalizeDecimalNumberString('1 234,56')).toEqual('1234.56');
  });

  it('trims surrounding blanks', () => {
    expect(normalizeDecimalNumberString(' 3,57 ')).toEqual('3.57');
  });

  it('leaves an empty or missing value untouched', () => {
    expect(normalizeDecimalNumberString('')).toEqual('');
    expect(normalizeDecimalNumberString(null)).toEqual(null);
    expect(normalizeDecimalNumberString(undefined)).toEqual(undefined);
  });

  it('reads a text without any digit (e.g. a lone minus, comma or dot) as empty', () => {
    expect(normalizeDecimalNumberString('-')).toEqual('');
    expect(normalizeDecimalNumberString(',')).toEqual('');
    expect(normalizeDecimalNumberString('.')).toEqual('');
  });

  it('returns a text that is no number as typed, for the backend to reject it', () => {
    expect(normalizeDecimalNumberString('3,5a')).toEqual('3,5a');
    expect(normalizeDecimalNumberString('1e5')).toEqual('1e5');
    // no ASCII digit, but a forbidden character: no number, not "nothing typed yet"
    expect(normalizeDecimalNumberString('Infinity')).toEqual('Infinity');
    expect(normalizeDecimalNumberString('abc')).toEqual('abc');
    expect(normalizeDecimalNumberString('٣,٥')).toEqual('٣,٥');
  });

  it('reads the separators the same way whatever the session language', () => {
    initNumeralLocales('de', {
      numberDecimalSeparator: ',',
      numberGroupingSeparator: '.',
    });
    expect(normalizeDecimalNumberString('3,57')).toEqual('3.57');
    expect(normalizeDecimalNumberString('3.57')).toEqual('3.57');

    initNumeralLocales('en', {
      numberDecimalSeparator: '.',
      numberGroupingSeparator: ',',
    });
    expect(normalizeDecimalNumberString('3,57')).toEqual('3.57');
    expect(normalizeDecimalNumberString('3.57')).toEqual('3.57');
  });
});

describe('isAllowedDecimalNumberInput', () => {
  it('accepts digits, both separators and a leading minus', () => {
    expect(isAllowedDecimalNumberInput('-1.234,56')).toBe(true);
    expect(isAllowedDecimalNumberInput('1,234.56')).toBe(true);
    expect(isAllowedDecimalNumberInput('')).toBe(true);
  });

  it('rejects letters, an apostrophe, and a minus that is not leading', () => {
    expect(isAllowedDecimalNumberInput('3,5a')).toBe(false);
    expect(isAllowedDecimalNumberInput('1e5')).toBe(false);
    expect(isAllowedDecimalNumberInput('3-5')).toBe(false);
    expect(isAllowedDecimalNumberInput("1'234.56")).toBe(false);
  });

  it('accepts blanks and no-break spaces, which are dropped when the value is read', () => {
    expect(isAllowedDecimalNumberInput('1 234,56')).toBe(true);
    expect(isAllowedDecimalNumberInput('1 234,56')).toBe(true);
    expect(isAllowedDecimalNumberInput(' -3,5')).toBe(true);
  });
});

describe('formatDecimalNumberForEditing', () => {
  it('shows a stored number with the decimal comma of a German session, without grouping', () => {
    expect(formatDecimalNumberForEditing('3.57', DE)).toEqual('3,57');
    expect(formatDecimalNumberForEditing('1234.5', DE)).toEqual('1234,5');
    expect(formatDecimalNumberForEditing('-0.5', DE)).toEqual('-0,5');
    expect(formatDecimalNumberForEditing('1000', DE)).toEqual('1000');
    expect(formatDecimalNumberForEditing(3.57, DE)).toEqual('3,57');
  });

  it('shows a stored number unchanged in an English session', () => {
    expect(formatDecimalNumberForEditing('1234.5', EN)).toEqual('1234.5');
  });

  it('leaves empty values and text that is no stored number untouched', () => {
    expect(formatDecimalNumberForEditing('', DE)).toEqual('');
    expect(formatDecimalNumberForEditing(null, DE)).toEqual(null);
    expect(formatDecimalNumberForEditing('3,57', DE)).toEqual('3,57');
  });

  it('round-trips: what edit mode shows is read back as the stored value', () => {
    ['3.57', '1234.5', '-0.5', '1000', '0', '1.000'].forEach((stored) => {
      expect(
        normalizeDecimalNumberString(formatDecimalNumberForEditing(stored, DE))
      ).toEqual(stored);
      expect(
        normalizeDecimalNumberString(formatDecimalNumberForEditing(stored, EN))
      ).toEqual(stored);
    });
  });
});

describe('isValidDecimalNumberString', () => {
  it('accepts a number typed with either the comma or the dot as the decimal separator', () => {
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
    ].forEach((text) => expect(isValidDecimalNumberString(text)).toBe(true));
    expect(isValidDecimalNumberString(undefined)).toBe(true);
  });

  it('refuses a text that is no number, including a letters-only or non-ASCII-digit text', () => {
    ['3,5a', '1e5', '1,2,3x', '12x9', 'Infinity', 'abc', '٣,٥'].forEach((text) =>
      expect(isValidDecimalNumberString(text)).toBe(false)
    );
  });
});
