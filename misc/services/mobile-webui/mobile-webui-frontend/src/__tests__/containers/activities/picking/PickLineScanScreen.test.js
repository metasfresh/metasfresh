import { convertScannedBarcodeToResolvedResult } from '../../../../containers/activities/picking/PickLineScanScreen';

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
