import {
  convertScannedBarcodeToResolvedResult,
  convertScannedCULabelToResolvedResult,
} from '../../../../containers/activities/picking/PickLineScanScreen';

describe('PickingLineScanScreen', () => {
  it('convertScannedBarcodeToResolvedResult', async () => {
    const result = await convertScannedBarcodeToResolvedResult({
      scannedBarcode:
        'HU#1#{"id":"59c0e3b5845d9fca58ccd9f2a906-30926","packingInfo":{"huUnitType":"V","packingInstructionsId":101,"caption":"No Packing Item"},"product":{"id":2009297,"code":"MyCode","name":"MyProduct"},"attributes":[{"code":"HU_BestBeforeDate","displayName":"Mindesthaltbarkeit","value":"2024-04-10"},{"code":"Lot-Nummer","displayName":"Lot-Nummer","value":"010124"},{"code":"WeightNet","displayName":"Gewicht Netto","value":"180.000"}]}',
      expectedProductId: '2009297',
    });

    // the function is async and now also returns the parsed QR code and the scanned HU type
    expect(result).toEqual({
      bestBeforeDate: '2024-04-10',
      catchWeight: 180,
      lotNo: '010124',
      qrCode: expect.objectContaining({
        barcodeType: 'HU',
        huUnitType: 'V',
        productId: '2009297',
        weightNet: 180,
        lotNo: '010124',
        bestBeforeDate: '2024-04-10',
      }),
      scannedHU: { huUnitType: 'V' },
    });
  });
});

describe('PickingLineScanScreen: new mode label scan', () => {
  const GS = '\u001d';
  const label = `0109506000134352` + `91PTST001${GS}17261107` + `10LA0000001${GS}922`;
  const constant = (startPosition, endPosition, constantValue) => ({
    type: 'CONSTANT',
    startPosition,
    endPosition,
    constantValue,
  });
  const cuLabelFormat = {
    id: 7,
    name: 'CU label',
    parts: [
      constant(1, 2, '01'),
      constant(17, 18, '91'),
      constant(27, 28, '17'),
      constant(35, 36, '10'),
      { type: 'PRODUCT_CODE', startPosition: 19, endPosition: 25 },
      { type: 'BEST_BEFORE_DATE', startPosition: 29, endPosition: 34, dateFormat: 'yyMMdd' },
      { type: 'LOT', startPosition: 37, endPosition: 45 },
    ],
  };

  it('fills lot and best-before and keeps the raw label', () => {
    expect(
      convertScannedCULabelToResolvedResult({ scannedBarcode: label, cuLabelFormat, expectedProductNo: 'PTST001' })
    ).toEqual({
      lotNo: 'LA0000001',
      bestBeforeDate: '2026-11-07',
      cuLabel: label,
      isTUToBePickedAsWhole: false,
    });
  });

  it.each([
    ['another product', label, cuLabelFormat, 'OTHER01', 'activities.picking.qrcode.differentProduct'],
    ['a code of another format', 'HU#1#{}', cuLabelFormat, 'PTST001', 'picking.cuLabel.formatMismatch'],
    ['no format on the line', label, undefined, 'PTST001', 'picking.cuLabel.noFormat'],
    ['a GRAI', '9506000.13435.SER1', cuLabelFormat, 'PTST001', 'error.qrCode.invalid'],
  ])('refuses %s', (_name, scannedBarcode, format, expectedProductNo, messageKey) => {
    expect(() =>
      convertScannedCULabelToResolvedResult({ scannedBarcode, cuLabelFormat: format, expectedProductNo })
    ).toThrow(expect.objectContaining({ messageKey }));
  });
});
