import { hasLabelRequiredLine, isQuickPackAllAllowed } from '../../../utils/picking';
import { POLICY } from '../../../__testSupport__/picking/testPolicies';

const line = (mode) => ({ pickingLineId: 'l', cuQtyEntryPolicy: POLICY[mode] });

describe('hasLabelRequiredLine', () => {
  it('is false without lines', () => {
    expect(hasLabelRequiredLine([])).toBe(false);
    expect(hasLabelRequiredLine(undefined)).toBe(false);
  });
  it('is false when all lines are MANUAL', () => {
    expect(hasLabelRequiredLine([line('MANUAL'), line('MANUAL')])).toBe(false);
  });
  it('is true with one label-required-mode line', () => {
    expect(hasLabelRequiredLine([line('MANUAL'), line('SCAN_ONCE_ENTER_QTY')])).toBe(true);
    expect(hasLabelRequiredLine([line('SCAN_EACH_CU')])).toBe(true);
  });
  it('is true for a mixed job', () => {
    expect(hasLabelRequiredLine([line('SCAN_EACH_CU'), line('MANUAL'), line(undefined)])).toBe(true);
  });
  it('is false when the line field is absent', () => {
    expect(hasLabelRequiredLine([{ pickingLineId: 'l' }, line(undefined)])).toBe(false);
  });
});

describe('isQuickPackAllAllowed', () => {
  it('needs the application switch', () => {
    expect(isQuickPackAllAllowed({ allowQuickPackAll: false, lines: [] })).toBe(false);
  });
  it('is allowed for Manual-only and absent-field jobs', () => {
    expect(isQuickPackAllAllowed({ allowQuickPackAll: true, lines: [line('MANUAL'), {}] })).toBe(true);
  });
  it('is refused when a line has a label-required mode', () => {
    expect(isQuickPackAllAllowed({ allowQuickPackAll: true, lines: [line('SCAN_EACH_CU')] })).toBe(false);
  });
});
