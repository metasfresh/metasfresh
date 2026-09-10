import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';
import { ShipmentSchedulePage } from '../utils/pages/ShipmentSchedulePage';
import { ProductProposalPage } from '../utils/pages/ProductProposalPage';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT } from '../utils/common';
import { SALES_ORDER_WINDOW_ID } from '../utils/WindowIds';

/**
 * Product Proposals (Produktvorschläge) - "delivered-only" filter E2E (TC1-TC4).
 *
 * The overlay lists every product of the active price-list version. This spec proves the
 * real-world case: a salesperson re-ordering a regular customer's assortment restricts the list
 * to the products this partner has already had delivered, instead of scanning past products the
 * customer has never bought.
 *
 * Fixture (shared across all four cases):
 * 1. One partner (CUSTOMER_WITH_HISTORY) gets a real order -> shipment -> completion for
 *    Product1 + Product2. Product3 is never shipped to them.
 * 2. A second partner (CUSTOMER_NO_HISTORY) never receives anything (TC4).
 * 3. `C_BPartner_Product_Stats` is populated asynchronously after shipment completion
 *    (interceptor/M_InOut.java:58 -> BPartnerProductStatsEventSender -> distributed event bus ->
 *    BPartnerProductStatsEventHandler). The overlay's loader only attaches lastShipmentDays at
 *    view-construction time, so the spec polls by repeatedly opening a FRESH overlay until the
 *    value appears - asserting immediately would be a guaranteed flake.
 *
 * All assertions read row content from the rendered UI (product cells / row count), never via
 * page.request, per the workspace E2E conventions.
 */

// Same overlay scoping ProductProposalPage.js uses internally (not exported): the overlay is a
// raw-modal panel layered over the still-mounted order window, so an unscoped `table tbody tr`
// would also match the order lines grid behind it.
const OVERLAY_ROWS = '.raw-modal .panel-modal table tbody tr';
const OVERLAY_FILTER_CHECKBOX =
  '.raw-modal .panel-modal .filters-frequent .inline-filters label.input-checkbox input[type="checkbox"]';

// Named so a poll timeout points straight at the mechanism, not just "value never appeared".
const STATS_ASYNC_MECHANISM =
  'BPartnerProductStatsEventSender (distributed C_BPartner_Product_Stats event topic, ' +
  'consumed by BPartnerProductStatsEventHandler)';

const DAYS_PATTERN = /^\d+$/;
const hasDeliveryValue = (lastShipmentDaysText) => DAYS_PATTERN.test((lastShipmentDaysText || '').trim());

/**
 * Read every row of the currently-rendered overlay grid: product cell text (which contains the
 * product's code - the same signal other specs in this suite match on, e.g.
 * `inline-edit.spec.js` matching `masterdata.products.Product1.productCode` in rendered text) and
 * the raw "Tage vergangen" (lastShipmentDays) cell text ('' when the value is null).
 */
async function readOverlayRows(page) {
  const rows = page.locator(OVERLAY_ROWS);
  const count = await rows.count();

  const result = [];
  for (let i = 0; i < count; i += 1) {
    const row = rows.nth(i);
    const product = (await row.locator('td[data-cy="cell-product"]').innerText()).trim();
    const lastShipmentDays = (await row.locator('td[data-cy="cell-lastShipmentDays"]').innerText()).trim();
    result.push({ product, lastShipmentDays });
  }

  return result;
}

