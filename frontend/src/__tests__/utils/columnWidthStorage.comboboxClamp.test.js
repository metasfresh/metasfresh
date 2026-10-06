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
  it('clamps a stored combobox column width below 90px up to the 90px floor', () => {
    const columnWidths = { Partiecode: 60 };
    const columns = [comboboxColumn('Partiecode')];

    const clamped = clampComboboxColumnWidths(columnWidths, columns);

    expect(clamped.Partiecode).toBe(90);
  });

  // A narrow-but-usable stored width is kept: widening it makes the column grow on entering edit mode.
  it('leaves a stored combobox column width just above the 90px floor (120px) unchanged', () => {
    const columnWidths = { Partiecode: 120 };
    const columns = [comboboxColumn('Partiecode')];

    const clamped = clampComboboxColumnWidths(columnWidths, columns);

    expect(clamped.Partiecode).toBe(120);
  });

  it('leaves a stored combobox column width already above 90px unchanged', () => {
    const columnWidths = { Partiecode: 260 };
    const columns = [comboboxColumn('Partiecode')];

    const clamped = clampComboboxColumnWidths(columnWidths, columns);

    expect(clamped.Partiecode).toBe(260);
  });

  it('leaves a stored non-combobox column width below 90px unchanged', () => {
    const columnWidths = { Bezeichnung: 60 };
    const columns = [textColumn('Bezeichnung')];

    const clamped = clampComboboxColumnWidths(columnWidths, columns);

    expect(clamped.Bezeichnung).toBe(60);
  });
});
