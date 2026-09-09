import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { FRONTEND_BASE_URL } from '../utils/common';

/**
 * "Sprung zu Prognose" overlay — the Menge column.
 *
 * The action on a Material Cockpit v2 row opens an overlay listing the forecast documents that have a
 * line for that row's product / warehouse / organisation / attribute-set instance. A forecast document
 * can hold lines for several products, so the quantity shown must be the sum of that document's lines
 * for the COCKPIT product only — never the document's multi-product total.
 *
 * The scenario builds exactly that trap: one forecast document carrying a line for the cockpit product
 * AND a line for a second product with a much larger quantity. A regression that sums the whole document
 * shows the combined figure, which this spec fails on.
 *
 * The overlay is an ordinary grid over the aggregate view M_Forecast_ProductQty_V, so the assertions read
 * individual grid CELLS by their column name (`data-cy="cell-<ColumnName>"`) — never the row's text. The
 * row also carries the forecast's name, which contains a timestamp, and a whole-row text match would both
 * pass for the wrong reason and flake whenever those digits happened to spell the forbidden total.
 */

const FORECAST_WINDOW_ID = 328;
// The included-tab segment of a document path is a DetailId, i.e. "AD_Tab-<id>" — a bare tab id makes
// DetailId.fromJson throw and the request answers 500.
const FORECAST_LINES_DETAIL_ID = 'AD_Tab-654';
const MATERIAL_COCKPIT_V2_WINDOW_ID = 541963;

// Shared with forecast-generator.spec.js: the standard warehouse and the caption its lookup renders.
const WAREHOUSE_ID = 540008;
const WAREHOUSE_CAPTION = 'Hauptlager';

// The stock UOM the seeded products get. A forecast line needs it set explicitly: the line's QtyTU
// callout (HUPackingAwareBL.calculateQtyTU) asserts C_UOM_ID > 0 and answers 500 otherwise.
const UOM_ID = 100;
const UOM_CAPTION = 'PCE';

const QTY_COCKPIT_PRODUCT = 7;
const QTY_OTHER_PRODUCT = 900;
const QTY_DOCUMENT_TOTAL = QTY_COCKPIT_PRODUCT + QTY_OTHER_PRODUCT;

/** Poll the WebAPI until a record reports validStatus.valid — an invalid record silently does not save. */
async function waitForRecordValid(page, windowId, recordId, { timeout = 20000 } = {}) {
  const start = Date.now();
  let last = null;
  while (Date.now() - start < timeout) {
    last = await page.evaluate(
      async ({ windowId, recordId }) => {
        const resp = await fetch(`/rest/api/window/${windowId}/${recordId}`, {
          credentials: 'include',
          headers: { Accept: 'application/json' },
        });
        if (!resp.ok) return { valid: false, reason: `HTTP ${resp.status}` };
        const data = await resp.json();
        return data[0]?.validStatus || { valid: false, reason: 'no validStatus' };
      },
      { windowId, recordId }
    );
    if (last?.valid) return last;
    await page.waitForTimeout(500);
  }
  throw new Error(`Record ${windowId}/${recordId} never became valid: ${JSON.stringify(last)}`);
}

/**
 * Set one field via the WebAPI PATCH endpoint.
 *
 * A lookup field takes a JSONLookupValue — `{ key: <numeric id>, caption: <text> }`. The key must be a
 * NUMBER: passing it as a string makes the server throw and the PATCH answers 500.
 */
async function patchFields(page, path, fields) {
  return page.evaluate(
    async ({ path, fields }) => {
      const resp = await fetch(`/rest/api/window/${path}`, {
        method: 'PATCH',
        credentials: 'include',
        headers: { 'Content-Type': 'application/json', Accept: 'application/json' },
        body: JSON.stringify(fields.map(([field, value]) => ({ op: 'replace', path: field, value }))),
      });
      if (!resp.ok) {
        throw new Error(`PATCH ${fields.map(([f]) => f).join(',')} failed: ${resp.status} ${await resp.text()}`);
      }
      return resp.json();
    },
    { path, fields }
  );
}

const patchField = (page, path, fieldName, value) => patchFields(page, path, [[fieldName, value]]);

/**
 * Create a row in an included tab AND fill it, in a single request.
 *
 * Creating first and PATCHing afterwards is unreliable: the new row is not saved yet, and addressing it
 * from a second request intermittently answers 404 DocumentNotFoundException. Sending the field ops in
 * the NEW request body avoids that window entirely.
 *
 * The header must already be valid — an included row cannot be created under an invalid document
 * ("Cannot create included document because it's not allowed"). Note the response shape: it is
 * `{ documents: [ … ] }` (the plain document GET answers a bare array), and for an included row the entry
 * carries the ROOT document's id in `id` and the new row's id in `rowId`.
 */
