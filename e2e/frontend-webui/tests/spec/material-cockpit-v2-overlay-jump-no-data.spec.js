import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { FRONTEND_BASE_URL } from '../utils/common';

/**
 * Material Cockpit v2 — "Sprung zu Prognose" (AD_Process 585515, the generic
 * RelationTypeInOverlayProcess) when the selected row has no matching forecast (acceptance
 * criterion TC4).
 *
 * Deliberately its OWN file, not an addition to `material-cockpit-v2-jump-preconditions.spec.js`:
 * that spec covers the three BESPOKE jumps (TC1-TC3/TC5) and needs a materially different
 * `createMasterdata` setup (a forecast document, not sales/purchase orders + a BOM). Per the e2e
 * suite rule ("the shared `createMasterdata` setup is the grouping unit"), a different setup means
 * a separate spec file.
 *
 * Unlike the three bespoke jumps, this shared process cannot be disabled up front — its
 * `checkPreconditionsApplicable` cannot reach the target data. So the fix corrects the AFTER-click
 * register instead: an empty result now surfaces as an acknowledgeable, translated message
 * (`UserMessagePresentation.ACKNOWLEDGE_DIALOG`) rather than as two red error toasts riding a raw
 * HTTP 500.
 *
 * Both scenarios below assert on STRUCTURE only (data-testid, CSS classes, non-empty text) — never
 * on the German message string — per the e2e language-independence rule.
 */

const MATERIAL_COCKPIT_V2_WINDOW_ID = 541963;
const FORECAST_ACTION_TESTID = 'quick-action-QtyDemand_QtySupply_V_to_Forecast'; // AD_Process_ID 585515

// --- Fixtures reused from forecast-overlay-qty.spec.js (proven values in this DB) ----------------
const FORECAST_WINDOW_ID = 328;
// The included-tab segment of a document path is a DetailId, i.e. "AD_Tab-<id>" — a bare tab id makes
// DetailId.fromJson throw and the request answers 500.
const FORECAST_LINES_DETAIL_ID = 'AD_Tab-654';
const WAREHOUSE_ID = 540008;
const WAREHOUSE_CAPTION = 'Hauptlager';
// A forecast line needs an explicit stock UOM: its QtyTU callout (HUPackingAwareBL.calculateQtyTU)
// asserts C_UOM_ID > 0 and answers 500 otherwise.
const UOM_ID = 100;
const UOM_CAPTION = 'PCE';
const FORECAST_QTY = 5;

/**
 * Poll the Material Cockpit v2 view until the row for `productId` appears, then re-fetch it through
 * a product-filtered view so it is the only row on the page (material-dispo processes demand/supply
 * asynchronously).
 */
async function waitForCockpitRow(page, productId, { timeout = 120000 } = {}) {
  const deadline = Date.now() + timeout;
  while (Date.now() < deadline) {
    const view = await page.evaluate(async ({ windowId }) => {
      const created = await fetch(`/rest/api/documentView/${windowId}`, {
        method: 'POST',
        credentials: 'include',
        headers: { 'Content-Type': 'application/json', Accept: 'application/json' },
        body: JSON.stringify({ windowId: String(windowId), viewType: 'grid' }),
      });
      if (!created.ok) return null;
      const { viewId } = await created.json();
      const rows = await fetch(`/rest/api/documentView/${windowId}/${viewId}?firstRow=0&pageLength=200`, {
        credentials: 'include',
        headers: { Accept: 'application/json' },
      });
      if (!rows.ok) return null;
      return rows.json();
    }, { windowId: MATERIAL_COCKPIT_V2_WINDOW_ID });

    const row = view?.result?.find((r) => String(r.fieldsByName?.M_Product_ID?.value?.key) === String(productId));
    if (row) {
      const productValue = row.fieldsByName?.ProductValue?.value;
      const filtered = await page.evaluate(async ({ windowId, productValue }) => {
        const created = await fetch(`/rest/api/documentView/${windowId}`, {
          method: 'POST',
          credentials: 'include',
          headers: { 'Content-Type': 'application/json', Accept: 'application/json' },
          body: JSON.stringify({
            windowId: String(windowId),
            viewType: 'grid',
            filters: [{ filterId: 'default', parameters: [{ parameterName: 'ProductValue', value: productValue }] }],
          }),
        });
        if (!created.ok) throw new Error(`filtered view failed: ${created.status}`);
        const { viewId } = await created.json();
        const rows = await fetch(`/rest/api/documentView/${windowId}/${viewId}?firstRow=0&pageLength=200`, {
          credentials: 'include',
          headers: { Accept: 'application/json' },
        });
        if (!rows.ok) throw new Error(`filtered rows failed: ${rows.status}`);
        return rows.json();
      }, { windowId: MATERIAL_COCKPIT_V2_WINDOW_ID, productValue });

      const filteredRow = filtered.result?.find((r) => String(r.fieldsByName?.M_Product_ID?.value?.key) === String(productId));
      if (!filteredRow) throw new Error(`product ${productValue} vanished from its own filtered view`);
      return { viewId: filtered.viewId, rowId: filteredRow.id };
    }
    await page.waitForTimeout(3000);
  }
  throw new Error(`No Material Cockpit v2 row appeared for product ${productId} within ${timeout}ms`);
}

