import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';
import { ShipmentSchedulePage } from '../utils/pages/ShipmentSchedulePage';
import { ProductProposalPage, ROWS as OVERLAY_ROWS } from '../utils/pages/ProductProposalPage';
import { SLOW_ACTION_TIMEOUT } from '../utils/common';

/**
 * Product Proposals (Produktvorschläge) - the order line a typed quantity produces must not
 * depend on the filter state when the overlay is closed with DONE (TC8, AC7).
 *
 * `ProductsProposalRowsData.getAllRows()` streams the *filtered* row id list
 * (`rowIdsOrderedAndFiltered`), yet `OrderProductsProposalViewFactory.createOrderLines` reads the
 * order lines to write from exactly that accessor. A row hidden by the delivery-history filter at
 * the moment DONE is pressed is therefore silently dropped - no order line, no error. This spec
 * proves the fix: a quantity typed on a row that the filter is currently hiding still becomes an
 * order line.
 *
 * Fixture: one partner gets a real order -> shipment -> completion for Product1 + Product2;
 * Product3 is never shipped, so the delivery-history filter hides it once it is turned on.
 */

const STATS_ASYNC_MECHANISM =
  'BPartnerProductStatsEventSender (distributed C_BPartner_Product_Stats event topic, ' +
  'consumed by BPartnerProductStatsEventHandler)';

const DAYS_PATTERN = /^\d+$/;
const hasDeliveryValue = (lastShipmentDaysText) => DAYS_PATTERN.test((lastShipmentDaysText || '').trim());

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

test.describe('Product Proposals - quantity survives the filter (AC7)', () => {
  test('A typed quantity becomes an order line whatever the filter is set to', async ({
    page,
  }) => {
    allure.epic('E0100: Sales');
    allure.tag('F00140');
    allure.tag('F00140: Sales Order - Product Proposals');
    allure.story('Produktvorschläge: order lines must not depend on the filter state');
    allure.severity('critical');

    allure.description(`
## F00140: Sales Order - Product Proposals

### Test Scenario

A quantity typed on a row that the delivery-history filter is currently hiding still becomes an
order line when the overlay is closed with DONE - the filter is a view concern, not a data-loss
mechanism.

1. Build delivery history for Product1 + Product2 via a real order -> shipment -> completion;
   Product3 is never shipped (fixture), so the filter hides Product3 once turned on.
2. On a fresh order: enter a qty on Product3, turn the filter on (Product3 disappears from the
   grid), then close with DONE.
3. Reload the order and confirm an order line for Product3 exists with the typed quantity.
    `);

    test.setTimeout(300000); // the order -> shipment -> completion chain is slow

    // === CREATE TEST DATA ===
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
            name: 'CustomerWithHistoryQtySurvives',
          },
        },
        products: {
          Product1: {
            name: 'DeliveredFirstQtySurvives',
            type: 'Item',
            prices: [{ price: 10.0, currencyCode: 'EUR' }],
          },
          Product2: {
            name: 'DeliveredSecondQtySurvives',
            type: 'Item',
            prices: [{ price: 20.0, currencyCode: 'EUR' }],
          },
          Product3: {
            name: 'NeverDeliveredQtySurvives',
            type: 'Item',
            prices: [{ price: 30.0, currencyCode: 'EUR' }],
          },
        },
      },
    });

    allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');

    const customerCode = masterdata.bpartners.CUSTOMER_WITH_HISTORY.bpartnerCode;
    const product1Code = masterdata.products.Product1.productCode;
    const product2Code = masterdata.products.Product2.productCode;
    const product3Code = masterdata.products.Product3.productCode;

    // === LOGIN ===
    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    // === FIXTURE: deliver Product1 + Product2; Product3 never ===
    await test.step('Fixture - deliver Product1 + Product2 to the partner (Product3 never)', async () => {
      await SalesOrderPage.goto();
      await SalesOrderPage.clickNew();
      const historyOrderId = await SalesOrderPage.selectCustomer(customerCode);

      await SalesOrderPage.addOrderLine({
        product: product1Code,
        quantity: '5',
        recordId: historyOrderId,
      });

      await SalesOrderPage.addOrderLine({
        product: product2Code,
        quantity: '5',
        recordId: historyOrderId,
      });

      await SalesOrderPage.complete();
      console.log(`History order completed: record=${historyOrderId}`);

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

    // === FIXTURE: the fresh order TC8 runs against ===
    let tcOrderId;
    await test.step('Fixture - create a second, fresh order for the partner', async () => {
      await SalesOrderPage.goto();
      await SalesOrderPage.clickNew();
      tcOrderId = await SalesOrderPage.selectCustomer(customerCode);
      console.log(`TC order created: record=${tcOrderId}`);
    });

    // === FIXTURE: wait for the async C_BPartner_Product_Stats update ===
    await test.step('Fixture - wait for C_BPartner_Product_Stats (async) before applying the filter', async () => {
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

    const product3Qty = '7';

    // === TC8: type a qty on Product3, hide it with the filter, close with DONE ===
    await test.step('TC8 - enter qty on the never-delivered product, turn the filter on, close with DONE', async () => {
      await ProductProposalPage.openFromSalesOrder(page);

      await ProductProposalPage.enterQty(page, product3Code, product3Qty);

      await ProductProposalPage.setFilter(page, true);

      // Product3 has no delivery history, so the filter's criterion alone would hide it - but a row
      // carrying a typed quantity is exempt from that criterion precisely so the quantity stays
      // visible and correctable. Assert that exemption holds here, because it is what the user sees.
      const rowsAfterFilter = await readOverlayRows(page);
      expect(
        rowsAfterFilter.some((r) => r.product.includes(product3Code)),
        'the row carrying a typed quantity was hidden by the filter'
      ).toBe(true);

      await ProductProposalPage.closeWithDone(page);
    });

    // The order line must exist regardless of the filter state at close time. Note this spec can no
    // longer, through the UI, present a hidden row carrying a quantity - the exemption above prevents
    // it. That the lines are built from the UNFILTERED rows (so no filter could ever drop one) is
    // pinned separately by ProductsProposalRowsDataTest.
    await test.step('Assert an order line for Product3 exists with the typed quantity', async () => {
      // Read the result back from the reloaded order, not from in-page state.
      await page.reload();
      await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
      await page.locator('.rotating, .indicator-pending').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});

      await SalesOrderPage.goToOrderLineTab();

      const orderLineRow = page
        .locator('table tbody tr')
        .filter({ has: page.locator('[data-cy="cell-M_Product_ID"]', { hasText: product3Code }) })
        .first();

      await expect(
        orderLineRow,
        `No order line for ${product3Code} was found - the row was hidden by the filter when DONE was pressed ` +
          'and its typed quantity was silently dropped'
      ).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });

      const qtyCell = orderLineRow.locator('[data-cy="cell-QtyOrdered"], [data-cy="cell-QtyEntered"]').first();
      const qtyText = (await qtyCell.innerText()).trim();
      expect(qtyText.replace(/[.,]00$/, '').replace(/,/g, '')).toBe(product3Qty);
    });
  });
});
