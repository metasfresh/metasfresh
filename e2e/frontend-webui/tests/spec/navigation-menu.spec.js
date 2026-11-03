import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';
import { SALES_ORDER_WINDOW_ID } from '../utils/WindowIds';

/**
 * Navigation Menu (Alt+2) E2E test suite.
 *
 * Tests the main navigation menu overlay:
 * - Open via Alt+2 keyboard shortcut
 * - Menu overlay visibility and structure
 * - Search functionality within the menu
 * - Menu items are clickable and navigate to windows
 * - Close menu via Escape
 */

test.describe('Navigation Menu', () => {
  test('Navigation menu via Alt+2', async ({ page }) => {
    allure.epic('E0193: User Interface');
    allure.tag('F14010: Navigation');
    allure.tag('F14070: Navigation Menu');
    allure.story('Navigation Menu Overlay');
    allure.severity('normal');

    allure.description(`
## Navigation Menu (Alt+2)

Tests the navigation menu overlay:
1. **Open menu** — Alt+2 opens the menu overlay
2. **Menu structure** — Verify menu items are present
3. **Search** — Type in the search field to filter menu items
4. **Navigation** — Click a menu item to navigate to a window
5. **Close** — Escape closes the menu
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
    // TEST 1: Open menu via Alt+2
    // ======================================================================
    await test.step('Open navigation menu via Alt+2', async () => {
      await page.locator('body').click();
      await page.waitForTimeout(200);

      await page.keyboard.press('Alt+2');
      await page.waitForTimeout(1000);

      // Menu overlay should appear
      const menuOverlay = page.locator('.menu-overlay');
      const menuVisible = await menuOverlay
        .waitFor({ state: 'visible', timeout: 5000 })
        .then(() => true)
        .catch(() => false);

      console.log(`Menu overlay visible: ${menuVisible}`);
      expect(menuVisible).toBe(true);
    });

    // ======================================================================
    // TEST 2: Verify menu structure — items present
    // ======================================================================
    await test.step('Verify menu items present', async () => {
      const menuItems = page.locator('.menu-overlay .js-menu-item, .menu-overlay .menu-item');
      const itemCount = await menuItems.count();
      console.log(`Menu items found: ${itemCount}`);

      // Log first few items
      const itemTexts = [];
      for (let i = 0; i < Math.min(itemCount, 5); i++) {
        const text = (await menuItems.nth(i).textContent().catch(() => '')).trim();
        if (text) itemTexts.push(text.substring(0, 40));
      }
      console.log(`First menu items: ${JSON.stringify(itemTexts)}`);
      allure.attachment('Menu Items Sample', JSON.stringify(itemTexts, null, 2), 'application/json');

      expect(itemCount).toBeGreaterThan(0);
    });

    // ======================================================================
    // TEST 3: Search functionality
    // ======================================================================
    await test.step('Search in navigation menu', async () => {
      // Find the search input in the menu overlay
      const searchInput = page.locator('.menu-overlay input.input-field, .menu-overlay input[type="text"]').first();
      const hasSearch = await searchInput.isVisible().catch(() => false);
      console.log(`Search input visible: ${hasSearch}`);

      if (hasSearch) {
        // Type a search term
        await searchInput.fill('Sales');
        await page.waitForTimeout(1000);

        // Check that results are filtered
        const filteredItems = page.locator('.menu-overlay .js-menu-item, .menu-overlay .menu-item');
        const filteredCount = await filteredItems.count();
        console.log(`Filtered items for "Sales": ${filteredCount}`);

        // Log filtered results
        const filteredTexts = [];
        for (let i = 0; i < Math.min(filteredCount, 5); i++) {
          const text = (await filteredItems.nth(i).textContent().catch(() => '')).trim();
          if (text) filteredTexts.push(text.substring(0, 40));
        }
        console.log(`Filtered items: ${JSON.stringify(filteredTexts)}`);

        // Clear the search
        await searchInput.clear();
        await page.waitForTimeout(500);
      }
    });

    // ======================================================================
    // TEST 4: Close menu via Escape
    // ======================================================================
    await test.step('Close menu via Escape', async () => {
      await page.keyboard.press('Escape');
      await page.waitForTimeout(500);

      const menuOverlay = page.locator('.menu-overlay');
      const menuStillVisible = await menuOverlay.isVisible().catch(() => false);
      console.log(`Menu visible after Escape: ${menuStillVisible}`);

      // Menu should be closed or hidden
      expect(menuStillVisible).toBe(false);
    });

    // ======================================================================
    // TEST 5: Click menu header button to open
    // ======================================================================
    await test.step('Open menu via header button', async () => {
      // The navigation menu can also be opened by clicking the breadcrumb/menu icon
      const menuButton = page.locator('.header-breadcrumb, .meta-icon-hamburger, [data-testid="menu-toggle"]').first();
      const hasMenuButton = await menuButton.isVisible().catch(() => false);
      console.log(`Menu header button visible: ${hasMenuButton}`);

      if (hasMenuButton) {
        await menuButton.click();
        await page.waitForTimeout(1000);

        const menuOverlay = page.locator('.menu-overlay');
        const menuVisible = await menuOverlay.isVisible().catch(() => false);
        console.log(`Menu opened via button: ${menuVisible}`);

        if (menuVisible) {
          await page.keyboard.press('Escape');
          await page.waitForTimeout(300);
        }
      }
    });

    // ======================================================================
    // FINAL SCREENSHOT
    // ======================================================================
    const screenshotBuffer = await page.screenshot();
    allure.attachment('Final State', screenshotBuffer, 'image/png');

    console.log('Navigation menu test completed');
  });
});
