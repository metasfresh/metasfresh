import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';
import { PurchaseOrderPage } from '../utils/pages/PurchaseOrderPage';
import { SLOW_ACTION_TIMEOUT } from '../utils/common';
import { SALES_ORDER_WINDOW_ID, PURCHASE_ORDER_WINDOW_ID } from '../utils/WindowIds';
import { waitForTabAllowsNew } from '../utils/WebAPIValidation';

/**
 * Product Purchase/Sales Gate E2E test suite.
 *
 * Features tested:
 * - F00315: Produktfreigabe für Einkauf  (IsPurchased gate)
 * - F00320: Produktfreigabe für Verkauf  (IsSold gate)
 *
 * The gate is controlled by SysConfig M_Product_EnforcePurchaseSalesFlags.
 * This spec enables it via the masterdata sysconfigs map (auto-restored after the run).
 *
 * Tests:
 * 1. Sales order / batch entry: IsSold=N product is absent from picker; IsSold=Y product appears
 * 2. Purchase order / batch entry: IsPurchased=N product is absent from picker; IsPurchased=Y appears
 */

// ============================================================================
// Shared helpers
// ============================================================================

/**
 * Create masterdata for the gate tests.
 *
 * Enables the gate via the sysconfigs map (auto-restored after the test run).
 * Creates:
 *  - One user (for login)
 *  - One customer  (for sales order)
 *  - One vendor    (for purchase order)
 *  - BlockedProduct: IsSold=false, IsPurchased=false
 *  - ControlProduct: IsSold=true, IsPurchased=true (API defaults — explicitly set for clarity)
 *
 * @param {string} language
 */
async function createMasterdata(language) {
  return await Backend.createMasterdata({
    request: {
      sysconfigs: {
        M_Product_EnforcePurchaseSalesFlags: 'Y',
      },
      login: {
        user: {
          language,
          firstname: 'PSG',
          lastname: 'Test',
        },
      },
      bpartners: {
        CUSTOMER1: {
          isVendor: false,
          isCustomer: true,
          isSoPriceList: true,
          name: 'PSGCustomer',
        },
        VENDOR1: {
          isVendor: true,
          isCustomer: false,
          isSoPriceList: false,
          name: 'PSGVendor',
        },
      },
      products: {
        BlockedProduct: {
          name: 'PSGBLOCKED',
          type: 'Item',
          isSold: false,
          isPurchased: false,
          prices: [{ price: 5.0, currencyCode: 'EUR' }],
        },
        ControlProduct: {
          name: 'PSGCONTROL',
          type: 'Item',
          isSold: true,
          isPurchased: true,
          prices: [{ price: 10.0, currencyCode: 'EUR' }],
        },
      },
    },
  });
}

/**
 * Open a sales or purchase order in batch-entry mode.
 * Returns the batchEntryButton locator (still clicked-open).
 *
 * @param {import('@playwright/test').Page} page
 * @param {object} masterdata
 * @param {string} orderType - 'sales' | 'purchase'
 * @param {string} language
 */
async function openOrderInBatchEntryMode(page, masterdata, orderType, language) {
  await LoginPage.goto();
  await LoginPage.login(masterdata.login.user);
  await DashboardPage.expectVisible();

  if (orderType === 'sales') {
    await SalesOrderPage.goto();
    await SalesOrderPage.clickNew();

    const recordId = await SalesOrderPage.selectCustomer(
      masterdata.bpartners.CUSTOMER1.bpartnerCode
    );
    console.log(`[${language}] Sales Order ${recordId} created`);

    await SalesOrderPage.goToOrderLineTab();

    await waitForTabAllowsNew(SALES_ORDER_WINDOW_ID, recordId, 'AD_Tab-187', {
      maxRetries: 15,
      retryDelayMs: 1000,
    });
  } else {
    // purchase
    await PurchaseOrderPage.goto();
    await PurchaseOrderPage.clickNew();

    const recordId = await PurchaseOrderPage.selectBusinessPartner(
      masterdata.bpartners.VENDOR1.bpartnerCode
    );
    console.log(`[${language}] Purchase Order ${recordId} created`);

    await PurchaseOrderPage.goToOrderLineTab();

    await waitForTabAllowsNew(PURCHASE_ORDER_WINDOW_ID, recordId, 'AD_Tab-293', {
      maxRetries: 15,
      retryDelayMs: 1000,
    });
  }

  const batchEntryButton = page.getByTestId('batch-entry-toggle');
  await batchEntryButton.scrollIntoViewIfNeeded();
  await batchEntryButton.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await batchEntryButton.click();

  await page.locator('.quick-input-container').waitFor({
    state: 'visible',
    timeout: SLOW_ACTION_TIMEOUT,
  });

  return batchEntryButton;
}

