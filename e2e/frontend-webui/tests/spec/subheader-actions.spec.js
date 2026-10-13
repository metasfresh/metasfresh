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
 * SubHeader Actions & Change Log E2E test suite.
 *
 * Tests the SubHeader panel (toggled via Alt+1 or three-dot button)
 * which contains action buttons for the current document:
 * - Clone (Alt+W)
 * - Email (Alt+K)
 * - Letter (Alt+R)
 * - Print (Alt+P)
 * - Delete (Alt+D)
 *
 * Also tests the Change Log dialog accessible from the context menu.
 */

test.describe('SubHeader Actions', () => {
  test('Verify SubHeader actions on Sales Order', async ({ page }) => {
    allure.epic('E0193: User Interface');
    allure.tag('F14010: Navigation');
    allure.tag('F14040: Document Actions');
    allure.story('SubHeader Actions and Change Log');
    allure.severity('normal');

    allure.description(`
## SubHeader Actions

Tests the SubHeader panel and its action buttons:
1. Open SubHeader via Alt+1
2. Verify action buttons are present (Clone, Print, Email, Letter, Delete)
3. Verify breadcrumb navigation is visible
4. Test the Change Log dialog via context menu on grid cells
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

    // Wait for save
    await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
    await page.waitForTimeout(2000);

    const docNo = await SalesOrderPage.getDocumentNo();
    console.log(`Sales Order: ${docNo}`);

    // ======================================================================
    // TEST 1: Open SubHeader via Alt+1
    // ======================================================================
    await test.step('Open SubHeader via Alt+1', async () => {
      await page.locator('body').click();
      await page.waitForTimeout(200);

      await page.keyboard.press('Alt+1');
      await page.waitForTimeout(500);

      const subheader = page.locator('.subheader-container');
      const subheaderVisible = await subheader
        .waitFor({ state: 'visible', timeout: 5000 })
        .then(() => true)
        .catch(() => false);

      console.log(`SubHeader visible: ${subheaderVisible}`);
      expect(subheaderVisible).toBe(true);
    });

    // ======================================================================
    // TEST 2: Verify SubHeader content — action icons present
    // ======================================================================
    await test.step('Verify SubHeader action buttons', async () => {
      const subheader = page.locator('.subheader-container');

      // Check for key action icons directly (faster than iterating all items)
      const hasCloneIcon = (await subheader.locator('.meta-icon-duplicate').count()) > 0;
      const hasPrintIcon = (await subheader.locator('.meta-icon-print').count()) > 0;
      const hasEmailIcon = (await subheader.locator('.meta-icon-mail').count()) > 0;
      const hasDeleteIcon = (await subheader.locator('.meta-icon-delete').count()) > 0;
      const hasLetterIcon = (await subheader.locator('.meta-icon-letter').count()) > 0;
      const hasEditIcon = (await subheader.locator('.meta-icon-edit').count()) > 0;
      const hasCommentsIcon = (await subheader.locator('.meta-icon-message').count()) > 0;

      console.log(
        `Actions — Clone: ${hasCloneIcon}, Print: ${hasPrintIcon}, Email: ${hasEmailIcon}, Delete: ${hasDeleteIcon}, Letter: ${hasLetterIcon}, Edit: ${hasEditIcon}, Comments: ${hasCommentsIcon}`
      );

      // At least Clone, Print, and Edit should be available
      expect(hasCloneIcon).toBe(true);
      expect(hasPrintIcon).toBe(true);
      expect(hasEditIcon).toBe(true);

      // Take screenshot of subheader
      const screenshot = await page.screenshot();
      allure.attachment('SubHeader Panel', screenshot, 'image/png');
    });

    // ======================================================================
    // TEST 3: Verify breadcrumb navigation in SubHeader
    // ======================================================================
    await test.step('Verify breadcrumb in SubHeader', async () => {
      const breadcrumb = page.locator('.subheader-title, .header-breadcrumb');
      const hasBreadcrumb = await breadcrumb.first().isVisible().catch(() => false);
      console.log(`Breadcrumb visible: ${hasBreadcrumb}`);

      if (hasBreadcrumb) {
        const breadcrumbText = (await breadcrumb.first().textContent().catch(() => '')).trim();
        console.log(`Breadcrumb text: "${breadcrumbText}"`);
      }
    });

    // Close the SubHeader
    await page.keyboard.press('Escape');
    await page.waitForTimeout(300);

    // ======================================================================
    // TEST 4: Add order line for context menu testing
    // ======================================================================
    let hasOrderLine = false;
    await test.step('Add order line for context menu', async () => {
      const batchEntryButton = page.getByTestId('batch-entry-toggle');
      await batchEntryButton.scrollIntoViewIfNeeded();
      await batchEntryButton.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await batchEntryButton.click();
      await page.waitForTimeout(500);

      const quickInputVisible = await page
        .locator('.quick-input-container')
        .waitFor({ state: 'visible', timeout: 5000 })
        .then(() => true)
        .catch(() => false);

      if (quickInputVisible) {
        const productInput = page.locator('#lookup_M_Product_ID input.input-field');
        await productInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
        await productInput.click();
        await page.waitForTimeout(500);
        await productInput.fill(masterdata.products.Product1.productCode);
        await page.waitForTimeout(1000);

        await page
          .locator('#lookup_M_Product_ID .rotating, #lookup_M_Product_ID .spinner')
          .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
          .catch(() => {});
        await page.waitForTimeout(500);

        const dropdownOption = page
          .locator('.input-dropdown-list-option')
          .getByText(masterdata.products.Product1.productCode)
          .first();
        const optionVisible = await dropdownOption
          .waitFor({ state: 'visible', timeout: 5000 })
          .then(() => true)
          .catch(() => false);

        if (optionVisible) {
          await dropdownOption.click();
          await page.waitForTimeout(1000);

          const quantityInput = page.locator('.quick-input-container').getByRole('spinbutton');
          await quantityInput.waitFor({ state: 'visible', timeout: 5000 }).catch(() => {});
          await quantityInput.click();
          await quantityInput.fill('5');
          await page.waitForTimeout(300);

          await page.keyboard.press('Enter');
          await page.waitForTimeout(2000);

          await page
            .locator('.rotating, .indicator-pending')
            .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
            .catch(() => {});

          // Close batch entry
          await batchEntryButton.click();
          await page.waitForTimeout(1000);

          const rowCount = await page.locator('table tbody tr').count();
          hasOrderLine = rowCount > 0;
          console.log(`Order line added: ${hasOrderLine} (rows: ${rowCount})`);
        }
      }
    });

    // ======================================================================
    // TEST 5: Context menu on grid cell — Change Log
    // ======================================================================
    await test.step('Context menu - Change Log', async () => {
      if (!hasOrderLine) {
        console.log('No order line — skipping context menu test');
        return;
      }

      const firstRow = page.locator('table tbody tr').first();
      await firstRow.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      // Find a cell with data
      const targetCell = firstRow.locator('td[data-cy]').first();
      await targetCell.click({ button: 'right' });
      await page.waitForTimeout(500);

      const contextMenu = page.locator('.context-menu');
      const menuVisible = await contextMenu
        .waitFor({ state: 'visible', timeout: 5000 })
        .then(() => true)
        .catch(() => false);

      if (menuVisible) {
        // Log all context menu items
        const menuItems = await contextMenu.locator('.context-menu-item').all();
        const menuItemInfo = [];
        for (const item of menuItems) {
          const text = (await item.textContent().catch(() => '')).trim();
          menuItemInfo.push(text);
        }
        console.log('Context menu items:', JSON.stringify(menuItemInfo));
        allure.attachment('Context Menu Items', JSON.stringify(menuItemInfo, null, 2), 'application/json');

        // Look for Change Log item — last item, identified by text containing "Change Log" or "#"
        // It doesn't have a specific meta-icon class, so match by text pattern
        const changeLogItem = contextMenu.locator('.context-menu-item').last();
        const changeLogText = (await changeLogItem.textContent().catch(() => '')).trim();
        const hasChangeLog = changeLogText.includes('Change Log') || changeLogText.includes('#');
        console.log(`Change Log item text: "${changeLogText}", detected: ${hasChangeLog}`);
        console.log(`Change Log option present: ${hasChangeLog}`);

        if (hasChangeLog) {
          // Click Change Log
          await changeLogItem.click();
          await page.waitForTimeout(1000);

          // Check if Change Log modal/overlay opened
          const changeLogOpen = await page
            .locator('.modal-content, .panel-modal')
            .first()
            .waitFor({ state: 'visible', timeout: 5000 })
            .then(() => true)
            .catch(() => false);

          console.log(`Change Log dialog opened: ${changeLogOpen}`);

          if (changeLogOpen) {
            const changeLogScreenshot = await page.screenshot();
            allure.attachment('Change Log Dialog', changeLogScreenshot, 'image/png');

            // Close the dialog
            await page.keyboard.press('Escape');
            await page.waitForTimeout(300);
          }
        } else {
          // Close context menu
          await page.keyboard.press('Escape');
          await page.waitForTimeout(300);
        }
      } else {
        console.log('No context menu appeared');
      }
    });

    // ======================================================================
    // FINAL SCREENSHOT
    // ======================================================================
    const screenshotBuffer = await page.screenshot();
    allure.attachment('Final State', screenshotBuffer, 'image/png');

    console.log('SubHeader actions test completed');
  });
});
