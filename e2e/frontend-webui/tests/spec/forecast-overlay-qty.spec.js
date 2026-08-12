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
 */

const FORECAST_WINDOW_ID = 328;
const FORECAST_LINES_TAB_ID = 654;
const MATERIAL_COCKPIT_V2_WINDOW_ID = 541963;

const QTY_COCKPIT_PRODUCT = 7;
const QTY_OTHER_PRODUCT = 900;

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

async function patchField(page, path, fieldName, value) {
  return page.evaluate(
    async ({ path, fieldName, value }) => {
      const resp = await fetch(`/rest/api/window/${path}`, {
        method: 'PATCH',
        credentials: 'include',
        headers: { 'Content-Type': 'application/json', Accept: 'application/json' },
        body: JSON.stringify([{ op: 'replace', path: fieldName, value }]),
      });
      if (!resp.ok) throw new Error(`PATCH ${fieldName} failed: ${resp.status} ${await resp.text()}`);
      return resp.json();
    },
    { path, fieldName, value }
  );
}

/** Create a new record in a window (or included tab) and return its documentId. */
async function createRecord(page, path) {
  return page.evaluate(async ({ path }) => {
    const resp = await fetch(`/rest/api/window/${path}/NEW`, {
      method: 'PATCH',
      credentials: 'include',
      headers: { 'Content-Type': 'application/json', Accept: 'application/json' },
      body: JSON.stringify([]),
    });
    if (!resp.ok) throw new Error(`create ${path} failed: ${resp.status} ${await resp.text()}`);
    const data = await resp.json();
    return data[0]?.rowId ?? data[0]?.id ?? data[0]?.documentId;
  }, { path });
}

