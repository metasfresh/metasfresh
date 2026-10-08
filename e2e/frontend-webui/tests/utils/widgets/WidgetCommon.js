/**
 * Common utilities shared across all widget types.
 *
 * Provides base selectors, timeouts, and helper functions used by
 * all widget implementations.
 */
import { test } from '../../../playwright.config';
import { getPage, SLOW_ACTION_TIMEOUT } from '../common';

/**
 * Common widget utility functions and constants.
 */
export class WidgetCommon {
  /**
   * Standard timeout for widget interactions.
   */
  static WIDGET_TIMEOUT = SLOW_ACTION_TIMEOUT;

  /**
   * Debounce delay after typing (milliseconds).
   * Accounts for autocomplete debounce in lookup fields.
   */
  static DEBOUNCE_DELAY = 500;

  /**
   * Get the container locator for a field by its database column name.
   * metasfresh uses #lookup_{fieldName} pattern for widget containers.
   *
   * @param {string} fieldName - Database column name (e.g., 'C_BPartner_ID', 'Name')
   * @param {string} widgetType - Optional widget type to make selector more specific (e.g., 'LongText', 'Image')
   * @returns {import('@playwright/test').Locator} Container locator
   */
  static getFieldContainer(fieldName, widgetType = null) {
    const page = getPage();

    // If widget type is specified, use it for more specific matching
    // This helps when multiple elements have the same form-field- class (e.g., standalone vs Composed)
    if (widgetType) {
      return page.locator(`.widgetType-${widgetType}.form-field-${fieldName}`).first();
    }

    // Primary pattern: #lookup_{fieldName}
    // Fallback pattern: .form-field-{fieldName}
    return page.locator(`#lookup_${fieldName}, .form-field-${fieldName}`).first();
  }

  /**
   * Get the input element within a field container.
   *
   * @param {string} fieldName - Database column name
   * @param {string} inputSelector - Optional specific input selector (default: 'input')
   * @returns {import('@playwright/test').Locator} Input locator
   */
  static getFieldInput(fieldName, inputSelector = 'input') {
    return this.getFieldContainer(fieldName).locator(inputSelector).first();
  }

  /**
   * Wait for any loading spinner to disappear within a field container.
   *
   * @param {string} fieldName - Database column name
   */
  static async waitForFieldLoaded(fieldName) {
    const page = getPage();
    const container = this.getFieldContainer(fieldName);

    await container
      .locator('.rotating, .spinner, .icon-rotate')
      .waitFor({
        state: 'detached',
        timeout: this.WIDGET_TIMEOUT,
      })
      .catch(() => {
        // Ignore if no spinner exists
      });
  }

  /**
   * Wait for the global save indicator to complete.
   * Called after field changes to ensure record is saved.
   */
  static async waitForSaveComplete() {
    const page = getPage();

    await page
      .locator('.rotating, .indicator-pending')
      .waitFor({
        state: 'detached',
        timeout: this.WIDGET_TIMEOUT,
      })
      .catch(() => {
        // Ignore if indicator doesn't exist
      });
  }

  /**
   * Wait for dropdown list to appear.
   */
  static async waitForDropdown() {
    const page = getPage();
    await page.locator('.input-dropdown-list').waitFor({
      state: 'visible',
      timeout: this.WIDGET_TIMEOUT,
    });
  }

  /**
   * Wait for dropdown list to disappear.
   */
  static async waitForDropdownClosed() {
    const page = getPage();
    await page
      .locator('.input-dropdown-list')
      .waitFor({
        state: 'detached',
        timeout: this.WIDGET_TIMEOUT,
      })
      .catch(() => {
        // Ignore if already closed
      });
  }

  /**
   * Check if a field is readonly/disabled.
   *
   * @param {string} fieldName - Database column name
   * @returns {Promise<boolean>} True if field is readonly
   */
  static async isFieldReadonly(fieldName) {
    const input = this.getFieldInput(fieldName);
    const isDisabled = await input.isDisabled().catch(() => false);
    const hasReadonly = await input.getAttribute('readonly').catch(() => null);
    return isDisabled || hasReadonly !== null;
  }

  /**
   * Trigger blur event by pressing Tab.
   * This causes metasfresh to save the record.
   */
  static async triggerBlur() {
    const page = getPage();
    await page.keyboard.press('Tab');
    await page.waitForTimeout(this.DEBOUNCE_DELAY);
  }

  /**
   * Clear a field by selecting all and deleting.
   *
   * @param {string} fieldName - Database column name
   */
  static async clearField(fieldName) {
    const input = this.getFieldInput(fieldName);
    await input.click();
    const page = getPage();
    await page.keyboard.press('Control+a');
    await page.keyboard.press('Delete');
  }

  /**
   * Get the currently displayed value of a field.
   *
   * @param {string} fieldName - Database column name
   * @returns {Promise<string>} Current value
   */
  static async getFieldValue(fieldName) {
    const input = this.getFieldInput(fieldName);
    return await input.inputValue();
  }

  /**
   * Execute a widget operation within a test step for better reporting.
   *
   * @param {string} stepName - Name for the test step
   * @param {Function} fn - Async function to execute
   * @returns {Promise<*>} Result of fn
   */
  static async withStep(stepName, fn) {
    return await test.step(stepName, fn);
  }
}
