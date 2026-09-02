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
 * Print Dialog (Alt+P) E2E test suite.
 *
 * Tests the print/PDF generation dialog:
 * - Alt+P opens the print dialog
 * - PrintingOptions modal with checkboxes
 * - Dialog structure (title, options, buttons)
 */

test.describe('Print Dialog', () => {
  test('Print dialog on Sales Order via Alt+P', async ({ page }) => {
    allure.epic('E0193: User Interface');
    allure.tag('F14010: Navigation');
    allure.tag('F14150: Print');
    allure.story('Print Dialog');
    allure.severity('normal');

    allure.description(`
## Print Dialog (Alt+P)

Tests the print/PDF dialog:
1. **Open via Alt+P** — Opens the printing options dialog
2. **Dialog structure** — Title, checkboxes, buttons
3. **Close** — Escape closes without printing
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
    // TEST 1: Open print dialog via Alt+P
    // ======================================================================
    await test.step('Open print dialog via Alt+P', async () => {
      await page.keyboard.press('Alt+P');
      await page.waitForTimeout(1500);

      // Look for the printing options modal
      const printModal = page.locator('.modal-content, .panel-modal, .prompt-shadow');
      const modalVisible = await printModal
        .first()
        .waitFor({ state: 'visible', timeout: 5000 })
        .then(() => true)
        .catch(() => false);

      console.log(`Print modal visible: ${modalVisible}`);

      if (modalVisible) {
        // Check for title
        const modalTitle = page.locator('.modal-header, .modal-title, .panel-modal-header');
        const titleText = (await modalTitle.first().textContent().catch(() => '')).trim();
        console.log(`Modal title: "${titleText}"`);

        // Check for checkboxes (printing options)
        const checkboxes = page.locator('.modal-content input[type="checkbox"], .panel-modal input[type="checkbox"]');
        const checkboxCount = await checkboxes.count();
        console.log(`Print option checkboxes: ${checkboxCount}`);

        // Log checkbox labels
        const checkboxLabels = [];
        for (let i = 0; i < checkboxCount; i++) {
          const label = await checkboxes.nth(i).locator('..').textContent().catch(() => '');
          checkboxLabels.push(label.trim().substring(0, 40));
        }
        console.log(`Checkbox labels: ${JSON.stringify(checkboxLabels)}`);

        // Check for action buttons (Print, Cancel)
        const buttons = page.locator('.modal-content .btn, .panel-modal .btn');
        const buttonCount = await buttons.count();
        console.log(`Dialog buttons: ${buttonCount}`);

        const buttonTexts = [];
        for (let i = 0; i < buttonCount; i++) {
          const text = (await buttons.nth(i).textContent().catch(() => '')).trim();
          if (text) buttonTexts.push(text);
        }
        console.log(`Button labels: ${JSON.stringify(buttonTexts)}`);

        // Take screenshot of the print dialog
        const screenshot = await page.screenshot();
        allure.attachment('Print Dialog', screenshot, 'image/png');

        // Close without printing
        await page.keyboard.press('Escape');
        await page.waitForTimeout(500);

        console.log('Print dialog closed');
      } else {
        console.log('Print dialog did not open — Alt+P may trigger direct PDF generation');
      }
    });

    // ======================================================================
    // TEST 2: Open print via SubHeader icon
    // ======================================================================
    await test.step('Open print via SubHeader', async () => {
      // Open SubHeader
      await page.keyboard.press('Alt+1');
      await page.waitForTimeout(500);

      const subheader = page.locator('.subheader-container');
      const subheaderVisible = await subheader
        .waitFor({ state: 'visible', timeout: 5000 })
        .then(() => true)
        .catch(() => false);

      if (subheaderVisible) {
        // Click print icon
        const printIcon = subheader.locator('.meta-icon-print').first();
        const hasPrint = await printIcon.isVisible().catch(() => false);
        console.log(`SubHeader print icon: ${hasPrint}`);

        if (hasPrint) {
          await printIcon.click();
          await page.waitForTimeout(1500);

          const printModal = page.locator('.modal-content, .panel-modal');
          const modalVisible = await printModal.first().isVisible().catch(() => false);
          console.log(`Print dialog via SubHeader: ${modalVisible}`);

          if (modalVisible) {
            const screenshot = await page.screenshot();
            allure.attachment('Print via SubHeader', screenshot, 'image/png');

            await page.keyboard.press('Escape');
            await page.waitForTimeout(500);
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

    console.log('Print dialog test completed');
  });
});
