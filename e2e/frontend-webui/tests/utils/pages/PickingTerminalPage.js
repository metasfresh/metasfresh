import { expect } from '@playwright/test';
import { test } from '../../../playwright.config';
import { FRONTEND_BASE_URL, getPage, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../common';
import { PICKING_TERMINAL_V2_WINDOW_ID } from '../WindowIds';

/**
 * Page object for the Picking Terminal V2 (window 540485).
 * This is a grid view showing packageable shipment schedules.
 * Selecting a row reveals quick actions (Pick, Process, Unprocess, Remove, etc.)
 */
export class PickingTerminalPage {
  /**
   * Navigate to the Picking Terminal V2.
   */
  static async goto() {
    return await test.step('PickingTerminalPage - Navigate', async () => {
      const page = getPage();
      await page.goto(`${FRONTEND_BASE_URL}/window/${PICKING_TERMINAL_V2_WINDOW_ID}`);
      await this.waitForGrid();
    });
  }

  /**
   * Wait for the grid to be loaded with rows.
   */
  static async waitForGrid() {
    return await test.step('PickingTerminalPage - Wait for grid', async () => {
      const page = getPage();
      // Wait for table header to be visible
      await page.locator('table thead tr').first().waitFor({
        state: 'visible',
        timeout: VERY_SLOW_ACTION_TIMEOUT,
      });
    });
  }

  /**
   * Select a row by clicking on it. Uses data-testid="table-row-{id}".
   * @param {object} options
   * @param {string} [options.salesOrderDocNo] - Filter by sales order document number text
   * @param {number} [options.rowIndex] - Select by 0-based row index
   */
  static async selectRow({ salesOrderDocNo, rowIndex }) {
    return await test.step(
      `PickingTerminalPage - Select row ${salesOrderDocNo || `index ${rowIndex}`}`,
      async () => {
        const page = getPage();
        let row;

        if (salesOrderDocNo) {
          // Find row containing the document number
          row = page.locator('table tbody tr').filter({ hasText: salesOrderDocNo }).first();
        } else {
          row = page.locator('table tbody tr').nth(rowIndex || 0);
        }

        await row.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
        await row.click();

        // Wait for quick actions to update
        await page.waitForTimeout(500);
      }
    );
  }

  /**
   * Click a quick action button by its data-testid.
   * Quick actions appear in the toolbar above the grid after selecting a row.
   * @param {string} actionTestId - The data-testid of the action (e.g., 'action-WEBUI_Picking_M_Picking_Candidate_Process')
   */
  static async clickQuickAction(actionTestId) {
    return await test.step(`PickingTerminalPage - Click quick action ${actionTestId}`, async () => {
      const page = getPage();

      const actionButton = page.getByTestId(actionTestId);
      await actionButton.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await actionButton.click();

      // Process may show a brief modal — wait for it to settle
      await page.locator('.screen-freeze').waitFor({ state: 'detached', timeout: VERY_SLOW_ACTION_TIMEOUT }).catch(() => {});
      await page.waitForTimeout(500);
    });
  }

  /**
   * Open the quick actions dropdown (the "..." button) and click an action.
   * Some actions are hidden in the overflow dropdown.
   * @param {string} actionTestId - The data-testid of the action
   */
  static async clickQuickActionFromDropdown(actionTestId) {
    return await test.step(`PickingTerminalPage - Click dropdown action ${actionTestId}`, async () => {
      const page = getPage();

      // Try direct button first
      const directButton = page.getByTestId(actionTestId);
      if (await directButton.isVisible().catch(() => false)) {
        await directButton.click();
      } else {
        // Open the dropdown
        const dropdownToggle = page.locator('.quick-actions-wrapper .btn-meta-outline-secondary');
        if (await dropdownToggle.isVisible().catch(() => false)) {
          await dropdownToggle.click();
          await page.waitForTimeout(300);
        }

        // Click from dropdown
        const dropdownAction = page.getByTestId(actionTestId);
        await dropdownAction.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
        await dropdownAction.click();
      }

      await page.locator('.screen-freeze').waitFor({ state: 'detached', timeout: VERY_SLOW_ACTION_TIMEOUT }).catch(() => {});
      await page.waitForTimeout(500);
    });
  }

  /**
   * Get the text content of a cell in the currently selected row.
   * @param {string} columnTestId - Column data-testid (e.g., 'column-SalesOrder')
   * @returns {Promise<string>}
   */
  static async getSelectedRowCellText(columnIndex) {
    return await test.step(`PickingTerminalPage - Get cell text at column ${columnIndex}`, async () => {
      const page = getPage();
      const selectedRow = page.locator('table tbody tr.row-selected, table tbody tr[class*="selected"]').first();
      const cells = selectedRow.locator('td');
      return await cells.nth(columnIndex).innerText();
    });
  }

  /**
   * Check if a quick action button is visible (enabled).
   * @param {string} actionTestId
   * @returns {Promise<boolean>}
   */
  static async isQuickActionVisible(actionTestId) {
    return await test.step(`PickingTerminalPage - Check action visible: ${actionTestId}`, async () => {
      const page = getPage();
      return await page.getByTestId(actionTestId).isVisible().catch(() => false);
    });
  }
}
