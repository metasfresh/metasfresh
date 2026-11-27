import { parseQRCodeString } from '../../../utils/qrCode/hu';

const huQrCode = `HU#1#{"id":"abcdefghijklmnop261107xyz-30926","packingInfo":{"huUnitType":"V"}}`;

const constant = (startPosition, endPosition, constantValue) => ({
  type: 'CONSTANT',
  startPosition,
  endPosition,
  constantValue,
});
const dataParts = [
  { type: 'PRODUCT_CODE', startPosition: 19, endPosition: 25 },
  { type: 'BEST_BEFORE_DATE', startPosition: 29, endPosition: 34, dateFormat: 'yyMMdd' },
];
const formatWithMarkers = {
  id: 1,
  name: 'with markers',
  parts: [constant(1, 2, '01'), constant(17, 18, '91'), constant(27, 28, '17'), constant(35, 36, '10'), ...dataParts],
};
const formatWithoutMarkers = { id: 2, name: 'without markers', parts: dataParts };

describe('parseQRCodeString: marker parts keep the global QR code out of a custom label format', () => {
  it('negative control: without markers the HU QR code is read as a custom label', () => {
    const result = parseQRCodeString({ string: huQrCode, customQRCodeFormats: [formatWithoutMarkers] });
    expect(result.barcodeType).toBe('CUSTOM');
    expect(result.bestBeforeDate).toBe('2026-11-07');
  });

  it('with markers the HU QR code is read as a global HU QR code', () => {
    const result = parseQRCodeString({ string: huQrCode, customQRCodeFormats: [formatWithMarkers] });
    expect(result.barcodeType).toBe('HU');
    expect(result.huUnitType).toBe('V');
  });
});
