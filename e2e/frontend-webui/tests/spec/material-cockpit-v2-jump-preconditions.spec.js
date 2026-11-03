import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { FRONTEND_BASE_URL } from '../utils/common';

/**
 * Material Cockpit v2 — the three bespoke jump actions must be offered as disabled-with-a-reason
 * when their jump target holds no records, and enabled (and must actually jump) when it holds at
 * least one.
 *
 * Three cockpit rows, one per stream, each built so exactly one of its two candidate streams is
 * empty and the other covers the enabled/over-rejection path:
 *  - `salesOnly`     — a sales order only  -> M_ShipmentSchedule non-empty, M_ReceiptSchedule empty.
 *  - `purchaseOnly`  — a purchase order only -> M_ReceiptSchedule non-empty, M_ShipmentSchedule AND
 *                       PP_Order_Candidate empty (it is not a manufactured product).
 *  - `manufactured`  — a BOM + PP_Product_Planning(isManufactured) + a sales order big enough to
 *                       push its ATP negative, so material-dispo advises a PP_Order_Candidate.
 *
 * The production candidate is generated asynchronously by material-dispo, so its enabled+opens
 * check retries for up to two minutes; the other two streams settle synchronously, but reuse the
 * same retry helper to be safe against timing.
 */

const MATERIAL_COCKPIT_V2_WINDOW_ID = 541963;

// The quick-action data-testid is `quick-action-<AD_Process.Value>`. The trailing German captions
// below are documentation only — never used to select or assert.
const RECEIPT_ACTION_TESTID = 'quick-action-QtyDemand_QtySupply_V_to_ReceiptSchedule'; // AD_Process_ID 585514, "Sprung zu Wareneingangsdispo"
const SHIPMENT_ACTION_TESTID = 'quick-action-QtyDemand_QtySupply_V_to_ShipmentSchedule'; // AD_Process_ID 585513, "Sprung zu Lieferdisposition"
const PRODUCTION_ACTION_TESTID = 'quick-action-QtyDemand_QtySupply_V_to_PP_Order_Candidate'; // AD_Process_ID 585516, "Sprung zu Produktionsdisposition"

// The target window id is deliberately NOT hardcoded per stream: it can be remapped by an active
// AD_Window.Overrides_Window_ID customization, so it varies per instance. What IS stable: the jump
// always opens some window other than the cockpit's own — see assertActionEnabledAndOpens.

const ORDER_QTY = 5;
// Large relative to the manufactured product's zero on-hand stock, so the sales order alone pushes
// its ATP negative and material-dispo advises a production candidate for it.
const PRODUCTION_ORDER_QTY = 50;

/**
 * Poll the Material Cockpit v2 view until the row for `productId` appears, then re-fetch it through
 * a product-filtered view so it is the only row on the page (mirrors forecast-overlay-qty.spec.js —
 * the view is fed by material-dispo, which processes demand/supply asynchronously).
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

/**
 * Empty case: the action is offered, disabled, and states why.
 *
 * SOFT on purpose: a hard assertion would abort the run at the first failing stream, hiding
 * whether the other two streams had regressed too. Soft reports all three every run.
 */
async function assertActionDisabled(dropdown, testId) {
  const entry = dropdown.getByTestId(testId);
  await expect(entry, `${testId} must be offered`).toBeVisible();
  await expect.soft(entry, `${testId} must be disabled — its jump target holds no records`).toHaveClass(/quick-actions-item-disabled/);
  // Asserts the reason element is present with non-empty content, not its (localized) text.
  await expect
    .soft(entry.locator('p.one-line small'), `${testId} must state why it is disabled, beside its caption`)
    .toContainText(/\S/);
}

/**
 * Non-empty case (the over-rejection guard): enabled, no reason text, and the jump actually opens
 * the target grid. Retries the whole select-row/open-dropdown/click/assert cycle, re-navigating to
 * `viewId`/`rowId` on every attempt: the underlying record can still be in flight from the async
 * material-dispo pipeline, and a previous attempt may already have opened the jump's modal, leaving
 * the dropdown toggle stale or its open/closed state indeterminate for a bare retry.
 *
 * The jump opens a same-tab overlay modal, which does not navigate the browser — `page.url()` can't
 * tell a real jump from a no-op here. What does differ: the modal mounts a grid for the target
 * window, firing a `documentView` rows GET for that window id. Waiting for exactly that GET — for
 * any window id other than the cockpit's own — proves a new grid was opened; a no-op click fires no
 * such request at all.
 */
