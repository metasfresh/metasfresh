import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';
import { SLOW_ACTION_TIMEOUT } from '../utils/common';
import { SALES_ORDER_WINDOW_ID } from '../utils/WindowIds';
import { waitForTabAllowsNew, getRecordData, getTabRows } from '../utils/WebAPIValidation';

/**
 * Sales Order window (143) — a Menge (quantity) edited from 4 to 5 purely by keyboard in the
 * order-line grid, after the lines were added via batch entry, must survive order completion.
 *
 * Regression guard: a keyboard-only edit of an order-line Menge in the grid (product added via
 * batch entry, then batch entry closed, then Menge edited in the grid using Tab/Arrow navigation
 * and typed digits — no mouse/click anywhere in the edit itself) must be PATCHed and must persist
 * through document completion, a page reload, and the WebAPI read-back.
 *
 * Flow:
 * 1. Create the order and select the customer (Alt+N, then mouse pick — matches
 *    SalesOrderPage.clickNew / selectCustomer, already used across this suite).
 * 2. Batch entry (Alt+Q): add 3 lines, each qty 4 (product field filled, Menge typed
 *    by keyboard).
 * 3. Close batch entry (Alt+Q) — focus leaves the order lines.
 * 4. Edit every line's Menge to 5, keyboard only (Tab/Arrow navigation, type, Tab).
 * 5. Complete the order by keyboard (Alt+U).
 * Expected: every line keeps Menge 5 — in the grid after completion, after a reload,
 * and as persisted QtyEntered read back from the WebAPI; DocStatus is CO.
 *
 * Measured locally 2026-09-23 with webui.quickinput.EnablePackingInstructionsField=Y (this
 * window's batch entry then shows a Packvorschrift field before Menge) — this local DB's
 * column set is NOT assumed identical elsewhere (e.g. core CI's preloaded image), so the
 * grid Tab-navigation below does not hardcode a Tab count; it Tabs one key at a time and
 * checks focus after each press (see tabToFirstRowQtyCell):
 * - the order-line grid carries several columns before Menge: Zeile Nr., Produkt,
 *   Verfügbare Menge, Verfügbar, Merkmale, Gebindemenge, Zusagbar (ATP), Packvorschrift,
 *   THEN Menge — 9 Tabs from <body> reached row 0's cell-QtyEntered on this local DB.
 * - in batch entry, pressing Enter on the resolved product moves focus into the
 *   (empty) Packvorschrift field (M_HU_PI_Item_Product_ID) rather than straight to
 *   Menge; Tab is the key that leaves that empty field and lands on Menge (same
 *   escape as tests/spec/quick-input.spec.js TEST 9's "Tab — the pre-existing escape
 *   that already worked").
 * - after Alt+Q closes batch entry, focus falls to <body>, and Tabbing from there
 *   reaches row 0's cell-QtyEntered by walking through: Line, M_Product_ID,
 *   QtyAvailableForSales, InsufficientQtyAvailableForSalesColor_ID,
 *   M_AttributeSetInstance_ID, QtyEnteredTU, Qty_AvailableToPromise,
 *   M_HU_PI_Item_Product_ID, QtyEntered (locally; the exact set/order may differ in
 *   another environment, which is why the count itself is not hardcoded).
 *
 * Keyboard shortcuts: metasfresh/frontend/src/shortcuts/keymap.js
 * (Alt+N NEW_DOCUMENT, Alt+Q TOGGLE_QUICK_INPUT, Alt+U COMPLETE_STATUS).
 * Grid navigation: metasfresh/frontend/src/components/table/Table.js handleKeyDown
 * (ArrowUp/Down select a row keeping the column, ArrowLeft/Right move between cells)
 * and TableRow.handleKeyDown (a digit on a focused cell opens the editor, Tab leaves it
 * and PATCHes).
 */

const ORDER_LINE_TAB_ID = 'AD_Tab-187';

const PRODUCT_KEYS = ['P1', 'P2', 'P3'];
const INITIAL_QTY = '4';
const EDITED_QTY = '5';
// Safety cap for tabToFirstRowQtyCell below — locally this window's grid needed 9 Tabs (see
// file header), but the exact column set is not assumed stable across environments, so the
// loop checks focus after every Tab instead of hardcoding a count; this only bounds the
// give-up point.
const MAX_TABS_TO_FIRST_ROW_QTY_CELL = 20;

// ---------------------------------------------------------------------------
// Setup helpers
// ---------------------------------------------------------------------------

