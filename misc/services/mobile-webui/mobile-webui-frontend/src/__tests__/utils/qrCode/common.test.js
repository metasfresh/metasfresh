import { checkPartialScannedCode, isBarcodeProductNoMatching, ScanCompleteness } from '../../../utils/qrCode/common';

describe('common tests', () => {
  // Dispatcher-level tests only: it delegates to the per-format checkers (HU classification cases
  // live in hu.test.js) and must never throw.
  describe('checkPartialScannedCode', () => {
    it('NOT_APPLICABLE for empty / plain barcodes / non-string (never throws)', () => {
      expect(checkPartialScannedCode('')).toBe(ScanCompleteness.NOT_APPLICABLE);
      expect(checkPartialScannedCode(null)).toBe(ScanCompleteness.NOT_APPLICABLE);
      expect(checkPartialScannedCode(undefined)).toBe(ScanCompleteness.NOT_APPLICABLE);
      expect(checkPartialScannedCode(12345)).toBe(ScanCompleteness.NOT_APPLICABLE);
      expect(checkPartialScannedCode({ not: 'a string' })).toBe(ScanCompleteness.NOT_APPLICABLE);
      expect(checkPartialScannedCode('2100001234567')).toBe(ScanCompleteness.NOT_APPLICABLE);
    });

    it('delegates HU classification (PARTIAL while arriving, COMPLETE once closed)', () => {
      expect(checkPartialScannedCode('HU#1#{"id":"x')).toBe(ScanCompleteness.PARTIAL_SCAN);
      expect(checkPartialScannedCode('HU#1#{"id":"x"}')).toBe(ScanCompleteness.COMPLETE_SCAN);
    });
  });

  describe('isBarcodeProductNoMatching', () => {
    it('expectedProductNo is null', () => {
      expect(
        isBarcodeProductNoMatching({ expectedProductNo: null, barcodeProductNo: '12345', barcodeType: 'HU' })
      ).toBe(true);
    });
    it('barcodeProductNo is null', () => {
      expect(
        isBarcodeProductNoMatching({ expectedProductNo: '12345', barcodeProductNo: null, barcodeType: 'HU' })
      ).toBe(true);
    });
    it('HU barcode - exact product', () => {
      expect(
        isBarcodeProductNoMatching({ expectedProductNo: '12345', barcodeProductNo: '12345', barcodeType: 'HU' })
      ).toBe(true);
    });
    it('HU barcode - expected productNo starts with barcode productNo', () => {
      expect(
        isBarcodeProductNoMatching({ expectedProductNo: '123456789', barcodeProductNo: '12345', barcodeType: 'HU' })
      ).toBe(false);
    });
    it('EAN13 barcode - different productNo(s)', () => {
      expect(
        isBarcodeProductNoMatching({ expectedProductNo: '12345', barcodeProductNo: '12344', barcodeType: 'EAN13' })
      ).toBe(false);
    });
    it('EAN13 barcode - exact product', () => {
      expect(
        isBarcodeProductNoMatching({ expectedProductNo: '12345', barcodeProductNo: '12345', barcodeType: 'EAN13' })
      ).toBe(true);
    });
    it('EAN13 barcode - expected productNo starts with barcode productNo', () => {
      expect(
        isBarcodeProductNoMatching({ expectedProductNo: '123456789', barcodeProductNo: '12345', barcodeType: 'EAN13' })
      ).toBe(true);
    });
    it('EAN13 barcode - different productNo(s)', () => {
      expect(
        isBarcodeProductNoMatching({ expectedProductNo: '12345', barcodeProductNo: '12344', barcodeType: 'EAN13' })
      ).toBe(false);
    });
    it('EAN13 barcode - from Ean13ProductCode', () => {
      expect(
        isBarcodeProductNoMatching({
          expectedProductNo: '00027_20250312T233125110',
          expectedGS1ProductCodes: { ean13ProductCode: '4888' },
          barcodeProductNo: '4888',
          barcodeType: 'EAN13',
        })
      ).toBe(true);
    });
    it('EAN13 barcode - from Ean13ProductCode, not matching', () => {
      expect(
        isBarcodeProductNoMatching({
          expectedProductNo: '00027_20250312T233125110',
          expectedGS1ProductCodes: { ean13ProductCode: '4888' },
          barcodeProductNo: '48889',
          barcodeType: 'EAN13',
        })
      ).toBe(false);
    });
  });
});
