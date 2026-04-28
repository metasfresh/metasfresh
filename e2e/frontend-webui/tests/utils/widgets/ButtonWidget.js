/**
 * Button Widget Utility
 *
 * Handles Button widget type - process/action buttons.
 * Used for Processing fields that trigger actions.
 *
 * Types of buttons:
 * - Button: Simple action button
 * - ProcessButton: Triggers a background process
 * - ActionButton: Triggers an action with possible parameters
 * - ZoomIntoButton: Opens linked record
 *
 * Selectors:
 * - Container: #lookup_{fieldName}
 * - Button: #lookup_{fieldName} button
 */
import { WidgetCommon } from './WidgetCommon';
import { getPage } from '../common';

export class ButtonWidget {
  /**
   * Click a button widget.
   *
   * @param {string} fieldName - Database column name (e.g., 'Processing')
   * @param {Object} options - Optional settings
   * @param {boolean} options.waitForProcess - Wait for process to complete (default: true)
   * @param {number} options.processTimeout - Timeout for process in ms (default: 30000)
   */
  static async click(fieldName, { waitForProcess = true, processTimeout = 30000 } = {}) {
    return await WidgetCommon.withStep(`ButtonWidget - Click ${fieldName}`, async () => {
      const page = getPage();
      const container = WidgetCommon.getFieldContainer(fieldName);

      // Find and click the button
      const button = container.locator('button').first();
      await button.waitFor({ state: 'visible', timeout: WidgetCommon.WIDGET_TIMEOUT });

      // Check if button is enabled
      const isDisabled = await button.isDisabled();
      if (isDisabled) {
        console.log(`ButtonWidget - ${fieldName} is disabled, skipping`);
        return;
      }

      await button.click();

      if (waitForProcess) {
        // Wait for any processing indicator to appear then disappear
        await page.waitForTimeout(500);

        await page.locator('.rotating, .indicator-pending, .processing').waitFor({
          state: 'detached',
          timeout: processTimeout,
        }).catch(() => {});

        // Also wait for any modal that might appear (process result)
        await page.waitForTimeout(500);
      }
    });
  }

  /**
   * Click button and wait for a confirmation dialog, then confirm.
   *
   * @param {string} fieldName - Database column name
   * @param {boolean} confirm - True to confirm, false to cancel (default: true)
   */
  static async clickAndConfirm(fieldName, confirm = true) {
    return await WidgetCommon.withStep(`ButtonWidget - Click ${fieldName} and ${confirm ? 'confirm' : 'cancel'}`, async () => {
      const page = getPage();

      await this.click(fieldName, { waitForProcess: false });

      // Wait for confirmation dialog
      await page.locator('.modal-content, .modal, .panel-modal, [role="dialog"]').waitFor({
        state: 'visible',
        timeout: WidgetCommon.WIDGET_TIMEOUT,
      });

      if (confirm) {
        // Click OK/Yes/Confirm button
        const confirmButton = page.locator('.modal-content button.btn-meta-success, .modal-content button:has-text("OK"), .modal-content button:has-text("Yes")').first();
        await confirmButton.click();
      } else {
        // Click Cancel/No button
        const cancelButton = page.locator('.modal-content button.btn-meta-secondary, .modal-content button:has-text("Cancel"), .modal-content button:has-text("No")').first();
        await cancelButton.click();
      }

      // Wait for modal to close
      await page.locator('.modal-content, .modal, .panel-modal').waitFor({
        state: 'detached',
        timeout: WidgetCommon.WIDGET_TIMEOUT,
      }).catch(() => {});

      // Wait for any processing to complete
      await page.locator('.rotating, .indicator-pending').waitFor({
        state: 'detached',
        timeout: WidgetCommon.WIDGET_TIMEOUT,
      }).catch(() => {});
    });
  }

  /**
   * Check if button is enabled/clickable.
   *
   * @param {string} fieldName - Database column name
   * @returns {Promise<boolean>} True if button is enabled
   */
  static async isEnabled(fieldName) {
    return await WidgetCommon.withStep(`ButtonWidget - Check if ${fieldName} is enabled`, async () => {
      const container = WidgetCommon.getFieldContainer(fieldName);
      const button = container.locator('button').first();

      const isDisabled = await button.isDisabled().catch(() => true);
      return !isDisabled;
    });
  }

  /**
   * Check if button is visible.
   *
   * @param {string} fieldName - Database column name
   * @returns {Promise<boolean>} True if button is visible
   */
  static async isVisible(fieldName) {
    return await WidgetCommon.withStep(`ButtonWidget - Check if ${fieldName} is visible`, async () => {
      const container = WidgetCommon.getFieldContainer(fieldName);
      const button = container.locator('button').first();

      return await button.isVisible().catch(() => false);
    });
  }

  /**
   * Get the button's text/label.
   *
   * @param {string} fieldName - Database column name
   * @returns {Promise<string>} Button label text
   */
  static async getLabel(fieldName) {
    return await WidgetCommon.withStep(`ButtonWidget - Get ${fieldName} label`, async () => {
      const container = WidgetCommon.getFieldContainer(fieldName);
      const button = container.locator('button').first();
      const text = await button.textContent();
      return text?.trim() || '';
    });
  }

  /**
   * Wait for button to become enabled.
   *
   * @param {string} fieldName - Database column name
   * @param {number} timeout - Timeout in ms (default: 30000)
   */
  static async waitForEnabled(fieldName, timeout = 30000) {
    return await WidgetCommon.withStep(`ButtonWidget - Wait for ${fieldName} to be enabled`, async () => {
      const container = WidgetCommon.getFieldContainer(fieldName);
      const button = container.locator('button').first();

      await button.waitFor({
        state: 'visible',
        timeout,
      });

      // Poll for enabled state
      const startTime = Date.now();
      while (Date.now() - startTime < timeout) {
        const isDisabled = await button.isDisabled().catch(() => true);
        if (!isDisabled) {
          return;
        }
        await button.page().waitForTimeout(500);
      }

      throw new Error(`ButtonWidget - ${fieldName} did not become enabled within ${timeout}ms`);
    });
  }
}