async function createMasterdata(language) {
  const products = {};
  PRODUCT_KEYS.forEach((key) => {
    products[key] = {
      name: `QtyEdit${key}`,
      type: 'Item',
      prices: [{ price: 10.0, currencyCode: 'EUR' }],
    };
  });
  return await Backend.createMasterdata({
    request: {
      login: { user: { language, firstname: 'QtyEdit', lastname: 'Test' } },
      bpartners: {
        CUSTOMER1: {
          isVendor: false,
          isCustomer: true,
          isSoPriceList: true,
          name: 'Customer',
        },
      },
      products,
    },
  });
}

async function pollRecord(windowId, recordId, predicate, description) {
  for (let i = 0; i < 20; i++) {
    const record = await getRecordData(windowId, recordId);
    if (predicate(record)) return record;
    await new Promise((r) => setTimeout(r, 1000));
  }
  throw new Error(`Timeout waiting for: ${description}`);
}

async function savedQtys(recordId) {
  const rows = await getTabRows(SALES_ORDER_WINDOW_ID, recordId, ORDER_LINE_TAB_ID);
  return rows.map((row) => Number(row.fieldsByName?.QtyEntered?.value));
}

function parseQty(cellText) {
  // language-independent: strip thousands/space, decimal comma -> dot
  return parseFloat(
    cellText.replace(/\s/g, '').replace(',', '.').replace(/[^0-9.]/g, '')
  );
}

const gridRows = (page) => page.locator('.table-flex-wrapper table tbody tr');
const qtyCellOfRow = (page, idx) =>
  gridRows(page).nth(idx).locator('[data-cy="cell-QtyEntered"]').first();

async function gridQtys(page) {
  const count = await gridRows(page).count();
  const qtys = [];
  for (let i = 0; i < count; i++) {
    qtys.push(parseQty((await qtyCellOfRow(page, i).innerText()).trim()));
  }
  return qtys;
}

async function activeCellInfo(page) {
  return await page.evaluate(() => {
    const e = document.activeElement;
    const td = e?.closest('td');
    const tr = e?.closest('tr');
    return {
      tag: e?.tagName,
      cell: td?.getAttribute('data-cy') || null,
      rowIdx: tr ? Array.from(tr.parentElement.children).indexOf(tr) : null,
      rowSelected: tr ? tr.classList.contains('row-selected') : null,
      inQuickInput: !!e?.closest('.quick-input-container'),
      lookup: e?.closest('[id^="lookup_"]')?.id || null,
      value: e?.value,
    };
  });
}

async function expectFocus(page, predicate, description) {
  await expect
    .poll(async () => predicate(await activeCellInfo(page)), {
      message: `focus: ${description}`,
      timeout: SLOW_ACTION_TIMEOUT,
    })
    .toBe(true);
}

// ---------------------------------------------------------------------------
// Order header + batch entry
// ---------------------------------------------------------------------------

/**
 * Navigate to Sales Order window, create new (Alt+N via SalesOrderPage.clickNew), pick the
 * customer (mouse click via SalesOrderPage.selectCustomer — the same reused pattern as
 * tests/spec/quick-input.spec.js), open the Order Line tab.
 * Returns recordId.
 */
async function createOrderAndOpenLinesTab(page, masterdata) {
  await SalesOrderPage.goto();
  await SalesOrderPage.clickNew();
  const recordId = await SalesOrderPage.selectCustomer(
    masterdata.bpartners.CUSTOMER1.bpartnerCode
  );
  await SalesOrderPage.goToOrderLineTab();
  await waitForTabAllowsNew(SALES_ORDER_WINDOW_ID, recordId, ORDER_LINE_TAB_ID, {
    maxRetries: 15,
    retryDelayMs: 1000,
  });
  return recordId;
}

/**
 * Open batch entry (Alt+Q) and add PRODUCT_KEYS.length lines, each with the given qty.
 * Product code is filled (click + spinner wait + pauses, per
 * tests/spec/quick-input.spec.js typeProductAndWaitForDropdown), Enter resolves it.
 *
 * With webui.quickinput.EnablePackingInstructionsField=Y, Enter on the resolved product lands
 * focus on the empty Packvorschrift field, not on Menge; Tab is the key that leaves that empty
 * field (same escape as tests/spec/quick-input.spec.js TEST 9's Tab control). Menge is then
 * typed by keyboard only (never clicked) and Enter adds the line. Batch entry is left OPEN
 * (closed by the caller).
 *
 * Written inline rather than via SalesOrderPage.openQuickEntryAndSelectProduct /
 * submitQuickEntryLine: those helpers open batch entry with a mouse click on the toggle
 * button (not Alt+Q) and fill Menge with .fill() (not keyboard.type), so they leave a
 * different post-close focus target than the Alt+Q path this test measured, which is where
 * tabToFirstRowQtyCell starts; they also don't loop several products or skip the Packvorschrift
 * field the way this test needs.
 */
