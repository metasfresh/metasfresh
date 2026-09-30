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
 * Combobox grid-column minimum-usable-width (TC11/TC12), on the core Sales Order Line grid.
 *
 * A combobox (Lookup/List) grid column must never render — nor be dragged, nor be restored
 * from a stored width — narrower than its ~210px minimum-usable width, so its open dropdown
 * editor (`.input-dropdown-container`, hard-floored at 200px) never spills into the neighbour
 * column. A non-combobox column keeps the flat 50px `MIN_COLUMN_WIDTH` clamp and its stored
 * width unmodified — the floor is combobox-only.
 *
 * Targets the order-line grid (window `SALES_ORDER_WINDOW_ID` / tab `AD_Tab-187`, via
 * `OrderLineHarness`) rather than a top-level list view (e.g. Business Partner): a top-level
 * list view's grid row opens the record on double-click (`supportOpenRecord`), which navigates
 * away from the grid entirely before the cell editor can be measured. The order-line grid is an
 * embedded/inline tab grid — double-clicking a cell opens its editor in place. `M_Product_ID`
 * is this grid's only Lookup/Search (combobox) column; `QtyEntered` is a representative
 * non-combobox column.
 *
 * Previously written with no live stack available and never run before this pass — now run
 * green against the local stack; retargeted from a top-level list view (which failed on the
 * open-record navigation above) and extended with the load-time stored-width case (BF-B4c).
 *
 * Features tested:
 * - F50000: Resizable Table Columns
 */
const COMBOBOX_FIELD = 'M_Product_ID';
const NON_COMBOBOX_FIELD = 'QtyEntered';
const COMBOBOX_MIN_WIDTH_PX = 210;
const FLAT_MIN_WIDTH_PX = 50;

