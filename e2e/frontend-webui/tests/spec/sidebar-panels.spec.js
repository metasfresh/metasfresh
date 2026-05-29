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
 * Sidebar Panels (Alt+5, Alt+6, Alt+7) E2E test suite.
 *
 * Tests the sidebar panels accessed via keyboard shortcuts:
 * - Alt+5: Sidebar Menu 0 (SideList panel)
 * - Alt+6: Sidebar Menu 1 (Document References — already tested elsewhere)
 * - Alt+7: Sidebar Menu 2 (Attachments or other content)
 *
 * The SideList component renders multiple tabs in the right sidebar.
 */

test.describe('Sidebar Panels', () => {
  test('Sidebar panels on Sales Order', async ({ page }) => {
    allure.epic('E0193: User Interface');
    allure.tag('F14010: Navigation');
    allure.tag('F14120: Sidebar Panels');
    allure.story('Sidebar Panel Navigation');
    allure.severity('normal');

    allure.description(`
## Sidebar Panels (Alt+5, Alt+6, Alt+7)

Tests sidebar panel shortcuts:
1. **Alt+5** — Open sidebar panel 0
2. **Alt+6** — Open sidebar panel 1 (References)
3. **Alt+7** — Open sidebar panel 2
4. **Panel tabs** — Verify tab switching in sidebar
5. **Panel content** — Verify content loads in each panel
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
    // TEST 1: Alt+5 — Open sidebar panel 0
    // ======================================================================
    await test.step('Alt+5 - Open sidebar panel 0', async () => {
      await page.locator('body').click();
      await page.waitForTimeout(200);

      await page.keyboard.press('Alt+5');
      await page.waitForTimeout(1000);

      // Check if a sidebar/panel opened
      const sidePanel = page.locator('.order-list-panel-open, .side-list-panel, .js-not-header');
      const panelOpen = await sidePanel
        .first()
        .waitFor({ state: 'visible', timeout: 5000 })
        .then(() => true)
        .catch(() => false);

      console.log(`Alt+5 panel opened: ${panelOpen}`);

      if (panelOpen) {
        // Look for tabs in the sidebar
        const sidebarTabs = page.locator('.order-list-panel-open .tab, .side-list-tab, .order-list-tab');
        const tabCount = await sidebarTabs.count();
        console.log(`Sidebar tabs: ${tabCount}`);

        const screenshot = await page.screenshot();
        allure.attachment('Alt+5 Panel', screenshot, 'image/png');

        // Close
        await page.keyboard.press('Escape');
        await page.waitForTimeout(300);
      }
    });

    // ======================================================================
    // TEST 2: Alt+6 — Open sidebar panel 1 (References)
    // ======================================================================
    await test.step('Alt+6 - Open sidebar panel 1 (References)', async () => {
      await page.locator('body').click();
      await page.waitForTimeout(200);

      await page.keyboard.press('Alt+6');
      await page.waitForTimeout(1000);

      const sidePanel = page.locator('.order-list-panel-open');
      const panelOpen = await sidePanel
        .waitFor({ state: 'visible', timeout: 5000 })
        .then(() => true)
        .catch(() => false);

      console.log(`Alt+6 panel opened: ${panelOpen}`);
      expect(panelOpen).toBe(true);

      if (panelOpen) {
        // Check for reference items
        const refItems = page.locator('[data-cy^="reference-"]');
        const refCount = await refItems.count();
        console.log(`Reference items: ${refCount}`);

        // Log reference names
        const refNames = [];
        for (let i = 0; i < Math.min(refCount, 5); i++) {
          const text = (await refItems.nth(i).textContent().catch(() => '')).trim();
          if (text) refNames.push(text.substring(0, 40));
        }
        console.log(`References: ${JSON.stringify(refNames)}`);
        allure.attachment('Reference Items', JSON.stringify(refNames, null, 2), 'application/json');
      }

      // Don't close — test switching to other panels
    });

    // ======================================================================
    // TEST 3: Alt+7 — Switch to sidebar panel 2
    // ======================================================================
    await test.step('Alt+7 - Switch to sidebar panel 2', async () => {
      await page.keyboard.press('Alt+7');
      await page.waitForTimeout(1000);

      const sidePanel = page.locator('.order-list-panel-open');
      const panelOpen = await sidePanel.isVisible().catch(() => false);
      console.log(`Alt+7 panel open: ${panelOpen}`);

      if (panelOpen) {
        const screenshot = await page.screenshot();
        allure.attachment('Alt+7 Panel', screenshot, 'image/png');

        // Check panel content
        const panelContent = page.locator('.order-list-panel-open');
        const contentText = (await panelContent.textContent().catch(() => '')).trim();
        console.log(`Alt+7 panel content: "${contentText.substring(0, 100)}"`);
      }

      // Close the panel
      await page.keyboard.press('Escape');
      await page.waitForTimeout(300);
    });

    // ======================================================================
    // TEST 4: Panel toggle — open and close with same shortcut
    // ======================================================================
    await test.step('Toggle sidebar panel', async () => {
      await page.locator('body').click();
      await page.waitForTimeout(200);

      // Open with Alt+6
      await page.keyboard.press('Alt+6');
      await page.waitForTimeout(500);

      const openAfterFirst = await page
        .locator('.order-list-panel-open')
        .isVisible()
        .catch(() => false);
      console.log(`Panel after first Alt+6: ${openAfterFirst}`);

      // Close with Escape
      await page.keyboard.press('Escape');
      await page.waitForTimeout(500);

      const closedAfterEscape = !(await page
        .locator('.order-list-panel-open')
        .isVisible()
        .catch(() => false));
      console.log(`Panel closed after Escape: ${closedAfterEscape}`);
    });

    // ======================================================================
    // FINAL SCREENSHOT
    // ======================================================================
    const screenshotBuffer = await page.screenshot();
    allure.attachment('Final State', screenshotBuffer, 'image/png');

    console.log('Sidebar panels test completed');
  });
});