async function addLinesViaBatchEntry(page, masterdata, qty) {
  await page.keyboard.press('Alt+Q'); // TOGGLE_QUICK_INPUT
  const quickInput = page.locator('.quick-input-container');
  await quickInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

  for (const [i, key] of PRODUCT_KEYS.entries()) {
    const productCode = masterdata.products[key].productCode;
    const productInput = quickInput.locator('#lookup_M_Product_ID input.input-field');
    await productInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await productInput.click();
    await page
      .locator('#lookup_M_Product_ID .rotating, #lookup_M_Product_ID .spinner')
      .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
      .catch(() => {});
    await page.waitForTimeout(300);
    await productInput.fill(productCode);
    await page.waitForTimeout(500);
    await page
      .locator('#lookup_M_Product_ID .rotating, #lookup_M_Product_ID .spinner')
      .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
      .catch(() => {});
    await page.waitForTimeout(300);

    await page.keyboard.press('Enter'); // resolve product, focus -> empty Packvorschrift field
    await page.waitForTimeout(1000);
    expect(await productInput.inputValue(), `product ${key} resolved`).toBeTruthy();

    await page.keyboard.press('Tab'); // leaves the empty Packvorschrift field, focus -> Menge
    await page.waitForTimeout(300);

    await page.keyboard.type(qty, { delay: 30 }); // Menge: keyboard only, never clicked
    await page.keyboard.press('Enter'); // add the line, focus back to product
    await page.waitForTimeout(2000);
    await expect(gridRows(page)).toHaveCount(i + 1, { timeout: SLOW_ACTION_TIMEOUT });
  }
}

/**
 * Close batch entry (Alt+Q) and assert focus left the order lines
 * (not inside quick input, not on any grid cell).
 */
async function closeBatchEntryAndAssertFocusOutsideLines(page) {
  await page.keyboard.press('Alt+Q'); // TOGGLE_QUICK_INPUT (close)
  await page
    .locator('.quick-input-container')
    .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
  // Asserts that focus left the order-line grid: after Alt+Q the quick-input
  // container detaches (its focused input goes with it) and the browser falls
  // back to a non-grid focus target (<body> — measured 2026-09-23), so the Tabs
  // below start navigating the grid from outside it, not mid-row.
  await expectFocus(
    page,
    (f) => !f.inQuickInput && f.cell === null,
    'focus left the order lines after closing batch entry'
  );
}

// ---------------------------------------------------------------------------
// Keyboard-only grid edit + completion
// ---------------------------------------------------------------------------

/**
 * From the closed batch entry (focus on <body>): press Tab one key at a time, checking focus
 * after each press, until row 0's Menge cell (cell-QtyEntered) is reached — this window has
 * several grid columns before Menge (see file header) and the exact column set/order is not
 * assumed stable across environments, so no fixed Tab count is hardcoded here. Gives up after
 * MAX_TABS_TO_FIRST_ROW_QTY_CELL Tabs with a failure naming the last focused cell.
 */
async function tabToFirstRowQtyCell(page) {
  for (let i = 0; i < MAX_TABS_TO_FIRST_ROW_QTY_CELL; i++) {
    await page.keyboard.press('Tab');
    const info = await activeCellInfo(page);
    if (info.cell === 'cell-QtyEntered' && info.rowIdx === 0) {
      return;
    }
  }
  const info = await activeCellInfo(page);
  throw new Error(
    `Tab did not reach row 0's cell-QtyEntered within ${MAX_TABS_TO_FIRST_ROW_QTY_CELL} Tabs; ` +
      `last focused cell: ${JSON.stringify(info)}`
  );
}

/**
 * tabToFirstRowQtyCell reaches row 0's Menge cell, then ArrowDown+ArrowUp select row 0 (keeping
 * the Menge column), then per row: type qty, Tab (leaves the cell -> PATCH fires), ArrowLeft
 * (back to Menge), ArrowDown (next row, same column).
 * No waitForResponse on the QtyEntered PATCH anywhere in this function — only loading
 * indicators / fixed pauses. QtyEntered PATCH requests are recorded passively.
 */