async function assertActionEnabledAndOpens(page, testId, viewId, rowId, { timeout = 120000 } = {}) {
  await expect(async () => {
    await selectCockpitRow(page, viewId, rowId);
    const dropdown = await openQuickActionsDropdown(page);
    const entry = dropdown.getByTestId(testId);
    await expect(entry, `${testId} must be offered`).toBeVisible();
    await expect(entry, `${testId} must not be disabled once its target holds records`).not.toHaveClass(/quick-actions-item-disabled/);
    // Once enabled, the reason element must be absent entirely, not merely empty.
    await expect(entry.locator('p.one-line small'), `${testId} must not show a disabled-reason once its target holds records`).toHaveCount(0);

    const otherWindowViewRowsResponse = page.waitForResponse((response) => {
      if (response.request().method() !== 'GET') return false;
      // Matches the rows GET only — excludes the sibling `layout` and `quickActions` calls the same
      // modal mount also fires, and excludes the cockpit's own window.
      const match = response.url().match(/\/documentView\/(\d+)\/[^/?]+\?firstRow=/);
      return !!match && match[1] !== String(MATERIAL_COCKPIT_V2_WINDOW_ID);
    }, { timeout: 15000 });
    await entry.click();
    await otherWindowViewRowsResponse;
  }).toPass({ timeout, intervals: [3000, 5000, 10000, 15000] });
}

