import { expect } from '@playwright/test';
import { test } from '../../../playwright.config';
import {
  FRONTEND_BASE_URL,
  FAST_ACTION_TIMEOUT,
  getPage,
  SLOW_ACTION_TIMEOUT,
  VERY_SLOW_ACTION_TIMEOUT,
} from '../common';
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
    // scoped to the dialog's content: the order window behind it renders some of the same columns (e.g. M_HU_PI_Item_Product_ID) too
    return getPage().locator(`.panel-modal-content .form-field-${columnName}`);
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

      // not QtyTU: it is hidden for "No Packing Item" and for individual CUs
      await page.getByTestId('process-modal-start-button').waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
      await this.#settle();
    });
  }

  /** Wait for the dialog's pending requests to finish. */
  static async #settle() {
    const page = getPage();
    await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
  }

  /**
   * Run the action that triggers a dialog parameter change and wait for the server's answer (the PATCH that carries
   * the recomputed dependent fields). The wait is registered before the action. If the action throws, its error is
   * the one reported; there is no fallback: when the expected PATCH does not come, the wait times out and the test fails.
   */
  static async #actAndAwaitParameterChange(action) {
    await Promise.all([
      getPage().waitForResponse(
        (response) => response.request().method() === 'PATCH' && response.url().includes('/process/'),
        { timeout: SLOW_ACTION_TIMEOUT }
      ),
      action(),
    ]);
  }

  /**
   * Fill a field, leave it and wait for the server's answer to the change. Without waiting for that response the
   * dependent fields are read before they are updated. The frontend sends no PATCH when the value did not change,
   * so a value that is already shown is only asserted, never waited for.
   */
  static async #fillAndWaitForRecompute(columnName, text) {
    const input = this.#input(columnName);
    const current = await this.getValue(columnName);
    const unchanged = text === '' ? current === '' : current !== '' && Number(current) === Number(text);
    if (unchanged) {
      return; // nothing to change; the value is already what was asked for
    }
    await input.click();
    // the widget registers the focus one tick after the DOM focus; a blur before that is ignored and sends no PATCH
    await this.#field(columnName).locator('.input-body-container.focused').waitFor({ timeout: SLOW_ACTION_TIMEOUT });
    await input.press('Control+a');
    await this.#actAndAwaitParameterChange(async () => {
      if (text === '') {
        await input.press('Backspace'); // a real key press: the widget does not report an emptied field after fill('')
      }
      else {
        await input.pressSequentially(text);
      }
      await input.blur();
    });
    await this.#settle();
  }

  /**
   * Click an entry of the dropdown opened on the given field. If the entry is the one the field already shows,
   * nothing changes and no PATCH is sent: the list is closed again and that is all.
   * @returns {Promise<boolean>} true when the value was changed (and the server answered), false when it was already selected
   */
  static async #chooseOption(columnName, optionLocator) {
    const page = getPage();
    await optionLocator.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    const wanted = ((await optionLocator.textContent()) ?? '').trim();
    const current = await this.getValue(columnName);
    if (current !== '' && current === wanted) {
      expect(current, `${columnName} keeps the already selected option`).toBe(wanted);
      await page.keyboard.press('Escape');
      await optionLocator.waitFor({ state: 'hidden', timeout: SLOW_ACTION_TIMEOUT });
      return false;
    }
    await this.#actAndAwaitParameterChange(() => optionLocator.click());
    await this.#settle();
    return true;
  }

  /**
   * Choose an entry of a dropdown field (packing instruction, pallet) and wait for the server's answer.
   * An entry that is already selected is only checked (the field keeps showing it), not re-chosen.
   * @param {string} columnName
   * @param {string|RegExp} option - text (or pattern) of the entry
   */
  static async selectOption(columnName, option) {
    return await test.step(`ManufacturingOrderReceiptPage - Select ${option} in ${columnName}`, async () => {
      const page = getPage();
      await this.#field(columnName).locator('.input-dropdown').click();
      await this.#chooseOption(columnName, page.locator('.input-dropdown-list-option').filter({ hasText: option }).first());
    });
  }

  /** Choose "No Packing Item" in the packing instruction dropdown (its name depends on the language, its ID 101 does not). */
  static async selectNoPackingItem() {
    return await test.step('ManufacturingOrderReceiptPage - Select "No Packing Item"', async () => {
      const page = getPage();
      await this.#field('M_HU_PI_Item_Product_ID').locator('.input-dropdown').click();
      // data-test-id is the ID followed by the caption: match exactly ID 101, not e.g. 1010001
      const options = page.locator('.input-dropdown-list-option[data-test-id]');
      await options.first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      const testIds = await options.evaluateAll((elements) => elements.map((element) => element.getAttribute('data-test-id')));
      const index = testIds.findIndex((testId) => /^101\D/.test(testId));
      expect(index, `"No Packing Item" (ID 101) among ${testIds}`).toBeGreaterThanOrEqual(0);
      await this.#chooseOption('M_HU_PI_Item_Product_ID', options.nth(index));
    });
  }

  /** Empty a dropdown field (pallet) by its clear icon and wait for the server's answer. An already empty field is left as it is. */
  static async clearOption(columnName) {
    return await test.step(`ManufacturingOrderReceiptPage - Clear ${columnName}`, async () => {
      if ((await this.getValue(columnName)) === '') {
        return;
      }
      await this.#actAndAwaitParameterChange(async () => {
        await this.#field(columnName).locator('.input-dropdown').hover();
        await this.#field(columnName).locator('.input-icon').click();
      });
      await this.#settle();
    });
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
      await this.#fillAndWaitForRecompute(columnName, String(value));
    });
  }

  /** Empty a field and wait until the dependent fields are recomputed. */
  static async clear(columnName) {
    return await test.step(`ManufacturingOrderReceiptPage - Clear ${columnName}`, async () => {
      await this.#fillAndWaitForRecompute(columnName, '');
    });
  }

  /**
   * Press "Start" and expect the process to be refused with an error toast. The dialog stays open.
   * @returns {Promise<string>} the text of the error toast
   */
  static async startExpectingRefusal() {
    return await test.step('ManufacturingOrderReceiptPage - Start (expect refusal)', async () => {
      const page = getPage();
      // process errors are shown as a notification (frontend Notification component), not as a Toastify toast
      const toast = page.locator('.notification-item.error .notification-content').first();
      await page.getByTestId('process-modal-start-button').click();
      await toast.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      const text = await toast.textContent();
      await page.locator('.notification-item.error .notification-header i').first().click();
      await toast.waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
      return text ?? '';
    });
  }

  /** True when the dialog does not let the user press "Start" (a mandatory field is empty). */
  static async isStartDisabled() {
    return await getPage().getByTestId('process-modal-start-button').isDisabled();
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
      await page.getByTestId('process-modal-start-button').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    });
  }

  /** Assert a numeric dialog field. */
  static async expectNumber(columnName, expected) {
    expect(await this.getNumber(columnName), `${columnName} in the receipt dialog`).toBe(expected);
  }
}