/** Navigate to the given view and select its (single) row. */
async function selectCockpitRow(page, viewId, rowId) {
  await page.goto(`${FRONTEND_BASE_URL}/window/${MATERIAL_COCKPIT_V2_WINDOW_ID}?viewId=${viewId}`);
  const row = page.locator(`tbody tr[data-testid="table-row-${rowId}"]`);
  await row.waitFor({ state: 'visible', timeout: 30000 });
  // A click right after the grid renders does not always land as a selection — assert it.
  await expect(async () => {
    await row.click();
    await expect(row).toHaveClass(/row-selected/, { timeout: 2000 });
  }).toPass({ timeout: 30000 });
  return row;
}

/** Open the quick-actions dropdown for the currently selected row. */
async function openQuickActionsDropdown(page) {
  await page.locator('[data-testid="quick-action-dropdown-toggle"]').first().click();
  const dropdown = page.locator('.quick-actions-dropdown');
  await dropdown.waitFor({ state: 'visible', timeout: 15000 });
  return dropdown;
}

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

/** Set one or more fields via the WebAPI PATCH endpoint. A lookup field takes a JSONLookupValue. */
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
 * Create a row in an included tab AND fill it, in a single request — creating first and PATCHing
 * afterwards intermittently answers 404 DocumentNotFoundException on the freshly created row.
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