test.describe('Material Cockpit v2 — jump actions reflect whether their target holds records', () => {
  test('receipt/shipment/production jumps disable with a reason when empty, stay enabled when not', async ({ page }) => {
    allure.epic('E0300: Planning');
    allure.tag('F19011: Material Cockpit v2');
    allure.tag('F19011');
    allure.story('Jump-action preconditions: disabled-with-a-reason when the target is empty');
    allure.severity('critical');
    allure.description(`
### Scenario
Three cockpit rows, each isolating one stream as empty while another stream on the same row (or a
third row) covers the enabled/non-empty path:
1. \`salesOnly\` — a sales order only: **Sprung zu Wareneingangsdispo** (receipt) has nothing to
   show; **Sprung zu Lieferdisposition** (shipment) does.
2. \`purchaseOnly\` — a purchase order only: **Sprung zu Lieferdisposition** and
   **Sprung zu Produktionsdisposition** have nothing to show; **Sprung zu Wareneingangsdispo** does.
3. \`manufactured\` — a BOM + product planning + a sales order that pushes its ATP negative:
   **Sprung zu Produktionsdisposition** has a production candidate to show.

### Why it matters
Today all three actions accept unconditionally and an empty jump result is indistinguishable from no
jump at all (\`ProcessExecutionResult\` collapses an empty record list to null) — the planner sees
nothing happen and cannot tell a real failure from "there is genuinely nothing here".
    `);

    // Worst case is 3x waitForCockpitRow (up to 120s each) + 3x assertActionEnabledAndOpens (up to
    // 120s each) = 720s; the production stream's candidate generation alone can take up to ~60s, and
    // the other two streams' retries exist to be safe against timing too — the budget below has to
    // cover the sum, not shrink the inner retries.
    test.setTimeout(900000);

    // --- Seed masterdata -----------------------------------------------------------------------
    // Split into two independent `createMasterdata` calls with separate (non-shared) contexts:
    // `CreateBPartnerCommand.createPricingSystem()` fixes one M_PricingSystem direction per context
    // from the FIRST bpartner's IsSOPriceList, so a single request supports a working sales order OR
    // a working purchase order, never both.
    const datePromised = new Date(Date.now() + 24 * 60 * 60 * 1000).toISOString();

    // Call 1 — sales context: customer bpartner, salesOnly + manufactured products, both sales orders.
    const salesMasterdata = await Backend.createMasterdata({
      request: {
        login: { user: { language: 'de_DE', firstname: 'cockpit', lastname: 'jump' } },
        bpartners: {
          bp1: { isVendor: false, isCustomer: true, isSoPriceList: true, name: 'CockpitJumpPreconditionsSalesPartner' },
        },
        warehouses: {
          wh: {},
        },
        products: {
          // BOM component — must be created before `manufactured` references it.
          component: { name: 'CockpitJumpPreconditions-Component', prices: [{ price: 1, currencyCode: 'EUR' }] },
          salesOnly: { name: 'CockpitJumpPreconditions-SalesOnly', prices: [{ price: 10, currencyCode: 'EUR' }] },
          manufactured: {
            name: 'CockpitJumpPreconditions-Manufactured',
            prices: [{ price: 10, currencyCode: 'EUR' }],
            bom: { lines: [{ product: 'component', qty: 1 }] },
          },
        },
        // Marks `manufactured` as isManufactured=true — needs its own `bom: 'manufactured'` (not
        // `pickingOrder`) so the demand its sales order raises is matched to a PP_Order_Candidate.
        productPlannings: {
          manufacturedPlanning: { product: 'manufactured', warehouse: 'wh', bom: 'manufactured' },
        },
        salesOrders: {
          soSalesOnly: {
            bpartner: 'bp1',
            warehouse: 'wh',
            datePromised,
            // Two lines for the SAME product+warehouse: the existence probe must match multiple
            // records, not just one — a probe that regressed onto a throwing first-id lookup would
            // hide the jump action entirely instead of merely disabling it. Do not collapse to one line.
            lines: [
              { product: 'salesOnly', qty: ORDER_QTY },
              { product: 'salesOnly', qty: ORDER_QTY },
            ],
          },
          soManufactured: {
            bpartner: 'bp1',
            warehouse: 'wh',
            datePromised,
            lines: [{ product: 'manufactured', qty: PRODUCTION_ORDER_QTY }],
          },
        },
      },
    });

    // Call 2 — purchase context: vendor bpartner, purchaseOnly product, purchase order.
    const purchaseMasterdata = await Backend.createMasterdata({
      request: {
        bpartners: {
          bp2: { isVendor: true, isCustomer: false, isSoPriceList: false, name: 'CockpitJumpPreconditionsPurchasePartner' },
        },
        warehouses: {
          wh2: {},
        },
        products: {
          purchaseOnly: { name: 'CockpitJumpPreconditions-PurchaseOnly', prices: [{ price: 10, currencyCode: 'EUR' }] },
        },
        purchaseOrders: {
          poPurchaseOnly: {
            bpartner: 'bp2',
            warehouse: 'wh2',
            datePromised,
            lines: [{ product: 'purchaseOnly', qty: ORDER_QTY }],
          },
        },
      },
    });

    const masterdata = {
      ...salesMasterdata,
      products: { ...salesMasterdata.products, ...purchaseMasterdata.products },
    };
    allure.attachment('Test Data (sales context)', JSON.stringify(salesMasterdata, null, 2), 'application/json');
    allure.attachment('Test Data (purchase context)', JSON.stringify(purchaseMasterdata, null, 2), 'application/json');

    const salesOnlyProductId = masterdata.products.salesOnly.id;
    const purchaseOnlyProductId = masterdata.products.purchaseOnly.id;
    const manufacturedProductId = masterdata.products.manufactured.id;

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    // --- Row 1: sales order only -> receipt empty, shipment non-empty --------------------------
    await test.step('salesOnly: Sprung zu Wareneingangsdispo disabled, Sprung zu Lieferdisposition enabled', async () => {
      const { viewId, rowId } = await waitForCockpitRow(page, salesOnlyProductId);

      // Enabled path first (hard assertion): if the masterdata never materialised, fail on that
      // rather than on a disabled-check that was never going to be meaningful.
      await assertActionEnabledAndOpens(page, SHIPMENT_ACTION_TESTID, viewId, rowId);

      // Re-select: the jump above opened the shipment-schedule grid in a modal overlay; re-navigating
      // resets that state.
      await selectCockpitRow(page, viewId, rowId);
      const dropdown = await openQuickActionsDropdown(page);
      await assertActionDisabled(dropdown, RECEIPT_ACTION_TESTID);
    });

    // --- Row 2: purchase order only -> shipment + production empty, receipt non-empty ----------
    await test.step('purchaseOnly: Sprung zu Lieferdisposition and Sprung zu Produktionsdisposition disabled, Sprung zu Wareneingangsdispo enabled', async () => {
      const { viewId, rowId } = await waitForCockpitRow(page, purchaseOnlyProductId);

      await assertActionEnabledAndOpens(page, RECEIPT_ACTION_TESTID, viewId, rowId);

      await selectCockpitRow(page, viewId, rowId);
      const dropdown = await openQuickActionsDropdown(page);
      await assertActionDisabled(dropdown, SHIPMENT_ACTION_TESTID);
      await assertActionDisabled(dropdown, PRODUCTION_ACTION_TESTID);
    });

    // --- Row 3: manufactured product with a shortage -> production non-empty -------------------
    await test.step('manufactured: Sprung zu Produktionsdisposition enabled once material-dispo advises a production candidate', async () => {
      const { viewId, rowId } = await waitForCockpitRow(page, manufacturedProductId);
      await assertActionEnabledAndOpens(page, PRODUCTION_ACTION_TESTID, viewId, rowId);
    });
  });
});