test.describe('Product Proposals - delivered-only filter', () => {
  test('Filter Produktvorschläge to products already delivered to this partner (TC1-TC4)', async ({ page }) => {
    allure.epic('E0100: Sales');
    allure.tag('F00140: Sales Order - Product Proposals');
    allure.story('Produktvorschläge: restrict to products already delivered');
    allure.severity('critical');

    allure.description(`
## F00140: Sales Order - Product Proposals

### Test Scenario (TC1, TC2, TC3, TC4)

A salesperson re-ordering a regular customer's assortment restricts the *Produktvorschläge*
overlay to the products this partner has already had delivered, instead of scanning past products
the customer has never bought.

1. Build delivery history for Product1 + Product2 via a real order -> shipment -> completion;
   Product3 is never shipped (fixture).
2. Poll for the async \`C_BPartner_Product_Stats\` update before asserting anything (fixture).
3. **TC2** - a freshly opened overlay lists all three products with the filter present but off.
4. **TC1** - switching the filter on narrows the list to Product1 + Product2.
5. **TC3** - switching it back off restores the full list.
6. **TC4** - a partner with no delivery history at all sees an empty list with the filter on, and
   no error.
    `);

    test.setTimeout(300000); // the order -> shipment -> completion chain is slow

    // === CREATE TEST DATA ===
    // Both bpartners share the SAME M_PriceList_Version - CreateBPartnerCommand reuses the unique
    // PricingSystemId already in the masterdata context instead of creating a second one - so both
    // partners see the same 3 products in the overlay; only their delivery history differs.
    const masterdata = await Backend.createMasterdata({
      request: {
        login: {
          user: { language: 'en_US', firstname: 'first', lastname: 'last' },
        },
        bpartners: {
          CUSTOMER_WITH_HISTORY: {
            isVendor: false,
            isCustomer: true,
            isSoPriceList: true,
            name: 'CustomerWithHistory',
          },
          CUSTOMER_NO_HISTORY: {
            isVendor: false,
            isCustomer: true,
            isSoPriceList: true,
            name: 'CustomerNoHistory',
          },
        },
        products: {
          Product1: {
            name: 'DeliveredFirst',
            type: 'Item',
            prices: [{ price: 10.0, currencyCode: 'EUR' }],
          },
          Product2: {
            name: 'DeliveredSecond',
            type: 'Item',
            prices: [{ price: 20.0, currencyCode: 'EUR' }],
          },
          Product3: {
            name: 'NeverDelivered',
            type: 'Item',
            prices: [{ price: 30.0, currencyCode: 'EUR' }],
          },
        },
      },
    });

    allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');

    const customerWithHistoryCode = masterdata.bpartners.CUSTOMER_WITH_HISTORY.bpartnerCode;
    const customerNoHistoryCode = masterdata.bpartners.CUSTOMER_NO_HISTORY.bpartnerCode;
    const product1Code = masterdata.products.Product1.productCode;
    const product2Code = masterdata.products.Product2.productCode;
    const product3Code = masterdata.products.Product3.productCode;

    // === LOGIN ===
    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    // === FIXTURE STEP 1: deliver Product1 + Product2 to CUSTOMER_WITH_HISTORY; Product3 never ===
    await test.step('Fixture - deliver Product1 + Product2 to CUSTOMER_WITH_HISTORY (Product3 never)', async () => {
      await SalesOrderPage.goto();
      await SalesOrderPage.clickNew();
      const historyOrderId = await SalesOrderPage.selectCustomer(customerWithHistoryCode);

      await SalesOrderPage.addOrderLine({
        product: product1Code,
        quantity: '5',
        recordId: historyOrderId,
      });

      // Reload before adding the second line. SalesOrderPage.addOrderLine() reuses the same
      // batch-entry toggle button across calls; back-to-back calls on one order can leave that
      // toggle in a state where the second click closes an already-open panel instead of opening
      // it, so the second line silently never reaches the server (known sharp edge of the shared
      // helper - see compensation-group-bundle.spec.js's own note on addOrderLine's fragility). A
      // full reload guarantees the batch-entry panel starts closed for the second call.
      await page.goto(`${FRONTEND_BASE_URL}/window/${SALES_ORDER_WINDOW_ID}/${historyOrderId}`);
      await page.locator('.rotating, .panel-spaced-lg').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
      await page.waitForTimeout(1000);

      await SalesOrderPage.addOrderLine({
        product: product2Code,
        quantity: '5',
        recordId: historyOrderId,
      });

      await SalesOrderPage.complete();
      console.log(`History order completed: record=${historyOrderId}`);

      // Wait for async schedule creation (same pattern as document-references.spec.js)
      await page.waitForTimeout(5000);

      await SalesOrderPage.openRelatedShipmentCandidate({
        maxRetries: 10,
        retryDelay: 3000,
        refreshOnRetry: true,
      });
      await ShipmentSchedulePage.expectVisible();
      await ShipmentSchedulePage.createShipment();
      console.log('Shipment created and completed for Product1 + Product2');
    });

    // === FIXTURE STEP 2: the second, fresh sales order TC1/TC2/TC3 run against ===
    await test.step('Fixture - create a second, fresh order for CUSTOMER_WITH_HISTORY', async () => {
      await SalesOrderPage.goto();
      await SalesOrderPage.clickNew();
      const tcOrderId = await SalesOrderPage.selectCustomer(customerWithHistoryCode);
      console.log(`TC order created: record=${tcOrderId}`);
    });

    // === FIXTURE STEP 3: wait for the async C_BPartner_Product_Stats update ===
    // Shipment completion only ENQUEUES the stats update; the overlay's loader attaches
    // lastShipmentDays only at view-construction time, so every poll attempt must open a
    // genuinely FRESH overlay (close + reopen), not just re-read an already-open one.
    await test.step('Fixture - wait for C_BPartner_Product_Stats (async) before any filter assertion', async () => {
      await expect
        .poll(
          async () => {
            await ProductProposalPage.openFromSalesOrder(page);
            const rows = await readOverlayRows(page);
            await ProductProposalPage.closeWithDone(page);

            const row1 = rows.find((r) => r.product.includes(product1Code));
            const row2 = rows.find((r) => r.product.includes(product2Code));

            return Boolean(
              row1 && hasDeliveryValue(row1.lastShipmentDays) && row2 && hasDeliveryValue(row2.lastShipmentDays)
            );
          },
          {
            timeout: 120000,
            message:
              'Tage vergangen (lastShipmentDays) never appeared for Product1/Product2 - the async stats update ' +
              `(${STATS_ASYNC_MECHANISM}) was not consumed within the timeout`,
          }
        )
        .toBe(true);
    });

    // === TC2: freshly opened overlay - filter present, off, full row set ===
    await test.step('TC2 - overlay opens with the filter present but not active; all three products listed', async () => {
      await ProductProposalPage.openFromSalesOrder(page);

      expect(await ProductProposalPage.getRowCount(page)).toBe(3);

      const rows = await readOverlayRows(page);
      const row1 = rows.find((r) => r.product.includes(product1Code));
      const row2 = rows.find((r) => r.product.includes(product2Code));
      const row3 = rows.find((r) => r.product.includes(product3Code));

      expect(row1, 'Product1 row missing from the unfiltered overlay').toBeTruthy();
      expect(row2, 'Product2 row missing from the unfiltered overlay').toBeTruthy();
      expect(row3, 'Product3 row missing from the unfiltered overlay').toBeTruthy();
      expect(hasDeliveryValue(row1.lastShipmentDays)).toBe(true);
      expect(hasDeliveryValue(row2.lastShipmentDays)).toBe(true);
      expect(hasDeliveryValue(row3.lastShipmentDays)).toBe(false); // Product3 was never shipped

      // The filter control itself must be present, and unchecked (AC2, AC3)
      const filterCheckbox = page.locator(OVERLAY_FILTER_CHECKBOX).first();
      await expect(filterCheckbox).toBeAttached();
      await expect(filterCheckbox).not.toBeChecked();
    });

    // === TC1: switch the filter on - only the delivered products remain ===
    await test.step('TC1 - filter on narrows the list to the delivered products', async () => {
      await ProductProposalPage.setFilter(page, true);

      expect(await ProductProposalPage.getRowCount(page)).toBe(2);

      const rows = await readOverlayRows(page);
      const productsShown = rows.map((r) => r.product);

      expect(productsShown.some((p) => p.includes(product1Code))).toBe(true);
      expect(productsShown.some((p) => p.includes(product2Code))).toBe(true);
      expect(productsShown.some((p) => p.includes(product3Code))).toBe(false);

      for (const row of rows) {
        expect(hasDeliveryValue(row.lastShipmentDays), `row for ${row.product} has no Tage vergangen value`).toBe(
          true
        );
      }
    });

    // === TC3: switch the filter off again - the full list returns ===
    await test.step('TC3 - filter off restores the full list', async () => {
      await ProductProposalPage.setFilter(page, false);

      expect(await ProductProposalPage.getRowCount(page)).toBe(3);

      const rows = await readOverlayRows(page);
      const productsShown = rows.map((r) => r.product);
      expect(productsShown.some((p) => p.includes(product1Code))).toBe(true);
      expect(productsShown.some((p) => p.includes(product2Code))).toBe(true);
      expect(productsShown.some((p) => p.includes(product3Code))).toBe(true);
    });

    await ProductProposalPage.closeWithDone(page);

    // === TC4: a partner with no delivery history at all ===
    await test.step('TC4 - partner with no delivery history: filter on yields an empty list, no error', async () => {
      await SalesOrderPage.goto();
      await SalesOrderPage.clickNew();
      await SalesOrderPage.selectCustomer(customerNoHistoryCode);

      await ProductProposalPage.openFromSalesOrder(page);

      // Sanity: same shared price list, unfiltered - all three products are still offered; the
      // difference from CUSTOMER_WITH_HISTORY is purely the (absent) delivery history.
      expect(await ProductProposalPage.getRowCount(page)).toBe(3);

      await ProductProposalPage.setFilter(page, true);

      expect(await ProductProposalPage.getRowCount(page)).toBe(0);

      const errorToast = page.locator('.Toastify div[role="alert"].Toastify__toast-body');
      await expect(errorToast).toHaveCount(0);
    });
  });
});
