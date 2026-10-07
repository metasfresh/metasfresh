import { test } from '../../playwright.config';
import { expect } from '@playwright/test';
import { allure } from 'allure-playwright';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SLOW_ACTION_TIMEOUT } from '../utils/common';
import {
  createMasterdata,
  gotoOrderList,
  createNewOrder,
  selectOrderCustomer,
  addOrderLine,
} from '../utils/OrderLineHarness';

/**
 * Combobox (Lookup/List) column minimum width on the sales order-line grid:
 * - dragged as narrow as possible, a combobox column stops at 90px, any other column at 50px;
 * - the open combobox editor stays inside its cell and does not widen the column;
 * - a stored combobox width below 90px loads as 90px; a stored width of another column loads as stored.
 *
 * `M_Product_ID` is the grid's combobox column, `QtyEntered` the other column. The order-line grid
 * is used because double-clicking a cell there opens its editor (in a top-level list view it opens
 * the record).
 *
 * Features tested:
 * - F50000: Resizable Table Columns
 */
const COMBOBOX_FIELD = 'M_Product_ID';
const NON_COMBOBOX_FIELD = 'QtyEntered';
const COMBOBOX_MIN_WIDTH_PX = 90;
// Sub-pixel rounding / border allowance when comparing a rendered width to the floor.
const WIDTH_TOLERANCE_PX = 5;
const FLAT_MIN_WIDTH_PX = 50;

