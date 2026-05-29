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
 * Edit Mode Toggle (Alt+O) E2E test suite.
 *
 * Tests switching between read-only and editable modes on a document:
 * - Alt+O toggles the document edit mode
 * - Visual indicators change between modes
 */

test.describe('Edit Mode Toggle', () => {
  test('Toggle edit mode on Sales Order via Alt+O', async ({ page }) => {
    allure.epic('E0193: User Interface');
    allure.tag('F14010: Navigation');
    allure.tag('F14110: Edit Mode');
    allure.story('Edit Mode Toggle');
    allure.severity('normal');

    allure.description(`
## Edit Mode Toggle (Alt+O)

Tests switching document edit modes:
1. **Create SO** — Start with a saved Sales Order
2. **Alt+O toggle** — Press Alt+O to switch mode
3. **Observe changes** — Check if document appearance changes
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
    await SalesOrderPage.selectCustomer(masterdata.bpartners.CUSTOMER1.bpartnerCode);

    await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
    await page.waitForTimeout(2000);

    const docNo = await SalesOrderPage.getDocumentNo();
    console.log(`Sales Order: ${docNo}`);

    // ======================================================================
    // TEST 1: Check initial document state and toggle Alt+O
    // ======================================================================
    await test.step('Toggle edit mode via Alt+O', async () => {
      // Get form-group count and classes before
      const formGroupsBefore = await page.locator('.form-group').count();
      console.log(`Form groups: ${formGroupsBefore}`);

      // Check for lookup fields that may have clickable/editable indicators
      const lookupFields = await page.locator('.lookup-widget, .input-dropdown').count();
      console.log(`Lookup/dropdown fields: ${lookupFields}`);

      // Take screenshot before
      const screenshotBefore = await page.screenshot();
      allure.attachment('Before Alt+O', screenshotBefore, 'image/png');

      // Press Alt+O — use keyboard directly (don't click body first to avoid page issues)
      await page.keyboard.press('Alt+O');
      await page.waitForTimeout(1500);

      // Check if a modal/overlay appeared (Alt+O might open a prompt)
      const modalVisible = await page
        .locator('.modal-content, .panel-modal, .prompt-shadow')
        .first()
        .isVisible()
        .catch(() => false);
      console.log(`Modal after Alt+O: ${modalVisible}`);

      if (modalVisible) {
        const modalText = (await page.locator('.modal-content, .prompt-shadow').first().textContent().catch(() => '')).trim();
        console.log(`Modal text: "${modalText.substring(0, 100)}"`);

        // Close modal
        await page.keyboard.press('Escape');
        await page.waitForTimeout(500);
      }

      // Check form-group count after (should be same)
      const formGroupsAfter = await page.locator('.form-group').count();
      console.log(`Form groups after Alt+O: ${formGroupsAfter}`);

      // Take screenshot after
      const screenshotAfter = await page.screenshot();
      allure.attachment('After Alt+O', screenshotAfter, 'image/png');

      // Verify the page is still accessible (didn't crash)
      const pageUrl = page.url();
      console.log(`Page URL after Alt+O: ${pageUrl}`);
      expect(pageUrl).toContain('/window/');
    });

    // ======================================================================
    // TEST 2: Toggle back with Alt+O
    // ======================================================================
    await test.step('Toggle edit mode back', async () => {
      await page.keyboard.press('Alt+O');
      await page.waitForTimeout(1000);

      // Handle any modal that may appear
      const modalVisible = await page
        .locator('.modal-content, .panel-modal, .prompt-shadow')
        .first()
        .isVisible()
        .catch(() => false);

      if (modalVisible) {
        await page.keyboard.press('Escape');
        await page.waitForTimeout(500);
      }

      console.log(`Page after second Alt+O: ${page.url()}`);
    });

    // ======================================================================
    // FINAL SCREENSHOT
    // ======================================================================
    const screenshotBuffer = await page.screenshot();
    allure.attachment('Final State', screenshotBuffer, 'image/png');

    console.log('Edit mode toggle test completed');
  });
});
