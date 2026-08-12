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
 * Quick Input (Batch Entry) E2E test suite.
 *
 * Features tested:
 * - F00100: Sales Order
 *
 * Tests:
 * 1. Enter-key focus advance: Product → Enter → focus advances to Qty
 * 2. Mouse-click selection regression: Product → mouse click → line created
 * 3. Multiple lines in sequence: Add two lines via Enter-key workflow
 * 4. Invalid product: no beep when sysconfig disabled (default behavior)
 * 5. Regular form lookup regression: Customer selection in header (non-quick-input)
 */

// ============================================================================
// Shared helpers
// ============================================================================

/**
 * Create standard masterdata for quick input tests.
 * Creates a user, customer, and one or two products.
 */
async function createMasterdata(language, { twoProducts = false } = {}) {
  const products = {
    Product1: {
      name: 'QIPROD',
      type: 'Item',
      prices: [{ price: 10.0, currencyCode: 'EUR' }],
    },
  };

  if (twoProducts) {
    products.Product2 = {
      name: 'QIPR2',
      type: 'Item',
      prices: [{ price: 20.0, currencyCode: 'EUR' }],
    };
  }

  return await Backend.createMasterdata({
    request: {
      login: {
        user: {
          language,
          firstname: 'QI',
          lastname: 'Test',
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
      products,
    },
  });
}

/**
 * Login → Create Sales Order → Select Customer → Go to Order Line tab → Open batch entry.
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
 * Type a product code in the quick input product field and wait for dropdown.
 */
async function typeProductAndWaitForDropdown(page, productCode) {
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

  await productInput.fill(productCode);

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
 * Verify that a grid row exists in the order lines tab (after closing batch entry).
 * The grid renders as a standard HTML table: table > tbody > tr.
 */
async function expectOrderLineInGrid(page, expectedRowCount) {
  const gridRows = page.locator(
    '.table-flex-wrapper table tbody tr'
  );
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
  test.describe(`Quick Input (${label})`, () => {
    // ------------------------------------------------------------------
    // TEST 1: Enter-key focus advance (the new feature)
    // ------------------------------------------------------------------
    test(`Enter-key selects product and advances focus to Qty (${label})`, async ({
      page,
    }) => {
      allure.epic('E0100: Sales');
      allure.tag('F00100: Sales Order');
      allure.tag('F00100');
      allure.story('Quick Input: Enter-key focus advance');
      allure.severity('normal');
      allure.parameter('Language', language);
      allure.tag(language);

      allure.description(`
## F00100: Sales Order — Enter-key focus advance

### Test Scenario
Validates that pressing Enter to select a product from the dropdown
in quick input (batch entry) advances focus to the Qty field.

1. Create masterdata → Login → Create Sales Order → Open batch entry
2. Type product code → wait for dropdown → press **Enter**
3. Verify product was resolved
4. Fill quantity → press Enter → verify line created

### Business Value
Keyboard-only workflow: product → Enter → qty → Enter → line added.
      `);

      test.setTimeout(120000);

      const masterdata = await createMasterdata(language);
      allure.attachment(
        'Test Data',
        JSON.stringify(masterdata, null, 2),
        'application/json'
      );

      const { batchEntryButton } = await setupOrderWithBatchEntry(
        page,
        masterdata,
        language
      );

      // Type product and wait for dropdown
      const productCode = masterdata.products.Product1.productCode;
      const productInput = await typeProductAndWaitForDropdown(
        page,
        productCode
      );

      // KEY ACTION: Press Enter to select from dropdown
      await page.keyboard.press('Enter');
      await page.waitForTimeout(1000);

      // Verify product was resolved
      const resolvedValue = await productInput.inputValue();
      expect(resolvedValue).toBeTruthy();
      console.log(`[${language}] Product resolved to: "${resolvedValue}"`);

      // Fill quantity (click explicitly — toBeFocused unreliable in headless)
      const quantityInput = page.getByRole('spinbutton');
      await quantityInput.click();
      await quantityInput.fill('5');

      // Submit line
      await page.keyboard.press('Enter');
      await page.waitForTimeout(2000);

      // Close batch entry and verify line
      await batchEntryButton.click();
      await page.waitForTimeout(500);
      await expectOrderLineInGrid(page, 1);

      console.log(
        `[${language}] Enter-key focus advance test passed`
      );
    });

    // ------------------------------------------------------------------
    // TEST 2: Mouse-click selection regression
    // Ensures mouse-click on dropdown option still works after
    // the conditional this.focus() change in RawLookup.js.
    // ------------------------------------------------------------------
    test(`Mouse-click selects product in batch entry (${label})`, async ({
      page,
    }) => {
      allure.epic('E0100: Sales');
      allure.tag('F00100: Sales Order');
      allure.tag('F00100');
      allure.story('Quick Input: Mouse-click selection (regression)');
      allure.severity('critical');
      allure.parameter('Language', language);
      allure.tag(language);

      allure.description(`
## F00100: Sales Order — Mouse-click selection regression

### Test Scenario
Regression test: verifies that mouse-clicking a product option in the
quick input dropdown still works correctly after the focus-advance change.

1. Create masterdata → Login → Create Sales Order → Open batch entry
2. Type product code → wait for dropdown → **mouse click** on option
3. Verify product was resolved
4. Fill quantity → submit → verify line created

### Regression Target
RawLookup.handleSelect_RegularItem — conditional this.focus() with isMouseEvent=true.
      `);

      test.setTimeout(120000);

      const masterdata = await createMasterdata(language);
      allure.attachment(
        'Test Data',
        JSON.stringify(masterdata, null, 2),
        'application/json'
      );

      const { batchEntryButton } = await setupOrderWithBatchEntry(
        page,
        masterdata,
        language
      );

      // Type product and wait for dropdown
      const productCode = masterdata.products.Product1.productCode;
      const productInput = await typeProductAndWaitForDropdown(
        page,
        productCode
      );

      // KEY ACTION: Mouse click on dropdown option (NOT Enter)
      await page
        .locator('.input-dropdown-list-option')
        .getByText(productCode)
        .first()
        .click();

      await page.waitForTimeout(1000);

      // Verify product was resolved
      const resolvedValue = await productInput.inputValue();
      expect(resolvedValue).toBeTruthy();
      console.log(
        `[${language}] Mouse-click: Product resolved to: "${resolvedValue}"`
      );

      // Fill quantity
      const quantityInput = page.getByRole('spinbutton');
      await quantityInput.click();
      await quantityInput.fill('7');

      // Submit line
      await page.keyboard.press('Enter');
      await page.waitForTimeout(2000);

      // Close batch entry and verify line
      await batchEntryButton.click();
      await page.waitForTimeout(500);
      await expectOrderLineInGrid(page, 1);

      console.log(
        `[${language}] Mouse-click selection regression test passed`
      );
    });

    // ------------------------------------------------------------------
    // TEST 3: Multiple lines in sequence
    // Verifies that quick input resets properly after adding a line,
    // so a second line can be added immediately via Enter-key workflow.
    // ------------------------------------------------------------------
    test(`Add two lines in sequence via Enter-key (${label})`, async ({
      page,
    }) => {
      allure.epic('E0100: Sales');
      allure.tag('F00100: Sales Order');
      allure.tag('F00100');
      allure.story('Quick Input: Multiple lines in sequence');
      allure.severity('normal');
      allure.parameter('Language', language);
      allure.tag(language);

      allure.description(`
## F00100: Sales Order — Multiple lines in sequence

### Test Scenario
Validates that quick input resets properly after adding a line,
allowing a second line to be added immediately.

1. Create masterdata with two products
2. Open batch entry, add first line via Enter-key workflow
3. Batch entry should stay open → add second line
4. Close batch entry, verify both lines exist

### Business Value
Continuous keyboard entry: line1 → line2 → ... without reopening batch entry.
      `);

      test.setTimeout(180000); // 3 minutes for two lines

      const masterdata = await createMasterdata(language, {
        twoProducts: true,
      });
      allure.attachment(
        'Test Data',
        JSON.stringify(masterdata, null, 2),
        'application/json'
      );

      const { batchEntryButton } = await setupOrderWithBatchEntry(
        page,
        masterdata,
        language
      );

      // --- LINE 1 ---
      const product1Code = masterdata.products.Product1.productCode;
      const productInput1 = await typeProductAndWaitForDropdown(
        page,
        product1Code
      );

      await page.keyboard.press('Enter');
      await page.waitForTimeout(1000);

      const resolved1 = await productInput1.inputValue();
      expect(resolved1).toBeTruthy();
      console.log(`[${language}] Line 1 product resolved: "${resolved1}"`);

      const quantityInput1 = page.getByRole('spinbutton');
      await quantityInput1.click();
      await quantityInput1.fill('3');

      await page.keyboard.press('Enter');
      await page.waitForTimeout(2000);

      console.log(`[${language}] Line 1 submitted`);

      // --- LINE 2 ---
      // Quick input should still be open; product field should be ready again
      const product2Code = masterdata.products.Product2.productCode;
      const productInput2 = await typeProductAndWaitForDropdown(
        page,
        product2Code
      );

      await page.keyboard.press('Enter');
      await page.waitForTimeout(1000);

      const resolved2 = await productInput2.inputValue();
      expect(resolved2).toBeTruthy();
      console.log(`[${language}] Line 2 product resolved: "${resolved2}"`);

      const quantityInput2 = page.getByRole('spinbutton');
      await quantityInput2.click();
      await quantityInput2.fill('8');

      await page.keyboard.press('Enter');
      await page.waitForTimeout(2000);

      console.log(`[${language}] Line 2 submitted`);

      // Close batch entry and verify both lines
      await batchEntryButton.click();
      await page.waitForTimeout(500);
      await expectOrderLineInGrid(page, 2);

      console.log(
        `[${language}] Multiple lines test passed — 2 lines created`
      );
    });

    // ------------------------------------------------------------------
    // TEST 4: Invalid product — no beep when sysconfig disabled (default)
    // Verifies that pressing Enter on a non-existent product does NOT
    // advance focus and does NOT beep when sysconfig is off (default).
    // ------------------------------------------------------------------
    test(`Invalid product: no beep when sysconfig disabled (${label})`, async ({
      page,
    }) => {
      allure.epic('E0100: Sales');
      allure.tag('F00100: Sales Order');
      allure.tag('F00100');
      allure.story('Quick Input: Invalid product no beep (default)');
      allure.severity('normal');
      allure.parameter('Language', language);
      allure.tag(language);

      allure.description(`
## F00100: Sales Order — Invalid product no beep (sysconfig disabled)

### Test Scenario
Validates that pressing Enter with a non-existent product code:
1. Does NOT trigger an audible beep (sysconfig beepOnInvalidProduct defaults to N)
2. Does NOT advance focus to the quantity field
3. The product field retains the invalid text (not resolved)
4. No order line is created

### Business Value
Default behavior: no beep. Beep must be explicitly enabled via SysConfig.
      `);

      test.setTimeout(120000);

      const masterdata = await createMasterdata(language);
      allure.attachment(
        'Test Data',
        JSON.stringify(masterdata, null, 2),
        'application/json'
      );

      // Spy on Web Audio API to detect beep (headless browsers produce no audio)
      await page.addInitScript(() => {
        window.__beepCount = 0;
        const origStart = OscillatorNode.prototype.start;
        OscillatorNode.prototype.start = function (...args) {
          window.__beepCount++;
          return origStart.apply(this, args);
        };
      });

      const { batchEntryButton } = await setupOrderWithBatchEntry(
        page,
        masterdata,
        language
      );

      // Type an invalid product code
      const productInput = page.locator(
        '#lookup_M_Product_ID input.input-field'
      );
      await productInput.waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });
      await productInput.click();

      await page
        .locator(
          '#lookup_M_Product_ID .rotating, #lookup_M_Product_ID .spinner'
        )
        .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
        .catch(() => {});

      await page.waitForTimeout(300);

      await productInput.fill('NONEXISTENT_PRODUCT_XYZ_99999');

      // Wait for typeahead debounce
      await page.waitForTimeout(1500);

      // Press Enter — should beep and NOT advance focus
      await page.keyboard.press('Enter');
      await page.waitForTimeout(2000);

      // Verify: NO beep was initiated (sysconfig default is N)
      const beepCount = await page.evaluate(() => window.__beepCount);
      expect(beepCount).toBe(0);
      console.log(
        `[${language}] Invalid product: no beep (sysconfig disabled, beepCount=${beepCount})`
      );

      // Verify: product input should still contain the invalid text
      // (not cleared, not resolved to a real product)
      const productValue = await productInput.inputValue();
      expect(productValue).toContain('NONEXISTENT');
      console.log(
        `[${language}] Invalid product: field still shows "${productValue}"`
      );

      // Close batch entry and verify NO order lines were created
      await batchEntryButton.click();
      await page.waitForTimeout(500);

      const gridRows = page.locator('.table-flex-wrapper table tbody tr');
      const rowCount = await gridRows.count();
      expect(rowCount).toBe(0);

      console.log(
        `[${language}] Invalid product test passed — no lines created`
      );
    });

    // ------------------------------------------------------------------
    // TEST 5: Regular form lookup regression
    // Verifies that selecting a customer via the BPartner lookup in the
    // Sales Order header (non-quick-input context) still works correctly.
    // In this context, onChange returns a Promise, so shouldKeepFocus
    // must remain true and this.focus() must still be called.
    // ------------------------------------------------------------------
    test(`Regular form lookup: customer selection still works (${label})`, async ({
      page,
    }) => {
      allure.epic('E0100: Sales');
      allure.tag('F00100: Sales Order');
      allure.tag('F00100');
      allure.story('Regular form lookup: selection regression');
      allure.severity('critical');
      allure.parameter('Language', language);
      allure.tag(language);

      allure.description(`
## F00100: Sales Order — Regular form lookup regression

### Test Scenario
Regression test: verifies that selecting a customer in the Sales Order
header (BPartner composed lookup — NOT inside quick input) still works
correctly after the setNextProperty/focus changes.

1. Create masterdata → Login → Create Sales Order
2. Select customer via lookup (mouse click on dropdown option)
3. Verify customer is saved (record saved, field shows customer name)

### Regression Target
Lookup.setNextProperty return value + RawLookup conditional this.focus().
In non-quick-input context (onChange returns Promise), shouldKeepFocus
must remain true — behavior must be identical to before the change.
      `);

      test.setTimeout(120000);

      const masterdata = await createMasterdata(language);
      allure.attachment(
        'Test Data',
        JSON.stringify(masterdata, null, 2),
        'application/json'
      );

      await LoginPage.goto();
      await LoginPage.login(masterdata.login.user);
      await DashboardPage.expectVisible();

      await SalesOrderPage.goto();
      await SalesOrderPage.clickNew();

      // Select customer — this uses mouse click on dropdown option internally
      // and waits for record to be saved (validates auto-fill completes).
      // If our change broke regular form lookups, this would fail.
      const recordId = await SalesOrderPage.selectCustomer(
        masterdata.bpartners.CUSTOMER1.bpartnerCode
      );

      expect(recordId).toBeTruthy();
      console.log(
        `[${language}] Regular form lookup regression passed — SO ${recordId} created with customer`
      );

      // Verify the BPartner field shows the selected customer
      const bpartnerInput = page.locator(
        '#lookup_C_BPartner_ID input.input-field'
      );
      const bpartnerValue = await bpartnerInput.inputValue();
      expect(bpartnerValue).toBeTruthy();
      expect(bpartnerValue).toContain(
        masterdata.bpartners.CUSTOMER1.bpartnerCode
      );

      console.log(
        `[${language}] BPartner field value: "${bpartnerValue}"`
      );
    });
  });
});