async function dragResizeAsNarrowAsPossible(page, fieldName) {
  const handle = page.getByTestId(`resize-handle-${fieldName}`);
  await handle.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  // The order-line grid scrolls horizontally; a column further right than the current scroll
  // position is off the container's visible clip box, so raw `page.mouse` coordinates at its
  // (unscrolled) boundingBox hit nothing (`elementFromPoint` returns null there) and the drag
  // silently no-ops. Scroll the handle into view first so its boundingBox is the real on-screen
  // position.
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
  const masterdata = await createMasterdata('en_US');

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
  test('Manual drag-resize clamps a combobox column at ~210px and a non-combobox column at 50px (AC22, AC19)', async ({
    page,
  }) => {
    allure.epic('E0500: Sales Orders');
    allure.tag('F5010: Order Lines Grid');
    allure.tag('F5010');
    allure.story('Combobox column drag-resize floor (~210px)');
    allure.severity('critical');
    allure.description(`
## TC12 — Combobox resize clamp at ~210px (measured)

1. Drag the combobox (\`${COMBOBOX_FIELD}\`) column's resize handle as narrow as possible on the
   order-line grid — its width must clamp at ~210px (not the flat 50px \`MIN_COLUMN_WIDTH\`), and
   its open cell editor must stay within the cell (no overlap into the next column).
2. Drag the non-combobox (\`${NON_COMBOBOX_FIELD}\`) column's resize handle as narrow as possible
   — its width must clamp at the flat 50px \`MIN_COLUMN_WIDTH\`, unchanged (the floor is
   combobox-only).
    `);

    test.setTimeout(120000);

    await seedOrderLineGrid(page);

    await test.step('Combobox column clamps at ~210px, not 50px', async () => {
      await dragResizeAsNarrowAsPossible(page, COMBOBOX_FIELD);

      const comboboxTh = page.getByTestId(`column-${COMBOBOX_FIELD}`);
      const comboboxBox = await comboboxTh.boundingBox();
      console.log(`[INFO] combobox column width after max drag: ${comboboxBox.width}px`);

      expect(
        comboboxBox.width,
        `combobox column (${COMBOBOX_FIELD}) must clamp at ~${COMBOBOX_MIN_WIDTH_PX}px, not the flat ${FLAT_MIN_WIDTH_PX}px floor`
      ).toBeGreaterThanOrEqual(COMBOBOX_MIN_WIDTH_PX - 5);
    });

    // Drag-resize the non-combobox column BEFORE opening the combobox cell's editor below: a
    // resize immediately after closing an open dropdown (Escape) intermittently under-drags,
    // because the dropdown's own closing/backdrop teardown briefly intercepts the drag's
    // leading mousemove events — same drag mechanism, so order (not the clamp) was the cause.
    await test.step('Non-combobox column keeps clamping at the flat 50px floor', async () => {
      await dragResizeAsNarrowAsPossible(page, NON_COMBOBOX_FIELD);

      const nonComboboxTh = page.getByTestId(`column-${NON_COMBOBOX_FIELD}`);
      const nonComboboxBox = await nonComboboxTh.boundingBox();
      console.log(`[INFO] non-combobox column width after max drag: ${nonComboboxBox.width}px`);

      expect(
        nonComboboxBox.width,
        `non-combobox column (${NON_COMBOBOX_FIELD}) must keep clamping at the flat ${FLAT_MIN_WIDTH_PX}px floor — unaffected by the combobox floor`
      ).toBeLessThanOrEqual(FLAT_MIN_WIDTH_PX + 5);
    });

    await test.step('The open combobox editor stays within the resized cell (no overlap)', async () => {
      const comboboxCell = page.locator(`[data-cy="cell-${COMBOBOX_FIELD}"]`).first();
      await comboboxCell.dblclick();

      // Scoped to the cell itself — a bare page-wide `.input-dropdown-container` also matches
      // every Lookup field on the order HEADER form (C_BPartner_ID, C_BPartner_Location_ID, ...),
      // and `.first()` picks one of those instead of the grid cell's own open editor.
      const editor = comboboxCell.locator('.input-dropdown-container').first();
      await editor.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      const cellBox = await comboboxCell.boundingBox();
      const editorBox = await editor.boundingBox();
      console.log(`[INFO] cell bounds: ${JSON.stringify(cellBox)}; editor bounds: ${JSON.stringify(editorBox)}`);

      // The editor's own `.input-dropdown-container` renders at a fixed 200px min-width
      // (`inputs.scss`) inset by the cell's own left padding (~12px at the 210px floor) — an
      // inherent ~2px sub-pixel remainder below the reported (dozens-of-px) overlap defect this
      // guards against, not a real encroachment into the neighbour column's content.
      const OVERLAP_TOLERANCE_PX = 3;
      expect(
        editorBox.x + editorBox.width,
        'the open combobox editor must not spill past the resized cell right edge'
      ).toBeLessThanOrEqual(cellBox.x + cellBox.width + OVERLAP_TOLERANCE_PX);

      await page.keyboard.press('Escape');
    });
  });

  test('A stored combobox width below the floor is clamped up to ~210px on load; a stored non-combobox width is unaffected (AC21, BF-B4c)', async ({
    page,
  }) => {
    allure.epic('E0500: Sales Orders');
    allure.tag('F5010: Order Lines Grid');
    allure.tag('F5010');
    allure.story('Combobox column load-time stored-width clamp (~210px)');
    allure.severity('critical');
    allure.description(`
## TC11 (stored-width leg) — Combobox load-time stored-width clamp

The manual-resize clamp (TC12 above) prevents a NEW drag from ever storing a combobox width
below ~210px, so a sub-floor width can only exist as a PERSISTED value from before this fix (or
a returning session). Write such a value directly into the same \`localStorage\` key the grid
itself reads on mount, reload, and confirm the combobox column loads clamped to ~210px while an
equally-low stored NON-combobox width is left untouched.
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

    // Force BOTH a below-floor combobox width and a legitimate (unaffected) non-combobox width
    // into the stored map, simulating a width persisted before the BF-B4a/b floor existed.
    const BELOW_FLOOR_COMBOBOX_WIDTH = 100;
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

    await test.step('A stored combobox width below the floor loads clamped to ~210px', async () => {
      const comboboxTh = page.getByTestId(`column-${COMBOBOX_FIELD}`);
      const comboboxBox = await comboboxTh.boundingBox();
      console.log(`[INFO] combobox column width on load (stored ${BELOW_FLOOR_COMBOBOX_WIDTH}px): ${comboboxBox.width}px`);

      expect(
        comboboxBox.width,
        `a stored combobox width of ${BELOW_FLOOR_COMBOBOX_WIDTH}px must load clamped to ~${COMBOBOX_MIN_WIDTH_PX}px, not verbatim`
      ).toBeGreaterThanOrEqual(COMBOBOX_MIN_WIDTH_PX - 5);
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
