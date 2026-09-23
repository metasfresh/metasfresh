import { test } from '../../playwright.config';
import { expect } from '@playwright/test';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT, getPage } from '../utils/common';
import { SALES_ORDER_WINDOW_ID } from '../utils/WindowIds';
import { waitForRecordSaved, waitForTabAllowsNew } from '../utils/WebAPIValidation';

/**
 * Thin E2E harness for the core Sales Order Line grid.
 *
 * HARNESS ONLY: login + open + seed helpers that later legs extend with their own `test()` cases
 * IN THIS SAME FILE — do not fork a second spec file for those legs.
 *
 * Targets the core Sales Order window (see `SALES_ORDER_WINDOW_ID`) / Order Line tab — the
 * window every plain-core customer opens, present on the core `-preloaded` CI image. All test
 * data (user, customer, product with a resolvable price) is created fresh per run via the
 * Backend masterdata API, so this spec needs no customer-specific seed data.
 */

const ORDER_LINE_TAB_ID = 'AD_Tab-187';
const LANGUAGE = 'de_DE';

// Order-line grid columns this harness checks render. M_Product_ID is the only Search/Lookup
// (combobox) column among these.
const GRID_COLUMNS = [
  'M_Product_ID',
  'QtyEntered',
  'C_UOM_ID',
  'PriceEntered',
  'M_AttributeSetInstance_ID',
  'Discount',
  'PriceActual',
  'LineNetAmt',
  'C_Tax_ID',
  'Description',
];
const COMBOBOX_COLUMN = 'M_Product_ID';

/**
 * Create a customer and a priced product for this run via the Backend masterdata API.
 *
 * The product MUST carry a resolvable price: the product-lookup typeahead silently excludes
 * any product with no price condition for the order's customer/price-list combination — an
 * out-of-candidate-set product then returns "no results" with no error, which reads like a
 * flake but is a real, silent business-rule filter.
 */
async function createMasterdata() {
  return await Backend.createMasterdata({
    request: {
      login: {
        user: { language: LANGUAGE, firstname: 'BF', lastname: 'Harness' },
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
async function gotoOrderList() {
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
async function createNewOrder() {
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
async function selectOrderCustomer(recordId, bpartnerCode) {
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
async function addOrderLine(recordId, { productCode, quantity }) {
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

test.describe('Sales order-line grid — thin bugfix E2E harness (de_DE)', () => {
  test('Editable order-line grid renders with a saved order line and its expected columns', async ({
    page,
  }) => {
    allure.epic('E0500: Sales Orders');
    allure.tag('F5010: Order Lines Grid');
    allure.tag('F5010');
    allure.story('Order-line grid — thin bugfix E2E harness');
    allure.severity('critical');
    allure.description(`
## Thin bugfix E2E harness

Login (fresh masterdata user, de_DE) -> open the core Sales Order window -> create a new order ->
select the seeded customer -> add one order line via batch entry, leaving it SAVED -> smoke-assert
the order-line grid renders every expected column, including the Search/Lookup (combobox) column,
and that its cell editor opens as a combobox.

This is the reusable harness (login/open/seed) that later legs extend with their own defect
assertions in this same file.
    `);

    test.setTimeout(180000);

    const masterdata = await createMasterdata();
    allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    await gotoOrderList();

    const recordId = await createNewOrder();
    await selectOrderCustomer(recordId, masterdata.bpartners.CUSTOMER1.bpartnerCode);
    await addOrderLine(recordId, { productCode: masterdata.products.Product1.productCode, quantity: 1 });

    await test.step('Order-line grid renders every expected column', async () => {
      for (const column of GRID_COLUMNS) {
        await expect(
          page.getByTestId(`column-${column}`),
          `grid column ${column} must render`
        ).toBeVisible();
      }
    });

    await test.step('Order-line grid has at least one saved order line', async () => {
      const rows = page.locator('table tbody tr');
      await rows.first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      expect(await rows.count(), 'grid must have at least one saved order line').toBeGreaterThan(0);
    });

    await test.step('The combobox (Search/Lookup) cell opens its editor as a dropdown', async () => {
      const comboboxCell = page.locator(`[data-cy="cell-${COMBOBOX_COLUMN}"]`).first();
      await comboboxCell.dblclick();

      const editor = page.locator('.input-dropdown-container').first();
      await editor.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      await page.keyboard.press('Escape');
    });

    const screenshotBuffer = await page.screenshot();
    allure.attachment('Order-line grid (de_DE)', screenshotBuffer, 'image/png');
  });
});
