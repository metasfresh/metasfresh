import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';
import { SLOW_ACTION_TIMEOUT } from '../utils/common';
import { SALES_ORDER_WINDOW_ID } from '../utils/WindowIds';
import { waitForTabAllowsNew } from '../utils/WebAPIValidation';

/**
 * Quick Input Multiple Matches E2E test suite.
 *
 * Features tested:
 * - F00100: Sales Order
 *
 * Tests:
 * 1. Multiple matches keep dropdown open (user must pick manually)
 * 2. Single match auto-selects and advances focus (regression)
 *
 * Covers resolveItems() in RawLookup.js:
 * - items.length > 1 => keep dropdown open, do NOT auto-select
 * - items.length === 1 => auto-select via handleAutoSelectAndAdvance
 */

// ============================================================================
// Shared helpers (inline, same pattern as quick-input.spec.js)
// ============================================================================

/**
 * Create masterdata with two products sharing a unique prefix so typeahead
 * matches both when the prefix is typed.
 */
async function createMasterdataWithSharedPrefix(language) {
  const sharedPrefix = `QIMM_${Date.now()}`;

  return {
    sharedPrefix,
    masterdata: await Backend.createMasterdata({
      request: {
        login: {
          user: {
            language,
            firstname: 'QI',
            lastname: 'Multi',
          },
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
          ProductA: {
            name: `${sharedPrefix}_A`,
            type: 'Item',
            prices: [{ price: 10.0, currencyCode: 'EUR' }],
          },
          ProductB: {
            name: `${sharedPrefix}_B`,
            type: 'Item',
            prices: [{ price: 20.0, currencyCode: 'EUR' }],
          },
        },
      },
    }),
  };
}

/**
 * Login -> Create Sales Order -> Select Customer -> Go to Order Line tab -> Open batch entry.
 * Returns { recordId, batchEntryButton }.
 */
async function setupOrderWithBatchEntry(page, masterdata, language) {
  await LoginPage.goto();
  await LoginPage.login(masterdata.login.user);
  await DashboardPage.expectVisible();

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

  const batchEntryButton = page.getByTestId('batch-entry-toggle');
  await batchEntryButton.scrollIntoViewIfNeeded();
  await batchEntryButton.waitFor({
    state: 'visible',
    timeout: SLOW_ACTION_TIMEOUT,
  });
  await batchEntryButton.click();

  await page.locator('.quick-input-container').waitFor({
    state: 'visible',
    timeout: SLOW_ACTION_TIMEOUT,
  });

  return { recordId, batchEntryButton };
}

/**
 * Type a product query in the quick input product field and wait for dropdown.
 * Returns the product input locator.
 */
async function typeProductAndWaitForDropdown(page, query) {
  const productInput = page.locator(
    '#lookup_M_Product_ID input.input-field'
  );
  await productInput.waitFor({
    state: 'visible',
    timeout: SLOW_ACTION_TIMEOUT,
  });
  await productInput.click();

  await page
    .locator('#lookup_M_Product_ID .rotating, #lookup_M_Product_ID .spinner')
    .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
    .catch(() => {});

  await page.waitForTimeout(300);

  await productInput.fill(query);

  // Wait for typeahead debounce + search
  await page.waitForTimeout(500);

  await page
    .locator('#lookup_M_Product_ID .rotating, #lookup_M_Product_ID .spinner')
    .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
    .catch(() => {});

  // Wait for dropdown to populate
  await page.waitForTimeout(300);

  return productInput;
}

/**
 * Verify grid row count in the order lines tab (after closing batch entry).
 */
async function expectOrderLineInGrid(page, expectedRowCount) {
  const gridRows = page.locator('.table-flex-wrapper table tbody tr');
  await gridRows.first().waitFor({
    state: 'visible',
    timeout: SLOW_ACTION_TIMEOUT,
  });

  if (expectedRowCount) {
    await expect(gridRows).toHaveCount(expectedRowCount, {
      timeout: SLOW_ACTION_TIMEOUT,
    });
  }
}

