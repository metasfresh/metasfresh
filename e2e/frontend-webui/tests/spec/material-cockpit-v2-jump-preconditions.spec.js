import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { FRONTEND_BASE_URL } from '../utils/common';

/**
 * Material Cockpit v2 — the three bespoke jump actions must be offered as disabled-with-a-reason
 * when their jump target holds no records, and offered enabled (and must actually jump) when it
 * holds at least one.
 *
 * Today all three (`QtyDemand_QtySupply_V_to_ReceiptSchedule`, `..._to_ShipmentSchedule`,
 * `..._to_PP_Order_Candidate`) accept unconditionally, so an empty target silently does nothing —
 * this spec's three "disabled when empty" assertions are RED against that code.
 *
 * Three cockpit rows, one per stream, each built so exactly one of its two candidate streams is
 * empty and the other already covers the over-rejection guard for free:
 *  - `salesOnly`     — a sales order only  -> M_ShipmentSchedule non-empty, M_ReceiptSchedule empty.
 *  - `purchaseOnly`  — a purchase order only -> M_ReceiptSchedule non-empty, M_ShipmentSchedule AND
 *                       PP_Order_Candidate empty (it is not a manufactured product).
 *  - `manufactured`  — a BOM + PP_Product_Planning(isManufactured) + a sales order big enough to
 *                       push its ATP negative, so material-dispo advises a PP_Order_Candidate.
 *
 * Within each step the ENABLED (over-rejection guard) check runs before the DISABLED one, and the
 * disabled checks are soft — see `assertActionDisabled`.
 *
 * The production candidate is generated asynchronously by material-dispo (mirrors
 * `productionCandidate.feature`'s "after not more than 60s, PP_Order_Candidates are found"), so its
 * enabled+opens check retries for up to two minutes; the other two streams are settled
 * synchronously by the masterdata API before it returns (shipment schedule) or shortly after
 * (receipt schedule), so the same retry helper is reused there mostly to be safe against timing.
 */

const MATERIAL_COCKPIT_V2_WINDOW_ID = 541963;

// QuickActionsDropdown.js renders each entry as
// `data-testid="quick-action-${action.internalName || action.processId || 'unknown'}"`. For an
// AD_Process-backed action `internalName` is always `AD_Process.Value`
// (ADProcessDescriptorsFactory#retrieveProcessDescriptor: `.setInternalName(InternalName.ofString(adProcess.getValue()))`),
// confirmed against the live DB (`SELECT AD_Process_ID, Value FROM AD_Process WHERE AD_Process_ID
// IN (585513,585514,585516)`) and the Value columns in
// backend/de.metas.ui.web.base/src/main/sql/postgresql/system/41-de.metas.ui.web.base/5774370_material_cockpit_v2_processes.sql.
// The trailing German AD_Process_Trl.Name (base language) captions are documentation only — never
// used to select or assert.
const RECEIPT_ACTION_TESTID = 'quick-action-QtyDemand_QtySupply_V_to_ReceiptSchedule'; // AD_Process_ID 585514, "Sprung zu Wareneingangsdispo"
const SHIPMENT_ACTION_TESTID = 'quick-action-QtyDemand_QtySupply_V_to_ShipmentSchedule'; // AD_Process_ID 585513, "Sprung zu Lieferdisposition"
const PRODUCTION_ACTION_TESTID = 'quick-action-QtyDemand_QtySupply_V_to_PP_Order_Candidate'; // AD_Process_ID 585516, "Sprung zu Produktionsdisposition"

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
 * SOFT on purpose. These are the three assertions this spec is RED on, and they go green one stream
 * at a time as each jump is migrated onto the precondition base. A hard assertion would abort the
 * run at the first still-unmigrated stream, hiding whether the other two had regressed — and hiding
 * the over-rejection guards below them. Soft reports all three every run; the test still fails
 * while any of them is unmet.
 */
async function assertActionDisabled(dropdown, testId) {
  const entry = dropdown.getByTestId(testId);
  await expect(entry, `${testId} must be offered`).toBeVisible();
  await expect.soft(entry, `${testId} must be disabled — its jump target holds no records`).toHaveClass(/quick-actions-item-disabled/);
  // Language-invariant reason check: the disabled reason is rendered as `<p className="one-line">
  // <small>({action.disabledReason})</small></p>` beside the caption (QuickActionsDropdown.js) —
  // assert that element is present with non-empty content, not its (localized) text.
  await expect
    .soft(entry.locator('p.one-line small'), `${testId} must state why it is disabled, beside its caption`)
    .toContainText(/\S/);
}

/**
 * Non-empty case (the over-rejection guard): enabled, no reason text, and the jump actually opens
 * the target grid. Retries the whole open-dropdown/click/assert cycle: the underlying record can
 * still be in flight from the async material-dispo pipeline when the row was first selected.
 */
