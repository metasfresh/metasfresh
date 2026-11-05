import { convertScannedBarcodeToResolvedResult } from '../../../../containers/activities/picking/PickLineScanScreen';
import { getScannedHUQRCodeInfo } from '../../../../api/picking';
import { trl } from '../../../../utils/translations';

jest.mock('../../../../api/picking', () => ({
  ...jest.requireActual('../../../../api/picking'),
  getScannedHUQRCodeInfo: jest.fn(),
}));

//
// Tier: Jest rather than Playwright. The state under test - the HU lookup answering with an HU that
// carries no qty of the line's product - cannot be provoked through the E2E masterdata: the backend
// resolves such a label by external lot number, an attribute no test fixture (nor JsonCreateHURequest)
// can set. Mocking the one API call is the only honest way to reach the branch.
//
// A whole-TU label: the custom weight-label format from the whole-HU over-picking picking E2E spec.
// parseCustomQRCode flags every match as isTUToBePickedAsWhole, so a barcode matching this format
// takes the whole-TU branch.
const WEIGHT_LABEL_FORMAT = {
  name: 'weight label',
  parts: [
    { startPosition: 1, endPosition: 4, type: 'PRODUCT_CODE' },
    { startPosition: 5, endPosition: 10, type: 'WEIGHT_KG' },
    { startPosition: 11, endPosition: 18, type: 'LOT' },
    { startPosition: 19, endPosition: 24, type: 'PRODUCTION_DATE', dateFormat: 'yyMMdd' },
    { startPosition: 25, endPosition: 30, type: 'BEST_BEFORE_DATE', dateFormat: 'yyMMdd' },
  ],
};
// product 1234, 1.000 kg, lot 123, produced 2025-04-03, best before 2026-04-10
const WEIGHT_LABEL_BARCODE = '123400100000000123250403260410';

const resolveWeightLabelForLine = () =>
  convertScannedBarcodeToResolvedResult({
    scannedBarcode: WEIGHT_LABEL_BARCODE,
    expectedProductNo: '1234',
    customQRCodeFormats: [WEIGHT_LABEL_FORMAT],
    // the picking job + line the operator is picking - what gates the whole-TU HU lookup
    wfProcessId: 'wfProcess-1',
    lineId: 'line-1',
  });

describe('PickingLineScanScreen', () => {
  beforeEach(() => {
    getScannedHUQRCodeInfo.mockReset();
  });

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
      isTUToBePickedAsWhole: false,
      bestBeforeDate: '2024-04-10',
      lotNo: '010124',
      scannedHU: { huUnitType: 'V' },
    });
  });

  // The lookup resolves an HU - it just carries no pickable qty of the product this line picks. Both
  // shapes reach the UI: the backend omits productQty when the HU holds none of the product, and reports
  // 0 for a storage row that has been emptied. Without a positive qty there is nothing to bound the pick
  // by, so the whole TU would book unasked and unbounded: the scan has to fail instead.
  it.each([
    ['reports no qty at all', {}],
    ['reports a zero qty', { productQty: 0 }],
  ])('fails the scan when the whole-TU HU lookup %s of the line product', async (_caption, productQtyPart) => {
    getScannedHUQRCodeInfo.mockResolvedValue({
      huQRCode: { code: 'HU#1#{"id":"other-hu"}' },
      qtyTUs: 1,
      ...productQtyPart,
    });

    await expect(resolveWeightLabelForLine()).rejects.toEqual(trl('activities.picking.notEligibleHUBarcode'));

    expect(getScannedHUQRCodeInfo).toHaveBeenCalledWith({
      qrCode: WEIGHT_LABEL_BARCODE,
      productNo: '1234',
      wfProcessId: 'wfProcess-1',
      lineId: 'line-1',
    });
  });

  it('resolves qtyInitial from the whole-TU HU lookup when it reports a qty of the line product', async () => {
    getScannedHUQRCodeInfo.mockResolvedValue({
      huQRCode: { code: 'HU#1#{"id":"the-hu"}' },
      qtyTUs: 1,
      productQty: 3, // JsonHUInfo.productQty is a BigDecimal, so it arrives as a JSON number
    });

    const result = await resolveWeightLabelForLine();

    expect(result.isTUToBePickedAsWhole).toBe(true);
    expect(result.qtyInitial).toBe(3);
  });
});
