import { expect } from '@playwright/test';
import { test } from '../../../playwright.config';
import { FRONTEND_BASE_URL, getPage, holdForCaptureIfEnabled, isUatCapture, SLOW_ACTION_TIMEOUT } from '../common';

export const PRODUCT_COST_WINDOW_ID = '344';
export const PRODUCT_COST_M_COST_TAB_ID = 'AD_Tab-701';

/**
 * Page object for Product Cost (Produktkosten, window 344) — product header + M_Cost tab.
 */
export class ProductCostPage {
  /** Open the product's Product Cost (Produktkosten, window 344) record and wait for its M_Cost grid rows. */
  static async open(productId) {
    await test.step(`Open Product Cost (Produktkosten, window 344) for product ${productId}`, async () => {
      const page = getPage();
      await page.goto(`${FRONTEND_BASE_URL}/window/${PRODUCT_COST_WINDOW_ID}/${productId}`);
      await page
        .locator('.table-flex-wrapper table tbody tr')
        .first()
        .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    });
  }

  /** @returns {import('@playwright/test').Locator} the given column's cell of the M_Cost grid row with the given row id */
  static rowCell(rowId, columnName) {
    return getPage().getByTestId(`table-row-${rowId}`).locator(`td[data-cy="cell-${columnName}"]`);
  }

  /**
   * Try to edit the Current Cost Price cell of an M_Cost row the way a user would:
   * double-click the cell and type a new value. The cell is rendered read-only, so no editor opens.
   * @param {string} rowId the M_Cost row's id
   * @returns {Promise<string>} the cell text after the attempt
   */
  static async attemptEditCurrentCostPrice(rowId, value) {
    return await test.step(`Try to edit Current Cost Price (type ${value}) -> refused, cell is read-only`, async () => {
      const page = getPage();
      const cell = this.rowCell(rowId, 'CurrentCostPrice');
      await cell.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await cell.scrollIntoViewIfNeeded();
      const before = (await cell.innerText()).trim();

      if (isUatCapture()) {
        await cell.evaluate((el) => {
          el.style.outline = '3px solid #e8a100';
          el.style.outlineOffset = '-3px';
        });
      }

      await cell.dblclick();
      await page.keyboard.type(String(value));
      await page.waitForTimeout(500);

      // Rendered read-only, and no editable input was opened in the cell.
      await expect(cell).toHaveClass(/cell-disabled/);
      await expect(cell.locator('input:not([disabled]):not([readonly])')).toHaveCount(0);
      const after = (await cell.innerText()).trim();
      expect(after).toBe(before);

      await holdForCaptureIfEnabled(3000);
      await page.keyboard.press('Escape');
      return after;
    });
  }
}
