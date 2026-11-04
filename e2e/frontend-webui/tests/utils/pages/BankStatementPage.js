import { expect } from '@playwright/test';
import { test } from '../../../playwright.config';
import { FRONTEND_BASE_URL, getPage, holdForCaptureIfEnabled, isUatCapture, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../common';

const BANK_STATEMENT_WINDOW_ID = 194;
const ORDER_FIELD = 'C_Order_ID';

/**
 * Page object for the bank statement window (Bankauszug, window 194), tab of the statement lines.
 * Under UAT_CAPTURE it also paints a URL banner, highlights the checked element and saves a still into
 * EVIDENCE_DIR (if set); otherwise those are no-ops.
 */
export class BankStatementPage {
  static async #banner() {
    await getPage().evaluate(() => {
      let banner = document.getElementById('__uatUrlBanner');
      if (!banner) {
        banner = document.createElement('div');
        banner.id = '__uatUrlBanner';
        banner.style.cssText =
          'position:fixed;bottom:0;left:0;right:0;z-index:99999;pointer-events:none;background:#222;color:#fff;font:12px monospace;padding:4px 8px';
        document.body.appendChild(banner);
      }
      banner.textContent = location.href;
    });
  }

  static async #showForCapture(locator, stillName) {
    if (!isUatCapture()) {
      return;
    }
    await locator.evaluate((el) => {
      el.style.outline = '3px solid #2ecc40';
      el.style.outlineOffset = '2px';
    });
    await this.#banner();
    if (process.env.EVIDENCE_DIR) {
      await getPage().screenshot({ path: `${process.env.EVIDENCE_DIR}/${stillName}.png` });
    }
    await holdForCaptureIfEnabled(stillName.includes('dropdown') ? 3000 : 1500);
  }

  static async goto(statementId) {
    return await test.step('BankStatementPage - open the bank statement', async () => {
      const page = getPage();
      await page.goto(`${FRONTEND_BASE_URL}/window/${BANK_STATEMENT_WINDOW_ID}/${statementId}`);
      await page.locator('tbody tr').first().waitFor({ timeout: VERY_SLOW_ACTION_TIMEOUT });
    });
  }

  static async expectOrderColumnInLineGrid() {
    return await test.step('BankStatementPage - the line grid has the order column', async () => {
      const column = getPage().locator(`th[data-testid="column-${ORDER_FIELD}"]`).first();
      await expect(column).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
      await this.#showForCapture(column, '01-line-grid-order-column');
    });
  }

  /** Opens the first line in its advanced-edit form (Alt+E on the selected line row) and checks the order field is there */
  static async openFirstLineAdvancedEditWithOrderField() {
    return await test.step('BankStatementPage - the line advanced-edit form has the order field', async () => {
      const page = getPage();
      const field = page.locator(`.form-field-${ORDER_FIELD}`).first();
      for (let attempt = 0; attempt < 4 && !(await field.count()); attempt++) {
        const cell = page.locator('tbody tr').first().locator('td').nth(3);
        await cell.click();
        await page.waitForTimeout(1500);
        await cell.click();
        await page.waitForTimeout(1000);
        await page.keyboard.press('Alt+e');
        await field.waitFor({ timeout: 5000 }).catch(() => {});
        if (!(await field.count())) {
          await page.keyboard.press('Escape');
          await page.waitForTimeout(1500);
        }
      }
      await expect(field).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
      await field.scrollIntoViewIfNeeded();
      await this.#showForCapture(field, '02-line-form-order-field');
    });
  }

  /** @returns {Promise<string[]>} the options the order dropdown of the open line form offers */
  static async openOrderDropdownAndGetOptions() {
    return await test.step('BankStatementPage - open the order dropdown', async () => {
      const page = getPage();
      const field = page.locator(`.form-field-${ORDER_FIELD}`).first();
      await field.locator('.input-dropdown, .input-block, input, .raw-list').first().click();
      const list = page.locator('.input-dropdown-list');
      await list.waitFor({ timeout: SLOW_ACTION_TIMEOUT });
      const options = list.locator('.input-dropdown-list-option');
      await options.first().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
      await this.#showForCapture(list, '03-order-dropdown');
      return (await options.allInnerTexts()).map((text) => text.trim());
    });
  }
}
