import { getSizeClass, getSizeStyle } from '../../utils/tableHelpers';

describe('combobox column minimum width', () => {
  // The combobox floor is 90px. Only the td-sm band (60px) is below it; td-md (144px) clears it.
  it.each(['Lookup', 'List'])('floors a %s column at WidgetSize=S (td-sm, 60px) to exactly 90px', (widgetType) => {
    const col = { widgetType, size: 'S' };

    const style = getSizeStyle(col);

    expect(style).toEqual({ minWidth: '90px' });
  });

  it.each(['Lookup', 'List'])('does not floor a %s column at WidgetSize=M (td-md, 144px clears 90px)', (widgetType) => {
    const col = { widgetType, size: 'M' };

    expect(getSizeStyle(col)).toBeUndefined();
    expect(getSizeClass(col)).toBe('td-md');
  });

  it.each(['S', 'M'])('does NOT floor a non-combobox (Text) column at WidgetSize=%s', (size) => {
    const col = { widgetType: 'Text', size };

    expect(getSizeStyle(col)).toBeUndefined();
    // its normal band is untouched
    expect(getSizeClass(col)).toBe(size === 'S' ? 'td-sm' : 'td-md');
  });

  it('does not floor a combobox column already above 90px (WidgetSize=L)', () => {
    const col = { widgetType: 'Lookup', size: 'L' };

    expect(getSizeStyle(col)).toBeUndefined();
    expect(getSizeClass(col)).toBe('td-lg');
  });

  it('leaves the resolved band class unchanged for a floored combobox column (no band promotion)', () => {
    expect(getSizeClass({ widgetType: 'Lookup', size: 'S' })).toBe('td-sm');
    expect(getSizeClass({ widgetType: 'Lookup', size: 'M' })).toBe('td-md');
  });
});

describe('price/amount column minimum width', () => {
  // The price/amount floor is 68px: the td-sm band (60px) is below it, td-md (144px) clears it.
  it.each(['CostPrice', 'Amount'])('floors a %s column without a WidgetSize (td-sm, 60px) to exactly 68px', (widgetType) => {
    const col = { widgetType };

    expect(getSizeStyle(col)).toEqual({ minWidth: '68px' });
    expect(getSizeClass(col)).toBe('td-sm');
  });

  it('floors a CostPrice column at WidgetSize=S (td-sm, 60px) to exactly 68px', () => {
    expect(getSizeStyle({ widgetType: 'CostPrice', size: 'S' })).toEqual({ minWidth: '68px' });
  });

  it('does not floor a CostPrice column at WidgetSize=M (td-md, 144px clears 68px)', () => {
    expect(getSizeStyle({ widgetType: 'CostPrice', size: 'M' })).toBeUndefined();
  });
});
