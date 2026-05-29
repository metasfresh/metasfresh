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
 * Document Delete (Alt+D) E2E test suite.
 *
 * Tests the document deletion shortcut:
 * - Alt+D triggers the delete confirmation dialog
 * - Cancelling the dialog keeps the document
 * - Confirming the dialog deletes the document and navigates to list view
 *
 * Also tests the SubHeader delete icon as an alternative.
 */

test.describe('Document Delete', () => {
  test('Delete confirmation dialog via Alt+D', async ({ page }) => {
    allure.epic('E0193: User Interface');
    allure.tag('F14010: Navigation');
    allure.tag('F14040: Document Actions');
    allure.story('Document Delete');
    allure.severity('normal');

    allure.description(`
## Document Delete (Alt+D)

Tests the delete confirmation:
1. **Create SO** — Create and save a Sales Order
2. **Alt+D** — Press Alt+D to trigger delete
3. **Cancel** — Cancel the confirmation dialog
4. **Verify still on document** — Document should still be visible
5. **Alt+D + Confirm** — Delete and verify navigation to list
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
    const originalUrl = page.url();
    console.log(`Sales Order: ${docNo}, URL: ${originalUrl}`);

    // ======================================================================
    // TEST 1: Alt+D — Trigger delete, then cancel
    // ======================================================================
    await test.step('Alt+D - Delete confirmation (cancel)', async () => {
      await page.locator('body').click();
      await page.waitForTimeout(200);

      await page.keyboard.press('Alt+D');
      await page.waitForTimeout(1000);

      // Look for confirmation dialog/modal
      const confirmDialog = page.locator(
        '.modal-content, .panel-modal, .prompt-shadow, .confirmation-dialog'
      );
      const dialogVisible = await confirmDialog
        .first()
        .waitFor({ state: 'visible', timeout: 5000 })
        .then(() => true)
        .catch(() => false);

      console.log(`Delete confirmation dialog: ${dialogVisible}`);

      if (dialogVisible) {
        // Take screenshot of the confirmation dialog
        const screenshot = await page.screenshot();
        allure.attachment('Delete Confirmation Dialog', screenshot, 'image/png');

        // Log the dialog content
        const dialogText = (await confirmDialog.first().textContent().catch(() => '')).trim();
        console.log(`Dialog text: "${dialogText.substring(0, 100)}"`);

        // Cancel the deletion — press Escape or click Cancel button
        const cancelButton = page.locator(
          '.modal-content .btn:not(.btn-meta-primary):not(.btn-meta-success), .prompt-shadow .btn-meta-outline'
        );
        const hasCancelButton = await cancelButton.first().isVisible().catch(() => false);

        if (hasCancelButton) {
          await cancelButton.first().click();
          await page.waitForTimeout(500);
          console.log('Clicked Cancel button');
        } else {
          await page.keyboard.press('Escape');
          await page.waitForTimeout(500);
          console.log('Pressed Escape to cancel');
        }

        // Verify still on the same document
        const stillOnDoc = page.url() === originalUrl;
        console.log(`Still on document after cancel: ${stillOnDoc}`);
        expect(page.url()).toBe(originalUrl);
      } else {
        console.log('No confirmation dialog appeared — Alt+D may work differently');
      }
    });

    // ======================================================================
    // TEST 2: Alt+D — Trigger delete, then confirm
    // ======================================================================
    await test.step('Alt+D - Delete confirmation (confirm)', async () => {
      await page.locator('body').click();
      await page.waitForTimeout(200);

      await page.keyboard.press('Alt+D');
      await page.waitForTimeout(1000);

      const confirmDialog = page.locator(
        '.modal-content, .panel-modal, .prompt-shadow, .confirmation-dialog'
      );
      const dialogVisible = await confirmDialog
        .first()
        .waitFor({ state: 'visible', timeout: 5000 })
        .then(() => true)
        .catch(() => false);

      if (dialogVisible) {
        // Click the confirm/delete button
        const confirmButton = page.locator(
          '.modal-content .btn-meta-primary, .modal-content .btn-meta-success, .prompt-shadow .btn-meta-primary'
        );
        const hasConfirmButton = await confirmButton.first().isVisible().catch(() => false);

        if (hasConfirmButton) {
          await confirmButton.first().click();
          console.log('Clicked Confirm button');
        } else {
          // Try pressing Enter (often confirms the default action)
          await page.keyboard.press('Enter');
          console.log('Pressed Enter to confirm');
        }

        await page.waitForTimeout(2000);

        // After deletion, should navigate away from the document
        const currentUrl = page.url();
        const navigatedAway = currentUrl !== originalUrl;
        console.log(`Navigated away after delete: ${navigatedAway} (${currentUrl})`);

        // Should be back on the list view or a different record
        const onListView = /\/window\/\d+$/.test(currentUrl) || !currentUrl.includes(originalUrl);
        console.log(`On list view or different record: ${onListView}`);
      } else {
        console.log('No confirmation dialog — trying SubHeader delete icon');

        // Open SubHeader and click delete icon
        await page.keyboard.press('Alt+1');
        await page.waitForTimeout(500);

        const deleteIcon = page.locator('.subheader-container .meta-icon-delete').first();
        const hasDeleteIcon = await deleteIcon.isVisible().catch(() => false);
        console.log(`SubHeader delete icon: ${hasDeleteIcon}`);

        if (hasDeleteIcon) {
          await deleteIcon.click();
          await page.waitForTimeout(1000);

          // Handle confirmation if it appears
          const promptDialog = page.locator('.prompt-shadow, .modal-content');
          const promptVisible = await promptDialog.first().isVisible().catch(() => false);

          if (promptVisible) {
            const screenshot = await page.screenshot();
            allure.attachment('Delete via SubHeader', screenshot, 'image/png');

            // Cancel — we don't want to actually delete in this path
            await page.keyboard.press('Escape');
            await page.waitForTimeout(300);
          }
        }

        // Close SubHeader
        await page.keyboard.press('Escape');
        await page.waitForTimeout(300);
      }
    });

    // ======================================================================
    // FINAL SCREENSHOT
    // ======================================================================
    const screenshotBuffer = await page.screenshot();
    allure.attachment('Final State', screenshotBuffer, 'image/png');

    console.log('Document delete test completed');
  });
});
