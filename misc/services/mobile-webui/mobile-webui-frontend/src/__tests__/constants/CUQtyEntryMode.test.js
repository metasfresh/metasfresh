import { CUQtyEntryMode, isNewMode } from '../../constants/CUQtyEntryMode';

describe('CUQtyEntryMode.isNewMode', () => {
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
  ])('isNewMode(%p) = %p', (code, expected) => {
    expect(isNewMode(code)).toBe(expected);
  });
});