test.describe('Material Cockpit v2 — Sprung zu Prognose with no matching forecast', () => {
  test('no forecast: acknowledge dialog is shown once and is dismissible', async ({ page }) => {
    allure.epic('E0300: Planning');
    allure.tag('F19011: Material Cockpit v2');
    allure.tag('F19011');
    allure.story('Sprung zu Prognose: empty result is an acknowledgeable message, not a system error');
    allure.severity('critical');
    allure.description(`
### Scenario
1. A product with a sales order but NO forecast at all.
2. Select the row in Material Cockpit v2, open Aktionen, run Sprung zu Prognose.
3. Today: HTTP 500 + two red error toasts reading "Keine zugehörigen Dokumente gefunden.".
4. Fixed: a single acknowledge dialog with a non-empty, translated message, dismissed via OK.

### Why it matters
This jump's precondition cannot detect emptiness up front (unlike the three bespoke jumps), so the
correction happens after the click: an empty result must read as information, not as a system failure.
    `);

    test.setTimeout(180000);

    // --- Seed masterdata: product + sales order, deliberately no forecast ----------------------
    const datePromised = new Date(Date.now() + 24 * 60 * 60 * 1000).toISOString();
    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: { language: 'de_DE', firstname: 'cockpit', lastname: 'overlayjump' } },
        bpartners: {
          bp1: { isVendor: false, isCustomer: true, isSoPriceList: true, name: 'OverlayJumpNoDataPartner' },
        },
        warehouses: { wh: {} },
        products: {
          noForecast: { name: 'OverlayJumpNoData-Product', prices: [{ price: 10, currencyCode: 'EUR' }] },
        },
        salesOrders: {
          so1: {
            bpartner: 'bp1',
            warehouse: 'wh',
            datePromised,
            lines: [{ product: 'noForecast', qty: 5 }],
          },
        },
      },
    });
    allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');

    const productId = masterdata.products.noForecast.id;

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    const { viewId, rowId } = await waitForCockpitRow(page, productId);
    await selectCockpitRow(page, viewId, rowId);
    const dropdown = await openQuickActionsDropdown(page);

    const entry = dropdown.getByTestId(FORECAST_ACTION_TESTID);
    await expect(entry, `${FORECAST_ACTION_TESTID} must be offered`).toBeVisible();

    await test.step('invoke Sprung zu Prognose on a row with no forecast', async () => {
      await entry.click();
    });

    await test.step('exactly one acknowledge dialog appears, with a non-empty message', async () => {
      const dialog = page.locator('.panel-prompt');
      await expect(dialog, 'the acknowledge dialog must appear').toBeVisible({ timeout: 30000 });
      // Exactly one: the reducer holds a single acknowledgeDialog slot, so a repeated dispatch (e.g.
      // from a retried/duplicated request) overwrites rather than stacks a second dialog.
      await expect(dialog, 'exactly one acknowledge dialog must be present').toHaveCount(1);
      await expect(
        dialog.locator('.panel-prompt-content'),
        'the dialog must carry a non-empty message'
      ).toContainText(/\S/);
    });

    await test.step('OK is actionable and dismisses the dialog', async () => {
      const dialog = page.locator('.panel-prompt');
      // A plain .click() runs Playwright's actionability checks (visible, stable, receives events,
      // enabled) — it fails if anything overlays the button, which is the real layering assertion
      // (the earlier bug rendered the dialog BEHIND an open modal, at equal z-index).
      await dialog.locator('.btn-submit').click();
      await expect(dialog, 'the dialog is dismissed after OK').toHaveCount(0);
    });

    // No error toast should have accompanied the dialog — the fix's whole point is that this reads
    // as information, not as a system error. Notification.js renders classnames('notification-item',
    // { [notifType]: notifType }), so an error toast is two space-separated classes
    // "notification-item error", nested under NotificationHandler's ".notification-handler" wrapper —
    // there is no react-redux-toastr (".rrt-error") in this frontend and no single ".notification-error"
    // class, so the old selector matched nothing and could never fail.
    await expect(
      page.locator('.notification-handler .notification-item.error')
    ).toHaveCount(0);
  });

  test('forecast exists: the jump still opens the forecast overlay, no acknowledge dialog', async ({ page }) => {
    allure.epic('E0300: Planning');
    allure.tag('F19011: Material Cockpit v2');
    allure.tag('F19011');
    allure.story('Sprung zu Prognose: unchanged behaviour when a matching forecast exists');
    allure.severity('critical');
    allure.description(`
### Scenario
1. A product with a forecast document carrying a line for it.
2. Select the row in Material Cockpit v2, open Aktionen, run Sprung zu Prognose.
3. Expected (unchanged): the forecast overlay opens; no acknowledge dialog appears.

### Why it matters
The fix only changes the register of an EMPTY result. The over-rejection risk is that the same code
path might now swallow or misreport the non-empty case too — this proves it does not.
    `);

    test.setTimeout(180000);

    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: { language: 'de_DE', firstname: 'cockpit', lastname: 'overlayjumpok' } },
        products: {
          hasForecast: { name: 'OverlayJumpNoData-HasForecast' },
        },
      },
    });
    allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');

    const productId = masterdata.products.hasForecast.id;
    const productName = masterdata.products.hasForecast.productName || 'OverlayJumpNoData-HasForecast';

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    // --- Create a forecast document with a line for the product --------------------------------
    const forecastName = `OverlayJumpNoData-${Date.now()}`;
    await test.step('create a forecast with a line for the product', async () => {
      await page.goto(`${FRONTEND_BASE_URL}/window/${FORECAST_WINDOW_ID}/NEW`);
      await page.waitForURL(/\/window\/\d+\/\d+/, { timeout: 30000 });
      await page.locator('.rotating, .indicator-pending').waitFor({ state: 'detached', timeout: 30000 }).catch(() => {});
      const id = page.url().split('/').pop().split('?')[0];
      await patchField(page, `${FORECAST_WINDOW_ID}/${id}`, 'Name', forecastName);
      await patchField(page, `${FORECAST_WINDOW_ID}/${id}`, 'M_Warehouse_ID', { key: WAREHOUSE_ID, caption: WAREHOUSE_CAPTION });
      await patchField(page, `${FORECAST_WINDOW_ID}/${id}`, 'DatePromised', new Date().toISOString().slice(0, 10));
      await waitForRecordValid(page, FORECAST_WINDOW_ID, id);

      await createIncludedRecord(page, FORECAST_WINDOW_ID, id, FORECAST_LINES_DETAIL_ID, [
        ['M_Product_ID', { key: productId, caption: productName }],
        ['C_UOM_ID', { key: UOM_ID, caption: UOM_CAPTION }],
        ['Qty', FORECAST_QTY],
      ]);
    });

    const { viewId, rowId } = await waitForCockpitRow(page, productId);
    await selectCockpitRow(page, viewId, rowId);
    const dropdown = await openQuickActionsDropdown(page);

    const entry = dropdown.getByTestId(FORECAST_ACTION_TESTID);
    await expect(entry, `${FORECAST_ACTION_TESTID} must be offered`).toBeVisible();

    await test.step('invoke Sprung zu Prognose and the forecast overlay opens', async () => {
      // The jump opens a same-tab overlay modal without navigating the browser. Waiting for the
      // documentView rows GET for the forecast window (never the cockpit's own) proves a new grid
      // was actually opened, not merely that the click was accepted.
      const otherWindowViewRowsResponse = page.waitForResponse((response) => {
        if (response.request().method() !== 'GET') return false;
        const match = response.url().match(/\/documentView\/(\d+)\/[^/?]+\?firstRow=/);
        return !!match && match[1] !== String(MATERIAL_COCKPIT_V2_WINDOW_ID);
      }, { timeout: 30000 });
      await entry.click();
      await otherWindowViewRowsResponse;
    });

    const overlay = page.locator('.modal-content, .panel-modal').last();
    await expect(overlay, 'the forecast overlay must open').toBeVisible({ timeout: 30000 });
    await expect(overlay.locator('tbody tr'), 'the overlay lists at least the seeded forecast document').not.toHaveCount(0);

    // The over-rejection guard for this scenario: no acknowledge dialog on the non-empty path.
    await expect(page.locator('.panel-prompt'), 'no acknowledge dialog on the non-empty path').toHaveCount(0);
  });
});
