import {
  initNumeralLocales,
  normalizeDecimalNumberString,
  isAllowedDecimalNumberInput,
} from '../../utils/locale';

// The separators a user session reports (see JSONUserSessionLocale) for a German and an English user
const DE = { decimal: ',', thousands: '.' };
const EN = { decimal: '.', thousands: ',' };

describe('normalizeDecimalNumberString', () => {
  describe('German user (decimal comma, dot grouping)', () => {
    it('reads the comma as the decimal separator', () => {
      expect(normalizeDecimalNumberString('3,57', DE)).toEqual('3.57');
    });

    it('keeps accepting a dot decimal, as typed before', () => {
      expect(normalizeDecimalNumberString('3.57', DE)).toEqual('3.57');
    });

    it('drops the dot grouping separators when a decimal comma is present', () => {
      expect(normalizeDecimalNumberString('1.234,56', DE)).toEqual('1234.56');
    });

    it('reads several dots without a comma as grouping separators', () => {
      expect(normalizeDecimalNumberString('1.234.567', DE)).toEqual('1234567');
    });

    it('keeps the minus sign', () => {
      expect(normalizeDecimalNumberString('-3,5', DE)).toEqual('-3.5');
    });
  });

  describe('English user (decimal dot, comma grouping)', () => {
    it('keeps a dot decimal', () => {
      expect(normalizeDecimalNumberString('3.57', EN)).toEqual('3.57');
    });

    it('drops the comma grouping separators', () => {
      expect(normalizeDecimalNumberString('1,234.56', EN)).toEqual('1234.56');
    });

    it('reads a lone comma as a grouping separator', () => {
      expect(normalizeDecimalNumberString('1,234', EN)).toEqual('1234');
    });
  });

  it('trims surrounding blanks', () => {
    expect(normalizeDecimalNumberString(' 3,57 ', DE)).toEqual('3.57');
  });

  it('leaves an empty or missing value untouched', () => {
    expect(normalizeDecimalNumberString('', DE)).toEqual('');
    expect(normalizeDecimalNumberString(null, DE)).toEqual(null);
    expect(normalizeDecimalNumberString(undefined, DE)).toEqual(undefined);
  });

  it('reads a text without any digit (e.g. a lone minus or comma) as empty', () => {
    expect(normalizeDecimalNumberString('-', DE)).toEqual('');
    expect(normalizeDecimalNumberString(',', DE)).toEqual('');
    expect(normalizeDecimalNumberString('.', EN)).toEqual('');
  });

  it('leaves a text with more than one decimal separator untouched, for the backend to reject it', () => {
    expect(normalizeDecimalNumberString('3,57,', DE)).toEqual('3,57,');
    expect(normalizeDecimalNumberString('1,2,3', DE)).toEqual('1,2,3');
    expect(normalizeDecimalNumberString('1,234.56', DE)).toEqual('1,234.56');
    expect(normalizeDecimalNumberString('1.234.567,8', DE)).toEqual('1234567.8'); // dots before the comma are grouping
  });

  it('uses the separators of the logged-in user session by default', () => {
    initNumeralLocales('de', {
      numberDecimalSeparator: ',',
      numberGroupingSeparator: '.',
    });

    expect(normalizeDecimalNumberString('3,57')).toEqual('3.57');
  });
});

describe('isAllowedDecimalNumberInput', () => {
  it('accepts digits, both separators and a leading minus', () => {
    expect(isAllowedDecimalNumberInput('-1.234,56', DE)).toBe(true);
    expect(isAllowedDecimalNumberInput('1,234.56', EN)).toBe(true);
    expect(isAllowedDecimalNumberInput('', DE)).toBe(true);
  });

  it('rejects letters and a minus that is not leading', () => {
    expect(isAllowedDecimalNumberInput('3,5a', DE)).toBe(false);
    expect(isAllowedDecimalNumberInput('1e5', DE)).toBe(false);
    expect(isAllowedDecimalNumberInput('3-5', DE)).toBe(false);
  });

  it("accepts the session's own grouping separator, e.g. the Swiss apostrophe", () => {
    expect(
      isAllowedDecimalNumberInput("1'234.56", { decimal: '.', thousands: "'" })
    ).toBe(true);
  });
});
