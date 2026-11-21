import { CUQtyEntryMode, getCUQtyEntryPolicy } from '../../constants/CUQtyEntryMode';

const MANUAL_POLICY = { isLabelRequired: false, isPackIdRequired: false, isScanOnce: false };

describe('CUQtyEntryMode', () => {
  it('has the backend codes', () => {
    expect(CUQtyEntryMode).toEqual({
      MANUAL: 'MANUAL',
      SCAN_EACH_CU: 'SCAN_EACH_CU',
      SCAN_ONCE_ENTER_QTY: 'SCAN_ONCE_ENTER_QTY',
    });
  });
});

describe('CUQtyEntryMode.getCUQtyEntryPolicy', () => {
  it.each([
    ['MANUAL', MANUAL_POLICY],
    ['SCAN_EACH_CU', { isLabelRequired: true, isPackIdRequired: true, isScanOnce: false }],
    ['SCAN_ONCE_ENTER_QTY', { isLabelRequired: true, isPackIdRequired: false, isScanOnce: true }],
    [undefined, MANUAL_POLICY],
    [null, MANUAL_POLICY],
    ['', MANUAL_POLICY],
    ['SOMETHING_ELSE', MANUAL_POLICY],
    ['toString', MANUAL_POLICY],
  ])('%p', (code, expected) => {
    expect(getCUQtyEntryPolicy(code)).toEqual(expected);
  });
});
