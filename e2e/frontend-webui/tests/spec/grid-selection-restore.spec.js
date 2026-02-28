import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import {
  FRONTEND_BASE_URL,
  FAST_ACTION_TIMEOUT,
  SLOW_ACTION_TIMEOUT,
} from '../utils/common';

/**
 * Grid Selection Restore test suite for metasfresh web UI.
 *
 * Tests that the previously selected row is restored when navigating back
 * from the detail (single-record) view to the grid view using the browser
 * back button.
 *
 * Bug: When a user selected a non-first row, pressed Enter to open the detail
 * view, and then pressed browser Back, the first row was always re-selected
 * instead of the previously selected row.
 *
 * Fix: The selected row ID is now cached in Redux (listHandler) alongside
 * pagination/sorting/viewId when navigating to a detail view, and restored
 * when the grid view reloads after browser-back.
 *
 * me03#28448
 */
test.describe('Grid Selection Restore on Browser Back', () => {
  // Login before each test
  test.beforeEach(async ({ page }) => {
    await test.step('Login', async () => {
      await page.goto(`${FRONTEND_BASE_URL}/login`);
      await page
        .locator('.login-container')
        .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      await page.locator('input[name="username"]').fill('metasfresh');
      await page.locator('input[name="password"]').fill('metasfresh');
      await page.locator('.btn-meta-success').click();

      // Handle role selection if needed
      await page.waitForTimeout(1000);
      if (page.url().includes('/login')) {
        const sendButton = page.locator('.btn-meta-success');
        if (await sendButton.isVisible()) {
          await sendButton.click();
        }
      }

      // Wait for dashboard
      await page.waitForURL(
        (url) => !url.toString().includes('/login'),
        { timeout: SLOW_ACTION_TIMEOUT }
      );
      await page
        .locator('.app-content, .dashboard')
        .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    });
  });

  test('Row selection is preserved after browser back from detail view', async ({
    page,
  }) => {
    // Step 1: Navigate to a window with multiple rows (Organisation, AD_Window_ID=110)
    await test.step('Navigate to grid view', async () => {
      await page.goto(`${FRONTEND_BASE_URL}/window/110`);
      await page.waitForURL(/\/window\/110/, { timeout: SLOW_ACTION_TIMEOUT });
      // Wait for the grid table to load
      await page
        .locator('.table-flex-wrapper .table-flex-wrapper-row')
        .first()
        .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    });

    // Step 2: Click on a non-first row to select it
    let selectedRowId;
    await test.step('Select a non-first row', async () => {
      // Get all grid rows
      const rows = page.locator(
        '.table-flex-wrapper .table-flex-wrapper-row'
      );
      const rowCount = await rows.count();
      expect(rowCount).toBeGreaterThan(1);

      // Click the second row (index 1)
      const secondRow = rows.nth(1);
      await secondRow.click();

      // Wait briefly for selection to register in Redux
      await page.waitForTimeout(300);

      // Verify the second row is selected (has 'row-selected' class)
      await expect(secondRow).toHaveClass(/row-selected/, {
        timeout: FAST_ACTION_TIMEOUT,
      });

      // Capture the row ID for later verification
      selectedRowId = await secondRow.getAttribute('data-row-id');
      console.log(`Selected row ID: ${selectedRowId}`);
    });

    // Step 3: Press Enter to open detail view
    await test.step('Press Enter to open detail view', async () => {
      await page.keyboard.press('Enter');

      // Wait for navigation to detail view URL pattern: /window/110/{docId}
      await page.waitForURL(/\/window\/110\/\d+/, {
        timeout: SLOW_ACTION_TIMEOUT,
      });
      console.log(`Navigated to detail view: ${page.url()}`);
    });

    // Step 4: Press browser Back
    await test.step('Press browser Back button', async () => {
      await page.goBack();

      // Wait for grid view to reload
      await page.waitForURL(/\/window\/110\?/, {
        timeout: SLOW_ACTION_TIMEOUT,
      });
      await page
        .locator('.table-flex-wrapper .table-flex-wrapper-row')
        .first()
        .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      // Give Redux time to restore selection
      await page.waitForTimeout(500);
    });

    // Step 5: Verify the previously selected row is re-selected
    await test.step(
      'Verify previously selected row is re-selected',
      async () => {
        // Find the row with the previously selected ID
        const restoredRow = page.locator(
          `.table-flex-wrapper .table-flex-wrapper-row[data-row-id="${selectedRowId}"]`
        );

        // Verify it exists and is selected
        await expect(restoredRow).toBeVisible({
          timeout: FAST_ACTION_TIMEOUT,
        });
        await expect(restoredRow).toHaveClass(/row-selected/, {
          timeout: FAST_ACTION_TIMEOUT,
        });

        // Also verify the first row is NOT selected (unless it's the same row)
        const firstRow = page
          .locator('.table-flex-wrapper .table-flex-wrapper-row')
          .first();
        const firstRowId = await firstRow.getAttribute('data-row-id');

        if (firstRowId !== selectedRowId) {
          await expect(firstRow).not.toHaveClass(/row-selected/, {
            timeout: FAST_ACTION_TIMEOUT,
          });
          console.log(
            `Verified: row ${selectedRowId} is selected (not first row ${firstRowId})`
          );
        }
      }
    );
  });
});