async function dragResizeAsNarrowAsPossible(page, fieldName) {
  const handle = page.getByTestId(`resize-handle-${fieldName}`);
  await handle.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  // a scrolled-out handle is not hit at its bounding box; scroll it into view first
  await handle.scrollIntoViewIfNeeded();

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

async function seedOrderLineGrid(page) {
  const masterdata = await createMasterdata();

  await LoginPage.goto();
  await LoginPage.login(masterdata.login.user);
  await DashboardPage.expectVisible();

  await gotoOrderList();
  const recordId = await createNewOrder();
  await selectOrderCustomer(recordId, masterdata.bpartners.CUSTOMER1.bpartnerCode);
  await addOrderLine(recordId, { productCode: masterdata.products.Product1.productCode, quantity: 1 });

  await page
    .locator(`[data-cy="cell-${COMBOBOX_FIELD}"]`)
    .first()
    .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

  return { masterdata, recordId };
}

test.describe('Combobox grid-column resize clamp', () => {
  test('Dragged as narrow as possible, a combobox column stops at 90px and another column at 50px; the open combobox editor stays inside the cell', async ({
    page,
  }) => {
    allure.epic('E0500: Sales Orders');
    allure.tag('F5010: Order Lines Grid');
    allure.tag('F5010');
    allure.story('Combobox column drag-resize floor (90px)');
    allure.severity('critical');
    allure.description(`
1. Drag the combobox column (\`${COMBOBOX_FIELD}\`) as narrow as possible: it stops at 90px.
2. Drag the other column (\`${NON_COMBOBOX_FIELD}\`) as narrow as possible: it stops at 50px.
3. Open the combobox cell's editor: it stays inside the cell and the column keeps its width.
    `);

    test.setTimeout(120000);

    await seedOrderLineGrid(page);

    await test.step('The combobox column stops at 90px', async () => {
      await dragResizeAsNarrowAsPossible(page, COMBOBOX_FIELD);

      const comboboxTh = page.getByTestId(`column-${COMBOBOX_FIELD}`);
      const comboboxBox = await comboboxTh.boundingBox();
      console.log(`[INFO] combobox column width after max drag: ${comboboxBox.width}px`);

      expect(
        comboboxBox.width,
        `combobox column (${COMBOBOX_FIELD}) must clamp at ${COMBOBOX_MIN_WIDTH_PX}px, not the flat ${FLAT_MIN_WIDTH_PX}px floor`
      ).toBeGreaterThanOrEqual(COMBOBOX_MIN_WIDTH_PX - WIDTH_TOLERANCE_PX);
      expect(
        comboboxBox.width,
        `combobox column (${COMBOBOX_FIELD}) dragged to the extreme must stop AT the ${COMBOBOX_MIN_WIDTH_PX}px floor, not above it`
      ).toBeLessThanOrEqual(COMBOBOX_MIN_WIDTH_PX + WIDTH_TOLERANCE_PX);
    });

    // resize before opening the combobox editor: right after a dropdown closes, its backdrop can
    // still catch the drag's first mouse moves
    await test.step('The other column stops at 50px', async () => {
      await dragResizeAsNarrowAsPossible(page, NON_COMBOBOX_FIELD);

      const nonComboboxTh = page.getByTestId(`column-${NON_COMBOBOX_FIELD}`);
      const nonComboboxBox = await nonComboboxTh.boundingBox();
      console.log(`[INFO] non-combobox column width after max drag: ${nonComboboxBox.width}px`);

      expect(
        nonComboboxBox.width,
        `non-combobox column (${NON_COMBOBOX_FIELD}) must keep clamping at the flat ${FLAT_MIN_WIDTH_PX}px floor — unaffected by the combobox floor`
      ).toBeLessThanOrEqual(FLAT_MIN_WIDTH_PX + 5);
    });

    await test.step('The open combobox editor stays within the resized cell and does not widen it', async () => {
      const comboboxCell = page.locator(`[data-cy="cell-${COMBOBOX_FIELD}"]`).first();
      const cellBoxBeforeEdit = await comboboxCell.boundingBox();
      await comboboxCell.dblclick();

      // Scoped to the cell itself — a bare page-wide `.input-dropdown-container` also matches
      // every Lookup field on the order HEADER form (C_BPartner_ID, C_BPartner_Location_ID, ...),
      // and `.first()` picks one of those instead of the grid cell's own open editor.
      const editor = comboboxCell.locator('.input-dropdown-container').first();
      await editor.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      const cellBox = await comboboxCell.boundingBox();
      const editorBox = await editor.boundingBox();
      console.log(`[INFO] cell bounds: ${JSON.stringify(cellBox)}; editor bounds: ${JSON.stringify(editorBox)}`);

      // the tolerance absorbs sub-pixel rounding only
      const OVERLAP_TOLERANCE_PX = 3;
      expect(
        editorBox.x + editorBox.width,
        'the open combobox editor must not spill past the resized cell right edge'
      ).toBeLessThanOrEqual(cellBox.x + cellBox.width + OVERLAP_TOLERANCE_PX);
      expect(
        Math.abs(cellBox.width - cellBoxBeforeEdit.width),
        `opening the combobox editor must not widen the column (read=${cellBoxBeforeEdit.width}px, edit=${cellBox.width}px)`
      ).toBeLessThanOrEqual(1);

      await page.keyboard.press('Escape');
    });
  });

  test('A stored combobox width below 90px loads as 90px; a stored width of another column loads as stored', async ({
    page,
  }) => {
    allure.epic('E0500: Sales Orders');
    allure.tag('F5010: Order Lines Grid');
    allure.tag('F5010');
    allure.story('Combobox column load-time stored-width clamp (90px)');
    allure.severity('critical');
    allure.description(`
Store a combobox width below 90px and an equally low width of another column in the grid's
\`localStorage\` key, reload: the combobox column shows 90px, the other column its stored width.
    `);

    test.setTimeout(120000);

    await seedOrderLineGrid(page);

    // Discover the real persisted-width localStorage key by performing one legitimate resize
    // (widening, so it never engages any clamp) rather than guessing the key format — the key
    // is `columnWidths_<windowId>[_<viewId>]` and the order-line grid's viewId (if any) is
    // runtime-assigned, not something this spec should hardcode.
    const handle = page.getByTestId(`resize-handle-${COMBOBOX_FIELD}`);
    await handle.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    const handleBox = await handle.boundingBox();
    await page.mouse.move(handleBox.x + handleBox.width / 2, handleBox.y + handleBox.height / 2);
    await page.mouse.down();
    await page.mouse.move(handleBox.x + handleBox.width / 2 + 60, handleBox.y + handleBox.height / 2, { steps: 10 });
    await page.mouse.up();
    await page.waitForTimeout(300);

    const storageKey = await page.evaluate(() => {
      const key = Object.keys(localStorage).find((k) => k.startsWith('columnWidths_'));
      return key || null;
    });
    expect(storageKey, 'the order-line grid must persist column widths under a columnWidths_* localStorage key').toBeTruthy();
    console.log(`[INFO] resolved columnWidths localStorage key: ${storageKey}`);

    // store a combobox width below 90px and an equally low width of the other column
    const BELOW_FLOOR_COMBOBOX_WIDTH = 60;
    const NON_COMBOBOX_STORED_WIDTH = 80;
    await page.evaluate(
      ({ key, comboboxField, comboboxWidth, nonComboboxField, nonComboboxWidth }) => {
        const stored = JSON.parse(localStorage.getItem(key) || '{}');
        stored[comboboxField] = comboboxWidth;
        stored[nonComboboxField] = nonComboboxWidth;
        localStorage.setItem(key, JSON.stringify(stored));
      },
      {
        key: storageKey,
        comboboxField: COMBOBOX_FIELD,
        comboboxWidth: BELOW_FLOOR_COMBOBOX_WIDTH,
        nonComboboxField: NON_COMBOBOX_FIELD,
        nonComboboxWidth: NON_COMBOBOX_STORED_WIDTH,
      }
    );

    await page.reload();
    await page
      .locator(`[data-cy="cell-${COMBOBOX_FIELD}"]`)
      .first()
      .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

    await test.step('A stored combobox width below the floor loads clamped to 90px', async () => {
      const comboboxTh = page.getByTestId(`column-${COMBOBOX_FIELD}`);
      const comboboxBox = await comboboxTh.boundingBox();
      console.log(`[INFO] combobox column width on load (stored ${BELOW_FLOOR_COMBOBOX_WIDTH}px): ${comboboxBox.width}px`);

      expect(
        comboboxBox.width,
        `a stored combobox width of ${BELOW_FLOOR_COMBOBOX_WIDTH}px must load clamped to ${COMBOBOX_MIN_WIDTH_PX}px, not verbatim`
      ).toBeGreaterThanOrEqual(COMBOBOX_MIN_WIDTH_PX - WIDTH_TOLERANCE_PX);
      expect(
        comboboxBox.width,
        `a stored combobox width of ${BELOW_FLOOR_COMBOBOX_WIDTH}px must be raised only to the ${COMBOBOX_MIN_WIDTH_PX}px floor, not above it`
      ).toBeLessThanOrEqual(COMBOBOX_MIN_WIDTH_PX + WIDTH_TOLERANCE_PX);
    });

    await test.step('A stored non-combobox width is restored verbatim (unaffected)', async () => {
      const nonComboboxTh = page.getByTestId(`column-${NON_COMBOBOX_FIELD}`);
      const nonComboboxBox = await nonComboboxTh.boundingBox();
      console.log(`[INFO] non-combobox column width on load (stored ${NON_COMBOBOX_STORED_WIDTH}px): ${nonComboboxBox.width}px`);

      expect(
        Math.abs(nonComboboxBox.width - NON_COMBOBOX_STORED_WIDTH),
        `a stored non-combobox width of ${NON_COMBOBOX_STORED_WIDTH}px must be restored as-is — no combobox floor applies to it`
      ).toBeLessThanOrEqual(2);
    });

    // Restore, so this test never leaks a below-floor stored width into another run sharing the
    // same browser profile/storage state.
    await page.evaluate((key) => localStorage.removeItem(key), storageKey);
  });
});
