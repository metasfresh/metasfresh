import { expect } from '@playwright/test';
import { test } from '../../../playwright.config';
import {
  FRONTEND_BASE_URL,
  FAST_ACTION_TIMEOUT,
  getPage,
  SLOW_ACTION_TIMEOUT,
  VERY_SLOW_ACTION_TIMEOUT,
} from '../common';
import { ErrorToast } from '../components/ErrorToast';
import { MANUFACTURING_ORDER_WINDOW_ID } from '../WindowIds';

/**
 * Page object for the production receipt dialog ("Empfangen", process WEBUI_PP_Order_Receipt)
 * of a manufacturing order (window 53009).
 *
 * Flow: manufacturing order -> actions menu (Alt+1) -> "Produzieren" launcher (issue/receipt lines view)
 * -> select the main product line (or a co-product line) -> quick action "Empfangen" -> dialog.
 *
 * All dialog fields are addressed by their language-independent ColumnName
 * (`.form-field-<ColumnName>`): QtyTU ("TU Anzahl"), QtyLU ("LU Anzahl"), QtyTotalReceived ("Menge"),
 * QtyCUsPerTU ("Menge pro TU").
 */
export class ManufacturingOrderReceiptPage {
  static #field(columnName) {
    return getPage().locator(`.form-field-${columnName}`);
  }

  static #input(columnName) {
    return this.#field(columnName).locator('input.input-field');
  }

  /**
   * Open the manufacturing order and its issue/receipt lines view ("Produzieren").
   * @param {Object} options
   * @param {number} options.ppOrderId - PP_Order_ID (from the masterdata response)
   */
  static async openLinesView({ ppOrderId }) {
    return await test.step(`ManufacturingOrderReceiptPage - Open lines view of order ${ppOrderId}`, async () => {
      const page = getPage();
      await page.goto(`${FRONTEND_BASE_URL}/window/${MANUFACTURING_ORDER_WINDOW_ID}/${ppOrderId}`);
      await page.getByTestId('status-button').waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });

      // Actions menu (Alt+1), then the language-independent action by its internal name
      await page.keyboard.press('Alt+1');
      const launcher = page.getByTestId('action-WEBUI_PP_Order_IssueReceipt_Launcher');
      await launcher.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await launcher.click();

      await page.getByTestId('quick-action-button').waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
    });
  }

  /**
   * Open the "Empfangen" dialog on a line of the lines view: the main product line (row 0) by default,
   * or e.g. a co-product line. The action is chosen explicitly by its internal name - the default quick
   * action depends on the order (it can be another action, e.g. completing the planning).
   * @param {Object} options
   * @param {number} [options.rowIndex=0] - row of the lines view to receive on
   */
  static async openReceiptDialog({ rowIndex = 0 } = {}) {
    return await test.step('ManufacturingOrderReceiptPage - Open receipt dialog', async () => {
      const page = getPage();

      // The main product line comes pre-selected (a click would deselect it); wait for that state first.
      const row = page.locator('.panel-modal .table-row').nth(rowIndex);
      await row.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await page
        .locator('.panel-modal .table-row.row-selected')
        .first()
        .waitFor({ state: 'visible', timeout: FAST_ACTION_TIMEOUT })
        .catch(() => {});
      if (!(await row.evaluate((el) => el.classList.contains('row-selected')))) {
        await row.click();
      }
      await this.#settle();

      await page.getByTestId('quick-action-dropdown-toggle').click();
      const receiveAction = page.getByTestId('quick-action-WEBUI_PP_Order_Receipt');
      await receiveAction.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await receiveAction.click();

      await this.#field('QtyTU').waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
      await this.#settle();
    });
  }

  /** Wait for the pending-request indicators of the dialog to go away. */
  static async #settle() {
    const page = getPage();
    await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
    await page
      .locator('.rotating, .indicator-pending')
      .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
      .catch(() => {});
  }

  /** Is the dialog field rendered at all? (hidden parameters are not rendered) */
  static async isFieldVisible(columnName) {
    return (await this.#field(columnName).count()) > 0;
  }

  /** The localized caption of the field. */
  static async getLabel(columnName) {
    return (await this.#field(columnName).locator('label').first().textContent()).trim();
  }

  /** The field value as shown in the dialog, as a trimmed string ('' when empty). */
  static async getValue(columnName) {
    return (await this.#input(columnName).inputValue()).trim();
  }

  /** The field value as a number, or null when empty. */
  static async getNumber(columnName) {
    const value = await this.getValue(columnName);
    return value === '' ? null : Number(value);
  }

  /** True when the user cannot type into the field. */
  static async isReadOnly(columnName) {
    const input = this.#input(columnName);
    return (await input.isDisabled()) || (await input.getAttribute('readonly')) !== null;
  }

  /** Type a number into a field and wait until the dependent fields are recomputed. */
  static async setNumber(columnName, value) {
    return await test.step(`ManufacturingOrderReceiptPage - Set ${columnName} = ${value}`, async () => {
      const input = this.#input(columnName);
      await input.fill(String(value));
      await input.blur();
      await this.#settle();
    });
  }

  /** Empty a field and wait until the dependent fields are recomputed. */
  static async clear(columnName) {
    return await test.step(`ManufacturingOrderReceiptPage - Clear ${columnName}`, async () => {
      const input = this.#input(columnName);
      await input.fill('');
      await input.blur();
      await this.#settle();
    });
  }

  /**
   * Press "Start" and expect the process to be refused with an error toast. The dialog stays open.
   * @returns {Promise<string>} the text of the error toast
   */
  static async startExpectingRefusal() {
    return await test.step('ManufacturingOrderReceiptPage - Start (expect refusal)', async () => {
      const page = getPage();
      const toast = page.locator('.Toastify div[role="alert"].Toastify__toast-body').first();
      await page.getByTestId('process-modal-start-button').click();
      await toast.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      const text = await toast.textContent();
      await ErrorToast.closePopup().catch(() => {});
      return text ?? '';
    });
  }

  /** Press "Start" and wait for the dialog to close (the receipt was created). */
  static async start() {
    return await test.step('ManufacturingOrderReceiptPage - Start', async () => {
      const page = getPage();
      const startButton = page.getByTestId('process-modal-start-button');
      await startButton.click();
      // the dialog is gone when its start button is: the form fields re-render transiently on validation errors
      await startButton.waitFor({ state: 'detached', timeout: VERY_SLOW_ACTION_TIMEOUT });
      await this.#settle();
    });
  }

  /** Cancel the dialog without starting. */
  static async cancel() {
    return await test.step('ManufacturingOrderReceiptPage - Cancel dialog', async () => {
      const page = getPage();
      await page.getByTestId('process-modal-cancel-button').click();
      await this.#field('QtyTU').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    });
  }

  /** Assert a numeric dialog field. */
  static async expectNumber(columnName, expected) {
    expect(await this.getNumber(columnName), `${columnName} in the receipt dialog`).toBe(expected);
  }
}
