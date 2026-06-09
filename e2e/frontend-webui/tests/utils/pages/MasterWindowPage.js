import { expect } from '@playwright/test';
import { test } from '../../../playwright.config';
import { FRONTEND_BASE_URL, getPage, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT, } from '../common';

/**
 * Master window page object for document list views in metasfresh web UI.
 * Handles navigation to windows, table interactions, and row selection.
 */
export class MasterWindowPage {
  /**
   * Navigate to a specific window by ID.
   * @param {number} windowId - Window identifier (e.g., 123 for Business Partner)
   */
  static async goto(windowId) {
    return await test.step(`MasterWindowPage - Navigate to window ${windowId}`, async () => {
      const page = getPage();
      await page.goto(`${FRONTEND_BASE_URL}/window/${windowId}`);
      await this.waitForPage();
    });
  }

  /**
   * Wait for the window page to fully load.
   * Waits for document list container, network idle, and loading spinners to disappear.
   */
  static async waitForPage() {
    return await test.step('MasterWindowPage - Wait for page', async () => {
      const page = getPage();

      // Wait for document list container
      await page
        .locator('.document-list-wrapper, .document-list')
        .waitFor({
          state: 'visible',
          timeout: VERY_SLOW_ACTION_TIMEOUT,
        });

      // Wait for network to settle
      await page
        .waitForLoadState('networkidle', {
          timeout: SLOW_ACTION_TIMEOUT,
        })
        .catch(() => {
          // Ignore timeout - some pages may not reach networkidle
        });

      // Wait for loading spinners to disappear
      await page
        .locator('.rotating, .panel-spaced-lg')
        .waitFor({
          state: 'detached',
          timeout: SLOW_ACTION_TIMEOUT,
        })
        .catch(() => {
          // Ignore if spinner doesn't exist
        });
    });
  }

  /**
   * Verify that the window has loaded successfully.
   */
  static async expectWindowLoaded() {
    return await test.step('MasterWindowPage - Expect window loaded', async () => {
      const page = getPage();

      // Verify URL matches window pattern
      const url = page.url();
      expect(url).toMatch(/\/window\/\d+/);

      // Verify document list is visible
      await expect(
        page.locator('.document-list-wrapper, .document-list')
      ).toBeVisible();
    });
  }

  /**
   * Wait for table data to be loaded and visible.
   */
  static async waitForTableData() {
    return await test.step('MasterWindowPage - Wait for table data', async () => {
      const page = getPage();

      // Wait for at least one table row to be visible
      await page
        .locator('.table tbody tr, table tbody tr')
        .first()
        .waitFor({
          state: 'visible',
          timeout: VERY_SLOW_ACTION_TIMEOUT,
        });
    });
  }

  /**
   * Get the number of visible rows in the table.
   * @returns {Promise<number>} Number of rows
   */
  static async getRowCount() {
    return await test.step('MasterWindowPage - Get row count', async () => {
      const page = getPage();

      const table = page.locator('.table tbody, table tbody');
      await table.waitFor({ state: 'visible' });

      const rows = table.locator('tr');
      return await rows.count();
    });
  }

  /**
   * Double-click a specific table row by index to open detail view.
   * Note: Single click only selects the row, double-click opens the detail.
   * @param {number} index - Row index (0-based)
   */
  static async clickRow(index = 0) {
    return await test.step(`MasterWindowPage - Double-click row ${index}`, async () => {
      const page = getPage();

      const row = page.locator('.table tbody tr, table tbody tr').nth(index);
      // Note: metasfresh requires double-click to open record detail view
      await row.dblclick();

      // Wait for navigation to detail view
      await page.waitForURL(/\/window\/\d+\/\d+/, {
        timeout: SLOW_ACTION_TIMEOUT,
      });
    });
  }
}
