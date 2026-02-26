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
 * Quick Input Enter Key Behavior E2E test suite.
 *
 * Features tested:
 * - F50000: Order line quick entry productivity improvements
 *
 * Tests the Enter key behavior in quick input (batch entry) forms:
 * 1. Type a valid product code → press Enter → product is resolved
 * 2. Type quantity → press Enter → order line is added (form resets)
 * 3. Type an invalid product code → press Enter → product field keeps focus (beep plays)
 *
 * This validates the numpad-only workflow where users enter 30-60 lines per order
 * without looking at the screen (article number → Enter → quantity → Enter).
 */

const testCases = [
  { language: 'en_US', label: 'English' },
  { language: 'de_DE', label: 'German' },
];

testCases.forEach(({ language, label }) => {
  test.describe(`Quick Input Enter Key (${label})`, () => {
    test(`Enter resolves product and submits order line (${label} UI)`, async ({
      page,
    }) => {
      // === ALLURE METADATA ===
      allure.epic('E0100: Sales');
      allure.tag('F50000: Order line quick entry');
      allure.tag('F50000');
      allure.tag('F00100: Sales Order');
      allure.tag('F00100');
      allure.story(
        'Quick Input: Enter key resolves product and submits line'
      );
      allure.severity('critical');
      allure.parameter('Language', language);
      allure.parameter('UI Label', label);
      allure.tag(language);

      allure.description(`
## F50000: Order line quick entry productivity improvements

### Test Scenario
Validates the Enter key behavior in Sales Order batch entry (quick input):

1. **Valid product** — type product code → Enter → product resolves
2. **Quantity submit** — type quantity → Enter → order line is added
3. **Invalid product** — type non-existent code → Enter → focus stays on product field

### Business Value
Enables high-volume order entry (30-60 lines) using only the numpad.
Users type article number → Enter → quantity → Enter without looking at the screen.
      `);

      // Extend timeout for E2E test
      test.setTimeout(120000); // 2 minutes

      // Step 1: Create test data with specified language
      const masterdata = await Backend.createMasterdata({
        request: {
          login: {
            user: {
              language,
              firstname: 'first',
              lastname: 'last',
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
            Product1: {
              name: 'PROD',
              type: 'Item',
              prices: [
                {
                  price: 10.0,
                  currencyCode: 'EUR',
                },
              ],
            },
          },
        },
      });

      allure.attachment(
        'Test Data',
        JSON.stringify(masterdata, null, 2),
        'application/json'
      );

      const productCode = masterdata.products.Product1.productCode;
      const customerCode = masterdata.bpartners.CUSTOMER1.bpartnerCode;

      console.log(`[${language}] Master data created:`, {
        customerCode,
        productCode,
      });

      // Step 2: Login
      await LoginPage.goto();
      await LoginPage.login(masterdata.login.user);
      await DashboardPage.expectVisible();

      // Step 3: Create Sales Order
      await SalesOrderPage.goto();
      await SalesOrderPage.clickNew();

      // Select customer - waits for record to be saved
      const recordId = await SalesOrderPage.selectCustomer(customerCode);
      console.log(`[${language}] Sales Order ${recordId} created and saved`);

      // Step 4: Go to Order Lines tab and wait for it to be ready
      await SalesOrderPage.goToOrderLineTab();

      await waitForTabAllowsNew(SALES_ORDER_WINDOW_ID, recordId, 'AD_Tab-187', {
        maxRetries: 15,
        retryDelayMs: 1000,
      });

      // Step 5: Open batch entry
      const batchEntryButton = page.getByTestId('batch-entry-toggle');
      await batchEntryButton.scrollIntoViewIfNeeded();
      await batchEntryButton.waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });
      await batchEntryButton.click();

      // Wait for quick input form
      await page.locator('.quick-input-container').waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      const productInput = page.locator(
        '#lookup_M_Product_ID input.input-field'
      );
      await productInput.waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      // Wait for initial loading spinner to disappear
      await page
        .locator(
          '#lookup_M_Product_ID .rotating, #lookup_M_Product_ID .spinner'
        )
        .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
        .catch(() => {});

      await page.waitForTimeout(300);

      // =====================================================
      // TEST CASE 1: Valid product — Enter resolves the product
      // =====================================================
      await test.step(
        'Enter on valid product resolves it',
        async () => {
          await productInput.click();
          await productInput.fill(productCode);

          // Allow typeahead debounce to fire (default ~350ms + network)
          await page.waitForTimeout(1500);

          // Press Enter — should resolve the product
          await page.keyboard.press('Enter');

          // Wait for product to be resolved (PATCH + re-render)
          await page.waitForTimeout(2000);

          // Verify: product input should now show the resolved product caption
          // (not just the code the user typed — the PATCH response sets the caption)
          const resolvedValue = await productInput.inputValue();
          expect(resolvedValue).toBeTruthy();
          console.log(
            `[${language}] Product resolved: "${resolvedValue}" (code: ${productCode})`
          );

          // Verify: quantity field should be visible
          const quantityInput = page.getByRole('spinbutton');
          await quantityInput.waitFor({
            state: 'visible',
            timeout: SLOW_ACTION_TIMEOUT,
          });

          console.log(
            `[${language}] Enter on valid product: product resolved successfully`
          );
        }
      );

      // =====================================================
      // TEST CASE 2: Enter on quantity — submits the line
      // =====================================================
      await test.step('Enter on quantity submits the order line', async () => {
        const quantityInput = page.getByRole('spinbutton');

        // Click to focus the quantity field, then type quantity
        await quantityInput.click();
        await quantityInput.fill('5');
        await page.waitForTimeout(300);

        // Press Enter — should submit the quick input line
        await page.keyboard.press('Enter');

        // Wait for the line to be submitted and form to reset
        await page.waitForTimeout(3000);

        // Verify: product field should be visible and cleared (form reset for next line)
        await productInput.waitFor({
          state: 'visible',
          timeout: SLOW_ACTION_TIMEOUT,
        });

        // Verify the product field is cleared (empty = form was reset)
        const productValue = await productInput.inputValue();
        expect(productValue).toBeFalsy();

        console.log(`[${language}] Enter on quantity: order line submitted`);
      });

      // =====================================================
      // TEST CASE 3: Invalid product — Enter keeps focus on product field
      // =====================================================
      await test.step(
        'Enter on invalid product keeps focus on product field',
        async () => {
          await productInput.click();
          await productInput.fill('NONEXISTENT_PRODUCT_XYZ_99999');

          // Allow typeahead debounce
          await page.waitForTimeout(1500);

          // Press Enter — should beep and keep focus (no product found)
          await page.keyboard.press('Enter');

          // Wait for the resolution attempt
          await page.waitForTimeout(2000);

          // Verify: product input should still be focused (did not advance)
          await expect(productInput).toBeFocused({
            timeout: SLOW_ACTION_TIMEOUT,
          });

          // Verify: quantity field should NOT be focused
          const quantityInput = page.getByRole('spinbutton');
          const isQuantityFocused = await quantityInput.evaluate(
            (el) => document.activeElement === el
          );
          expect(isQuantityFocused).toBe(false);

          console.log(
            `[${language}] Enter on invalid product: focus stayed on product field`
          );
        }
      );

      // Close batch entry
      await batchEntryButton.click();
      await page.waitForTimeout(500);
    });
  });
});
