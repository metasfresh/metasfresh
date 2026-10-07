import { clampComboboxColumnWidths } from '../../utils/columnWidthStorage';

const comboboxColumn = (fieldName) => ({
  fields: [{ field: fieldName }],
  widgetType: 'Lookup',
});
const textColumn = (fieldName) => ({
  fields: [{ field: fieldName }],
  widgetType: 'Text',
});

describe('stored combobox column width clamp on load', () => {
  it('clamps a stored combobox column width below 90px up to the 90px floor', () => {
    const columnWidths = { LotCode: 60 };
    const columns = [comboboxColumn('LotCode')];

    const clamped = clampComboboxColumnWidths(columnWidths, columns);

    expect(clamped.LotCode).toBe(90);
  });

  // A narrow-but-usable stored width is kept: widening it makes the column grow on entering edit mode.
  it('leaves a stored combobox column width just above the 90px floor (120px) unchanged', () => {
    const columnWidths = { LotCode: 120 };
    const columns = [comboboxColumn('LotCode')];

    const clamped = clampComboboxColumnWidths(columnWidths, columns);

    expect(clamped.LotCode).toBe(120);
  });

  it('leaves a stored combobox column width already above 90px unchanged', () => {
    const columnWidths = { LotCode: 260 };
    const columns = [comboboxColumn('LotCode')];

    const clamped = clampComboboxColumnWidths(columnWidths, columns);

    expect(clamped.LotCode).toBe(260);
  });

  it('leaves a stored non-combobox column width below 90px unchanged', () => {
    const columnWidths = { Bezeichnung: 60 };
    const columns = [textColumn('Bezeichnung')];

    const clamped = clampComboboxColumnWidths(columnWidths, columns);

    expect(clamped.Bezeichnung).toBe(60);
  });
});
