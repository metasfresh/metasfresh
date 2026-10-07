import {
  initNumeralLocales,
  normalizeDecimalNumberString,
  isAllowedDecimalNumberInput,
  formatDecimalNumberForEditing,
  isValidDecimalNumberString,
} from '../../utils/locale';

// The separators a user session reports (see JSONUserSessionLocale) for a German and an English user
const DE = { decimal: ',', thousands: '.' };
const EN = { decimal: '.', thousands: ',' };
const FR = { decimal: ',', thousands: '\u202F' }; // narrow no-break space grouping

afterEach(() => {
  initNumeralLocales('en', {
    numberDecimalSeparator: '.',
    numberGroupingSeparator: ',',
  });
});

describe('normalizeDecimalNumberString', () => {
  describe('German user (decimal comma, dot grouping)', () => {
    it('reads the comma as the decimal separator', () => {
      expect(normalizeDecimalNumberString('3,57', DE)).toEqual('3.57');
    });

    it('refuses a dot that is no valid grouping: sent as typed, for the backend to reject it', () => {
      expect(normalizeDecimalNumberString('3.57', DE)).toEqual('3.57');
      expect(normalizeDecimalNumberString('1.2', DE)).toEqual('1.2');
      expect(normalizeDecimalNumberString('-3.5', DE)).toEqual('-3.5');
    });

    it('reads a dot in valid groups of three digits as grouping', () => {
      expect(normalizeDecimalNumberString('1.000', DE)).toEqual('1000');
      expect(normalizeDecimalNumberString('1.234', DE)).toEqual('1234');
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

  describe('a grouping separator is accepted only in valid groups of three digits', () => {
    it('sends an English comma that is no grouping as typed', () => {
      expect(normalizeDecimalNumberString('1,5', EN)).toEqual('1,5');
      expect(normalizeDecimalNumberString('3,57', EN)).toEqual('3,57');
      expect(normalizeDecimalNumberString('12,34', EN)).toEqual('12,34');
      expect(normalizeDecimalNumberString('1,2345', EN)).toEqual('1,2345');
    });

    it('accepts valid English grouping', () => {
      expect(normalizeDecimalNumberString('12,345', EN)).toEqual('12345');
      expect(normalizeDecimalNumberString('1,234,567.8', EN)).toEqual('1234567.8');
    });

    it('sends invalid German dot grouping as typed', () => {
      expect(normalizeDecimalNumberString('1.2.3', DE)).toEqual('1.2.3');
      expect(normalizeDecimalNumberString('12.34,5', DE)).toEqual('12.34,5');
    });
  });

  it('drops blanks, no-break spaces and narrow no-break spaces (e.g. pasted French or Swiss numbers)', () => {
    expect(normalizeDecimalNumberString('1 234,56', DE)).toEqual('1234.56');
    expect(normalizeDecimalNumberString('1\u00A0234,56', DE)).toEqual('1234.56');
    expect(normalizeDecimalNumberString('1\u202F234,56', FR)).toEqual('1234.56');
  });

  it('reads the separators of the current session, also after the user switched to a locale with other separators', () => {
    initNumeralLocales('de', { numberDecimalSeparator: ',', numberGroupingSeparator: '.' });
    initNumeralLocales('de', { numberDecimalSeparator: '.', numberGroupingSeparator: "'" }); // e.g. de_CH

    expect(normalizeDecimalNumberString("1'234.5")).toEqual('1234.5');
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

  it('leaves an English text with more than one decimal point untouched', () => {
    expect(normalizeDecimalNumberString('1.2.3', EN)).toEqual('1.2.3');
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

  it('accepts blanks and no-break spaces, which are dropped when the value is read', () => {
    expect(isAllowedDecimalNumberInput('1 234,56', DE)).toBe(true);
    expect(isAllowedDecimalNumberInput('1\u00A0234,56', DE)).toBe(true);
    expect(isAllowedDecimalNumberInput(' -3,5', DE)).toBe(true);
  });

  it("accepts the session's own grouping separator, e.g. the Swiss apostrophe", () => {
    expect(
      isAllowedDecimalNumberInput("1'234.56", { decimal: '.', thousands: "'" })
    ).toBe(true);
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
      expect(normalizeDecimalNumberString(formatDecimalNumberForEditing(stored, DE), DE)).toEqual(stored);
      expect(normalizeDecimalNumberString(formatDecimalNumberForEditing(stored, EN), EN)).toEqual(stored);
    });
  });
});

describe('isValidDecimalNumberString', () => {
  it('accepts the session decimal separator and grouping in groups of three', () => {
    ['3,57', '1.000', '1.234,56', '-5', '', '-', ' 1 234,5 '].forEach((text) =>
      expect(isValidDecimalNumberString(text, DE)).toBe(true)
    );
    expect(isValidDecimalNumberString(undefined, DE)).toBe(true);
  });

  it('refuses a separator that is no valid grouping', () => {
    ['3.57', '1.2', '1,234.56', '1,2,3'].forEach((text) =>
      expect(isValidDecimalNumberString(text, DE)).toBe(false)
    );
    ['1,5', '3,57', '12,34', '1.2.3'].forEach((text) =>
      expect(isValidDecimalNumberString(text, EN)).toBe(false)
    );
  });
});

describe('grouping rules', () => {
  it('refuses a first group with a leading zero', () => {
    ['0.500', '00.500', '0.500,5'].forEach((text) =>
      expect(isValidDecimalNumberString(text, DE)).toBe(false)
    );
    ['0,500', '00,500'].forEach((text) =>
      expect(isValidDecimalNumberString(text, EN)).toBe(false)
    );
    expect(normalizeDecimalNumberString('0,5', DE)).toEqual('0.5');
  });

  it('refuses blanks that are no grouping, and accepts blanks between groups of three', () => {
    ['3 57', '1 2', '12 34,5'].forEach((text) =>
      expect(isValidDecimalNumberString(text, DE)).toBe(false)
    );
    expect(normalizeDecimalNumberString('1 234,56', DE)).toEqual('1234.56');
    expect(normalizeDecimalNumberString('1\u00A0234\u202F567,8', DE)).toEqual('1234567.8');
    expect(normalizeDecimalNumberString(' 3,57 ', DE)).toEqual('3.57');
  });

  it("accepts the ASCII apostrophe where the session groups with an apostrophe (de_CH)", () => {
    const CH = { decimal: '.', thousands: '\u2019' };
    expect(normalizeDecimalNumberString("1'234.5", CH)).toEqual('1234.5');
    expect(normalizeDecimalNumberString('1\u2019234.5', CH)).toEqual('1234.5');
    expect(isAllowedDecimalNumberInput("1'234.5", CH)).toBe(true);
    expect(isValidDecimalNumberString("1'234.5", DE)).toBe(false);
  });
});
