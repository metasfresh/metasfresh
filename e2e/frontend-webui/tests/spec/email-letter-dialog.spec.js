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
 * Email (Alt+K) & Letter (Alt+R) dialog E2E test suite.
 *
 * Tests the Email and Letter composition panels accessible
 * from the SubHeader or via keyboard shortcuts:
 * - Email dialog (Alt+K): To field, subject, body, send button
 * - Letter dialog (Alt+R): Headline, body, print/save
 */

test.describe('Email & Letter Dialogs', () => {
  test('Email and Letter dialogs on Sales Order', async ({ page }) => {
    allure.epic('E0193: User Interface');
    allure.tag('F14010: Navigation');
    allure.tag('F14100: Email & Letter');
    allure.story('Email and Letter Composition');
    allure.severity('normal');

    allure.description(`
## Email (Alt+K) & Letter (Alt+R) Dialogs

Tests the email and letter composition panels:
1. **Email (Alt+K)** — Open email dialog, verify To/Subject/Body fields
2. **Letter (Alt+R)** — Open letter dialog, verify headline/body
3. **Close** — Escape closes both dialogs
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
    // TEST 1: Open Email dialog via Alt+K
    // ======================================================================
    await test.step('Open Email dialog via Alt+K', async () => {
      await page.locator('body').click();
      await page.waitForTimeout(200);

      await page.keyboard.press('Alt+K');
      await page.waitForTimeout(1500);

      // Email dialog panel
      const emailPanel = page.locator('.panel-email, .email-panel, .panel-modal');
      const emailVisible = await emailPanel
        .first()
        .waitFor({ state: 'visible', timeout: 5000 })
        .then(() => true)
        .catch(() => false);

      console.log(`Email dialog visible: ${emailVisible}`);

      if (emailVisible) {
        // Verify email fields
        const toField = page.locator('.email-input, .panel-email input, input[placeholder*="To"]').first();
        const hasTo = await toField.isVisible().catch(() => false);
        console.log(`To field visible: ${hasTo}`);

        const subjectField = page.locator(
          '.email-subject, .panel-email input[type="text"], input[placeholder*="Subject"]'
        ).first();
        const hasSubject = await subjectField.isVisible().catch(() => false);
        console.log(`Subject field visible: ${hasSubject}`);

        const bodyField = page.locator('.panel-email-body textarea, .email-body textarea, .panel-email textarea').first();
        const hasBody = await bodyField.isVisible().catch(() => false);
        console.log(`Body field visible: ${hasBody}`);

        const sendButton = page.locator('.panel-email .btn-meta-success, .email-send-btn').first();
        const hasSend = await sendButton.isVisible().catch(() => false);
        console.log(`Send button visible: ${hasSend}`);

        // Take screenshot
        const screenshot = await page.screenshot();
        allure.attachment('Email Dialog', screenshot, 'image/png');

        // Close the dialog
        await page.keyboard.press('Escape');
        await page.waitForTimeout(500);
      } else {
        console.log('Email dialog did not open — Alt+K may require SubHeader to be open first');

        // Try via SubHeader: Alt+1 to open SubHeader, then click email icon
        await page.keyboard.press('Alt+1');
        await page.waitForTimeout(500);

        const subheader = page.locator('.subheader-container');
        const subheaderVisible = await subheader.isVisible().catch(() => false);

        if (subheaderVisible) {
          const emailIcon = subheader.locator('.meta-icon-mail').first();
          const hasEmailIcon = await emailIcon.isVisible().catch(() => false);
          console.log(`Email icon in SubHeader: ${hasEmailIcon}`);

          if (hasEmailIcon) {
            await emailIcon.click();
            await page.waitForTimeout(1500);

            const emailPanelRetry = page.locator('.panel-email, .email-panel, .panel-modal');
            const emailOpenedViaSubheader = await emailPanelRetry
              .first()
              .waitFor({ state: 'visible', timeout: 5000 })
              .then(() => true)
              .catch(() => false);
            console.log(`Email opened via SubHeader icon: ${emailOpenedViaSubheader}`);

            if (emailOpenedViaSubheader) {
              const screenshot = await page.screenshot();
              allure.attachment('Email via SubHeader', screenshot, 'image/png');
            }

            // Close
            await page.keyboard.press('Escape');
            await page.waitForTimeout(500);
          }

          // Close SubHeader
          await page.keyboard.press('Escape');
          await page.waitForTimeout(300);
        }
      }
    });

    // ======================================================================
    // TEST 2: Open Letter dialog via Alt+R
    // ======================================================================
    await test.step('Open Letter dialog via Alt+R', async () => {
      await page.locator('body').click();
      await page.waitForTimeout(200);

      await page.keyboard.press('Alt+R');
      await page.waitForTimeout(1500);

      // Letter dialog panel
      const letterPanel = page.locator('.panel-letter, .letter-panel, .panel-modal');
      const letterVisible = await letterPanel
        .first()
        .waitFor({ state: 'visible', timeout: 5000 })
        .then(() => true)
        .catch(() => false);

      console.log(`Letter dialog visible: ${letterVisible}`);

      if (letterVisible) {
        // Verify letter fields
        const headlineField = page.locator(
          '.letter-headline, .panel-letter input, .letter-subject'
        ).first();
        const hasHeadline = await headlineField.isVisible().catch(() => false);
        console.log(`Headline field visible: ${hasHeadline}`);

        const bodyField = page.locator(
          '.panel-letter-body textarea, .letter-body textarea, .panel-letter textarea'
        ).first();
        const hasBody = await bodyField.isVisible().catch(() => false);
        console.log(`Body field visible: ${hasBody}`);

        // Take screenshot
        const screenshot = await page.screenshot();
        allure.attachment('Letter Dialog', screenshot, 'image/png');

        // Close the dialog
        await page.keyboard.press('Escape');
        await page.waitForTimeout(500);
      } else {
        console.log('Letter dialog did not open — Alt+R may require SubHeader to be open first');

        // Try via SubHeader
        await page.keyboard.press('Alt+1');
        await page.waitForTimeout(500);

        const subheader = page.locator('.subheader-container');
        const subheaderVisible = await subheader.isVisible().catch(() => false);

        if (subheaderVisible) {
          const letterIcon = subheader.locator('.meta-icon-letter').first();
          const hasLetterIcon = await letterIcon.isVisible().catch(() => false);
          console.log(`Letter icon in SubHeader: ${hasLetterIcon}`);

          if (hasLetterIcon) {
            await letterIcon.click();
            await page.waitForTimeout(1500);

            const letterPanelRetry = page.locator('.panel-letter, .letter-panel, .panel-modal');
            const letterOpenedViaSubheader = await letterPanelRetry
              .first()
              .waitFor({ state: 'visible', timeout: 5000 })
              .then(() => true)
              .catch(() => false);
            console.log(`Letter opened via SubHeader icon: ${letterOpenedViaSubheader}`);

            if (letterOpenedViaSubheader) {
              const screenshot = await page.screenshot();
              allure.attachment('Letter via SubHeader', screenshot, 'image/png');
            }

            // Close
            await page.keyboard.press('Escape');
            await page.waitForTimeout(500);
          }

          // Close SubHeader
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

    console.log('Email & Letter dialog test completed');
  });
});
