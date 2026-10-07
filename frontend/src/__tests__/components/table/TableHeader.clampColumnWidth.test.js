import { clampColumnWidth } from '../../../components/table/TableHeader';

describe('grid column drag-resize clamp', () => {
  it('clamps a combobox (Lookup) column dragged to 80px up to the 90px floor', () => {
    expect(clampColumnWidth({ widgetType: 'Lookup', px: 80 })).toBe(90);
  });

  it('leaves a combobox (Lookup) column dragged to 120px (above the 90px floor) unchanged', () => {
    expect(clampColumnWidth({ widgetType: 'Lookup', px: 120 })).toBe(120);
  });

  it('leaves a combobox (Lookup) column dragged to 300px unchanged', () => {
    expect(clampColumnWidth({ widgetType: 'Lookup', px: 300 })).toBe(300);
  });

  it('clamps a non-combobox (Text) column dragged to 30px at the flat 50px floor', () => {
    expect(clampColumnWidth({ widgetType: 'Text', px: 30 })).toBe(50);
  });

  it('leaves a non-combobox (Text) column dragged to 300px unchanged', () => {
    expect(clampColumnWidth({ widgetType: 'Text', px: 300 })).toBe(300);
  });
});
