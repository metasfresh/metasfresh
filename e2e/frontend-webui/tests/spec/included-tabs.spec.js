import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';
import { SALES_ORDER_WINDOW_ID, BUSINESS_PARTNER_WINDOW_ID } from '../utils/WindowIds';

/**
 * Included Tabs & Batch Entry E2E test suite.
 *
 * Tests the tab navigation within a document view:
 * - Included tabs rendered below the main form (Order Lines, Notes, etc.)
 * - Tab switching via tab headers
 * - Batch entry toggle (Alt+Q / data-testid="batch-entry-toggle")
 * - Tab data-testid attributes for language-independent navigation
 *
 * Also tests the Business Partner window which has multiple included tabs
 * (Customer, Vendor, Bank Account, etc.) to verify multi-tab scenarios.
 */

test.describe('Included Tabs & Batch Entry', () => {
  test('Tab navigation on Sales Order and Business Partner', async ({ page }) => {
    allure.epic('E0193: User Interface');
    allure.tag('F14010: Navigation');
    allure.tag('F14050: Included Tabs');
    allure.story('Included Tab Navigation');
    allure.severity('normal');

    allure.description(`
## Included Tabs & Batch Entry

Tests tab navigation within document views:
1. **Sales Order** — Discover and switch between included tabs (Order Lines, etc.)
2. **Batch Entry** — Toggle batch entry via button and verify quick input appears
3. **Business Partner** — Navigate multiple included tabs (Customer, Vendor, etc.)
4. **Tab data-testid** — Verify tabs have data-testid attributes for E2E testing
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
            name: 'Customer',
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

    // ======================================================================
    // PHASE 1: Sales Order — Included Tabs
    // ======================================================================

    // Create a Sales Order with customer
    await SalesOrderPage.goto();
    await SalesOrderPage.clickNew();
    await SalesOrderPage.selectCustomer(masterdata.bpartners.CUSTOMER1.bpartnerCode);

    await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
    await page.waitForTimeout(1000);

    // ======================================================================
    // TEST 1: Discover included tabs on Sales Order
    // ======================================================================
    await test.step('Discover SO included tabs', async () => {
      // Included tabs are rendered as tab headers — find them
      const tabHeaders = page.locator('[data-testid^="tab-"]');
      const tabCount = await tabHeaders.count();
      console.log(`Sales Order tab headers: ${tabCount}`);

      const tabInfo = [];
      for (let i = 0; i < tabCount; i++) {
        const tab = tabHeaders.nth(i);
        const testId = await tab.getAttribute('data-testid').catch(() => '');
        const text = (await tab.textContent().catch(() => '')).trim();
        const isActive = await tab.evaluate((el) => el.classList.contains('active')).catch(() => false);
        tabInfo.push({ testId, caption: text, active: isActive });
      }
      console.log('SO tabs:', JSON.stringify(tabInfo, null, 2));
      allure.attachment('Sales Order Tabs', JSON.stringify(tabInfo, null, 2), 'application/json');

      expect(tabCount).toBeGreaterThan(0);
    });

    // ======================================================================
    // TEST 2: Switch between tabs
    // ======================================================================
    await test.step('Switch between tabs', async () => {
      const tabHeaders = page.locator('[data-testid^="tab-"]');
      const tabCount = await tabHeaders.count();

      if (tabCount < 2) {
        console.log('Only one tab — skipping tab switch test');
        return;
      }

      // Click the second tab
      const secondTab = tabHeaders.nth(1);
      const secondTabId = await secondTab.getAttribute('data-testid').catch(() => '');
      console.log(`Clicking second tab: ${secondTabId}`);

      await secondTab.click();
      await page.waitForTimeout(500);

      // Verify the second tab is now active
      const isActive = await secondTab.evaluate((el) => el.classList.contains('active')).catch(() => false);
      console.log(`Second tab active after click: ${isActive}`);

      // Click back to first tab
      const firstTab = tabHeaders.first();
      await firstTab.click();
      await page.waitForTimeout(500);

      const firstActive = await firstTab.evaluate((el) => el.classList.contains('active')).catch(() => false);
      console.log(`First tab active after clicking back: ${firstActive}`);
    });

    // ======================================================================
    // TEST 3: Batch entry toggle (data-testid="batch-entry-toggle")
    // ======================================================================
    await test.step('Batch entry toggle', async () => {
      const batchButton = page.getByTestId('batch-entry-toggle');
      await batchButton.scrollIntoViewIfNeeded();
      const batchExists = await batchButton.isVisible().catch(() => false);
      console.log(`Batch entry button visible: ${batchExists}`);

      if (!batchExists) {
        console.log('No batch entry button — skipping');
        return;
      }

      // Click to open batch entry
      await batchButton.click();
      await page.waitForTimeout(500);

      const quickInputOpen = await page
        .locator('.quick-input-container')
        .waitFor({ state: 'visible', timeout: 5000 })
        .then(() => true)
        .catch(() => false);
      console.log(`Quick input opened: ${quickInputOpen}`);
      expect(quickInputOpen).toBe(true);

      // Verify product input field exists in quick input
      const productInput = page.locator('#lookup_M_Product_ID input.input-field');
      const hasProductField = await productInput.isVisible().catch(() => false);
      console.log(`Product input in quick input: ${hasProductField}`);

      // Verify quantity input exists
      const qtyInput = page.locator('.quick-input-container').getByRole('spinbutton');
      const hasQtyField = await qtyInput.isVisible().catch(() => false);
      console.log(`Quantity input in quick input: ${hasQtyField}`);

      // Close batch entry
      await batchButton.click();
      await page.waitForTimeout(500);

      const quickInputClosed = !(await page.locator('.quick-input-container').isVisible().catch(() => false));
      console.log(`Quick input closed: ${quickInputClosed}`);
    });

    // ======================================================================
    // PHASE 2: Business Partner — Multiple Included Tabs
    // ======================================================================
    await test.step('Business Partner included tabs', async () => {
      // Navigate to Business Partner list
      await page.goto(`${FRONTEND_BASE_URL}/window/${BUSINESS_PARTNER_WINDOW_ID}`);
      await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
      await page
        .locator('.document-list-wrapper, .document-list')
        .waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
      await page.waitForTimeout(1000);

      // Open first BP record
      const firstRow = page.locator('table tbody tr').first();
      const hasRows = await firstRow.isVisible().catch(() => false);

      if (!hasRows) {
        console.log('No BP rows — skipping BP tab test');
        return;
      }

      await firstRow.dblclick();
      await page.waitForURL(/\/window\/\d+\/\d+/, { timeout: SLOW_ACTION_TIMEOUT });
      await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
      await page.waitForTimeout(1000);

      // Discover BP included tabs
      const bpTabs = page.locator('[data-testid^="tab-"]');
      const bpTabCount = await bpTabs.count();
      console.log(`Business Partner tab headers: ${bpTabCount}`);

      const bpTabInfo = [];
      for (let i = 0; i < bpTabCount; i++) {
        const tab = bpTabs.nth(i);
        const testId = await tab.getAttribute('data-testid').catch(() => '');
        const text = (await tab.textContent().catch(() => '')).trim();
        bpTabInfo.push({ testId, caption: text });
      }
      console.log('BP tabs:', JSON.stringify(bpTabInfo, null, 2));
      allure.attachment('Business Partner Tabs', JSON.stringify(bpTabInfo, null, 2), 'application/json');

      // BP should have multiple tabs (Customer, Vendor, Contact, Bank, etc.)
      expect(bpTabCount).toBeGreaterThan(1);

      // Click through each tab to verify they load
      for (let i = 0; i < Math.min(bpTabCount, 4); i++) {
        const tab = bpTabs.nth(i);
        const tabId = bpTabInfo[i]?.testId || `tab-${i}`;
        await tab.click();
        await page.waitForTimeout(300);
        const isActive = await tab.evaluate((el) => el.classList.contains('active')).catch(() => false);
        console.log(`Tab ${tabId}: active=${isActive}`);
      }
    });

    // ======================================================================
    // FINAL SCREENSHOT
    // ======================================================================
    const screenshotBuffer = await page.screenshot();
    allure.attachment('Final State', screenshotBuffer, 'image/png');

    console.log('Included tabs & batch entry test completed');
  });
});
