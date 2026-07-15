import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';
import { SALES_ORDER_WINDOW_ID } from '../utils/WindowIds';

/**
 * Keyboard Shortcuts E2E test suite.
 *
 * Verifies that the keyboard shortcuts defined in frontend/src/shortcuts/keymap.js
 * work correctly in the WebUI.
 *
 * Shortcuts tested (from keymap.js):
 * - Alt+N: NEW_DOCUMENT — Create new record
 * - Alt+6: OPEN_SIDEBAR_MENU_1 — Referenced documents panel
 * - Alt+E: OPEN_ADVANCED_EDIT — Advanced edit modal
 * - Alt+P: OPEN_PRINT_RAPORT — Print / PDF generation
 * - Alt+1: OPEN_ACTIONS_MENU — Toggle subheader (actions/breadcrumbs)
 * - Alt+3: OPEN_INBOX_MENU — Inbox / notifications panel
 * - Alt+I: DOC_STATUS — Document status dropdown
 */

test.describe('Keyboard Shortcuts', () => {
  test('Verify keyboard shortcuts on Sales Order window', async ({ page }) => {
    allure.epic('E0193: User Interface');
    allure.tag('F14010: Navigation');
    allure.tag('F14020: Keyboard Shortcuts');
    allure.story('Keyboard Shortcuts Verification');
    allure.severity('normal');

    allure.description(`
## Keyboard Shortcuts Verification

Tests keyboard shortcuts defined in keymap.js on a Sales Order detail view:
- **Alt+N**: Creates a new record (NEW_DOCUMENT)
- **Alt+6**: Opens referenced documents panel (OPEN_SIDEBAR_MENU_1)
- **Alt+E**: Opens advanced edit modal (OPEN_ADVANCED_EDIT)
- **Alt+P**: Opens print/PDF dialog (OPEN_PRINT_RAPORT)
- **Alt+1**: Toggles subheader with actions/breadcrumbs (OPEN_ACTIONS_MENU)
- **Alt+3**: Opens inbox/notifications (OPEN_INBOX_MENU)
- **Alt+I**: Opens document status dropdown (DOC_STATUS)
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

    const results = [];

    // ======================================================================
    // TEST 1: Alt+N — Create new record (NEW_DOCUMENT)
    // ======================================================================
    await test.step('Alt+N - Create new record', async () => {
      // Navigate to Sales Order window (list view)
      await page.goto(`${FRONTEND_BASE_URL}/window/${SALES_ORDER_WINDOW_ID}`);
      await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
      await page
        .locator('.document-list-wrapper, .document-list')
        .waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
      await page.waitForTimeout(1000);

      const urlBefore = page.url();

      // Press Alt+N
      await page.locator('body').click();
      await page.keyboard.press('Alt+N');

      // Wait for navigation to detail view
      await page.waitForURL(/\/window\/\d+\/\d+/, { timeout: SLOW_ACTION_TIMEOUT });
      await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});

      const urlAfter = page.url();
      const altNWorks = urlAfter !== urlBefore && /\/window\/\d+\/\d+/.test(urlAfter);

      console.log(`Alt+N: ${urlBefore} → ${urlAfter} (works: ${altNWorks})`);
      results.push({ shortcut: 'Alt+N', action: 'Create new record', works: altNWorks });
      expect(altNWorks).toBe(true);
    });

    // We're now on a new SO detail view. Wait for the document to fully load.
    await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
    await page.waitForTimeout(1000);

    // ======================================================================
    // TEST 2: Alt+6 — Open referenced documents panel (OPEN_SIDEBAR_MENU_1)
    // ======================================================================
    await test.step('Alt+6 - Open referenced documents panel', async () => {
      await page.locator('body').click();
      await page.waitForTimeout(200);

      await page.keyboard.press('Alt+6');
      await page.waitForTimeout(500);

      // Check if Alt+6 panel opened — .order-list-panel-open is the sidebar panel
      const panelOpen = await page
        .locator('.order-list-panel-open')
        .waitFor({ state: 'visible', timeout: 5000 })
        .then(() => true)
        .catch(() => false);

      console.log(`Alt+6: Panel opened: ${panelOpen}`);
      results.push({ shortcut: 'Alt+6', action: 'Open references panel', works: panelOpen });
      expect(panelOpen).toBe(true);

      // Close the panel
      await page.keyboard.press('Escape');
      await page.waitForTimeout(300);
    });

    // ======================================================================
    // TEST 3: Alt+E — Advanced edit / Extended tab (OPEN_ADVANCED_EDIT)
    // ======================================================================
    await test.step('Alt+E - Advanced edit (extended tab)', async () => {
      await page.locator('body').click();
      await page.waitForTimeout(200);

      await page.keyboard.press('Alt+E');
      await page.waitForTimeout(1000);

      // Advanced edit opens an overlay/modal with additional fields
      const advancedEditOpen = await page
        .locator('.panel-modal, .modal-content, .panel-modal-content')
        .first()
        .waitFor({ state: 'visible', timeout: 5000 })
        .then(() => true)
        .catch(() => false);

      console.log(`Alt+E: Advanced edit opened: ${advancedEditOpen}`);
      results.push({ shortcut: 'Alt+E', action: 'Advanced edit', works: advancedEditOpen });

      if (advancedEditOpen) {
        await page.keyboard.press('Escape');
        await page.waitForTimeout(500);
      }
    });

    // ======================================================================
    // TEST 4: Alt+P — Print / PDF (OPEN_PRINT_RAPORT)
    // ======================================================================
    await test.step('Alt+P - Print / PDF dialog', async () => {
      await page.locator('body').click();
      await page.waitForTimeout(200);

      await page.keyboard.press('Alt+P');
      await page.waitForTimeout(1000);

      // Print opens a modal or starts PDF generation
      const printModal = await page
        .locator('.modal-content, .panel-modal')
        .first()
        .waitFor({ state: 'visible', timeout: 5000 })
        .then(() => true)
        .catch(() => false);

      console.log(`Alt+P: Print modal opened: ${printModal}`);
      results.push({ shortcut: 'Alt+P', action: 'Print/PDF dialog', works: printModal });

      if (printModal) {
        await page.keyboard.press('Escape');
        await page.waitForTimeout(500);
      }
    });

    // ======================================================================
    // TEST 5: Alt+1 — Toggle subheader (OPEN_ACTIONS_MENU)
    // ======================================================================
    await test.step('Alt+1 - Toggle subheader', async () => {
      await page.locator('body').click();
      await page.waitForTimeout(200);

      // Check subheader state before
      const subheaderBefore = await page.locator('.subheader-container').isVisible().catch(() => false);

      await page.keyboard.press('Alt+1');
      await page.waitForTimeout(500);

      // Alt+1 toggles the subheader — look for .subheader-container.subheader-open
      const subheaderAfter = await page
        .locator('.subheader-container')
        .waitFor({ state: 'visible', timeout: 5000 })
        .then(() => true)
        .catch(() => false);

      const altOneWorks = subheaderBefore !== subheaderAfter;
      console.log(
        `Alt+1: Subheader toggled: ${altOneWorks} (before: ${subheaderBefore}, after: ${subheaderAfter})`
      );
      results.push({ shortcut: 'Alt+1', action: 'Toggle subheader', works: altOneWorks });

      // Close subheader if it opened
      if (subheaderAfter) {
        await page.keyboard.press('Escape');
        await page.waitForTimeout(300);
      }
    });

    // ======================================================================
    // TEST 6: Alt+3 — Open inbox / notifications (OPEN_INBOX_MENU)
    // ======================================================================
    await test.step('Alt+3 - Open inbox', async () => {
      await page.locator('body').click();
      await page.waitForTimeout(200);

      await page.keyboard.press('Alt+3');
      await page.waitForTimeout(1000);

      // Alt+3 opens the inbox/notifications panel — .inbox is conditionally rendered
      // Also check for .header-item-open on the inbox button
      const inboxOpen = await page
        .locator('.inbox')
        .waitFor({ state: 'visible', timeout: 5000 })
        .then(() => true)
        .catch(() => false);

      // Alternative: check if the inbox toggle button got header-item-open class
      const headerItemOpen = await page
        .locator('.header-item-open')
        .isVisible()
        .catch(() => false);

      const alt3Works = inboxOpen || headerItemOpen;
      console.log(`Alt+3: Inbox opened: ${alt3Works} (inbox element: ${inboxOpen}, header-item-open: ${headerItemOpen})`);
      results.push({ shortcut: 'Alt+3', action: 'Open inbox', works: alt3Works });

      if (alt3Works) {
        await page.keyboard.press('Escape');
        await page.waitForTimeout(300);
      }
    });

    // ======================================================================
    // TEST 7: Alt+I — Document status dropdown (DOC_STATUS)
    // ======================================================================
    await test.step('Alt+I - Document status dropdown', async () => {
      await page.locator('body').click();
      await page.waitForTimeout(200);

      // The DOC_STATUS shortcut focuses the document status dropdown toggler
      await page.keyboard.press('Alt+I');
      await page.waitForTimeout(500);

      // Check if the status dropdown toggler is focused or status options appeared
      const statusOptionsVisible = await page
        .locator('[data-testid="status-CO"], [data-testid="status-CL"], .dropdown-status-list')
        .first()
        .waitFor({ state: 'visible', timeout: 5000 })
        .then(() => true)
        .catch(() => false);

      // Alternative: check if the js-dropdown-toggler got focus
      const togglerFocused = await page
        .locator('.js-dropdown-toggler:focus, .meta-dropdown-toggle:focus')
        .first()
        .isVisible()
        .catch(() => false);

      const altIWorks = statusOptionsVisible || togglerFocused;
      console.log(
        `Alt+I: Status dropdown opened: ${altIWorks} (options: ${statusOptionsVisible}, toggler: ${togglerFocused})`
      );
      results.push({ shortcut: 'Alt+I', action: 'Document status dropdown', works: altIWorks });

      // Close the dropdown
      await page.keyboard.press('Escape');
      await page.waitForTimeout(300);
    });

    // ======================================================================
    // SUMMARY
    // ======================================================================
    const screenshotBuffer = await page.screenshot();
    allure.attachment('Final State', screenshotBuffer, 'image/png');

    const resultsHtml = `<table border="1">
      <tr><th>Shortcut</th><th>Action</th><th>Works</th></tr>
      ${results.map((r) => `<tr><td><code>${r.shortcut}</code></td><td>${r.action}</td><td>${r.works ? 'PASS' : 'FAIL'}</td></tr>`).join('\n')}
    </table>`;
    allure.attachment('Keyboard Shortcuts Results', resultsHtml, 'text/html');

    console.log('\n=== Keyboard Shortcuts Summary ===');
    results.forEach((r) => {
      console.log(`${r.works ? '[PASS]' : '[FAIL]'} ${r.shortcut}: ${r.action}`);
    });

    // Core shortcuts must work
    const coreShortcuts = results.filter((r) => ['Alt+N', 'Alt+6'].includes(r.shortcut));
    for (const shortcut of coreShortcuts) {
      expect(shortcut.works, `${shortcut.shortcut} must work`).toBe(true);
    }
  });
});
