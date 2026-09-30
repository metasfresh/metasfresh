import { clampComboboxColumnWidths } from '../../utils/columnWidthStorage';

const comboboxColumn = (fieldName) => ({
  fields: [{ field: fieldName }],
  widgetType: 'Lookup',
});
const textColumn = (fieldName) => ({
  fields: [{ field: fieldName }],
  widgetType: 'Text',
});

describe('combobox load-time stored-width clamp (BF-B4c)', () => {
  it('clamps a stored combobox column width below 210px up to the ~210px floor', () => {
    const columnWidths = { Partiecode: 120 };
    const columns = [comboboxColumn('Partiecode')];

    const clamped = clampComboboxColumnWidths(columnWidths, columns);

    expect(clamped.Partiecode).toBeGreaterThanOrEqual(210);
  });

  it('leaves a stored combobox column width already at or above 210px unchanged', () => {
    const columnWidths = { Partiecode: 260 };
    const columns = [comboboxColumn('Partiecode')];

    const clamped = clampComboboxColumnWidths(columnWidths, columns);

    expect(clamped.Partiecode).toBe(260);
  });

  it('leaves a stored non-combobox column width below 210px unchanged', () => {
    const columnWidths = { Bezeichnung: 120 };
    const columns = [textColumn('Bezeichnung')];

    const clamped = clampComboboxColumnWidths(columnWidths, columns);

    expect(clamped.Bezeichnung).toBe(120);
  });
});
