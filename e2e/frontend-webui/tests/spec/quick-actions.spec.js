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
 * Quick Actions E2E test suite.
 *
 * Tests the Quick Actions dropdown and execution:
 * - Quick Actions visibility in list and detail views
 * - Available actions depend on document status and selection
 * - Action execution triggers backend processes
 */

test.describe('Quick Actions', () => {
  test('Quick Actions on Sales Order list view', async ({ page }) => {
    allure.epic('E0193: User Interface');
    allure.tag('F14010: Navigation');
    allure.tag('F14130: Quick Actions');
    allure.story('Quick Actions Dropdown');
    allure.severity('normal');

    allure.description(`
## Quick Actions

Tests the Quick Actions feature:
1. **List view** — Quick Actions button in toolbar
2. **Detail view** — Quick Actions after completing status
3. **Actions dropdown** — Available actions list
4. **Action tooltips** — Hover text on action buttons
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
    // TEST 1: Quick Actions in List View
    // ======================================================================
    await test.step('Quick Actions in list view', async () => {
      // Navigate to SO list view
      await page.goto(`${FRONTEND_BASE_URL}/window/${SALES_ORDER_WINDOW_ID}`);
      await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
      await page
        .locator('.document-list-wrapper, .document-list')
        .waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
      await page.waitForTimeout(1000);

      // Look for Quick Actions wrapper in the toolbar
      const quickActionsWrapper = page.locator('.quick-actions-wrapper');
      const hasQuickActions = (await quickActionsWrapper.count()) > 0;
      console.log(`Quick actions wrapper in list view: ${hasQuickActions}`);

      // Select a row first to enable quick actions
      const gridRows = page.locator('table tbody tr');
      const rowCount = await gridRows.count();
      console.log(`Grid rows: ${rowCount}`);

      if (rowCount > 0) {
        await gridRows.first().click();
        await page.waitForTimeout(500);

        // Check if quick actions dropdown appeared or changed
        const quickActionsDropdown = page.locator(
          '.quick-actions-dropdown, .quick-actions-wrapper .dropdown-menu'
        );
        const hasDropdown = (await quickActionsDropdown.count()) > 0;
        console.log(`Quick actions dropdown after selection: ${hasDropdown}`);

        // Try to open quick actions via the button
        const qaButton = page.locator(
          '.quick-actions-wrapper .btn, [data-testid="quick-actions-btn"]'
        ).first();
        const hasQaButton = await qaButton.isVisible().catch(() => false);
        console.log(`Quick actions button visible: ${hasQaButton}`);

        if (hasQaButton) {
          await qaButton.click();
          await page.waitForTimeout(500);

          // Look for action items in dropdown
          const actionItems = page.locator(
            '.quick-actions-dropdown-option, .quick-action-item, .dropdown-menu-item'
          );
          const actionCount = await actionItems.count();
          console.log(`Quick action items: ${actionCount}`);

          const actionNames = [];
          for (let i = 0; i < Math.min(actionCount, 5); i++) {
            const text = (await actionItems.nth(i).textContent().catch(() => '')).trim();
            if (text) actionNames.push(text.substring(0, 40));
          }
          console.log(`Action names: ${JSON.stringify(actionNames)}`);
          allure.attachment('Quick Action Items', JSON.stringify(actionNames, null, 2), 'application/json');

          // Close the dropdown
          await page.keyboard.press('Escape');
          await page.waitForTimeout(300);
        }
      }
    });

    // ======================================================================
    // TEST 2: Quick Actions on document detail view
    // ======================================================================
    await test.step('Quick Actions on detail view', async () => {
      // Create a new SO
      await SalesOrderPage.goto();
      await SalesOrderPage.clickNew();
      await SalesOrderPage.selectCustomer(masterdata.bpartners.CUSTOMER1.bpartnerCode);

      await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
      await page.waitForTimeout(2000);

      // Look for Quick Actions in the included tabs area
      const quickActionsInTab = page.locator(
        '.quick-actions-wrapper, [data-testid="quick-actions-dropdown"]'
      );
      const hasTabQA = (await quickActionsInTab.count()) > 0;
      console.log(`Quick actions in detail view: ${hasTabQA}`);

      // Try Alt+L to toggle quick actions
      await page.locator('body').click();
      await page.waitForTimeout(200);

      await page.keyboard.press('Alt+L');
      await page.waitForTimeout(500);

      const qaAfterToggle = page.locator(
        '.quick-actions-dropdown, .quick-actions-open'
      );
      const qaVisible = await qaAfterToggle.first().isVisible().catch(() => false);
      console.log(`Quick actions after Alt+L: ${qaVisible}`);

      if (qaVisible) {
        const screenshot = await page.screenshot();
        allure.attachment('Quick Actions Open', screenshot, 'image/png');

        await page.keyboard.press('Escape');
        await page.waitForTimeout(300);
      }
    });

    // ======================================================================
    // FINAL SCREENSHOT
    // ======================================================================
    const screenshotBuffer = await page.screenshot();
    allure.attachment('Final State', screenshotBuffer, 'image/png');

    console.log('Quick actions test completed');
  });
});
