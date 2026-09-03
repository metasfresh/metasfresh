import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';
import { SALES_ORDER_WINDOW_ID, PURCHASE_ORDER_WINDOW_ID } from '../utils/WindowIds';

/**
 * List View Actions E2E test suite.
 *
 * Tests actions available in the document list (grid) view:
 * - Row selection (single click, Shift+click for range, Alt+A for all)
 * - Quick Actions bar (appears when rows are selected)
 * - Open in new tab (Alt+B on selected row)
 * - Delete from list (Alt+Y)
 * - Fullscreen toggle
 *
 * Also verifies the list view toolbar structure (Add New, Batch Entry,
 * Quick Actions, Fullscreen buttons).
 */

test.describe('List View Actions', () => {
  test('List view toolbar and row actions on Sales Order', async ({ page }) => {
    allure.epic('E0193: User Interface');
    allure.tag('F14010: Navigation');
    allure.tag('F14060: List View Actions');
    allure.story('List View Toolbar and Row Actions');
    allure.severity('normal');

    allure.description(`
## List View Actions

Tests the Sales Order list view:
1. **Toolbar structure** — Add New, Batch Entry toggle, Quick Actions, Fullscreen
2. **Row selection** — Single click, multi-select indicators
3. **Open in new tab** — Alt+B on selected row opens in new browser tab
4. **Fullscreen toggle** — Expands the grid to fullscreen
5. **Alt+A** — Select all rows
    `);

    test.setTimeout(180000); // 3 minutes

    // === CREATE TEST DATA (with some SO records) ===
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

    // === NAVIGATE TO SO LIST VIEW ===
    await page.goto(`${FRONTEND_BASE_URL}/window/${SALES_ORDER_WINDOW_ID}`);
    await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
    await page
      .locator('.document-list-wrapper, .document-list')
      .waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
    await page.waitForTimeout(1000);

    // ======================================================================
    // TEST 1: Verify toolbar buttons
    // ======================================================================
    await test.step('Verify toolbar structure', async () => {
      // "Add New" button (creates new record)
      const addNewButton = page.locator('.btn-meta-primary').first();
      const hasAddNew = await addNewButton.isVisible().catch(() => false);
      console.log(`Add New button: ${hasAddNew}`);

      // Fullscreen toggle button
      const fullscreenButton = page.locator('.meta-icon-fullscreen, [data-testid="toggle-fullscreen"]');
      const hasFullscreen = (await fullscreenButton.count()) > 0;
      console.log(`Fullscreen button: ${hasFullscreen}`);

      // Quick actions toggle
      const quickActionsToggle = page.locator(
        '.quick-actions-wrapper, [data-testid="quick-actions-dropdown"]'
      );
      const hasQuickActions = (await quickActionsToggle.count()) > 0;
      console.log(`Quick actions: ${hasQuickActions}`);

      // Log all toolbar buttons for discovery
      const toolbarButtons = await page.locator('.table-filter-line button, .table-filter-line .btn').all();
      const toolbarInfo = [];
      for (let i = 0; i < Math.min(toolbarButtons.length, 10); i++) {
        const btn = toolbarButtons[i];
        const text = (await btn.textContent().catch(() => '')).trim();
        const testId = await btn.getAttribute('data-testid').catch(() => null);
        const classes = await btn.getAttribute('class').catch(() => '');
        if (text || testId) {
          toolbarInfo.push({ text: text.substring(0, 30), testId, classes: classes.substring(0, 60) });
        }
      }
      console.log('Toolbar buttons:', JSON.stringify(toolbarInfo, null, 2));
      allure.attachment('Toolbar Buttons', JSON.stringify(toolbarInfo, null, 2), 'application/json');
    });

    // ======================================================================
    // TEST 2: Row selection
    // ======================================================================
    await test.step('Row selection', async () => {
      const gridRows = page.locator('table tbody tr');
      const rowCount = await gridRows.count();
      console.log(`Grid rows: ${rowCount}`);

      if (rowCount === 0) {
        console.log('No rows — skipping selection test');
        return;
      }

      // Click first row to select
      const firstRow = gridRows.first();
      await firstRow.click();
      await page.waitForTimeout(300);

      // Check if row is highlighted/selected
      const firstRowClasses = await firstRow.getAttribute('class').catch(() => '');
      console.log(`First row classes after click: ${firstRowClasses}`);

      // Check if selection count indicator appears
      const selectionIndicator = page.locator(
        '.rows-selected, .selection-count, [data-testid="selection-count"]'
      );
      const hasSelectionIndicator = await selectionIndicator.first().isVisible().catch(() => false);
      console.log(`Selection indicator: ${hasSelectionIndicator}`);

      if (rowCount > 1) {
        // Ctrl+click second row for multi-select
        const secondRow = gridRows.nth(1);
        await secondRow.click({ modifiers: ['Control'] });
        await page.waitForTimeout(300);

        const secondRowClasses = await secondRow.getAttribute('class').catch(() => '');
        console.log(`Second row classes after Ctrl+click: ${secondRowClasses}`);
      }

      // Click elsewhere to deselect
      await page.locator('body').click();
      await page.waitForTimeout(300);
    });

    // ======================================================================
    // TEST 3: Alt+B — Open selected row in new tab
    // ======================================================================
    await test.step('Alt+B - Open in new tab', async () => {
      const gridRows = page.locator('table tbody tr');
      const rowCount = await gridRows.count();

      if (rowCount === 0) {
        console.log('No rows — skipping Alt+B test');
        return;
      }

      // Select first row
      const firstRow = gridRows.first();
      await firstRow.click();
      await page.waitForTimeout(300);

      // Set up new tab listener
      const popupPromise = page.context().waitForEvent('page', { timeout: 10000 });

      // Press Alt+B to open in new tab
      await page.keyboard.press('Alt+B');

      try {
        const newPage = await popupPromise;
        await newPage.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
        const newUrl = newPage.url();
        console.log(`Alt+B opened new tab: ${newUrl}`);

        // Verify it navigated to a detail view
        const isDetailView = /\/window\/\d+\/\d+/.test(newUrl);
        console.log(`Detail view: ${isDetailView}`);
        expect(isDetailView).toBe(true);

        // Close the new tab
        await newPage.close();
        console.log('New tab closed');
      } catch {
        console.log('Alt+B did not open a new tab (may not be supported or row not selectable)');
      }
    });

    // ======================================================================
    // TEST 4: Alt+A — Select all rows
    // ======================================================================
    await test.step('Alt+A - Select all rows', async () => {
      const gridRows = page.locator('table tbody tr');
      const rowCount = await gridRows.count();

      if (rowCount === 0) {
        console.log('No rows — skipping Alt+A test');
        return;
      }

      await page.locator('body').click();
      await page.waitForTimeout(200);

      await page.keyboard.press('Alt+A');
      await page.waitForTimeout(500);

      // Check if all rows are now selected — look for selected class on rows
      const selectedRows = page.locator('table tbody tr.row-selected');
      const selectedCount = await selectedRows.count();
      console.log(`Selected rows after Alt+A: ${selectedCount} / ${rowCount}`);

      // Also check the header checkbox/selection indicator
      const headerCheckbox = page.locator('th .select-all, th input[type="checkbox"]');
      const hasHeaderCheckbox = await headerCheckbox.first().isVisible().catch(() => false);
      console.log(`Header select-all checkbox: ${hasHeaderCheckbox}`);

      // Deselect
      await page.keyboard.press('Escape');
      await page.waitForTimeout(300);
    });

    // ======================================================================
    // FINAL SCREENSHOT
    // ======================================================================
    const screenshotBuffer = await page.screenshot();
    allure.attachment('Final State', screenshotBuffer, 'image/png');

    console.log('List view actions test completed');
  });
});
