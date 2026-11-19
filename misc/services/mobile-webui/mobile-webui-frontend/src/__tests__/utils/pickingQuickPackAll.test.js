import { hasNewModeLine, isQuickPackAllAllowed } from '../../utils/picking';
import { CUQtyEntryMode } from '../../constants/CUQtyEntryMode';

const line = (cuQtyEntryMode) => ({ pickingLineId: 'l', cuQtyEntryMode });

describe('hasNewModeLine', () => {
  it('is false without lines', () => {
    expect(hasNewModeLine([])).toBe(false);
    expect(hasNewModeLine(undefined)).toBe(false);
  });
  it('is false when all lines are MANUAL', () => {
    expect(hasNewModeLine([line(CUQtyEntryMode.MANUAL), line(CUQtyEntryMode.MANUAL)])).toBe(false);
  });
  it('is true with one new-mode line', () => {
    expect(hasNewModeLine([line(CUQtyEntryMode.MANUAL), line(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY)])).toBe(true);
    expect(hasNewModeLine([line(CUQtyEntryMode.SCAN_EACH_CU)])).toBe(true);
  });
  it('is true for a mixed job', () => {
    expect(hasNewModeLine([line(CUQtyEntryMode.SCAN_EACH_CU), line(CUQtyEntryMode.MANUAL), line(undefined)])).toBe(
      true
    );
  });
  it('is false when the line field is absent', () => {
    expect(hasNewModeLine([{ pickingLineId: 'l' }, line(undefined)])).toBe(false);
  });
});

describe('isQuickPackAllAllowed', () => {
  it('needs the application switch', () => {
    expect(isQuickPackAllAllowed({ allowQuickPackAll: false, lines: [] })).toBe(false);
  });
  it('is allowed for Manual-only and absent-field jobs', () => {
    expect(isQuickPackAllAllowed({ allowQuickPackAll: true, lines: [line(CUQtyEntryMode.MANUAL), {}] })).toBe(true);
  });
  it('is refused when a line has a new mode', () => {
    expect(isQuickPackAllAllowed({ allowQuickPackAll: true, lines: [line(CUQtyEntryMode.SCAN_EACH_CU)] })).toBe(false);
  });
});
