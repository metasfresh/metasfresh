import { expect } from '@playwright/test';
import { test } from '../../../playwright.config';
import { getPage, SLOW_ACTION_TIMEOUT } from '../common';

/**
 * Helper for interacting with the inline Full-Text Search (FTS) filter input
 * that appears in the filter bar of supported windows (BPartner, Product, Invoice).
 *
 * The FTS filter renders as an inline text input. When the user types and
 * presses Enter, it triggers a PostgreSQL full-text search.
 */
export class FTSSearchHelper {
  /**
   * Wait for the inline FTS search input to be visible.
   * @param {number} [timeout] - Optional timeout in ms
   */
  static async waitForSearchInput(timeout = SLOW_ACTION_TIMEOUT) {
    return await test.step('FTS - Wait for search input', async () => {
      const page = getPage();
      await page
        .locator('.inline-filters input.js-input-field')
        .first()
        .waitFor({ state: 'visible', timeout });
    });
  }

  /**
   * Type search text into the FTS input and press Enter to trigger the search.
   * Waits for the loading indicator to appear and then disappear.
   * @param {string} searchText - The text to search for
   */
  static async search(searchText) {
    return await test.step(`FTS - Search for "${searchText}"`, async () => {
      const page = getPage();

      const input = page.locator('.inline-filters input.js-input-field').first();
      await input.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      // Clear existing text and type the new search
      await input.fill('');
      await input.fill(searchText);

      // Press Enter to trigger the search
      await input.press('Enter');

      // Wait for debounce + loading to complete
      await FTSSearchHelper.waitForResultsLoaded();
    });
  }

  /**
   * Clear the FTS input and wait for the full (unfiltered) list to reload.
   */
  static async clearSearch() {
    return await test.step('FTS - Clear search', async () => {
      const page = getPage();

      const input = page.locator('.inline-filters input.js-input-field').first();
      await input.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      // Clear the input
      await input.fill('');
      await input.press('Enter');

      // Wait for results to reload
      await FTSSearchHelper.waitForResultsLoaded();
    });
  }

  /**
   * Wait for loading spinners / pending indicators to disappear,
   * indicating that results have been loaded.
   */
  static async waitForResultsLoaded() {
    return await test.step('FTS - Wait for results loaded', async () => {
      const page = getPage();

      // Small delay for debounce
      await page.waitForTimeout(500);

      // Wait for any loading indicators to disappear
      await page
        .locator('.indicator-pending, .spinner')
        .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
        .catch(() => {});

      // Wait for table or empty-info to appear (results loaded)
      await page
        .locator('tbody tr.table-row, .empty-info-text')
        .first()
        .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT })
        .catch(() => {});

      // Additional stabilisation time
      await page.waitForTimeout(500);
    });
  }

  /**
   * Count the number of visible grid rows.
   * @returns {Promise<number>} Number of rows
   */
  static async getResultCount() {
    return await test.step('FTS - Get result count', async () => {
      const page = getPage();

      // Check if an empty-info-text is shown (no results)
      const emptyInfo = page.locator('.empty-info-text');
      if (await emptyInfo.isVisible().catch(() => false)) {
        return 0;
      }

      const rows = page.locator('tbody tr.table-row');
      return await rows.count();
    });
  }

  /**
   * Assert that the first result row contains the expected text in the given column.
   * @param {string} columnName - Database column name (used in data-cy attribute)
   * @param {string} expectedText - Text expected to appear in the cell
   */
  static async expectFirstResultContains(columnName, expectedText) {
    return await test.step(
      `FTS - Expect first result column "${columnName}" contains "${expectedText}"`,
      async () => {
        const page = getPage();

        const cell = page.locator(`tbody tr.table-row:first-child [data-cy="cell-${columnName}"]`);
        await cell.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

        const cellText = await cell.textContent();
        expect(cellText).toContain(expectedText);
      }
    );
  }

  /**
   * Double-click the first result row to open its detail view.
   */
  static async openFirstResult() {
    return await test.step('FTS - Open first result', async () => {
      const page = getPage();

      const firstRow = page.locator('tbody tr.table-row').first();
      await firstRow.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await firstRow.dblclick();

      // Wait for navigation to detail view
      await page.waitForURL(/\/window\/\d+\/\d+/, {
        timeout: SLOW_ACTION_TIMEOUT,
      });
    });
  }
}
