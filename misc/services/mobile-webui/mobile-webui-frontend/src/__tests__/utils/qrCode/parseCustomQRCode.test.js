import { parseCustomQRCode, parseLocalDate, parseNumber } from '../../../utils/qrCode/parseCustomQRCode';
import { ATTR_lotNo } from '../../../utils/qrCode/common';

// parseNumber takes decimalPointPosition (renamed from decimalPoint in https://github.com/metasfresh/metasfresh/pull/24984)
describe('parseNumber', () => {
  test('should return the number as is if decimalPoint is 0', () => {
    const result = parseNumber({ string: '123456', decimalPointPosition: 0 });
    expect(result).toBe(123456);
  });

  test('should apply the decimal point starting from the right', () => {
    const result = parseNumber({ string: '123456', decimalPointPosition: 2 });
    expect(result).toBe(1234.56);
  });

  test('should handle a decimalPoint equal to the string length', () => {
    const result = parseNumber({ string: '123456', decimalPointPosition: 6 });
    expect(result).toBe(0.123456);
  });

  test('should handle a decimalPoint greater than the string length', () => {
    const result = parseNumber({ string: '123456', decimalPointPosition: 8 });
    expect(result).toBe(0.00123456);
  });

  test('should handle input with leading zeros', () => {
    const result = parseNumber({ string: '00123456', decimalPointPosition: 3 });
    expect(result).toBe(123.456);
  });

  test('should throw an error for invalid string input', () => {
    expect(() => parseNumber({ string: 'abc', decimalPointPosition: 2 })).toThrow('Invalid number format: "abc"');
  });

  test('should throw an error for invalid decimalPoint', () => {
    expect(() => parseNumber({ string: '123456', decimalPointPosition: -1 })).toThrow('Invalid decimalPoint: "-1"');
    expect(() => parseNumber({ string: '123456', decimalPointPosition: 1.5 })).toThrow('Invalid decimalPoint: "1.5"');
    expect(() => parseNumber({ string: '123456', decimalPointPosition: '2' })).toThrow('Invalid decimalPoint: "2"');
  });

  test('should handle large numbers correctly', () => {
    const result = parseNumber({ string: '12345678901234567890', decimalPointPosition: 10 });
    expect(result).toBe(1234567890.123456789);
  });

  test('should return 0 if the input is "0"', () => {
    const result = parseNumber({ string: '0', decimalPointPosition: 5 });
    expect(result).toBe(0);
  });

  test('should return a valid output for input with decimalPoint less than string length', () => {
    const result = parseNumber({ string: '123', decimalPointPosition: 1 });
    expect(result).toBe(12.3);
  });
});

describe('parseLocalDate', () => {
  test('should parse a valid date string based on the provided format', () => {
    const result = parseLocalDate({ string: '2025-06-11', dateFormat: 'yyyy-MM-dd' });
    expect(result).toBe('2025-06-11');
  });

  test('should correctly parse a date with different formats', () => {
    const result = parseLocalDate({ string: '11/06/2025', dateFormat: 'dd/MM/yyyy' });
    expect(result).toBe('2025-06-11');
  });

  test('should handle a valid date with time', () => {
    const result = parseLocalDate({ string: '2025-06-11 14:30', dateFormat: 'yyyy-MM-dd HH:mm' });
    expect(result).toBe('2025-06-11');
  });

  test('should throw an error if the date string is not a string', () => {
    expect(() => parseLocalDate({ string: 12345, dateFormat: 'yyyy-MM-dd' })).toThrow(
      'Both string and format must be strings'
    );
  });

  test('should throw an error if the dateFormat is not a string', () => {
    expect(() => parseLocalDate({ string: '2025-06-11', dateFormat: 123 })).toThrow(
      'Both string and format must be strings'
    );
  });

  test('should throw an error for invalid date string', () => {
    expect(() => parseLocalDate({ string: 'invalid-date', dateFormat: 'yyyy-MM-dd' })).toThrow();
  });

  test('should throw an error for mismatched formats', () => {
    expect(() => parseLocalDate({ string: '11/06/2025', dateFormat: 'yyyy-MM-dd' })).toThrow();
  });

  test('should handle edge cases with valid dates', () => {
    const result = parseLocalDate({ string: '2024-02-29', dateFormat: 'yyyy-MM-dd' }); // Leap year
    expect(result).toBe('2024-02-29');
  });

  test('should parse if the input string is in the same format as the output', () => {
    const result = parseLocalDate({ string: '2025-06-11', dateFormat: 'yyyy-MM-dd' });
    expect(result).toBe('2025-06-11');
  });
});

