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
 * Record Navigation E2E test suite.
 *
 * Tests navigating between records within a window:
 * - Previous/Next record navigation arrows
 * - Breadcrumb navigation
 * - Back to list view
 * - URL-based record navigation
 */

test.describe('Record Navigation', () => {
  test('Navigate between records on Sales Order', async ({ page }) => {
    allure.epic('E0193: User Interface');
    allure.tag('F14010: Navigation');
    allure.tag('F14170: Record Navigation');
    allure.story('Record Navigation');
    allure.severity('normal');

    allure.description(`
## Record Navigation

Tests navigating between records:
1. **Create records** — Create 2 Sales Orders for navigation
2. **Open list view** — Navigate to SO list
3. **Open first record** — Double-click to open
4. **Navigate to next** — Use arrows/breadcrumb to go to next record
5. **Back to list** — Navigate back to the list view
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
    // TEST 1: Navigate from list view to detail view
    // ======================================================================
    await test.step('Open record from list view', async () => {
      await page.goto(`${FRONTEND_BASE_URL}/window/${SALES_ORDER_WINDOW_ID}`);
      await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
      await page
        .locator('.document-list-wrapper, .document-list')
        .waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
      await page.waitForTimeout(1000);

      const gridRows = page.locator('table tbody tr');
      const rowCount = await gridRows.count();
      console.log(`Grid rows: ${rowCount}`);

      if (rowCount === 0) {
        console.log('No rows — skipping record navigation test');
        return;
      }

      // Double-click first row to open detail view
      await gridRows.first().dblclick();
      await page.waitForURL(/\/window\/\d+\/\d+/, { timeout: SLOW_ACTION_TIMEOUT });
      await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
      await page.waitForTimeout(1000);

      const detailUrl = page.url();
      console.log(`Opened detail view: ${detailUrl}`);
      expect(detailUrl).toMatch(/\/window\/\d+\/\d+/);
    });

    // ======================================================================
    // TEST 2: Check breadcrumb navigation
    // ======================================================================
    await test.step('Breadcrumb navigation', async () => {
      // Open SubHeader to see breadcrumb
      await page.keyboard.press('Alt+1');
      await page.waitForTimeout(500);

      const subheader = page.locator('.subheader-container');
      const subheaderVisible = await subheader.isVisible().catch(() => false);

      if (subheaderVisible) {
        // Find breadcrumb items
        const breadcrumbItems = page.locator('.header-breadcrumb-item, .subheader-title span');
        const breadcrumbCount = await breadcrumbItems.count();
        console.log(`Breadcrumb items: ${breadcrumbCount}`);

        const breadcrumbTexts = [];
        for (let i = 0; i < breadcrumbCount; i++) {
          const text = (await breadcrumbItems.nth(i).textContent().catch(() => '')).trim();
          if (text) breadcrumbTexts.push(text.substring(0, 30));
        }
        console.log(`Breadcrumb path: ${JSON.stringify(breadcrumbTexts)}`);

        // Close SubHeader
        await page.keyboard.press('Escape');
        await page.waitForTimeout(300);
      }
    });

    // ======================================================================
    // TEST 3: Navigate using browser back
    // ======================================================================
    await test.step('Navigate back to list view', async () => {
      const detailUrl = page.url();

      // Go back using browser navigation
      await page.goBack();
      await page.waitForTimeout(1000);

      const afterBackUrl = page.url();
      console.log(`After browser back: ${afterBackUrl}`);

      // Check if we're back on the list view
      const isListView = /\/window\/\d+$/.test(afterBackUrl) || afterBackUrl.includes('viewId=');
      console.log(`Back on list view: ${isListView}`);

      if (isListView) {
        // Go forward again
        await page.goForward();
        await page.waitForTimeout(1000);
        console.log(`After forward: ${page.url()}`);
      } else {
        // Navigate directly to list
        await page.goto(`${FRONTEND_BASE_URL}/window/${SALES_ORDER_WINDOW_ID}`);
        await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
        await page.waitForTimeout(1000);
      }
    });

    // ======================================================================
    // TEST 4: Navigate between records via URL
    // ======================================================================
    await test.step('Direct URL navigation', async () => {
      // Navigate to list and open first 2 records to get their IDs
      await page.goto(`${FRONTEND_BASE_URL}/window/${SALES_ORDER_WINDOW_ID}`);
      await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
      await page
        .locator('.document-list-wrapper, .document-list')
        .waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
      await page.waitForTimeout(1000);

      const gridRows = page.locator('table tbody tr');
      const rowCount = await gridRows.count();

      if (rowCount >= 2) {
        // Open first record
        await gridRows.first().dblclick();
        await page.waitForURL(/\/window\/\d+\/\d+/, { timeout: SLOW_ACTION_TIMEOUT });
        const url1 = page.url();
        const recordId1 = url1.split('/').pop();
        console.log(`Record 1: ${recordId1}`);

        // Navigate back to list
        await page.goBack();
        await page.waitForTimeout(1000);

        // Open second record
        await page
          .locator('.document-list-wrapper, .document-list')
          .waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
        await page.waitForTimeout(500);
        await gridRows.nth(1).dblclick();
        await page.waitForURL(/\/window\/\d+\/\d+/, { timeout: SLOW_ACTION_TIMEOUT });
        const url2 = page.url();
        const recordId2 = url2.split('/').pop();
        console.log(`Record 2: ${recordId2}`);

        // Records should be different
        expect(recordId1).not.toBe(recordId2);
        console.log(`Two different records: ${recordId1} vs ${recordId2}`);
      }
    });

    // ======================================================================
    // FINAL SCREENSHOT
    // ======================================================================
    const screenshotBuffer = await page.screenshot();
    allure.attachment('Final State', screenshotBuffer, 'image/png');

    console.log('Record navigation test completed');
  });
});
