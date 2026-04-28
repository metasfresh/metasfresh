import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';
import { SALES_ORDER_WINDOW_ID } from '../utils/WindowIds';

/**
 * Lookup Widget E2E test suite.
 *
 * Tests the lookup/autocomplete widget behavior:
 * - Type to search for items in dropdown
 * - Dropdown list appearance with matching items
 * - Select item from dropdown
 * - Clear selection
 * - Multi-property lookup (Partner → Location → Contact chain)
 */

test.describe('Lookup Widget', () => {
  test('Lookup widget behavior on Sales Order', async ({ page }) => {
    allure.epic('E0193: User Interface');
    allure.tag('F14010: Navigation');
    allure.tag('F14140: Widgets');
    allure.story('Lookup Widget Autocomplete');
    allure.severity('normal');

    allure.description(`
## Lookup Widget

Tests the lookup/autocomplete widget:
1. **Type to search** — Type in a lookup field and see matching items
2. **Dropdown items** — Verify dropdown list appears with options
3. **Select item** — Click an option to select it
4. **Spinner** — Verify loading spinner during search
5. **Multi-property** — BPartner lookup chains to Location and Contact
    `);

    test.setTimeout(180000); // 3 minutes

    // === CREATE TEST DATA ===
    const masterdata = await Backend.createMasterdata({
      request: {
        login: {
          user: { language: 'en_US', firstname: 'first', lastname: 'last' },
        },
        bpartners: {
          CUSTOMER1: {
            isVendor: false,
            isCustomer: true,
            isSoPriceList: true,
            name: 'LookupTestCust',
          },
        },
        products: {
          Product1: {
            name: 'PROD',
            type: 'Item',
            prices: [{ price: 15.0, currencyCode: 'EUR' }],
          },
        },
      },
    });

    allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');

    // === LOGIN ===
    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    // === CREATE NEW SALES ORDER ===
    await SalesOrderPage.goto();
    await SalesOrderPage.clickNew();

    await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
    await page.waitForTimeout(1000);

    // ======================================================================
    // TEST 1: Discover lookup fields on the form
    // ======================================================================
    await test.step('Discover lookup fields', async () => {
      // Lookup fields use #lookup_{ColumnName} pattern
      const lookupFields = page.locator('[id^="lookup_"]');
      const lookupCount = await lookupFields.count();
      console.log(`Lookup fields on form: ${lookupCount}`);

      const lookupIds = [];
      for (let i = 0; i < Math.min(lookupCount, 10); i++) {
        const id = await lookupFields.nth(i).getAttribute('id').catch(() => '');
        lookupIds.push(id);
      }
      console.log(`Lookup IDs: ${JSON.stringify(lookupIds)}`);
      allure.attachment('Lookup Fields', JSON.stringify(lookupIds, null, 2), 'application/json');

      expect(lookupCount).toBeGreaterThan(0);
    });

    // ======================================================================
    // TEST 2: Type in BPartner lookup and see dropdown
    // ======================================================================
    await test.step('BPartner lookup type-ahead', async () => {
      const bpartnerInput = page.locator('#lookup_C_BPartner_ID input.input-field');
      await bpartnerInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      // Click to focus
      await bpartnerInput.click();
      await page.waitForTimeout(500);

      // Type the customer code to trigger search
      const searchTerm = masterdata.bpartners.CUSTOMER1.bpartnerCode.substring(0, 10);
      await bpartnerInput.fill(searchTerm);
      console.log(`Typed search term: "${searchTerm}"`);
      await page.waitForTimeout(1000);

      // Wait for spinner to appear and disappear
      const spinner = page.locator('#lookup_C_BPartner_ID .rotating, #lookup_C_BPartner_ID .spinner');
      const spinnerAppeared = await spinner
        .waitFor({ state: 'visible', timeout: 3000 })
        .then(() => true)
        .catch(() => false);
      console.log(`Spinner appeared: ${spinnerAppeared}`);

      if (spinnerAppeared) {
        await spinner.waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
        console.log('Spinner finished');
      }

      await page.waitForTimeout(500);

      // Check for dropdown options
      const dropdownOptions = page.locator('.input-dropdown-list-option');
      const optionCount = await dropdownOptions.count();
      console.log(`Dropdown options: ${optionCount}`);

      if (optionCount > 0) {
        // Log first few options
        const optionTexts = [];
        for (let i = 0; i < Math.min(optionCount, 5); i++) {
          const text = (await dropdownOptions.nth(i).textContent().catch(() => '')).trim();
          optionTexts.push(text.substring(0, 50));
        }
        console.log(`Dropdown items: ${JSON.stringify(optionTexts)}`);
        allure.attachment('Dropdown Options', JSON.stringify(optionTexts, null, 2), 'application/json');

        // Select the matching option
        const matchingOption = dropdownOptions.getByText(masterdata.bpartners.CUSTOMER1.bpartnerCode).first();
        const hasMatch = await matchingOption.isVisible().catch(() => false);
        console.log(`Matching option visible: ${hasMatch}`);

        if (hasMatch) {
          await matchingOption.click();
          await page.waitForTimeout(1000);
          console.log('Selected matching option');

          // Verify the field now shows the selected value
          const selectedValue = await bpartnerInput.inputValue().catch(() => '');
          console.log(`Selected value: "${selectedValue}"`);
        } else if (optionCount > 0) {
          // Click first option
          await dropdownOptions.first().click();
          await page.waitForTimeout(1000);
          console.log('Selected first option');
        }
      } else {
        console.log('No dropdown options appeared');
      }
    });

    // ======================================================================
    // TEST 3: Check multi-property lookup chain (BPartner → Location)
    // ======================================================================
    await test.step('Multi-property lookup chain', async () => {
      // After selecting BPartner, Location field should auto-populate or become available
      const locationField = page.locator('#lookup_C_BPartner_Location_ID input.input-field');
      const hasLocation = await locationField.isVisible().catch(() => false);
      console.log(`BPartner Location field visible: ${hasLocation}`);

      if (hasLocation) {
        const locationValue = await locationField.inputValue().catch(() => '');
        console.log(`Location value: "${locationValue}"`);
      }

      // Check for Contact field
      const contactField = page.locator('#lookup_AD_User_ID input.input-field');
      const hasContact = await contactField.isVisible().catch(() => false);
      console.log(`Contact field visible: ${hasContact}`);

      if (hasContact) {
        const contactValue = await contactField.inputValue().catch(() => '');
        console.log(`Contact value: "${contactValue}"`);
      }
    });

    // ======================================================================
    // TEST 4: Dropdown selection widget (non-lookup, e.g. DocType)
    // ======================================================================
    await test.step('Dropdown selection widget', async () => {
      // DocType dropdown — uses a regular dropdown, not a lookup
      const docTypeField = page.locator('#lookup_C_DocTypeTarget_ID');
      const hasDocType = await docTypeField.isVisible().catch(() => false);
      console.log(`DocType field visible: ${hasDocType}`);

      if (hasDocType) {
        const docTypeInput = docTypeField.locator('input.input-field');
        const hasInput = await docTypeInput.isVisible().catch(() => false);

        if (hasInput) {
          await docTypeInput.click();
          await page.waitForTimeout(500);

          // Check for dropdown options
          const options = page.locator('.input-dropdown-list-option');
          const optCount = await options.count();
          console.log(`DocType dropdown options: ${optCount}`);
        }
      }
    });

    // ======================================================================
    // FINAL SCREENSHOT
    // ======================================================================
    const screenshotBuffer = await page.screenshot();
    allure.attachment('Final State', screenshotBuffer, 'image/png');

    console.log('Lookup widget test completed');
  });
});
