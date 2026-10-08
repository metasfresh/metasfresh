import { convertScannedBarcodeToResolvedResult } from '../../../../containers/activities/picking/PickLineScanScreen';

describe('PickingLineScanScreen', () => {
  it('convertScannedBarcodeToResolvedResult', async () => {
    const scannedBarcode =
      'HU#1#{"id":"59c0e3b5845d9fca58ccd9f2a906-30926","packingInfo":{"huUnitType":"V","packingInstructionsId":101,"caption":"No Packing Item"},"product":{"id":2009297,"code":"MyCode","name":"MyProduct"},"attributes":[{"code":"HU_BestBeforeDate","displayName":"Mindesthaltbarkeit","value":"2024-04-10"},{"code":"Lot-Nummer","displayName":"Lot-Nummer","value":"010124"},{"code":"WeightNet","displayName":"Gewicht Netto","value":"180.000"}]}';

    // convertScannedBarcodeToResolvedResult is async and returns the full resolved result
    // (the parsed qrCode + the extracted pick attributes + the scannedHU unit type).
    const result = await convertScannedBarcodeToResolvedResult({
      scannedBarcode,
      expectedProductId: '2009297',
    });

    expect(result).toEqual({
      qrCode: {
        code: scannedBarcode,
        displayable: '30926',
        barcodeType: 'HU',
        isUnique: true,
        huUnitType: 'V',
        productId: '2009297',
        weightNet: 180,
        bestBeforeDate: '2024-04-10',
        lotNo: '010124',
      },
      catchWeight: 180,
      bestBeforeDate: '2024-04-10',
      lotNo: '010124',
      scannedHU: { huUnitType: 'V' },
    });
  });
});
