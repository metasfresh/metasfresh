import { test } from '../../playwright.config';
import { expect } from '@playwright/test';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT } from '../utils/common';
import { getViewLayout } from '../utils/WebAPIValidation';

/**
 * Combobox grid-column minimum-usable-width — manual drag-resize clamp (TC12).
 *
 * A combobox (Lookup/List) grid column must not be dragged narrower than its ~210px
 * minimum-usable width, so its open dropdown editor (`.input-dropdown-container`,
 * hard-floored at 200px) never spills into the neighbour column. A non-combobox column
 * keeps the flat 50px `MIN_COLUMN_WIDTH` clamp — this floor is combobox-only.
 *
 * Deferred leg: written per task BF-B4b, run together with the rest of the bugfix E2E
 * batch (BF-H1c) — no live stack available at authoring time.
 *
 * Data-driven off the Business Partner list view layout (window 123) so the test does not
 * hardcode which ColumnName happens to be a combobox — it discovers the first Lookup/List
 * grid column and the first non-combobox grid column from the real layout.
 *
 * Features tested:
 * - F50000: Resizable Table Columns
 */
const BUSINESS_PARTNER_WINDOW_ID = 123;
const COMBOBOX_WIDGET_TYPES = ['Lookup', 'List'];
const COMBOBOX_MIN_WIDTH_PX = 210;
const FLAT_MIN_WIDTH_PX = 50;

function elementWidgetType(element) {
  return element.widgetType || (element.fields || [])[0]?.widgetType;
}

async function dragResizeAsNarrowAsPossible(page, fieldName) {
  const handle = page.getByTestId(`resize-handle-${fieldName}`);
  await handle.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

  const handleBox = await handle.boundingBox();
  const startX = handleBox.x + handleBox.width / 2;
  const startY = handleBox.y + handleBox.height / 2;

  await page.mouse.move(startX, startY);
  await page.mouse.down();
  // drag far past any reasonable width — the clamp, not the drag distance, decides the result
  await page.mouse.move(startX - 600, startY, { steps: 15 });
  await page.mouse.up();
  await page.waitForTimeout(300);
}

test.describe('Combobox grid-column resize clamp', () => {
  test('Manual drag-resize clamps a combobox column at ~210px and a non-combobox column at 50px (AC22)', async ({
    page,
  }) => {
    allure.epic('E0200: WebUI Table Features');
    allure.tag('F50000: Resizable Table Columns');
    allure.tag('F50000');
    allure.story('Combobox column drag-resize floor (~210px)');
    allure.severity('critical');
    allure.description(`
## TC12 — Combobox resize clamp at ~210px (measured)

1. Discover a combobox (Lookup/List) grid column and a non-combobox grid column on the
   Business Partner list view layout.
2. Drag the combobox column's resize handle as narrow as possible — its width must clamp
   at ~210px (not the flat 50px \`MIN_COLUMN_WIDTH\`), and its open cell editor must stay
   within the cell (no overlap into the next column).
3. Drag the non-combobox column's resize handle as narrow as possible — its width must
   clamp at the flat 50px \`MIN_COLUMN_WIDTH\`, unchanged (the floor is combobox-only).
    `);

    test.setTimeout(120000);

    const masterdata = await Backend.createMasterdata({
      request: { login: { user: { language: 'en_US', firstname: 'E2E', lastname: 'ComboboxResize' } } },
    });

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);

    const layout = await getViewLayout(BUSINESS_PARTNER_WINDOW_ID, 'grid');
    const elements = layout.elements || [];

    const comboboxElement = elements.find((el) => COMBOBOX_WIDGET_TYPES.includes(elementWidgetType(el)));
    const nonComboboxElement = elements.find((el) => {
      const widgetType = elementWidgetType(el);
      return widgetType && !COMBOBOX_WIDGET_TYPES.includes(widgetType);
    });

    expect(comboboxElement, 'The Business Partner grid must expose at least one Lookup/List column').toBeTruthy();
    expect(nonComboboxElement, 'The Business Partner grid must expose at least one non-combobox column').toBeTruthy();

    const comboboxField = comboboxElement.fields[0].field;
    const nonComboboxField = nonComboboxElement.fields[0].field;
    console.log(
      `[INFO] combobox column under test: ${comboboxField} (${elementWidgetType(comboboxElement)}); ` +
        `non-combobox column under test: ${nonComboboxField} (${elementWidgetType(nonComboboxElement)})`
    );

    await page.goto(`${FRONTEND_BASE_URL}/window/${BUSINESS_PARTNER_WINDOW_ID}`);
    await page.locator('.js-table').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await page
      .locator('.js-table tbody tr')
      .first()
      .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

    await test.step('Combobox column clamps at ~210px, not 50px', async () => {
      await dragResizeAsNarrowAsPossible(page, comboboxField);

      const comboboxTh = page.getByTestId(`column-${comboboxField}`);
      const comboboxBox = await comboboxTh.boundingBox();
      console.log(`[INFO] combobox column width after max drag: ${comboboxBox.width}px`);

      expect(
        comboboxBox.width,
        `combobox column (${comboboxField}) must clamp at ~${COMBOBOX_MIN_WIDTH_PX}px, not the flat ${FLAT_MIN_WIDTH_PX}px floor`
      ).toBeGreaterThanOrEqual(COMBOBOX_MIN_WIDTH_PX - 5);
    });

    await test.step('The open combobox editor stays within the resized cell (no overlap)', async () => {
      const comboboxCell = page.locator(`[data-cy="cell-${comboboxField}"]`).first();
      await comboboxCell.dblclick();

      const editor = page.locator('.input-dropdown-container').first();
      await editor.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      const cellBox = await comboboxCell.boundingBox();
      const editorBox = await editor.boundingBox();
      console.log(`[INFO] cell bounds: ${JSON.stringify(cellBox)}; editor bounds: ${JSON.stringify(editorBox)}`);

      expect(
        editorBox.x + editorBox.width,
        'the open combobox editor must not spill past the resized cell right edge'
      ).toBeLessThanOrEqual(cellBox.x + cellBox.width + 1);

      await page.keyboard.press('Escape');
    });

    await test.step('Non-combobox column keeps clamping at the flat 50px floor', async () => {
      await dragResizeAsNarrowAsPossible(page, nonComboboxField);

      const nonComboboxTh = page.getByTestId(`column-${nonComboboxField}`);
      const nonComboboxBox = await nonComboboxTh.boundingBox();
      console.log(`[INFO] non-combobox column width after max drag: ${nonComboboxBox.width}px`);

      expect(
        nonComboboxBox.width,
        `non-combobox column (${nonComboboxField}) must keep clamping at the flat ${FLAT_MIN_WIDTH_PX}px floor — unaffected by the combobox floor`
      ).toBeLessThanOrEqual(FLAT_MIN_WIDTH_PX + 5);
    });
  });
});
