import { expect } from '@playwright/test';
import { test } from '../../../playwright.config';
import { getPage, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../common';

/**
 * Page object for Shipment Schedule window (ID: 500221).
 *
 * IMPORTANT: This page object assumes the shipment schedule is already opened
 * and selected via SalesOrderPage.openRelatedShipmentCandidate() (Alt+6 workflow).
 *
 * DO NOT navigate to window 500221 directly - it may select the wrong schedule!
 *
 * Workflow:
 * 1. Complete sales order in SalesOrderPage
 * 2. Use SalesOrderPage.openRelatedShipmentCandidate() to navigate (Alt+6)
 * 3. Shipment schedule is already selected and visible
 * 4. Validate ordered quantity
 */
export class ShipmentSchedulePage {
  /**
   * Verify shipment schedule window is visible and loaded.
   *
   * Assumes navigation via SalesOrderPage.openRelatedShipmentCandidate().
   * Waits for the window content to be fully loaded.
   */
  static async expectVisible() {
    return await test.step('ShipmentSchedulePage - Verify window is visible', async () => {
      const page = getPage();

      // Shipment schedule opens as a list view, not a detail window
      // Wait for document list container
      await page.locator('.document-list-wrapper, .document-list').waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      // Wait for spinners to disappear
      await page
        .locator('.rotating, .spinner')
        .waitFor({
          state: 'detached',
          timeout: SLOW_ACTION_TIMEOUT,
        })
        .catch(() => {
          // Continue if no spinners found
        });

      await page.waitForTimeout(500);
    });
  }

  /**
   * Get the ordered quantity from the shipment schedule grid.
   *
   * Since shipment schedule opens as a filtered list (not a detail window),
   * we read the quantity directly from the grid row using the language-independent
   * data-cy attribute on the cell.
   *
   * @returns {Promise<string>} The ordered quantity value
   */
  static async getOrderedQuantity() {
    return await test.step('ShipmentSchedulePage - Get ordered quantity from grid', async () => {
      const page = getPage();

      // Wait for grid table to be loaded
      await page.locator('.document-list-table').first().waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      // Wait for at least one table row (data row, not header)
      await page.locator('tbody tr').first().waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      // Use language-independent data-cy attribute to find the QtyOrdered cell
      // data-cy="cell-QtyOrdered_Calculated" is based on the database column name
      // and works across all languages (English, German, etc.)
      const qtyCell = page.locator('[data-cy="cell-QtyOrdered_Calculated"]').first();
      await qtyCell.waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      const quantityValue = await qtyCell.textContent();

      console.log(`QtyOrdered from grid (via data-cy): ${quantityValue}`);

      return quantityValue.trim();
    });
  }

  /**
   * Verify the ordered quantity matches the expected value.
   *
   * Handles different number formats:
   * - "10" vs "10.00" (decimal places)
   * - "10,00" (German decimal separator)
   *
   * @param {string|number} expectedQuantity - Expected quantity value
   */
  static async expectOrderedQuantity(expectedQuantity) {
    return await test.step(`ShipmentSchedulePage - Verify ordered quantity = ${expectedQuantity}`, async () => {
      const actualQuantity = await this.getOrderedQuantity();

      // Normalize both values to numbers for comparison
      // Handle both "." and "," as decimal separators
      const normalizeNumber = (value) => {
        const str = String(value).trim().replace(',', '.');
        return parseFloat(str);
      };

      const expected = normalizeNumber(expectedQuantity);
      const actual = normalizeNumber(actualQuantity);

      expect(actual).toBe(expected);
    });
  }

  /**
   * Execute M_ShipmentSchedule_EnqueueSelection action to create shipment.
   * This is a Quick Action on the shipment schedule list view.
   *
   * The action opens a modal with configuration options (e.g., QuantityType, Complete Shipment Note).
   * After clicking "Start" in the modal, the shipment is created asynchronously.
   */
  static async createShipment() {
    return await test.step('ShipmentSchedulePage - Create shipment', async () => {
      const page = getPage();

      // Step 1: Open the quick actions dropdown
      const dropdownToggle = page.getByTestId('quick-action-dropdown-toggle');
      await dropdownToggle.waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      await dropdownToggle.click();

      // Wait for dropdown to appear
      await page.locator('.quick-actions-dropdown').waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      await page.waitForTimeout(300);

      // Step 2: Click the specific M_ShipmentSchedule_EnqueueSelection action
      const actionItem = page.getByTestId('quick-action-M_ShipmentSchedule_EnqueueSelection');
      await actionItem.waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      await actionItem.click();

      // Step 3: Wait for modal to appear
      await page.locator('.panel-modal, .modal-content').waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      await page.waitForTimeout(500);

      // Step 4: Click the "Start" button in the modal
      // Try data-testid first, fall back to last button if not found
      const startButton = page.getByTestId('process-modal-start-button');
      const startButtonExists = await startButton.count();

      if (startButtonExists === 0) {
        // Fallback: Click last button in header (should be Start)
        const lastButton = page.locator('.panel-modal-header button').last();
        await lastButton.waitFor({
          state: 'visible',
          timeout: SLOW_ACTION_TIMEOUT,
        });
        await lastButton.click();
      } else {
        await startButton.waitFor({
          state: 'visible',
          timeout: SLOW_ACTION_TIMEOUT,
        });
        await startButton.click();
      }

      // Step 5: Wait for modal to close (indicates process completed)
      await page
        .locator('.panel-modal, .modal-content')
        .waitFor({
          state: 'detached',
          timeout: VERY_SLOW_ACTION_TIMEOUT,
        })
        .catch(() => {
          // Continue if modal doesn't close
        });

      // Wait for processing indicators to disappear
      await page
        .locator('.rotating, .indicator-pending')
        .waitFor({
          state: 'detached',
          timeout: VERY_SLOW_ACTION_TIMEOUT,
        })
        .catch(() => {
          // Continue if no spinner found
        });

      // Additional wait for backend processing
      await page.waitForTimeout(3000);
    });
  }
}
