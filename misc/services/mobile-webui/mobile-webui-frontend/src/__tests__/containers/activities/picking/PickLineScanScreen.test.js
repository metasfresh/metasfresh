import { convertScannedBarcodeToResolvedResult } from '../../../../containers/activities/picking/PickLineScanScreen';

describe('PickingLineScanScreen', () => {
  const scannedBarcode =
    'HU#1#{"id":"59c0e3b5845d9fca58ccd9f2a906-30926","packingInfo":{"huUnitType":"V","packingInstructionsId":101,"caption":"No Packing Item"},"product":{"id":2009297,"code":"MyCode","name":"MyProduct"},"attributes":[{"code":"HU_BestBeforeDate","displayName":"Mindesthaltbarkeit","value":"2024-04-10"},{"code":"Lot-Nummer","displayName":"Lot-Nummer","value":"010124"},{"code":"WeightNet","displayName":"Gewicht Netto","value":"180.000"}]}';

  it('convertScannedBarcodeToResolvedResult', async () => {
    const result = await convertScannedBarcodeToResolvedResult({
      scannedBarcode,
      expectedProductId: '2009297',
    });

    expect(result).toEqual({
      bestBeforeDate: '2024-04-10',
      catchWeight: 180,
      lotNo: '010124',
      productionDate: undefined, // not present in this QR code
      scannedHU: { huUnitType: 'V' },
      qrCode: {
        barcodeType: 'HU',
        bestBeforeDate: '2024-04-10',
        code: scannedBarcode,
        displayable: '30926',
        huUnitType: 'V',
        isUnique: true,
        lotNo: '010124',
        productId: '2009297',
        weightNet: 180,
      },
    });
  });
});
