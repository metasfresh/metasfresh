import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { FRONTEND_BASE_URL } from '../utils/common';
import { STOCK_PER_WEEK_WINDOW_ID } from '../utils/WindowIds';

/**
 * Stock per Week (Bestand pro Woche, AD_Window_ID 542159) — product-only filter must show EVERY
 * warehouse the product has stock/demand in.
 *
 * Regression guard: the selection factory persists the applied warehouse FILTER (null for a
 * product-only filter) — not the per-row warehouse — so the page render sources
 * MD_Stock_PerWeek_fn(product, NULL) and the page's join back to the selection restores every
 * warehouse. A prior defect persisted the per-row warehouse and re-parameterized the render with
 * one arbitrary warehouse, collapsing a product-only grid to a single warehouse.
 *
 * Setup: one product with an open sales-order line in EACH of two warehouses → Material Disposition
 * creates DEMAND candidates for the product in both warehouses → MD_Stock_PerWeek_V has rows for the
 * product in both. Filtering the window by product only must return rows spanning BOTH warehouses.
 */

test.describe('Stock per Week — product-only filter spans all warehouses', () => {
  test('Product filter returns rows for every warehouse the product is in', async ({ page }) => {
    allure.epic('E0155: Material Disposition');
    allure.tag('F19100: Stock per week');
    allure.tag('F19100');
    allure.story('Product-only filter shows all warehouses (not just one)');
    allure.severity('critical');

    test.setTimeout(240000); // SO completion + async dispo materialization

    // === SEED: one product, two warehouses, an order line for the product in each warehouse ===
    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: { language: 'de_DE', firstname: 'spw', lastname: 'multiwh' } },
        bpartners: {
          CUSTOMER1: { isVendor: false, isCustomer: true, isSoPriceList: true, name: 'Customer' },
        },
        products: {
          P1: { name: 'SPW_MULTIWH', type: 'Item', prices: [{ price: 30.0, currencyCode: 'EUR' }] },
        },
        warehouses: {
          whA: {},
          whB: {},
        },
        salesOrders: {
          SO_A: { bpartner: 'CUSTOMER1', warehouse: 'whA', datePromised: new Date().toISOString(), lines: [{ product: 'P1', qty: 5 }] },
          SO_B: { bpartner: 'CUSTOMER1', warehouse: 'whB', datePromised: new Date().toISOString(), lines: [{ product: 'P1', qty: 7 }] },
        },
      },
    });
    allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');

    const productId = masterdata.products.P1.id;
    expect(productId, 'seeded product must expose its M_Product_ID').toBeTruthy();

    // === LOGIN ===
    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();
    // Land on the window once so the browser context is authenticated against the same origin.
    await page.goto(`${FRONTEND_BASE_URL}/window/${STOCK_PER_WEEK_WINDOW_ID}`);

    const restBase = await page.evaluate(() => (window.config && window.config.API_URL) || null);
    const apiBase = restBase || process.env.WEBAPI_BASE_URL || 'http://localhost:8080/rest/api';

    // === PRODUCT-ONLY VIEW + assert rows span >= 2 warehouses (retry for async dispo) ===
    await test.step('Product-only view returns rows from both warehouses', async () => {
      let distinctWarehouses = new Set();
      let lastPayload = null;

      // Material Disposition materializes candidates asynchronously; poll the filtered view.
      for (let attempt = 1; attempt <= 12; attempt++) {
        const createResp = await page.request.post(`${apiBase}/documentView/${STOCK_PER_WEEK_WINDOW_ID}`, {
          headers: { 'Content-Type': 'application/json' },
          data: {
            documentType: String(STOCK_PER_WEEK_WINDOW_ID),
            viewType: 'grid',
            filters: [
              { filterId: 'M_Product_ID', parameters: [{ parameterName: 'M_Product_ID', value: productId }] },
            ],
          },
        });
        expect(createResp.ok(), `POST /documentView must succeed (attempt ${attempt})`).toBe(true);
        const view = await createResp.json();

        const rowsResp = await page.request.get(
          `${apiBase}/documentView/${STOCK_PER_WEEK_WINDOW_ID}/${view.viewId}?firstRow=0&pageLength=500`
        );
        expect(rowsResp.ok(), `GET view rows must succeed (attempt ${attempt})`).toBe(true);
        const rows = await rowsResp.json();
        lastPayload = { size: view.size, resultCount: (rows.result || []).length };

        distinctWarehouses = new Set(
          (rows.result || [])
            .map((r) => {
              const f = r.fieldsByName && r.fieldsByName.M_Warehouse_ID;
              const v = f && f.value;
              // Lookup fields serialize as { key, caption }; fall back to the raw value.
              return v && typeof v === 'object' ? v.key : v;
            })
            .filter((w) => w !== null && w !== undefined)
            .map(String)
        );

        console.log(`[INFO] attempt ${attempt}: view size=${view.size}, distinct warehouses=${[...distinctWarehouses].join(',')}`);
        if (distinctWarehouses.size >= 2) break;
        await page.waitForTimeout(10000);
      }

      allure.attachment('Last view payload', JSON.stringify(lastPayload, null, 2), 'application/json');
      allure.attachment('Distinct warehouses', JSON.stringify([...distinctWarehouses]), 'application/json');

      expect(
        distinctWarehouses.size,
        'product-only filter must return rows for BOTH warehouses the product is stocked in (not collapse to one)'
      ).toBeGreaterThanOrEqual(2);
    });

    console.log('[PASS] Stock-per-week product-only filter spans all warehouses');
  });
});
