import {
  CUQtyEntryMode,
  getCUQtyEntryPolicy,
  isLabelRequired,
  isScanEachCU,
  isScanOnce,
} from '../../constants/CUQtyEntryMode';

describe('CUQtyEntryMode.isLabelRequired', () => {
  it('has the backend codes', () => {
    expect(CUQtyEntryMode).toEqual({
      MANUAL: 'MANUAL',
      SCAN_EACH_CU: 'SCAN_EACH_CU',
      SCAN_ONCE_ENTER_QTY: 'SCAN_ONCE_ENTER_QTY',
    });
  });

  it.each([
    ['SCAN_EACH_CU', true],
    ['SCAN_ONCE_ENTER_QTY', true],
    ['MANUAL', false],
    [undefined, false],
    [null, false],
    ['', false],
    ['SOMETHING_ELSE', false],
  ])('isLabelRequired(%p) = %p', (code, expected) => {
    expect(isLabelRequired(code)).toBe(expected);
  });
});

describe('CUQtyEntryMode.getCUQtyEntryPolicy', () => {
  it.each([
    ['MANUAL', { isLabelRequired: false, isPackIdRequired: false, isScanOnce: false }],
    ['SCAN_EACH_CU', { isLabelRequired: true, isPackIdRequired: true, isScanOnce: false }],
    ['SCAN_ONCE_ENTER_QTY', { isLabelRequired: true, isPackIdRequired: false, isScanOnce: true }],
    [undefined, { isLabelRequired: false, isPackIdRequired: false, isScanOnce: false }],
    ['SOMETHING_ELSE', { isLabelRequired: false, isPackIdRequired: false, isScanOnce: false }],
  ])('%p', (code, expected) => {
    expect(getCUQtyEntryPolicy(code)).toEqual(expected);
  });

  it('the predicates read the policy', () => {
    expect(isScanOnce('SCAN_ONCE_ENTER_QTY')).toBe(true);
    expect(isScanOnce('SCAN_EACH_CU')).toBe(false);
    expect(isScanEachCU('SCAN_EACH_CU')).toBe(true);
    expect(isScanEachCU('SCAN_ONCE_ENTER_QTY')).toBe(false);
  });
});