/**
 * Type a product code/name into the quick-input product field,
 * wait for the search to settle, and return the input locator.
 *
 * Mirrors typeProductAndWaitForDropdown() in quick-input.spec.js exactly.
 *
 * @param {import('@playwright/test').Page} page
 * @param {string} productCode
 */
async function typeProductAndWaitForDropdown(page, productCode) {
  const productInput = page.locator('#lookup_M_Product_ID input.input-field');
  await productInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await productInput.click();

  await page
    .locator('#lookup_M_Product_ID .rotating, #lookup_M_Product_ID .spinner')
    .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
    .catch(() => {});

  await page.waitForTimeout(300);

  await productInput.fill(productCode);

  // Wait for typeahead debounce + search
  await page.waitForTimeout(500);

  await page
    .locator('#lookup_M_Product_ID .rotating, #lookup_M_Product_ID .spinner')
    .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
    .catch(() => {});

  // Wait for dropdown to populate (or remain empty)
  await page.waitForTimeout(300);

  return productInput;
}

/**
 * Assert that a product IS offered in the quick-input dropdown.
 * Mirrors the positive assertion in quick-input.spec.js (mouse-click test).
 *
 * @param {import('@playwright/test').Page} page
 * @param {string} productCode
 */
async function expectProductInPicker(page, productCode) {
  const option = page.locator('.input-dropdown-list-option').getByText(productCode).first();
  await option.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  // No further assertion needed — waitFor(visible) IS the assertion
  console.log(`[PASS] Control product "${productCode}" is visible in picker`);
}

/**
 * Assert that a product is NOT offered in the quick-input dropdown.
 *
 * Strategy: after the search spinner settles and the debounce window
 * passes (same wait budget as the positive case), assert that no
 * .input-dropdown-list-option contains the product code.
 *
 * We deliberately avoid waitFor(state:'visible') here because the picker
 * may show a "no results" placeholder or an empty list — both are correct.
 *
 * @param {import('@playwright/test').Page} page
 * @param {string} productCode
 */
async function expectProductAbsentFromPicker(page, productCode) {
  // Give the UI the same settling time as the positive case
  await page.waitForTimeout(800);

  const matchingOptions = page.locator('.input-dropdown-list-option').getByText(productCode);
  const count = await matchingOptions.count();
  expect(count).toBe(0);
  console.log(`[PASS] Blocked product "${productCode}" is absent from picker (count=${count})`);
}

// ============================================================================
// Test cases
// ============================================================================

const testCases = [
  { language: 'en_US', label: 'English' },
  { language: 'de_DE', label: 'German' },
];