async function assertActionEnabledAndOpens(page, testId, { timeout = 120000 } = {}) {
  await expect(async () => {
    const dropdown = await openQuickActionsDropdown(page);
    const entry = dropdown.getByTestId(testId);
    await expect(entry, `${testId} must be offered`).toBeVisible();
    await expect(entry, `${testId} must not be disabled once its target holds records`).not.toHaveClass(/quick-actions-item-disabled/);
    // The reason <p>/<small> is rendered only when `action.disabled` (QuickActionsDropdown.js), so
    // once enabled it must not be rendered at all — a language-invariant structural check.
    await expect(entry.locator('p.one-line small'), `${testId} must not show a disabled-reason once its target holds records`).toHaveCount(0);
    await entry.click();
    await expect(page.locator('.document-list-wrapper, .document-list'), `${testId} must open the target grid`).toBeVisible({ timeout: 5000 });
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

    test.setTimeout(300000);

    // --- Seed masterdata -----------------------------------------------------------------------
    // Split into two independent `createMasterdata` calls with separate (non-shared) contexts.
    // `CreateBPartnerCommand.createPricingSystem()` (backend/de.metas.frontend-testing/.../bpartner/
    // CreateBPartnerCommand.java:398-404) reuses a single M_PricingSystem/M_PriceList per
    // MasterdataContext, fixed by the FIRST bpartner's IsSOPriceList; `CreateProductCommand.
    // createPrice()` then does `context.getIdOfType(PriceListVersionId.class)`, which throws if the
    // context holds more than one price-list version. A single request can therefore support a
    // working sales order OR a working purchase order, never both. Each call below gets its own
    // MasterdataContext (a fresh one per REST call — CreateMasterdataCommand.execute()), so each
    // gets its own pricing system with its own direction.
    const datePromised = new Date(Date.now() + 24 * 60 * 60 * 1000).toISOString();

    // Call 1 — sales context: customer bpartner (IsSOPriceList=true -> sales price list), the
    // salesOnly + manufactured products (with the manufactured product's BOM + product planning),
    // and both sales orders. No vendor bpartner, no purchase order here.
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
        // Marks `manufactured` as isManufactured=true — must exist before its sales order below,
        // so the demand event that order raises is matched by PPOrderCandidateDemandMatcher.
        productPlannings: {
          manufacturedPlanning: { product: 'manufactured', warehouse: 'wh' },
        },
        salesOrders: {
          soSalesOnly: {
            bpartner: 'bp1',
            warehouse: 'wh',
            datePromised,
            lines: [{ product: 'salesOnly', qty: ORDER_QTY }],
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

    // Call 2 — purchase context: vendor bpartner (IsSOPriceList=false -> purchase price list), the
    // purchaseOnly product, and the purchase order. Independent context — never pass call 1's
    // `context` in here, or the pricing system would be reused and the bug returns.
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

    // Merge: the three product ids and the login user must resolve off one `masterdata` object.
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

      // Enabled path first: it is a hard assertion, and if the masterdata never materialised we want
      // to fail on THAT rather than on a disabled-check that was never going to be meaningful.
      await selectCockpitRow(page, viewId, rowId);
      await assertActionEnabledAndOpens(page, SHIPMENT_ACTION_TESTID);

      // Re-select: the click above navigated away to the shipment-schedule grid.
      await selectCockpitRow(page, viewId, rowId);
      const dropdown = await openQuickActionsDropdown(page);
      await assertActionDisabled(dropdown, RECEIPT_ACTION_TESTID);
    });

    // --- Row 2: purchase order only -> shipment + production empty, receipt non-empty ----------
    await test.step('purchaseOnly: Sprung zu Lieferdisposition and Sprung zu Produktionsdisposition disabled, Sprung zu Wareneingangsdispo enabled', async () => {
      const { viewId, rowId } = await waitForCockpitRow(page, purchaseOnlyProductId);

      await selectCockpitRow(page, viewId, rowId);
      await assertActionEnabledAndOpens(page, RECEIPT_ACTION_TESTID);

      await selectCockpitRow(page, viewId, rowId);
      const dropdown = await openQuickActionsDropdown(page);
      await assertActionDisabled(dropdown, SHIPMENT_ACTION_TESTID);
      await assertActionDisabled(dropdown, PRODUCTION_ACTION_TESTID);
    });

    // --- Row 3: manufactured product with a shortage -> production non-empty -------------------
    await test.step('manufactured: Sprung zu Produktionsdisposition enabled once material-dispo advises a production candidate', async () => {
      const { viewId, rowId } = await waitForCockpitRow(page, manufacturedProductId);
      await selectCockpitRow(page, viewId, rowId);
      await assertActionEnabledAndOpens(page, PRODUCTION_ACTION_TESTID);
    });
  });
});
