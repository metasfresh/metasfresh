import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT } from '../utils/common';

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
    // Grid data rows render as plain <tr> in a standard HTML <table>
    // (see e2e/frontend-webui/CLAUDE.md "Grid Row Selector"). The selected
    // row carries the `row-selected` class. Rows have no `data-row-id`
    // attribute, so row identity is tracked by the row's visible text.
    const gridRows = page.locator('.table-flex-wrapper table tbody tr');

    // Step 1: Navigate to a window with multiple rows (Organisation, AD_Window_ID=110)
    await test.step('Navigate to grid view', async () => {
      await page.goto(`${FRONTEND_BASE_URL}/window/110`);
      await page.waitForURL(/\/window\/110/, { timeout: SLOW_ACTION_TIMEOUT });
      await gridRows
        .first()
        .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    });

    // Step 2: Click on a non-first row to select it
    let selectedRowText;
    let firstRowText;
    await test.step('Select a non-first row', async () => {
      const rowCount = await gridRows.count();
      expect(rowCount).toBeGreaterThan(1);

      firstRowText = (await gridRows.first().innerText()).trim();

      // Click the <tr> of the second row (index 1) to select it. Clicking a
      // <td> would enter cell edit mode instead (CLAUDE.md "Click <tr>, Not <td>").
      const secondRow = gridRows.nth(1);
      selectedRowText = (await secondRow.innerText()).trim();
      await secondRow.click();

      // Auto-waits for Redux to register the selection — no fixed delay needed.
      await expect(secondRow).toHaveClass(/row-selected/, {
        timeout: SLOW_ACTION_TIMEOUT,
      });
      expect(selectedRowText).not.toBe(firstRowText);
      console.log(`[INFO] Selected non-first row: ${selectedRowText}`);
    });

    // Step 3: Press Enter to open detail view
    await test.step('Press Enter to open detail view', async () => {
      await page.keyboard.press('Enter');
      await page.waitForURL(/\/window\/110\/\d+/, {
        timeout: SLOW_ACTION_TIMEOUT,
      });
      console.log(`[INFO] Navigated to detail view: ${page.url()}`);
    });

    // Step 4: Press browser Back
    await test.step('Press browser Back button', async () => {
      await page.goBack();
      // Back to the grid URL (not the /window/110/<docId> detail URL).
      await page.waitForURL(/\/window\/110(?:\?.*)?$/, {
        timeout: SLOW_ACTION_TIMEOUT,
      });
      await gridRows
        .first()
        .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    });

    // Step 5: Verify the previously selected row is re-selected (not the first row)
    await test.step(
      'Verify previously selected row is re-selected',
      async () => {
        // Auto-waits for the restore to apply; exactly one row stays selected.
        const restoredRow = page.locator(
          '.table-flex-wrapper table tbody tr.row-selected'
        );
        await expect(restoredRow).toHaveCount(1, {
          timeout: SLOW_ACTION_TIMEOUT,
        });

        const restoredText = (await restoredRow.innerText()).trim();
        // The bug re-selected the FIRST row; the fix restores the row the
        // user had picked. Assert both to pin the regression precisely.
        expect(restoredText).toBe(selectedRowText);
        expect(restoredText).not.toBe(firstRowText);
        console.log(
          `[PASS] Restored selection is the previously picked row: ${restoredText}`
        );
      }
    );
  });
});