testCases.forEach(({ language, label }) => {
  test.describe(`Product Purchase/Sales Gate (${label})`, () => {
    // ------------------------------------------------------------------
    // TEST A: Sales order — IsSold=N product hidden from batch-entry picker
    // ------------------------------------------------------------------
    test(`Sales order: IsSold=N product absent, IsSold=Y product present in picker (${label})`, async ({
      page,
    }) => {
      allure.epic('E0320: QM');
      allure.tag('F00320: Produktfreigabe für Verkauf');
      allure.tag('F00320');
      allure.tag('F00315: Produktfreigabe für Einkauf');
      allure.tag('F00315');
      allure.story('Product gate: sales order quick-input picker filters IsSold=N');
      allure.severity('critical');
      allure.parameter('Language', language);
      allure.tag(language);

      allure.description(`
## F00320: Produktfreigabe für Verkauf — Sales order batch-entry picker gate

### SysConfig
M_Product_EnforcePurchaseSalesFlags = Y (enabled via masterdata sysconfigs map; auto-restored after test)

### Test Scenario
Validates that when the SysConfig gate is enabled, a product with IsSold=N
is filtered out of the quick-input (Schnellerfassung) product picker in a
SALES order, while a control product with IsSold=Y remains visible.

1. Create masterdata (sysconfig Y, customer, blocked product isSold=false, control product isSold=true)
2. Login → Create Sales Order → Open batch entry
3. Type BlockedProduct code → assert it is NOT in the dropdown
4. Clear field → type ControlProduct code → assert it IS in the dropdown

### Absence Assertion Strategy
After the search spinner settles and the 800 ms debounce window passes,
assert that .input-dropdown-list-option matching the blocked code has count=0.
      `);

      test.setTimeout(180000);

      const masterdata = await createMasterdata(language);
      allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');

      const batchEntryButton = await openOrderInBatchEntryMode(page, masterdata, 'sales', language);

      const blockedCode = masterdata.products.BlockedProduct.productCode;
      const controlCode = masterdata.products.ControlProduct.productCode;

      // --- A1: Blocked product must NOT appear ---
      await typeProductAndWaitForDropdown(page, blockedCode);
      await expectProductAbsentFromPicker(page, blockedCode);

      // Clear the field (press Escape to dismiss any partial dropdown, then clear)
      await page.keyboard.press('Escape');
      await page.waitForTimeout(300);

      const productInput = page.locator('#lookup_M_Product_ID input.input-field');
      await productInput.clear();
      await page.waitForTimeout(300);

      // --- A2: Control product MUST appear ---
      await typeProductAndWaitForDropdown(page, controlCode);
      await expectProductInPicker(page, controlCode);

      // Clean up: close batch entry
      await page.keyboard.press('Escape');
      await page.waitForTimeout(200);
      const isStillOpen = await page.locator('.quick-input-container').isVisible().catch(() => false);
      if (isStillOpen) {
        await batchEntryButton.click();
      }

      console.log(`[${language}] Sales order picker gate test passed`);
    });

    // ------------------------------------------------------------------
    // TEST B: Purchase order — IsPurchased=N product hidden from picker
    // ------------------------------------------------------------------
    test(`Purchase order: IsPurchased=N product absent, IsPurchased=Y product present in picker (${label})`, async ({
      page,
    }) => {
      allure.epic('E0320: QM');
      allure.tag('F00315: Produktfreigabe für Einkauf');
      allure.tag('F00315');
      allure.tag('F00320: Produktfreigabe für Verkauf');
      allure.tag('F00320');
      allure.story('Product gate: purchase order quick-input picker filters IsPurchased=N');
      allure.severity('critical');
      allure.parameter('Language', language);
      allure.tag(language);

      allure.description(`
## F00315: Produktfreigabe für Einkauf — Purchase order batch-entry picker gate

### SysConfig
M_Product_EnforcePurchaseSalesFlags = Y (enabled via masterdata sysconfigs map; auto-restored after test)

### Test Scenario
Validates that when the SysConfig gate is enabled, a product with IsPurchased=N
is filtered out of the quick-input product picker in a PURCHASE order,
while a control product with IsPurchased=Y remains visible.

1. Create masterdata (sysconfig Y, vendor, blocked product isPurchased=false, control product isPurchased=true)
2. Login → Create Purchase Order → Open batch entry
3. Type BlockedProduct code → assert NOT in dropdown
4. Clear field → type ControlProduct code → assert IS in dropdown
      `);

      test.setTimeout(180000);

      const masterdata = await createMasterdata(language);
      allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');

      const batchEntryButton = await openOrderInBatchEntryMode(page, masterdata, 'purchase', language);

      const blockedCode = masterdata.products.BlockedProduct.productCode;
      const controlCode = masterdata.products.ControlProduct.productCode;

      // --- B1: Blocked product must NOT appear ---
      await typeProductAndWaitForDropdown(page, blockedCode);
      await expectProductAbsentFromPicker(page, blockedCode);

      // Clear the field
      await page.keyboard.press('Escape');
      await page.waitForTimeout(300);

      const productInput = page.locator('#lookup_M_Product_ID input.input-field');
      await productInput.clear();
      await page.waitForTimeout(300);

      // --- B2: Control product MUST appear ---
      await typeProductAndWaitForDropdown(page, controlCode);
      await expectProductInPicker(page, controlCode);

      // Clean up
      await page.keyboard.press('Escape');
      await page.waitForTimeout(200);
      const isStillOpen = await page.locator('.quick-input-container').isVisible().catch(() => false);
      if (isStillOpen) {
        await batchEntryButton.click();
      }

      console.log(`[${language}] Purchase order picker gate test passed`);
    });
  });
});