async function createIncludedRecord(page, windowId, recordId, detailId, fields) {
  const path = `${windowId}/${recordId}/${detailId}`;
  const rowId = await page.evaluate(async ({ path, detailId, fields }) => {
    const resp = await fetch(`/rest/api/window/${path}/NEW`, {
      method: 'PATCH',
      credentials: 'include',
      headers: { 'Content-Type': 'application/json', Accept: 'application/json' },
      body: JSON.stringify(fields.map(([field, value]) => ({ op: 'replace', path: field, value }))),
    });
    if (!resp.ok) throw new Error(`create ${path} failed: ${resp.status} ${await resp.text()}`);
    const data = await resp.json();
    const documents = Array.isArray(data) ? data : data.documents;
    return documents?.find((doc) => doc.tabId === detailId)?.rowId;
  }, { path, detailId, fields });
  if (!rowId) throw new Error(`create ${path} returned no rowId for tab ${detailId}`);
  return rowId;
}

test.describe('Forecast overlay — Menge is scoped to the cockpit product', () => {
  test('Sprung zu Prognose shows only the cockpit product\'s forecast quantity', async ({ page }) => {
    allure.epic('E0300: Planning');
    allure.tag('F19042');
    allure.story('Sprung zu Prognose: per-product Menge in the forecast overlay');
    allure.severity('critical');
    allure.description(`
### Scenario
1. Seed two products.
2. Create one forecast document holding a line for BOTH products —
   ${QTY_COCKPIT_PRODUCT} of the cockpit product and ${QTY_OTHER_PRODUCT} of the other.
3. Open Material Cockpit v2 and select the row of the cockpit product.
4. Invoke **Sprung zu Prognose**.
5. The overlay lists the forecast document, and its **Menge** cell reads ${QTY_COCKPIT_PRODUCT} —
   the cockpit product's line only, NOT ${QTY_DOCUMENT_TOTAL}.
6. The **Prognose** column still zooms into the forecast document.

### Why it matters
A forecast document mixes products. Showing the document total would tell the planner a quantity that
has nothing to do with the product they jumped from.
    `);

    test.setTimeout(180000);

    // --- Seed masterdata -----------------------------------------------------------------------
    // The warehouse is the standard one rather than a seeded one: the forecast header's warehouse
    // lookup needs a caption, and reusing the known pair keeps the PATCH payload honest.
    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: { language: 'de_DE', firstname: 'forecast', lastname: 'overlay' } },
        products: {
          cockpitProduct: { name: 'ForecastOverlayQty-Cockpit' },
          otherProduct: { name: 'ForecastOverlayQty-Other' },
        },
      },
    });
    allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');

    const cockpitProductId = masterdata.products.cockpitProduct.id;
    const otherProductId = masterdata.products.otherProduct.id;
    const cockpitProductName = masterdata.products.cockpitProduct.productName || 'ForecastOverlayQty-Cockpit';
    const otherProductName = masterdata.products.otherProduct.productName || 'ForecastOverlayQty-Other';

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    // --- Create the forecast document with a line for each product -----------------------------
    const forecastName = `ForecastOverlayQty-${Date.now()}`;
    await test.step('create forecast with two products on it', async () => {
      // Create the header through the UI (the suite's proven path — a REST-only create races the
      // session and 401s), then fill the fields over the now-authenticated session.
      await page.goto(`${FRONTEND_BASE_URL}/window/${FORECAST_WINDOW_ID}/NEW`);
      await page.waitForURL(/\/window\/\d+\/\d+/, { timeout: 30000 });
      await page.locator('.rotating, .indicator-pending').waitFor({ state: 'detached', timeout: 30000 }).catch(() => {});
      const id = page.url().split('/').pop().split('?')[0];
      await patchField(page, `${FORECAST_WINDOW_ID}/${id}`, 'Name', forecastName);
      await patchField(page, `${FORECAST_WINDOW_ID}/${id}`, 'M_Warehouse_ID', { key: WAREHOUSE_ID, caption: WAREHOUSE_CAPTION });
      await patchField(page, `${FORECAST_WINDOW_ID}/${id}`, 'DatePromised', new Date().toISOString().slice(0, 10));
      await waitForRecordValid(page, FORECAST_WINDOW_ID, id);

      for (const [productId, productName, qty] of [
        [cockpitProductId, cockpitProductName, QTY_COCKPIT_PRODUCT],
        [otherProductId, otherProductName, QTY_OTHER_PRODUCT],
      ]) {
        await createIncludedRecord(page, FORECAST_WINDOW_ID, id, FORECAST_LINES_DETAIL_ID, [
          ['M_Product_ID', { key: productId, caption: productName }],
          ['C_UOM_ID', { key: UOM_ID, caption: UOM_CAPTION }],
          ['Qty', qty],
        ]);
      }
    });

    // --- Find the cockpit row for our product --------------------------------------------------
    // The Material Cockpit v2 view is fed by material-dispo, which processes the forecast lines
    // asynchronously — poll until the row for the cockpit product appears.
    const { viewId, rowId } = await test.step('wait for the Material Cockpit v2 row', async () => {
      const deadline = Date.now() + 90000;
      while (Date.now() < deadline) {
        // Creating a view answers only viewId + size; the rows come from a second GET.
        const view = await page.evaluate(async ({ windowId, productValue }) => {
          const body = { windowId: String(windowId), viewType: 'grid' };
          if (productValue) {
            body.filters = [
              { filterId: 'default', parameters: [{ parameterName: 'ProductValue', value: productValue }] },
            ];
          }
          const created = await fetch(`/rest/api/documentView/${windowId}`, {
            method: 'POST',
            credentials: 'include',
            headers: { 'Content-Type': 'application/json', Accept: 'application/json' },
            body: JSON.stringify(body),
          });
          if (!created.ok) return null;
          const { viewId } = await created.json();
          const rows = await fetch(
            `/rest/api/documentView/${windowId}/${viewId}?firstRow=0&pageLength=200`,
            { credentials: 'include', headers: { Accept: 'application/json' } }
          );
          if (!rows.ok) return null;
          return rows.json();
        }, { windowId: MATERIAL_COCKPIT_V2_WINDOW_ID, productValue: null });

        const row = view?.result?.find(
          (r) => String(r.fieldsByName?.M_Product_ID?.value?.key) === String(cockpitProductId)
        );
        if (row) {
          // Re-create the view filtered to this product. The unfiltered cockpit accumulates rows and
          // pages them, so the row we want is not reliably on page 1 of the grid the browser renders;
          // filtered, it is the only row there.
          const productValue = row.fieldsByName?.ProductValue?.value;
          const filtered = await page.evaluate(async ({ windowId, productValue }) => {
            const created = await fetch(`/rest/api/documentView/${windowId}`, {
              method: 'POST',
              credentials: 'include',
              headers: { 'Content-Type': 'application/json', Accept: 'application/json' },
              body: JSON.stringify({
                windowId: String(windowId),
                viewType: 'grid',
                filters: [
                  { filterId: 'default', parameters: [{ parameterName: 'ProductValue', value: productValue }] },
                ],
              }),
            });
            if (!created.ok) throw new Error(`filtered view failed: ${created.status}`);
            const { viewId } = await created.json();
            const rows = await fetch(
              `/rest/api/documentView/${windowId}/${viewId}?firstRow=0&pageLength=200`,
              { credentials: 'include', headers: { Accept: 'application/json' } }
            );
            if (!rows.ok) throw new Error(`filtered rows failed: ${rows.status}`);
            return rows.json();
          }, { windowId: MATERIAL_COCKPIT_V2_WINDOW_ID, productValue });

          const filteredRow = filtered.result?.find(
            (r) => String(r.fieldsByName?.M_Product_ID?.value?.key) === String(cockpitProductId)
          );
          if (!filteredRow) throw new Error(`product ${productValue} vanished from its own filtered view`);
          return { viewId: filtered.viewId, rowId: filteredRow.id };
        }
        await page.waitForTimeout(3000);
      }
      throw new Error(`No Material Cockpit v2 row appeared for product ${cockpitProductId}`);
    });

    // --- Drive the UI: select the row and invoke the action ------------------------------------
    await page.goto(`${FRONTEND_BASE_URL}/window/${MATERIAL_COCKPIT_V2_WINDOW_ID}?viewId=${viewId}`);
    // The grid marks each row with data-testid="table-row-<rowId>" (TableRow.js) — there is no data-id.
    const cockpitRow = page.locator(`tbody tr[data-testid="table-row-${rowId}"]`);
    await cockpitRow.waitFor({ state: 'visible', timeout: 30000 });
    // The action is offered only for a selected row ("Sprung zu Prognose (Keine Zeilen ausgewählt)"),
    // and a click right after the grid renders does not always land as a selection — so assert it.
    await expect(async () => {
      await cockpitRow.click();
      await expect(cockpitRow).toHaveClass(/row-selected/, { timeout: 2000 });
    }).toPass({ timeout: 30000 });

    await test.step('invoke Sprung zu Prognose', async () => {
      // The first action sits on the green button; ours lives behind the dropdown toggle next to it.
      await page.locator('[data-testid="quick-action-dropdown-toggle"]').first().click();
      const dropdown = page.locator('.quick-actions-dropdown');
      await dropdown.waitFor({ state: 'visible', timeout: 15000 });
      // The session is de_DE; the action's en_US caption is "Go to Forecast".
      await dropdown.getByText('Sprung zu Prognose', { exact: false }).first().click();
    });

    // --- Assert the overlay ---------------------------------------------------------------------
    // The overlay is the standard grid of the forecast-quantity window, opened as a modal.
    const overlay = page.locator('.modal-content, .panel-modal').last();
    await overlay.waitFor({ state: 'visible', timeout: 30000 });

    const overlayRows = overlay.locator('tbody tr');
    await expect(overlayRows, 'the overlay lists exactly the one matching forecast document').toHaveCount(1);

    const firstRow = overlayRows.first();
    const qtyCell = firstRow.locator('[data-cy="cell-Qty"]');
    await expect(qtyCell, 'the overlay shows a Menge column').toHaveCount(1);

    // Read the cell, not the row: the row also carries the forecast's timestamped name.
    const qtyText = (await qtyCell.innerText()).replace(/\s+/g, '');
    const qtyValue = Number(qtyText.replace(/\./g, '').replace(',', '.'));

    expect(
      qtyValue,
      `Menge must be the cockpit product's ${QTY_COCKPIT_PRODUCT}, not the document total ${QTY_DOCUMENT_TOTAL} (cell read: "${qtyText}")`
    ).toBe(QTY_COCKPIT_PRODUCT);

    // Maßeinheit is deliberately NOT a column: the quantity is always in the product's own UOM, which
    // the product already carries, so the column only duplicated information.
    await expect(
      firstRow.locator('[data-cy="cell-C_UOM_ID"]'),
      'Maßeinheit duplicates the product UOM and must not be a column'
    ).toHaveCount(0);

    // The requested column order. The row's cells appear in the grid's own order, so their data-cy
    // suffixes are the order — asserted as a whole list, because a per-cell check cannot catch a swap.
    const columnOrder = await firstRow
      .locator('[data-cy^="cell-"]')
      .evaluateAll((cells) => cells.map((cell) => cell.getAttribute('data-cy').replace(/^cell-/, '')));
    expect(columnOrder, 'Menge sits between Belegstatus and Zugesagter Termin').toEqual([
      'M_Forecast_ID',
      'DocStatus',
      'Qty',
      'DatePromised',
      'AD_Org_ID',
    ]);

    // Stichtag is a header-level date. Rendering it as Date+Time appended a meaningless 00:00:00 to
    // every row, so assert the de_DE date-only shape rather than merely a non-empty cell.
    const dateText = (await firstRow.locator('[data-cy="cell-DatePromised"]').innerText()).trim();
    expect(dateText, 'Zugesagter Termin renders date-only, with no time part').toMatch(/^\d{2}\.\d{2}\.\d{4}$/);

    // --- AC5: the Prognose column still zooms into the forecast document -----------------------
    await test.step('the listed forecast opens as its document', async () => {
      // Zoom-into lives in the grid's context menu (TableContextMenu), not on the cell itself. Match the
      // entry by its icon rather than its caption, which is server-translated.
      await firstRow.locator('[data-cy="cell-M_Forecast_ID"]').click({ button: 'right' });
      const contextMenu = page.locator('.context-menu-open');
      await contextMenu.waitFor({ state: 'visible', timeout: 15000 });
      // The zoom opens the document in a NEW TAB, so the assertions belong to that page, not to `page`.
      const [zoomed] = await Promise.all([
        page.context().waitForEvent('page', { timeout: 30000 }),
        contextMenu.locator('.meta-icon-share').first().click(),
      ]);
      await zoomed.waitForLoadState('domcontentloaded');
      await expect
        .poll(() => zoomed.url(), { timeout: 30000 })
        .toMatch(new RegExp(`/window/${FORECAST_WINDOW_ID}/\\d+`));
      // Prove it opened OUR forecast, not merely some forecast. The Name renders read-only here, so
      // match the document's text rather than an input value.
      await expect(
        zoomed.locator('.document-lists-wrapper, .window-wrapper, body').first(),
        'the zoom opens the forecast the overlay row stands for'
      ).toContainText(forecastName, { timeout: 30000 });
    });
  });
});
