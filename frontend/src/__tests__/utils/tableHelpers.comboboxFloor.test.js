import { getSizeClass, getSizeStyle } from '../../utils/tableHelpers';

describe('combobox size-resolution floor (BF-B4a)', () => {
  it.each(['S', 'M'])('floors a combobox (Lookup) column at WidgetSize=%s to >= 210px', (size) => {
    const col = { widgetType: 'Lookup', size };

    const style = getSizeStyle(col);

    expect(style).toBeDefined();
    expect(parseInt(style.minWidth, 10)).toBeGreaterThanOrEqual(210);
  });

  it.each(['S', 'M'])('floors a combobox (List) column at WidgetSize=%s to >= 210px', (size) => {
    const col = { widgetType: 'List', size };

    const style = getSizeStyle(col);

    expect(style).toBeDefined();
    expect(parseInt(style.minWidth, 10)).toBeGreaterThanOrEqual(210);
  });

  it.each(['S', 'M'])('does NOT floor a non-combobox (Text) column at WidgetSize=%s', (size) => {
    const col = { widgetType: 'Text', size };

    expect(getSizeStyle(col)).toBeUndefined();
    // its normal band is untouched
    expect(getSizeClass(col)).toBe(size === 'S' ? 'td-sm' : 'td-md');
  });

  it('does not floor a combobox column already at or above 210px (WidgetSize=L)', () => {
    const col = { widgetType: 'Lookup', size: 'L' };

    expect(getSizeStyle(col)).toBeUndefined();
    expect(getSizeClass(col)).toBe('td-lg');
  });

  it('leaves the resolved band class unchanged for a floored combobox column (no band promotion)', () => {
    expect(getSizeClass({ widgetType: 'Lookup', size: 'S' })).toBe('td-sm');
    expect(getSizeClass({ widgetType: 'Lookup', size: 'M' })).toBe('td-md');
  });
});
