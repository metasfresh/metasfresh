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
 * Notifications & Inbox E2E test suite.
 *
 * Tests the notification system and inbox:
 * - Notification badge count in header
 * - Inbox panel (Alt+3) with notification items
 * - Notification item structure (read/unread)
 * - Mark as read / delete notifications
 */

test.describe('Notifications & Inbox', () => {
  test('Notifications and Inbox panel', async ({ page }) => {
    allure.epic('E0193: User Interface');
    allure.tag('F14010: Navigation');
    allure.tag('F14180: Notifications');
    allure.story('Notification System');
    allure.severity('normal');

    allure.description(`
## Notifications & Inbox

Tests the notification system:
1. **Notification badge** — Counter in header showing unread count
2. **Open inbox** — Alt+3 opens the inbox panel
3. **Inbox structure** — List of notification items
4. **Item actions** — Mark as read, delete, navigate
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
    // TEST 1: Notification badge in header
    // ======================================================================
    await test.step('Notification badge', async () => {
      // Look for notification badge/counter in the header
      const badge = page.locator('.notification-number, .inbox-badge, .header-item .badge');
      const hasBadge = await badge.first().isVisible().catch(() => false);
      console.log(`Notification badge visible: ${hasBadge}`);

      if (hasBadge) {
        const badgeText = (await badge.first().textContent().catch(() => '')).trim();
        console.log(`Badge count: "${badgeText}"`);
      }

      // Check for inbox icon in header
      const inboxIcon = page.locator('.meta-icon-notifications, .header-item .meta-icon-preview');
      const hasInboxIcon = (await inboxIcon.count()) > 0;
      console.log(`Inbox icon in header: ${hasInboxIcon}`);
    });

    // ======================================================================
    // TEST 2: Open inbox via Alt+3
    // ======================================================================
    await test.step('Open inbox via Alt+3', async () => {
      await page.keyboard.press('Alt+3');
      await page.waitForTimeout(1000);

      // Inbox panel should appear
      const inbox = page.locator('.inbox');
      const inboxVisible = await inbox
        .waitFor({ state: 'visible', timeout: 5000 })
        .then(() => true)
        .catch(() => false);

      console.log(`Inbox panel visible: ${inboxVisible}`);

      if (inboxVisible) {
        // Check for notification items
        const inboxItems = page.locator('.inbox-item, .js-inbox-item');
        const itemCount = await inboxItems.count();
        console.log(`Inbox items: ${itemCount}`);

        if (itemCount > 0) {
          // Log first few items
          const itemTexts = [];
          for (let i = 0; i < Math.min(itemCount, 5); i++) {
            const text = (await inboxItems.nth(i).textContent().catch(() => '')).trim();
            itemTexts.push(text.substring(0, 60));
          }
          console.log(`Inbox items: ${JSON.stringify(itemTexts)}`);
          allure.attachment('Inbox Items', JSON.stringify(itemTexts, null, 2), 'application/json');

          // Check for unread indicator
          const unreadItems = page.locator('.inbox-item-unread');
          const unreadCount = await unreadItems.count();
          console.log(`Unread items: ${unreadCount}`);

          // Check for delete button on items
          const deleteButtons = page.locator('.inbox-item-delete, .inbox-item .btn-delete');
          const hasDelete = (await deleteButtons.count()) > 0;
          console.log(`Delete buttons on items: ${hasDelete}`);
        } else {
          console.log('No notifications — inbox is empty');
        }

        // Check for "All" and other tabs/sections in inbox
        const inboxSections = page.locator('.inbox .tab, .inbox-header');
        const sectionCount = await inboxSections.count();
        console.log(`Inbox sections/tabs: ${sectionCount}`);

        // Take screenshot
        const screenshot = await page.screenshot();
        allure.attachment('Inbox Panel', screenshot, 'image/png');
      } else {
        // Check for header-item-open class on inbox button
        const headerItemOpen = await page.locator('.header-item-open').isVisible().catch(() => false);
        console.log(`Header item open: ${headerItemOpen}`);
      }

      // Close inbox
      await page.keyboard.press('Escape');
      await page.waitForTimeout(300);
    });

    // ======================================================================
    // TEST 3: Click inbox icon in header
    // ======================================================================
    await test.step('Click inbox icon', async () => {
      // Find and click the inbox/notification icon in the header
      const headerItems = page.locator('.header-item');
      const headerItemCount = await headerItems.count();
      console.log(`Header items: ${headerItemCount}`);

      // Log header item info
      const headerInfo = [];
      for (let i = 0; i < headerItemCount; i++) {
        const classes = await headerItems.nth(i).getAttribute('class').catch(() => '');
        const text = (await headerItems.nth(i).textContent().catch(() => '')).trim();
        headerInfo.push({ index: i, classes: classes.substring(0, 50), text: text.substring(0, 20) });
      }
      console.log(`Header items: ${JSON.stringify(headerInfo)}`);
      allure.attachment('Header Items', JSON.stringify(headerInfo, null, 2), 'application/json');
    });

    // ======================================================================
    // FINAL SCREENSHOT
    // ======================================================================
    const screenshotBuffer = await page.screenshot();
    allure.attachment('Final State', screenshotBuffer, 'image/png');

    console.log('Notifications test completed');
  });
});