describe('parseCustomQRCode - part with an open end (reads to the end of the code)', () => {
  // 1..4 "(10)", lot from 5 to the end; the backend sends endPosition 0 for an open end
  const formatWithOpenEnd = (endPosition) => ({
    name: 'Test',
    parts: [
      { type: 'CONSTANT', startPosition: 1, endPosition: 4, constantValue: '(10)' },
      { type: 'LOT', startPosition: 5, endPosition },
    ],
  });

  test.each([0, null, undefined])('endPosition %p: lots of any length are read', (endPosition) => {
    ['7', '123456', '1234567', 'AB12CD34EF56GH78IJ90'].forEach((lot) => {
      const result = parseCustomQRCode({ string: `(10)${lot}`, format: formatWithOpenEnd(endPosition) });
      expect(result.error).toBeUndefined();
      expect(result[ATTR_lotNo]).toBe(lot);
    });
  });

  test('leading zeros are stripped and a trailing blank is trimmed, like in the backend', () => {
    const result = parseCustomQRCode({ string: '(10)012345 ', format: formatWithOpenEnd(0) });
    expect(result[ATTR_lotNo]).toBe('12345');
  });

  test('a filled end: a lot padded with trailing blanks is trimmed, like in the backend', () => {
    const result = parseCustomQRCode({ string: '(10)AB12  ', format: formatWithOpenEnd(10) });
    expect(result.error).toBeUndefined();
    expect(result[ATTR_lotNo]).toBe('AB12');
  });

  test('no character at the start position: not recognized', () => {
    expect(parseCustomQRCode({ string: '(10)', format: formatWithOpenEnd(0) }).error).toBeDefined();
  });

  test('a filled end is still exact', () => {
    const result = parseCustomQRCode({ string: '(10)1234567', format: formatWithOpenEnd(10) });
    expect(result[ATTR_lotNo]).toBe('123456');
    expect(parseCustomQRCode({ string: '(10)12345', format: formatWithOpenEnd(10) }).error).toBeDefined();
  });
});

