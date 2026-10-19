import { clampStoredColumnWidths } from '../../utils/columnWidthStorage';

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

    const clamped = clampStoredColumnWidths(columnWidths, columns);

    expect(clamped.LotCode).toBe(90);
  });

  // A narrow-but-usable stored width is kept: widening it makes the column grow on entering edit mode.
  it('leaves a stored combobox column width just above the 90px floor (120px) unchanged', () => {
    const columnWidths = { LotCode: 120 };
    const columns = [comboboxColumn('LotCode')];

    const clamped = clampStoredColumnWidths(columnWidths, columns);

    expect(clamped.LotCode).toBe(120);
  });

  it('leaves a stored combobox column width already above 90px unchanged', () => {
    const columnWidths = { LotCode: 260 };
    const columns = [comboboxColumn('LotCode')];

    const clamped = clampStoredColumnWidths(columnWidths, columns);

    expect(clamped.LotCode).toBe(260);
  });

  it('leaves a stored non-combobox column width below 90px unchanged', () => {
    const columnWidths = { Bezeichnung: 60 };
    const columns = [textColumn('Bezeichnung')];

    const clamped = clampStoredColumnWidths(columnWidths, columns);

    expect(clamped.Bezeichnung).toBe(60);
  });
});

describe('stored price/amount column width clamp on load', () => {
  const column = (fieldName, widgetType) => ({ fields: [{ field: fieldName }], widgetType });

  it.each(['CostPrice', 'Amount'])('clamps a stored %s column width below 68px up to the 68px floor', (widgetType) => {
    const clamped = clampStoredColumnWidths({ PriceEntered: 50 }, [column('PriceEntered', widgetType)]);

    expect(clamped.PriceEntered).toBe(68);
  });

  it('leaves a stored price column width above 68px unchanged', () => {
    const clamped = clampStoredColumnWidths({ PriceEntered: 80 }, [column('PriceEntered', 'CostPrice')]);

    expect(clamped.PriceEntered).toBe(80);
  });
});
