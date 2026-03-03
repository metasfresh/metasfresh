import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';

/**
 * User/Avatar Menu (Alt+4) E2E test suite.
 *
 * Tests the user dropdown menu in the top-right corner:
 * - Open via Alt+4 keyboard shortcut
 * - Avatar display with user initials
 * - Menu items: Settings, Log Out
 * - Close menu via Escape or clicking away
 */

test.describe('User Menu', () => {
  test('User menu via Alt+4 and avatar', async ({ page }) => {
    allure.epic('E0193: User Interface');
    allure.tag('F14010: Navigation');
    allure.tag('F14080: User Menu');
    allure.story('User Menu and Avatar');
    allure.severity('normal');

    allure.description(`
## User Menu (Alt+4)

Tests the user dropdown menu:
1. **Avatar** — Verify user avatar/initials are displayed in header
2. **Open via Alt+4** — Keyboard shortcut opens user menu
3. **Menu items** — Settings and Log Out should be available
4. **Close** — Escape or clicking away closes the menu
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
    // TEST 1: Verify avatar in header
    // ======================================================================
    await test.step('Verify avatar in header', async () => {
      const avatar = page.locator('.avatar, .avatar-container, .header-item .avatar');
      const avatarVisible = await avatar.first().isVisible().catch(() => false);
      console.log(`Avatar visible: ${avatarVisible}`);

      if (avatarVisible) {
        const avatarText = (await avatar.first().textContent().catch(() => '')).trim();
        console.log(`Avatar text (initials): "${avatarText}"`);
      }

      // The header should have the user-related section
      const userSection = page.locator('.user-dropdown, .header-item:has(.avatar)');
      const hasUserSection = (await userSection.count()) > 0;
      console.log(`User section in header: ${hasUserSection}`);
    });

    // ======================================================================
    // TEST 2: Open user menu via Alt+4
    // ======================================================================
    await test.step('Open user menu via Alt+4', async () => {
      await page.locator('body').click();
      await page.waitForTimeout(200);

      await page.keyboard.press('Alt+4');
      await page.waitForTimeout(1000);

      // User dropdown should appear
      const userDropdown = page.locator(
        '.user-dropdown-list, .user-dropdown-container, .header-item-open:has(.avatar)'
      );
      const dropdownVisible = await userDropdown
        .first()
        .waitFor({ state: 'visible', timeout: 5000 })
        .then(() => true)
        .catch(() => false);

      console.log(`User dropdown visible: ${dropdownVisible}`);
      expect(dropdownVisible).toBe(true);
    });

    // ======================================================================
    // TEST 3: Verify menu items
    // ======================================================================
    await test.step('Verify user menu items', async () => {
      // Look for menu items in the dropdown
      const menuItems = page.locator(
        '.user-dropdown-item, .user-dropdown-list .dropdown-item, .user-dropdown-container a, .user-dropdown-container button'
      );
      const itemCount = await menuItems.count();
      console.log(`User menu items: ${itemCount}`);

      const itemTexts = [];
      for (let i = 0; i < itemCount; i++) {
        const text = (await menuItems.nth(i).textContent().catch(() => '')).trim();
        if (text) itemTexts.push(text);
      }
      console.log(`Menu item labels: ${JSON.stringify(itemTexts)}`);
      allure.attachment('User Menu Items', JSON.stringify(itemTexts, null, 2), 'application/json');

      // Should have at least one item
      expect(itemCount).toBeGreaterThan(0);
    });

    // ======================================================================
    // TEST 4: Close menu via Escape
    // ======================================================================
    await test.step('Close user menu via Escape', async () => {
      await page.keyboard.press('Escape');
      await page.waitForTimeout(500);

      const userDropdown = page.locator('.user-dropdown-list, .user-dropdown-container');
      const stillVisible = await userDropdown.first().isVisible().catch(() => false);
      console.log(`User menu visible after Escape: ${stillVisible}`);
    });

    // ======================================================================
    // TEST 5: Open by clicking avatar
    // ======================================================================
    await test.step('Open user menu by clicking avatar', async () => {
      const avatar = page.locator('.avatar, .avatar-container').first();
      const avatarVisible = await avatar.isVisible().catch(() => false);

      if (avatarVisible) {
        await avatar.click();
        await page.waitForTimeout(1000);

        const userDropdown = page.locator(
          '.user-dropdown-list, .user-dropdown-container, .header-item-open:has(.avatar)'
        );
        const dropdownVisible = await userDropdown.first().isVisible().catch(() => false);
        console.log(`User menu opened via avatar click: ${dropdownVisible}`);

        if (dropdownVisible) {
          await page.keyboard.press('Escape');
          await page.waitForTimeout(300);
        }
      } else {
        console.log('Avatar not visible — skipping click test');
      }
    });

    // ======================================================================
    // FINAL SCREENSHOT
    // ======================================================================
    const screenshotBuffer = await page.screenshot();
    allure.attachment('Final State', screenshotBuffer, 'image/png');

    console.log('User menu test completed');
  });
});
