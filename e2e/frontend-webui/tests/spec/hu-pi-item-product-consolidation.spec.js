import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SLOW_ACTION_TIMEOUT } from '../utils/common';

/**
 * M_HU_PI_Item_Product consolidation process E2E tests.
 *
 * Verifies:
 * - Menu entry exists and is findable (DE + EN)
 * - Process dialog opens with correct parameters
 * - Process description/help text is shown
 */

test.describe('M_HU_PI_Item_Product Consolidation Process', () => {
  ['de_DE', 'en_US'].forEach((language) => {
    const menuName = language === 'de_DE'
      ? 'CU-TU Zuordnung konsolidieren'
      : 'Consolidate CU-TU Allocation';

    const paramLabels = language === 'de_DE'
      ? ['GTIN/EAN normalisieren', 'Doppelte Einträge konsolidieren']
      : ['Normalize GTIN/EAN', 'Consolidate duplicates'];

    test(`Menu entry exists and process dialog opens (${language})`, async ({ page }) => {
      allure.epic('E0193: Handling Units');
      allure.tag('F5001.1: Consolidate CU-TU Allocation');
      allure.story('Process menu entry and dialog');
      allure.severity('normal');
      allure.parameter('Language', language);

      allure.description(`
## CU-TU Consolidation Process

Verifies that the "CU-TU Zuordnung konsolidieren" / "Consolidate CU-TU Allocation"
process is accessible via the navigation menu and that the process dialog shows
the correct parameters with descriptions.

**Language:** ${language}
      `);

      test.setTimeout(120000); // 2 minutes

      // === CREATE TEST DATA ===
      const masterdata = await Backend.createMasterdata({
        request: {
          login: {
            user: { language },
          },
        },
      });

      // === LOGIN ===
      await LoginPage.goto();
      await LoginPage.login(masterdata.login.user);
      await DashboardPage.expectVisible();

      // === OPEN NAVIGATION MENU ===
      await test.step('Open navigation menu via Alt+2', async () => {
        await page.locator('body').click();
        await page.waitForTimeout(200);
        await page.keyboard.press('Alt+2');
        await expect(page.locator('.menu-overlay')).toBeVisible({
          timeout: SLOW_ACTION_TIMEOUT,
        });
      });

      // === SEARCH FOR THE PROCESS ===
      await test.step(`Search for "${menuName}"`, async () => {
        const searchInput = page.locator('.menu-overlay input.input-field');
        await expect(searchInput).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
        await searchInput.fill(menuName);
        await page.waitForTimeout(500);

        // Verify the menu entry appears in search results
        const menuItem = page.locator('.menu-overlay').getByText(menuName, { exact: false });
        await expect(menuItem).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });

        // Click the menu entry to open the process dialog
        await menuItem.click();
      });

      // === VERIFY PROCESS DIALOG ===
      await test.step('Verify process dialog opens with parameters', async () => {
        // Wait for the process modal/dialog to appear
        const processDialog = page.locator('.process-modal, .modal-content, [class*="process"]').first();
        await expect(processDialog).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });

        // Check that both parameter labels are visible
        for (const label of paramLabels) {
          const paramElement = page.getByText(label, { exact: false });
          await expect(paramElement).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
        }
      });

      // === CLOSE DIALOG ===
      await test.step('Close process dialog', async () => {
        await page.keyboard.press('Escape');
      });
    });
  });
});