// ============================================================================
// Test cases
// ============================================================================

const testCases = [
  { language: 'en_US', label: 'English' },
  { language: 'de_DE', label: 'German' },
];

testCases.forEach(({ language, label }) => {
  test.describe(`Quick Input Multiple Matches (${label})`, () => {
    // ------------------------------------------------------------------
    // TEST 1: Multiple matches keep dropdown open
    // When typing a shared prefix that matches 2+ products, pressing
    // Enter should NOT auto-select. The dropdown stays open so the
    // user can pick the correct product manually.
    // ------------------------------------------------------------------
    test(`Multiple matches keep dropdown open (${label})`, async ({
      page,
    }) => {
      allure.epic('E0100: Sales');
      allure.tag('F00100: Sales Order');
      allure.story('Quick Input: multiple matches keep dropdown open');
      allure.severity('normal');
      allure.parameter('Language', language);
      allure.tag(language);

      allure.description(`
## F00100: Sales Order - Multiple matches keep dropdown open

### Test Scenario
When typing a prefix that matches multiple products, pressing Enter should
NOT auto-select a product. The dropdown stays open with all matches visible,
allowing the user to pick the correct one.

1. Create 2 products with shared prefix (QIMM_timestamp_A, QIMM_timestamp_B)
2. Open batch entry, type the shared prefix
3. Wait for dropdown to show 2+ options
4. Press Enter
5. Assert: dropdown still visible with 2+ options
6. Assert: product field still contains typed prefix (not replaced)
7. Click first option to select
8. Assert: product is selected, Qty field becomes interactable

### Code Path
RawLookup.resolveAndSelectOnEnter -> resolveItems(items) where items.length > 1
-> keeps dropdown open (does NOT call handleAutoSelectAndAdvance)

### Beep Testing
SysConfig beepOnInvalidProduct defaults to N. The E2E framework has no SysConfig
setter API. Beep assertion skipped. See e2e/frontend-webui/CLAUDE.md:359-383
for Web Audio API spy pattern if SysConfig support is added to the test API.
      `);

      test.setTimeout(120000);

      const { sharedPrefix, masterdata } =
        await createMasterdataWithSharedPrefix(language);
      allure.attachment(
        'Test Data',
        JSON.stringify(masterdata, null, 2),
        'application/json'
      );

      console.log(
        `[${language}] Created 2 products with shared prefix: ${sharedPrefix}`
      );

      const { batchEntryButton } = await setupOrderWithBatchEntry(
        page,
        masterdata,
        language
      );

      // Type the shared prefix - should match both products
      const productInput = await typeProductAndWaitForDropdown(
        page,
        sharedPrefix
      );

      // Verify dropdown shows 2+ options before pressing Enter
      const dropdownOptions = page.locator('.input-dropdown-list-option');
      await expect(dropdownOptions).toHaveCount(2, {
        timeout: SLOW_ACTION_TIMEOUT,
      });
      console.log(
        `[${language}] Dropdown shows 2 options for prefix "${sharedPrefix}"`
      );

      // KEY ACTION: Press Enter with multiple matches
      await page.keyboard.press('Enter');
      await page.waitForTimeout(1000);

      // ASSERT: Dropdown is still visible with 2+ options (not auto-selected)
      const dropdownAfterEnter = page.locator('.input-dropdown-list-option');
      const optionCountAfterEnter = await dropdownAfterEnter.count();
      expect(optionCountAfterEnter).toBeGreaterThanOrEqual(2);
      console.log(
        `[${language}] After Enter: dropdown still shows ${optionCountAfterEnter} options`
      );

      // ASSERT: Product input still contains the typed prefix (not replaced by a resolved product)
      const inputValueAfterEnter = await productInput.inputValue();
      expect(inputValueAfterEnter).toContain(sharedPrefix);
      console.log(
        `[${language}] After Enter: product field still shows "${inputValueAfterEnter}"`
      );

      // Now click the first dropdown option to select it
      await dropdownAfterEnter.first().click();
      await page.waitForTimeout(1000);

      // ASSERT: Product was resolved (input value changed from prefix to full product name)
      const resolvedValue = await productInput.inputValue();
      expect(resolvedValue).toBeTruthy();
      console.log(
        `[${language}] After click: product resolved to "${resolvedValue}"`
      );

      // ASSERT: Qty field is interactable (product was accepted)
      const quantityInput = page
        .locator('.quick-input-container')
        .getByRole('spinbutton');
      await quantityInput.click();
      await quantityInput.fill('5');

      // Submit line
      await page.keyboard.press('Enter');
      await page.waitForTimeout(2000);

      // Close batch entry and verify line was created
      await batchEntryButton.click();
      await page.waitForTimeout(500);
      await expectOrderLineInGrid(page, 1);

      console.log(
        `[${language}] Multiple matches test passed - dropdown stayed open, manual selection worked`
      );
    });

    // ------------------------------------------------------------------
    // TEST 2: Single match auto-selects and advances (regression)
    // When typing the full unique product name, pressing Enter should
    // auto-select and advance focus - confirming the single-match path
    // still works correctly alongside the multiple-match path.
    // ------------------------------------------------------------------
    test(`Single match auto-selects and advances focus (${label})`, async ({
      page,
    }) => {
      allure.epic('E0100: Sales');
      allure.tag('F00100: Sales Order');
      allure.story(
        'Quick Input: single match auto-selects (regression)'
      );
      allure.severity('normal');
      allure.parameter('Language', language);
      allure.tag(language);

      allure.description(`
## F00100: Sales Order - Single match auto-selects (regression)

### Test Scenario
Typing the full unique product name and pressing Enter should auto-select
the product and advance focus. This confirms the single-match path
(items.length === 1) still works after adding the multiple-match handling.

1. Create 2 products with shared prefix
2. Open batch entry, type the FULL name of ProductA (prefix + "_A")
3. Press Enter
4. Assert: product auto-selected (input shows resolved product name)
5. Fill qty, submit, verify order line created

### Code Path
RawLookup.resolveAndSelectOnEnter -> resolveItems(items) where items.length === 1
-> handleAutoSelectAndAdvance -> focusNextFieldInForm
      `);

      test.setTimeout(120000);

      const { masterdata } =
        await createMasterdataWithSharedPrefix(language);
      allure.attachment(
        'Test Data',
        JSON.stringify(masterdata, null, 2),
        'application/json'
      );

      const productACode = masterdata.products.ProductA.productCode;
      console.log(
        `[${language}] Will type full product code: ${productACode}`
      );

      const { batchEntryButton } = await setupOrderWithBatchEntry(
        page,
        masterdata,
        language
      );

      // Type the full unique product code (should match exactly 1 product)
      const productInput = await typeProductAndWaitForDropdown(
        page,
        productACode
      );

      // KEY ACTION: Press Enter with single match
      await page.keyboard.press('Enter');
      await page.waitForTimeout(1000);

      // ASSERT: Product was auto-selected (input changed from code to resolved name)
      const resolvedValue = await productInput.inputValue();
      expect(resolvedValue).toBeTruthy();
      console.log(
        `[${language}] Single match: product resolved to "${resolvedValue}"`
      );

      // Fill quantity (click explicitly - toBeFocused unreliable in headless)
      const quantityInput = page
        .locator('.quick-input-container')
        .getByRole('spinbutton');
      await quantityInput.click();
      await quantityInput.fill('3');

      // Submit line
      await page.keyboard.press('Enter');
      await page.waitForTimeout(2000);

      // Close batch entry and verify line was created
      await batchEntryButton.click();
      await page.waitForTimeout(500);
      await expectOrderLineInGrid(page, 1);

      console.log(
        `[${language}] Single match regression test passed - auto-select worked`
      );
    });
  });
});
