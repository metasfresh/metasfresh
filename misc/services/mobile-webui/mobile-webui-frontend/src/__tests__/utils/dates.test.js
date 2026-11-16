import { formatDateToHumanReadableStr } from '../../utils/dates';
import { setLanguage } from '../../utils/translations';

describe('dates tests', () => {
  describe('formatDateToHumanReadableStr', () => {
    afterEach(() => setLanguage('en_US'));

    const expectations = [
      // [ language, ISO date, expected ]
      ['de_DE', '2026-11-04', '04.11.2026'],
      ['de_DE', '2026-01-31', '31.01.2026'],
      ['en_US', '2026-11-04', '11/04/2026'],
    ];
    expectations.forEach(([language, isoDate, expected]) => {
      test(`${isoDate} in ${language} => ${expected}`, () => {
        setLanguage(language);
        expect(formatDateToHumanReadableStr(isoDate)).toBe(expected);
      });
    });

    test('is not shifted by the timezone of the machine', () => {
      setLanguage('de_DE');
      const originalTZ = process.env.TZ;
      try {
        ['America/Los_Angeles', 'Pacific/Auckland'].forEach((tz) => {
          process.env.TZ = tz;
          expect(formatDateToHumanReadableStr('2026-11-04')).toBe('04.11.2026');
        });
      } finally {
        if (originalTZ === undefined) {
          delete process.env.TZ;
        } else {
          process.env.TZ = originalTZ;
        }
      }
    });

    test('null/empty => empty string', () => {
      expect(formatDateToHumanReadableStr(null)).toBe('');
      expect(formatDateToHumanReadableStr('')).toBe('');
    });
  });
});
