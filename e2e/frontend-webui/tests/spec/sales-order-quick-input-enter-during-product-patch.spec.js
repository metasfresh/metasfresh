import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';
import { SALES_ORDER_WINDOW_ID } from '../utils/WindowIds';

const WEBAPI_BASE_URL = process.env.WEBAPI_BASE_URL || 'http://localhost:8080/rest/api';
const ORDER_LINES_TAB_ID = 'AD_Tab-187';

/** How long the product PATCH response is held back: far longer than typing the qty + Enter takes. */
const PRODUCT_PATCH_DELAY_MS = 4000;

/**
 * Real-life case: in the sales order batch entry the user picks a product, types the quantity and
 * presses Enter before the server has answered the product PATCH (a slow server or network is enough).
 *
 * The line must still be added, with that product and that quantity. Before the fix the frontend
 * validated against the not-yet-updated product field ("Mandatory fields are not filled!"), sent no
 * quickInput/complete, and also dropped the quantity PATCH.
 *
 * The slow server is simulated by holding back the product PATCH response with page.route.
 */
test.describe('Sales order quick entry - Enter while the product PATCH is in flight', () => {
  test.beforeEach(() => {
    allure.epic('E0100: Sales');
    allure.tag('F00101: Sales Order Quick Entry');
  });

  // `page` is requested so the custom fixture in playwright.config.js publishes it globally.
  test('the line is added with the picked product and the typed qty', async ({ page }) => {
    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: { language: 'en_US' } },
        bpartners: {
          CUSTOMER: { isVendor: false, isCustomer: true, isSoPriceList: true, name: 'Customer' },
        },
        products: {
          PROD: { name: 'PROD', type: 'Item', prices: [{ price: 10, currencyCode: 'EUR' }] },
        },
      },
    });
    const customer = masterdata.bpartners.CUSTOMER.bpartnerCode;
    const product = masterdata.products.PROD.productName;

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);

    await SalesOrderPage.goto();
    await SalesOrderPage.clickNew();
    const recordId = await SalesOrderPage.selectCustomer(customer);

    // Hold back the response of the quick-input PATCH that sets the product.
    let productPatchAnsweredAt = null;
    await page.route('**/quickInput/**', async (route) => {
      const request = route.request();
      if (request.method() === 'PATCH' && (request.postData() || '').includes('M_Product_ID')) {
        const response = await route.fetch();
        await new Promise((resolve) => setTimeout(resolve, PRODUCT_PATCH_DELAY_MS));
        productPatchAnsweredAt = Date.now();
        await route.fulfill({ response });
      } else {
        await route.continue();
      }
    });

    // Picks the product and returns while its PATCH is still held back (the helper waits only 1.5 s).
    await SalesOrderPage.openQuickEntryAndSelectProduct({ product, recordId });
    expect(productPatchAnsweredAt, 'product PATCH must still be in flight').toBeNull();

    const completed = page.waitForResponse(
      (r) => r.request().method() === 'POST' && /\/quickInput\/[^/]+\/complete/.test(r.url()),
      { timeout: PRODUCT_PATCH_DELAY_MS + 15000 }
    );
    completed.catch(() => {}); // awaited below; avoids an unhandled rejection if a step throws first

    await page.getByRole('spinbutton').fill('7');
    await page.keyboard.press('Enter');
    const enterPressedAt = Date.now();

    // Enter was pressed before the product PATCH was answered.
    expect(productPatchAnsweredAt).toBeNull();

    const completeResponse = await completed;
    expect(completeResponse.ok()).toBe(true);
    expect(productPatchAnsweredAt).not.toBeNull();
    expect(productPatchAnsweredAt).toBeGreaterThan(enterPressedAt);

    // The user saw no validation error.
    await expect(page.getByText('Mandatory fields are not filled!')).toHaveCount(0);

    // The line is shown in the order lines grid ...
    await expect(page.locator('table tbody tr').filter({ hasText: product })).toHaveCount(1, {
      timeout: 15000,
    });

    // ... and, read back from the server, it has the picked product and the typed qty.
    const linesResponse = await page.request.get(
      `${WEBAPI_BASE_URL}/window/${SALES_ORDER_WINDOW_ID}/${recordId}/${ORDER_LINES_TAB_ID}`
    );
    expect(linesResponse.ok()).toBe(true);
    const linesJson = await linesResponse.json();
    const lines = Array.isArray(linesJson) ? linesJson : linesJson.result || [];
    expect(lines).toHaveLength(1);
    const fields = lines[0].fieldsByName;
    expect(fields.M_Product_ID.value.caption).toContain(product);
    expect(Number(fields.QtyEntered.value)).toBe(7);
  });
});
