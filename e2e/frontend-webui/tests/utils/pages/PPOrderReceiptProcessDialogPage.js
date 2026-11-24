import { expect } from '@playwright/test';
import { test } from '../../../playwright.config';
import { FRONTEND_BASE_URL, getPage, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../common';
import { MANUFACTURING_ORDER_WINDOW_ID } from '../WindowIds';

/** Manufacturing issue/receipt view (PPOrderLinesViewFactory), opened from an order by WEBUI_PP_Order_IssueReceipt_Launcher */
const MANUFACTURING_ISSUE_RECEIPT_WINDOW_ID = 540328;

/**
 * Page object for the desktop production receipt dialog ("Empfangen", process WEBUI_PP_Order_Receipt)
 * of a manufacturing order (window 53009).
 *
 * Flow: manufacturing order -> actions menu (Alt+1) -> issue/receipt launcher -> issue/receipt view (window 540328)
 * -> main product row -> quick action "Empfangen" -> dialog.
 *
 * Dialog fields are addressed by their language-independent ColumnName (`.form-field-<ColumnName>`),
 * list entries by their `data-testid="option-<ID>"`.
 */
export class PPOrderReceiptProcessDialogPage {
  static #field({ columnName }) {
    // scoped to the dialog: the views behind it render some of the same columns too
    return getPage().locator(`.panel-modal-content .form-field-${columnName}`);
  }

  /**
   * The testing API registers the order under its identifier in the masterdata context
   * (key `<PPOrderId class name>:<identifier>`); its response carries no order ID of its own.
   * @returns {number} the PP_Order_ID
   */
  static ppOrderIdOf({ masterdata, ppOrderIdentifier }) {
    const key = Object.keys(masterdata.context ?? {}).find((k) => k.endsWith(`PPOrderId:${ppOrderIdentifier}`));
    expect(key, `PP_Order_ID of "${ppOrderIdentifier}" in the masterdata context`).toBeDefined();
    return Number(masterdata.context[key]);
  }

  static async openManufacturingOrder({ ppOrderId }) {
    return await test.step(`PPOrderReceiptProcessDialogPage - Open manufacturing order ${ppOrderId}`, async () => {
      const page = getPage();
      await page.goto(`${FRONTEND_BASE_URL}/window/${MANUFACTURING_ORDER_WINDOW_ID}/${ppOrderId}`);
      await page.getByTestId('status-button').waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
    });
  }

  /** Opens the issue/receipt view of the order shown; the launcher is addressed by its internal name. */
  static async openIssueReceiptView() {
    return await test.step('PPOrderReceiptProcessDialogPage - Open issue/receipt view', async () => {
      const page = getPage();
      await page.keyboard.press('Alt+1'); // actions menu
      const launcher = page.getByTestId('action-WEBUI_PP_Order_IssueReceipt_Launcher');
      await launcher.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await Promise.all([
        page.waitForResponse(
          (response) =>
            response.request().method() === 'POST' &&
            response.url().includes(`/documentView/${MANUFACTURING_ISSUE_RECEIPT_WINDOW_ID}`) &&
            response.ok(),
          { timeout: VERY_SLOW_ACTION_TIMEOUT }
        ),
        launcher.click(),
      ]);
      await page.getByTestId('quick-action-button').waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
    });
  }

  /**
   * Selects the row of the received product (the main product row shows the product's value).
   * That row can come pre-selected, and a click would deselect it.
   */
  static async selectMainProductRow({ productValue }) {
    return await test.step(`PPOrderReceiptProcessDialogPage - Select main product row ${productValue}`, async () => {
      const page = getPage();
      const row = page.locator('.panel-modal .table-row').filter({ hasText: productValue }).first();
      await row.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      if (!(await row.evaluate((el) => el.classList.contains('row-selected')))) {
        await row.click();
      }
      await expect(row).toHaveClass(/\brow-selected\b/, { timeout: SLOW_ACTION_TIMEOUT });
    });
  }

  /** The action is chosen by its internal name: the default quick action depends on the order. */
  static async openReceiptDialog() {
    return await test.step('PPOrderReceiptProcessDialogPage - Open receipt dialog', async () => {
      const page = getPage();
      await page.getByTestId('quick-action-dropdown-toggle').click();
      const receiveAction = page.getByTestId('quick-action-WEBUI_PP_Order_Receipt');
      await receiveAction.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await receiveAction.click();
      await page.getByTestId('process-modal-start-button').waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
      await this.#field({ columnName: 'M_HU_PI_Item_ID' }).waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    });
  }

  /**
   * The entries of the dialog's pallet field (M_HU_PI_Item_ID); the list is closed again afterwards.
   * @returns {Promise<{id: string, text: string}[]>} id = M_HU_PI_Item_ID, text = "<PI version name> (<qty> x <TU PI name>)"
   */
  static async getLUPIItemOptions() {
    return await test.step('PPOrderReceiptProcessDialogPage - Read pallet entries', async () => {
      const page = getPage();
      const dropdown = this.#field({ columnName: 'M_HU_PI_Item_ID' }).locator('.input-dropdown');
      await dropdown.click();
      const options = page.locator('.input-dropdown-list-option[data-testid^="option-"]');
      await options.first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      const entries = await options.evaluateAll((elements) =>
        elements.map((element) => ({
          id: element.getAttribute('data-testid').substring('option-'.length),
          text: element.textContent.trim(),
        }))
      );
      // closed by a second click: Escape would also close the dialog
      await dropdown.click();
      await options.first().waitFor({ state: 'hidden', timeout: SLOW_ACTION_TIMEOUT });
      return entries;
    });
  }

  /** @returns {Promise<string>} the text the pallet field shows ('' when empty) */
  static async getSelectedLUPIItem() {
    return await this.getFieldValue({ columnName: 'M_HU_PI_Item_ID' });
  }

  /** @returns {Promise<string>} the field value as shown in the dialog, trimmed ('' when empty) */
  static async getFieldValue({ columnName }) {
    return (await this.#field({ columnName }).locator('input.input-field').inputValue()).trim();
  }

  static async startProcess() {
    return await test.step('PPOrderReceiptProcessDialogPage - Start', async () => {
      const startButton = getPage().getByTestId('process-modal-start-button');
      await startButton.click();
      // the dialog is gone when its start button is
      await startButton.waitFor({ state: 'detached', timeout: VERY_SLOW_ACTION_TIMEOUT });
    });
  }
}