async function editQtysKeyboardOnly(page, qty) {
  const patches = [];
  const onRequest = (req) => {
    if (
      req.method() === 'PATCH' &&
      req.url().includes(`/window/${SALES_ORDER_WINDOW_ID}/`) &&
      (req.postData() || '').includes('QtyEntered')
    ) {
      patches.push(req.postData());
    }
  };
  page.on('request', onRequest);
  try {
    await tabToFirstRowQtyCell(page); // returns only once row 0's Menge cell is focused

    // select row 0 by keyboard (Tab only focuses the cell, it does not select the row)
    await page.keyboard.press('ArrowDown');
    await expectFocus(page, (f) => f.cell === 'cell-QtyEntered' && f.rowIdx === 1 && f.rowSelected, 'row 1 Menge cell, row selected');
    await page.keyboard.press('ArrowUp');
    await expectFocus(page, (f) => f.cell === 'cell-QtyEntered' && f.rowIdx === 0 && f.rowSelected, 'row 0 Menge cell, row selected');

    for (let i = 0; i < PRODUCT_KEYS.length; i++) {
      await expectFocus(
        page,
        (f) => f.tag === 'TD' && f.cell === 'cell-QtyEntered' && f.rowIdx === i && f.rowSelected,
        `row ${i} Menge cell focused, row selected`
      );
      await page.keyboard.type(qty, { delay: 80 });
      await page.waitForTimeout(500);
      const typed = (await activeCellInfo(page)).value;
      // soft: report every row, so the post-completion symptom is also observed
      expect.soft(typed, `row ${i}: typed value in the cell input`).toBe(qty);

      await page.keyboard.press('Tab'); // leave the cell -> QtyEntered PATCH fires
      await page
        .locator('.rotating, .indicator-pending')
        .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
        .catch(() => {});
      await page.waitForTimeout(800);

      if (i < PRODUCT_KEYS.length - 1) {
        await page.keyboard.press('ArrowLeft'); // back to the Menge cell
        await page.waitForTimeout(300);
        await page.keyboard.press('ArrowDown'); // next row, same column
        await page.waitForTimeout(800);
      }
    }
  } finally {
    page.off('request', onRequest);
  }
  console.log(`[keyboard-only] QtyEntered PATCH(es) recorded during grid edit: ${patches.length}`);
}

/**
 * Complete the order via Alt+U — keyboard only. NOT SalesOrderPage.complete(), which clicks
 * the status button and the "status-CO" option (this scenario requires the completion step to
 * stay keyboard-only, matching every other step above).
 */
async function completeOrderKeyboardOnly(page, recordId) {
  // Alt+U = COMPLETE_STATUS (DocumentStatusContextShortcuts -> ActionButton.documentCompleteStatus)
  await page.keyboard.press('Alt+U');
  await pollRecord(
    SALES_ORDER_WINDOW_ID,
    recordId,
    (r) => r.fieldsByName?.DocStatus?.value?.key === 'CO',
    `order ${recordId} completed via Alt+U`
  );
}

// ---------------------------------------------------------------------------
// Test
// ---------------------------------------------------------------------------

test.describe('Sales order — keyboard-only Menge edit after batch entry', () => {
  test('batch entry Menge 4 -> keyboard grid edit to 5 -> Alt+U keeps 5', async ({ page }) => {
    allure.epic('E0100: Sales');
    allure.tag('F00100: Sales Order');
    allure.tag('F00100');
    allure.story('Keyboard-only Menge edit after batch entry survives completion');
    test.setTimeout(240000);

    const masterdata = await createMasterdata('de_DE');
    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    const recordId = await createOrderAndOpenLinesTab(page, masterdata);
    console.log(`[keyboard-only] order ${recordId}`);

    await test.step('batch entry: add 3 lines with Menge 4', async () => {
      await addLinesViaBatchEntry(page, masterdata, INITIAL_QTY);
      expect(await gridQtys(page)).toEqual([4, 4, 4]);
      expect(await savedQtys(recordId)).toEqual([4, 4, 4]);
    });

    await test.step('close batch entry, focus leaves the order lines', async () => {
      await closeBatchEntryAndAssertFocusOutsideLines(page);
    });

    await test.step('grid edit by keyboard: each Menge to 5, leave by Tab', async () => {
      await editQtysKeyboardOnly(page, EDITED_QTY);
    });

    await test.step('complete the order by keyboard (Alt+U)', async () => {
      await completeOrderKeyboardOnly(page, recordId);
    });

    await test.step('grid, and after reload grid + persisted QtyEntered, are 5', async () => {
      expect(await gridQtys(page)).toEqual([5, 5, 5]);
      await page.reload();
      await page
        .getByTestId('status-button')
        .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await expect(gridRows(page)).toHaveCount(PRODUCT_KEYS.length, {
        timeout: SLOW_ACTION_TIMEOUT,
      });
      expect(await gridQtys(page)).toEqual([5, 5, 5]);
      expect(await savedQtys(recordId)).toEqual([5, 5, 5]);
      const record = await getRecordData(SALES_ORDER_WINDOW_ID, recordId);
      expect(record.fieldsByName?.DocStatus?.value?.key).toBe('CO');
    });
  });
});