test.describe('Forecast overlay — Menge is scoped to the cockpit product', () => {
  test('Sprung zu Prognose shows only the cockpit product\'s forecast quantity', async ({ page }) => {
    allure.epic('E0300: Planning');
    allure.tag('F19042');
    allure.story('Sprung zu Prognose: per-product Menge in the forecast overlay');
    allure.severity('critical');
    allure.description(`
### Scenario
1. Seed a warehouse and two products.
2. Create one forecast document holding a line for BOTH products —
   ${QTY_COCKPIT_PRODUCT} of the cockpit product and ${QTY_OTHER_PRODUCT} of the other.
3. Open Material Cockpit v2 and select the row of the cockpit product.
4. Invoke **Sprung zu Prognose**.
5. The overlay lists the forecast document, and its **Menge** reads ${QTY_COCKPIT_PRODUCT} —
   the cockpit product's line only, NOT ${QTY_COCKPIT_PRODUCT + QTY_OTHER_PRODUCT}.
6. The **Name** column still zooms into the forecast document.

### Why it matters
A forecast document mixes products. Showing the document total would tell the planner a quantity that
has nothing to do with the product they jumped from.
    `);

    test.setTimeout(180000);

    // --- Seed masterdata -----------------------------------------------------------------------
    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: { language: 'en_US' } },
        warehouses: { warehouse: {} },
        products: {
          cockpitProduct: { name: 'ForecastOverlayQty-Cockpit' },
          otherProduct: { name: 'ForecastOverlayQty-Other' },
        },
      },
    });

    const warehouseId = masterdata.warehouses.warehouse.id;
    const cockpitProductId = masterdata.products.cockpitProduct.id;
    const otherProductId = masterdata.products.otherProduct.id;

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    // --- Create the forecast document with a line for each product -----------------------------
    const forecastId = await test.step('create forecast with two products on it', async () => {
      // Create the header through the UI (the suite's proven path — a REST-only create races the
      // session and 401s), then fill the fields over the now-authenticated session.
      await page.goto(`${FRONTEND_BASE_URL}/window/${FORECAST_WINDOW_ID}/NEW`);
      await page.waitForURL(/\/window\/\d+\/\d+/, { timeout: 30000 });
      await page.locator('.rotating, .indicator-pending').waitFor({ state: 'detached', timeout: 30000 }).catch(() => {});
      const id = page.url().split('/').pop();
      await patchField(page, `${FORECAST_WINDOW_ID}/${id}`, 'Name', `ForecastOverlayQty-${Date.now()}`);
      await patchField(page, `${FORECAST_WINDOW_ID}/${id}`, 'M_Warehouse_ID', { key: String(warehouseId) });
      await patchField(page, `${FORECAST_WINDOW_ID}/${id}`, 'DatePromised', new Date().toISOString().slice(0, 10));
      await waitForRecordValid(page, FORECAST_WINDOW_ID, id);

      for (const [productId, qty] of [
        [cockpitProductId, QTY_COCKPIT_PRODUCT],
        [otherProductId, QTY_OTHER_PRODUCT],
      ]) {
        const linePath = `${FORECAST_WINDOW_ID}/${id}/${FORECAST_LINES_TAB_ID}`;
        const lineId = await createRecord(page, linePath);
        await patchField(page, `${linePath}/${lineId}`, 'M_Product_ID', { key: String(productId) });
        await patchField(page, `${linePath}/${lineId}`, 'Qty', qty);
      }
      return id;
    });

    // --- Find the cockpit row for our product --------------------------------------------------
    // The Material Cockpit v2 view is fed by material-dispo, which processes the forecast lines
    // asynchronously — poll until the row for the cockpit product appears.
    const { viewId, rowId } = await test.step('wait for the Material Cockpit v2 row', async () => {
      const deadline = Date.now() + 90000;
      while (Date.now() < deadline) {
        const view = await page.evaluate(async ({ windowId }) => {
          const resp = await fetch(`/rest/api/documentView/${windowId}`, {
            method: 'POST',
            credentials: 'include',
            headers: { 'Content-Type': 'application/json', Accept: 'application/json' },
            body: JSON.stringify({ windowId: String(windowId), viewType: 'grid' }),
          });
          if (!resp.ok) return null;
          return resp.json();
        }, { windowId: MATERIAL_COCKPIT_V2_WINDOW_ID });

        const row = view?.result?.find(
          (r) => String(r.fieldsByName?.M_Product_ID?.value?.key) === String(cockpitProductId)
        );
        if (row) return { viewId: view.viewId, rowId: row.id };
        await page.waitForTimeout(3000);
      }
      throw new Error(`No Material Cockpit v2 row appeared for product ${cockpitProductId}`);
    });

    // --- Drive the UI: select the row and invoke the action ------------------------------------
    await page.goto(`${FRONTEND_BASE_URL}/window/${MATERIAL_COCKPIT_V2_WINDOW_ID}?viewId=${viewId}`);
    const cockpitRow = page.locator(`.table-flex-wrapper tbody tr[data-id="${rowId}"]`);
    await cockpitRow.waitFor({ state: 'visible', timeout: 30000 });
    await cockpitRow.click();

    await test.step('invoke Sprung zu Prognose', async () => {
      await page.locator('.quick-actions-wrapper .btn-meta-outline-secondary').first().click();
      await page.getByText('Sprung zu Prognose', { exact: false }).click();
    });

    // --- Assert the overlay ---------------------------------------------------------------------
    const overlay = page.locator('.modal-content, .window-wrapper .table-flex-wrapper').last();
    await overlay.waitFor({ state: 'visible', timeout: 30000 });

    const overlayRows = overlay.locator('tbody tr');
    await expect(overlayRows, 'the overlay lists exactly the one matching forecast document').toHaveCount(1);

    const overlayText = (await overlayRows.first().innerText()).replace(/\s+/g, ' ');

    expect(
      overlayText,
      `Menge must be the cockpit product's ${QTY_COCKPIT_PRODUCT}, not the document total ` +
        `${QTY_COCKPIT_PRODUCT + QTY_OTHER_PRODUCT}`
    ).toContain(String(QTY_COCKPIT_PRODUCT));

    expect(
      overlayText,
      'the other product\'s quantity must not leak into the cockpit product\'s Menge'
    ).not.toContain(String(QTY_COCKPIT_PRODUCT + QTY_OTHER_PRODUCT));

    // --- AC5: the Name column still zooms into the forecast document ---------------------------
    await test.step('Name zooms into the forecast document', async () => {
      await overlayRows.first().locator('td').first().click();
      await page.waitForURL(new RegExp(`/window/${FORECAST_WINDOW_ID}/${forecastId}`), { timeout: 30000 });
    });
  });
});
