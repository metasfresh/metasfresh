/**
 * Numeric Widget Utility
 *
 * Handles numeric widget types:
 * - Integer: Whole numbers (e.g., T_Integer)
 * - Number: Decimal numbers (e.g., T_Number)
 * - Amount: Currency amounts (e.g., T_Amount)
 * - Quantity: Quantities with UOM (e.g., T_Qty)
 *
 * Selectors:
 * - All numeric types: #lookup_{fieldName} input (spinbutton or text)
 */
import { WidgetCommon } from './WidgetCommon';
import { getPage } from '../common';

export class NumericWidget {
  /**
   * Set value in a numeric field.
   *
   * @param {string} fieldName - Database column name (e.g., 'T_Integer', 'T_Amount')
   * @param {number|string} value - Numeric value to set
   * @param {Object} options - Optional settings
   * @param {boolean} options.clearFirst - Clear existing value first (default: true)
   * @param {boolean} options.triggerSave - Press Tab to trigger save (default: true)
   */
  static async setValue(fieldName, value, { clearFirst = true, triggerSave = true } = {}) {
    return await WidgetCommon.withStep(`NumericWidget - Set ${fieldName} to ${value}`, async () => {
      const page = getPage();
      const container = WidgetCommon.getFieldContainer(fieldName);

      // Numeric fields use input or spinbutton
      const input = container.locator('input').first();
      await input.waitFor({ state: 'visible', timeout: WidgetCommon.WIDGET_TIMEOUT });

      // Check if field is readonly
      const isDisabled = await input.isDisabled().catch(() => false);
      if (isDisabled) {
        console.log(`NumericWidget - ${fieldName} is readonly, skipping`);
        return;
      }

      if (clearFirst) {
        await input.click();
        await page.keyboard.press('Control+a');
      }

      await input.fill(value.toString());

      if (triggerSave) {
        await WidgetCommon.triggerBlur();
        await WidgetCommon.waitForSaveComplete();
      }
    });
  }

  /**
   * Get current value of a numeric field.
   *
   * @param {string} fieldName - Database column name
   * @returns {Promise<string>} Current value as string
   */
  static async getValue(fieldName) {
    return await WidgetCommon.withStep(`NumericWidget - Get ${fieldName} value`, async () => {
      const container = WidgetCommon.getFieldContainer(fieldName);
      const input = container.locator('input').first();
      return await input.inputValue();
    });
  }

  /**
   * Get current value as a number.
   *
   * @param {string} fieldName - Database column name
   * @returns {Promise<number|null>} Current value as number, or null if empty
   */
  static async getNumericValue(fieldName) {
    const strValue = await this.getValue(fieldName);
    if (!strValue || strValue === 'none' || strValue.trim() === '') {
      return null;
    }
    // Handle locale-specific decimal separators
    const normalized = strValue.replace(',', '.');
    const parsed = parseFloat(normalized);
    return isNaN(parsed) ? null : parsed;
  }

  /**
   * Increment the numeric value using spinner buttons or keyboard.
   *
   * @param {string} fieldName - Database column name
   * @param {number} times - Number of times to increment (default: 1)
   */
  static async increment(fieldName, times = 1) {
    return await WidgetCommon.withStep(`NumericWidget - Increment ${fieldName} by ${times}`, async () => {
      const page = getPage();
      const container = WidgetCommon.getFieldContainer(fieldName);
      const input = container.locator('input').first();

      await input.click();
      for (let i = 0; i < times; i++) {
        await page.keyboard.press('ArrowUp');
      }

      await WidgetCommon.triggerBlur();
      await WidgetCommon.waitForSaveComplete();
    });
  }

  /**
   * Decrement the numeric value using spinner buttons or keyboard.
   *
   * @param {string} fieldName - Database column name
   * @param {number} times - Number of times to decrement (default: 1)
   */
  static async decrement(fieldName, times = 1) {
    return await WidgetCommon.withStep(`NumericWidget - Decrement ${fieldName} by ${times}`, async () => {
      const page = getPage();
      const container = WidgetCommon.getFieldContainer(fieldName);
      const input = container.locator('input').first();

      await input.click();
      for (let i = 0; i < times; i++) {
        await page.keyboard.press('ArrowDown');
      }

      await WidgetCommon.triggerBlur();
      await WidgetCommon.waitForSaveComplete();
    });
  }

  /**
   * Clear a numeric field.
   *
   * @param {string} fieldName - Database column name
   * @param {boolean} triggerSave - Press Tab to trigger save (default: true)
   */
  static async clear(fieldName, triggerSave = true) {
    return await WidgetCommon.withStep(`NumericWidget - Clear ${fieldName}`, async () => {
      const page = getPage();
      const container = WidgetCommon.getFieldContainer(fieldName);
      const input = container.locator('input').first();

      await input.click();
      await page.keyboard.press('Control+a');
      await page.keyboard.press('Delete');

      if (triggerSave) {
        await WidgetCommon.triggerBlur();
        await WidgetCommon.waitForSaveComplete();
      }
    });
  }

  /**
   * Check if numeric field is empty.
   *
   * @param {string} fieldName - Database column name
   * @returns {Promise<boolean>} True if field is empty
   */
  static async isEmpty(fieldName) {
    const value = await this.getValue(fieldName);
    return !value || value === 'none' || value.trim() === '';
  }
}
