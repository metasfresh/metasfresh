import { test } from '../../playwright.config';
import { Backend } from './Backend';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT, getPage } from './common';
import { SALES_ORDER_WINDOW_ID } from './WindowIds';
import { waitForRecordSaved, waitForTabAllowsNew } from './WebAPIValidation';

/**
 * Thin E2E harness for the core Sales Order Line grid (window `SALES_ORDER_WINDOW_ID` / Order
 * Line tab `ORDER_LINE_TAB_ID`) — the window every plain-core customer opens, present on the core
 * `-preloaded` CI image.
 *
 * Shared by `auftragsposition-bugfix.spec.js` and any spec extending its coverage (e.g. the
 * layout-jump geometry leg) — kept in a plain utils module, not re-exported from a `.spec.js`
 * file, so importing it never re-registers another file's `test()` cases.
 */

export const ORDER_LINE_TAB_ID = 'AD_Tab-187';
export const DEFAULT_LANGUAGE = 'de_DE';

/**
 * Create a customer and a priced product for this run via the Backend masterdata API.
 *
 * The product MUST carry a resolvable price: the product-lookup typeahead silently excludes
 * any product with no price condition for the order's customer/price-list combination — an
 * out-of-candidate-set product then returns "no results" with no error, which reads like a
 * flake but is a real, silent business-rule filter.
 */
export async function createMasterdata(language = DEFAULT_LANGUAGE) {
  return await Backend.createMasterdata({
    request: {
      login: {
        user: { language, firstname: 'BF', lastname: 'Harness' },
      },
      bpartners: {
        CUSTOMER1: {
          isVendor: false,
          isCustomer: true,
          isSoPriceList: true,
          name: 'Customer',
        },
      },
      products: {
        Product1: {
          name: 'BFPROD',
          type: 'Item',
          prices: [{ price: 12.5, currencyCode: 'EUR' }],
        },
      },
    },
  });
}

/**
 * Navigate to the Sales Order window's list view.
 */
export async function gotoOrderList() {
  return await test.step('Order window — navigate to list', async () => {
    const page = getPage();
    await page.goto(`${FRONTEND_BASE_URL}/window/${SALES_ORDER_WINDOW_ID}`);

    await page
      .locator('.document-list-wrapper, .document-list')
      .waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });

    await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
    await page
      .locator('.rotating, .panel-spaced-lg')
      .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
      .catch(() => {});
    await page.waitForTimeout(500);
  });
}

/**
 * Create a new order via Alt+N and return its record id.
 */
export async function createNewOrder() {
  return await test.step('Order window — create new (Alt+N)', async () => {
    const page = getPage();

    await page.locator('body').click();
    await page.waitForTimeout(200);
    await page.keyboard.press('Alt+N');

    await page.waitForURL(new RegExp(`/window/${SALES_ORDER_WINDOW_ID}/\\d+`), {
      timeout: SLOW_ACTION_TIMEOUT,
    });

    await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
    await page
      .locator('.rotating, .indicator-pending')
      .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
      .catch(() => {});
    await page.waitForTimeout(1000);

    const recordId = page.url().split('/').pop().split('?')[0];
    console.log(`[INFO] Created order ${SALES_ORDER_WINDOW_ID}/${recordId}`);
    return recordId;
  });
}

/**
 * Select the seeded customer on the freshly created order header and wait for the record to
 * save.
 */
export async function selectOrderCustomer(recordId, bpartnerCode) {
  return await test.step(`Order window — select customer: ${bpartnerCode}`, async () => {
    const page = getPage();

    const bpartnerInput = page.locator('#lookup_C_BPartner_ID input.input-field');
    await bpartnerInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await bpartnerInput.click();

    await page
      .locator('#lookup_C_BPartner_ID .rotating, #lookup_C_BPartner_ID .spinner')
      .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
      .catch(() => {});
    await page.waitForTimeout(300);

    await bpartnerInput.fill(bpartnerCode);
    await page.waitForTimeout(500);

    await page
      .locator('#lookup_C_BPartner_ID .rotating, #lookup_C_BPartner_ID .spinner')
      .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
      .catch(() => {});
    await page.waitForTimeout(300);

    await page.locator('.input-dropdown-list-option').getByText(bpartnerCode).first().click();
    await page
      .locator('.input-dropdown-list')
      .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
      .catch(() => {});

    await page.keyboard.press('Tab');
    await page.waitForTimeout(500);

    await page
      .locator('.rotating, .indicator-pending')
      .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
      .catch(() => {});
    await page.waitForTimeout(3000);

    await waitForRecordSaved(SALES_ORDER_WINDOW_ID, recordId, { maxRetries: 20, retryDelayMs: 1000 });
    console.log(`[INFO] Order ${SALES_ORDER_WINDOW_ID}/${recordId} saved with customer ${bpartnerCode}`);
  });
}

/**
 * Add one order line via batch entry (quick input) and submit it, leaving a SAVED line in the
 * order-line grid.
 */
export async function addOrderLine(recordId, { productCode, quantity }) {
  return await test.step(`Order line — add ${productCode} x ${quantity}`, async () => {
    const page = getPage();

    await waitForTabAllowsNew(SALES_ORDER_WINDOW_ID, recordId, ORDER_LINE_TAB_ID, {
      maxRetries: 15,
      retryDelayMs: 1000,
    });

    const batchEntryButton = page.getByTestId('batch-entry-toggle');
    await batchEntryButton.scrollIntoViewIfNeeded();
    await batchEntryButton.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await batchEntryButton.click();

    await page.locator('.quick-input-container').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

    const productInput = page.locator('#lookup_M_Product_ID input.input-field');
    await productInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await productInput.click();

    await page
      .locator('#lookup_M_Product_ID .rotating, #lookup_M_Product_ID .spinner')
      .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
      .catch(() => {});
    await page.waitForTimeout(300);

    await productInput.fill(productCode);
    await page.waitForTimeout(1000);

    await page
      .locator('#lookup_M_Product_ID .rotating, #lookup_M_Product_ID .spinner')
      .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
      .catch(() => {});
    await page.waitForTimeout(500);

    const option = page.locator('.input-dropdown-list-option').getByText(productCode).first();
    await option.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await option.click();
    await page.waitForTimeout(500);

    const quantityInput = page.locator('.quick-input-container').getByRole('spinbutton');
    await quantityInput.waitFor({ state: 'visible', timeout: 5000 }).catch(() => {});
    await quantityInput.click();
    await quantityInput.fill(quantity.toString());
    await page.waitForTimeout(300);

    // "Press 'Enter' to add" (as instructed by the quick-input UI itself)
    await page.keyboard.press('Enter');
    await page.waitForTimeout(2000);

    await page
      .locator('.rotating, .indicator-pending')
      .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
      .catch(() => {});

    const isStillOpen = await page.locator('.quick-input-container').isVisible().catch(() => false);
    if (isStillOpen) {
      await batchEntryButton.click();
      await page.waitForTimeout(1000);
    }
  });
}
