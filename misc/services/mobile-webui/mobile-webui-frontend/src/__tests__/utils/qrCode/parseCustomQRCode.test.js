import { parseLocalDate, parseNumber } from '../../../utils/qrCode/parseCustomQRCode';

describe('parseNumber', () => {
  test('should return the number as is if decimalPoint is 0', () => {
    const result = parseNumber({ string: '123456', decimalPoint: 0 });
    expect(result).toBe(123456);
  });

  test('should apply the decimal point starting from the right', () => {
    const result = parseNumber({ string: '123456', decimalPoint: 2 });
    expect(result).toBe(1234.56);
  });

  test('should handle a decimalPoint equal to the string length', () => {
    const result = parseNumber({ string: '123456', decimalPoint: 6 });
    expect(result).toBe(0.123456);
  });

  test('should handle a decimalPoint greater than the string length', () => {
    const result = parseNumber({ string: '123456', decimalPoint: 8 });
    expect(result).toBe(0.00123456);
  });

  test('should handle input with leading zeros', () => {
    const result = parseNumber({ string: '00123456', decimalPoint: 3 });
    expect(result).toBe(123.456);
  });

  test('should throw an error for invalid string input', () => {
    expect(() => parseNumber({ string: 'abc', decimalPoint: 2 })).toThrow('Invalid number format: "abc"');
  });

  test('should throw an error for invalid decimalPoint', () => {
    expect(() => parseNumber({ string: '123456', decimalPoint: -1 })).toThrow('Invalid decimalPoint: "-1"');
    expect(() => parseNumber({ string: '123456', decimalPoint: 1.5 })).toThrow('Invalid decimalPoint: "1.5"');
    expect(() => parseNumber({ string: '123456', decimalPoint: '2' })).toThrow('Invalid decimalPoint: "2"');
  });

  test('should handle large numbers correctly', () => {
    const result = parseNumber({ string: '12345678901234567890', decimalPoint: 10 });
    expect(result).toBe(1234567890.123456789);
  });

  test('should return 0 if the input is "0"', () => {
    const result = parseNumber({ string: '0', decimalPoint: 5 });
    expect(result).toBe(0);
  });

  test('should return a valid output for input with decimalPoint less than string length', () => {
    const result = parseNumber({ string: '123', decimalPoint: 1 });
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