describe('parseCustomQRCode - PACK_ID part (look-alike labels with the positions of the real label)', () => {
  // LABEL(n) = 01 09506000134352 91 P000042 17 261107 10 A1/B002C3 92 n
  const LABEL = (n, lot = 'A1/B002C3') => `010950600013435291P0000421726110710${lot}92${n}`;
  const LABEL_Z = (n) => LABEL(n, '0A1/B002C');
  const OLD_LABEL = '010950600013435291P0000421726110710A1/B002C3';
  // LABEL(n) with character 29 (or a stray `]`) inserted at positions 26 and 46, as a scanner may send it
  const withInserted = (n, ch) => {
    const s = LABEL(n);
    return `${s.substring(0, 25)}${ch}${s.substring(25, 44)}${ch}${s.substring(44)}`;
  };
  const GS = String.fromCharCode(29);

  const F_POS = {
    name: 'F_POS',
    parts: [
      { type: 'PRODUCT_CODE', startPosition: 19, endPosition: 25 },
      { type: 'BEST_BEFORE_DATE', startPosition: 28, endPosition: 33, dateFormat: 'yyMMdd' },
      { type: 'LOT', startPosition: 36, endPosition: 44 },
      { type: 'PACK_ID', startPosition: 47, endPosition: 0 },
    ],
  };
  const F49 = {
    name: 'F49',
    parts: [
      { type: 'PRODUCT_CODE', startPosition: 19, endPosition: 25 },
      { type: 'BEST_BEFORE_DATE', startPosition: 29, endPosition: 34, dateFormat: 'yyMMdd' },
      { type: 'LOT', startPosition: 37, endPosition: 45 },
      { type: 'PACK_ID', startPosition: 49, endPosition: 0 },
    ],
  };
  const F_POS_GUARDED = {
    name: 'F_POS_GUARDED',
    parts: [
      // guards first: on a shifted string the date part would otherwise fail before any guard is checked
      { type: 'CONSTANT', startPosition: 26, endPosition: 27, constantValue: '17' },
      { type: 'CONSTANT', startPosition: 34, endPosition: 35, constantValue: '10' },
      { type: 'CONSTANT', startPosition: 45, endPosition: 46, constantValue: '92' },
      ...F_POS.parts,
    ],
  };

  test.each(['1', '2', '3', '10'])('LABEL(%s) reads product, best-before, lot and pack ID', (n) => {
    const result = parseCustomQRCode({ string: LABEL(n), format: F_POS });
    expect(result.error).toBeUndefined();
    expect(result.productNo).toBe('P000042');
    expect(result.bestBeforeDate).toBe('2026-11-07');
    expect(result.lotNo).toBe('A1/B002C3');
    expect(result.packId).toBe(n);
  });

  test('pack ID keeps leading zeros', () => {
    expect(parseCustomQRCode({ string: LABEL('007'), format: F_POS }).packId).toBe('007');
  });

  test('pack ID is not trimmed', () => {
    expect(parseCustomQRCode({ string: LABEL(' 2 '), format: F_POS }).packId).toBe(' 2 ');
  });

  test('zero-led lot: leading zero stripped, pack ID read', () => {
    const result = parseCustomQRCode({ string: LABEL_Z(2), format: F_POS });
    expect(result.lotNo).toBe('A1/B002C');
    expect(result.packId).toBe('2');
  });

  test('old label without a pack ID: not recognized', () => {
    const result = parseCustomQRCode({ string: OLD_LABEL, format: F_POS });
    expect(result.error).toBe('Cannot extract part PACK_ID[47-]');
    expect(result.formatPart?.type).toBe('PACK_ID');
  });

  test('a format without a PACK_ID part leaves packId unset', () => {
    const result = parseCustomQRCode({
      string: LABEL(2),
      format: { name: 'no pack id', parts: F_POS.parts.slice(0, 3) },
    });
    expect(result.error).toBeUndefined();
    expect(result.packId).toBeUndefined();
  });

  test.each(['1', '2', '3', '10'])('LABEL49(%s) (character 29 at 26 and 46) reads the same values', (n) => {
    const result = parseCustomQRCode({ string: withInserted(n, GS), format: F49 });
    expect(result.error).toBeUndefined();
    expect(result.productNo).toBe('P000042');
    expect(result.bestBeforeDate).toBe('2026-11-07');
    expect(result.lotNo).toBe('A1/B002C3');
    expect(result.packId).toBe(n);
  });

  test('LABEL49 without the final character 29 + 92n: not recognized', () => {
    const s = withInserted(2, GS);
    const result = parseCustomQRCode({ string: s.substring(0, 45), format: F49 });
    expect(result.error).toBeDefined();
    expect(result.formatPart?.type).toBe('PACK_ID');
  });

  describe('guards (CONSTANT parts)', () => {
    test('LABEL(2) matches', () => {
      const result = parseCustomQRCode({ string: LABEL(2), format: F_POS_GUARDED });
      expect(result.error).toBeUndefined();
      expect(result.packId).toBe('2');
    });

    test('LABEL(2) with ] inserted at 26 and 46 (49 characters) is refused', () => {
      const shifted = withInserted(2, ']');
      expect(shifted).toHaveLength(49);
      const result = parseCustomQRCode({ string: shifted, format: F_POS_GUARDED });
      expect(result.error).toBeDefined();
      expect(result.formatPart?.type).toBe('CONSTANT');
      expect(result.error).toBe("Expected constant '17' but got ']1'");
    });
  });
});
