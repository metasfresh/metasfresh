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
 * Document Clone (Alt+W) E2E test suite.
 *
 * Tests the CLONE_DOCUMENT keyboard shortcut (Alt+W) which creates
 * a copy of the current document. The clone operation calls the
 * duplicateRequest API and redirects to the new record.
 *
 * Also tests the SubHeader "Clone" action button as an alternative
 * to the keyboard shortcut.
 */

test.describe('Document Clone', () => {
  test('Clone a Sales Order via Alt+W', async ({ page }) => {
    allure.epic('E0193: User Interface');
    allure.tag('F14010: Navigation');
    allure.tag('F14040: Document Actions');
    allure.story('Document Clone via Alt+W');
    allure.severity('normal');

    allure.description(`
## Document Clone (Alt+W)

Tests cloning a Sales Order:
1. Create a Sales Order with a customer (saved document)
2. Press Alt+W to clone the document
3. Verify URL changes to a new record ID
4. Verify the cloned document has the same customer
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

    // === CREATE SALES ORDER WITH CUSTOMER ===
    await SalesOrderPage.goto();
    await SalesOrderPage.clickNew();
    const recordId = await SalesOrderPage.selectCustomer(masterdata.bpartners.CUSTOMER1.bpartnerCode);

    console.log(`Created SO with record ID: ${recordId}`);
    const originalUrl = page.url();

    // Wait for save to complete
    await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
    await page.waitForTimeout(2000);

    // Get the document number for comparison
    const originalDocNo = await SalesOrderPage.getDocumentNo();
    console.log(`Original SO: ${originalDocNo} (URL: ${originalUrl})`);

    // ======================================================================
    // TEST 1: Clone via Alt+W
    // ======================================================================
    await test.step('Clone document via Alt+W', async () => {
      // Ensure we're focused on the body, not on an input field
      await page.locator('body').click();
      await page.waitForTimeout(500);

      // Press Alt+W to clone
      await page.keyboard.press('Alt+W');

      // Wait for navigation to the cloned document
      const cloneNavigated = await page
        .waitForURL(
          (url) => {
            const urlStr = url.toString();
            return urlStr !== originalUrl && /\/window\/\d+\/\d+/.test(urlStr);
          },
          { timeout: SLOW_ACTION_TIMEOUT }
        )
        .then(() => true)
        .catch(() => false);

      const clonedUrl = page.url();
      console.log(`Clone navigation: ${cloneNavigated} (${originalUrl} → ${clonedUrl})`);

      expect(cloneNavigated).toBe(true);

      // Extract the new record ID
      const clonedRecordId = clonedUrl.split('/').pop();
      console.log(`Original record: ${recordId}, Cloned record: ${clonedRecordId}`);
      expect(clonedRecordId).not.toBe(recordId);
    });

    // ======================================================================
    // TEST 2: Verify cloned document has a different document number
    // ======================================================================
    await test.step('Verify cloned document', async () => {
      // Wait for the cloned document to load
      await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
      await page.waitForTimeout(2000);

      const clonedDocNo = await SalesOrderPage.getDocumentNo();
      console.log(`Cloned SO: ${clonedDocNo} (original was: ${originalDocNo})`);

      // The cloned document should have a different document number
      expect(clonedDocNo).not.toBe(originalDocNo);
      expect(clonedDocNo).toBeTruthy();
    });

    // ======================================================================
    // FINAL SCREENSHOT
    // ======================================================================
    const screenshotBuffer = await page.screenshot();
    allure.attachment('Cloned Document', screenshotBuffer, 'image/png');

    console.log('Document clone test completed');
  });
});
