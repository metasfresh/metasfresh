/**
 * Text Widget Utility
 *
 * Handles Text and LongText widget types.
 * - Text: Single-line text input (e.g., Name, Description)
 * - LongText: Multi-line textarea (e.g., Help, CharacterData)
 *
 * Selectors:
 * - Text: #lookup_{fieldName} input
 * - LongText: #lookup_{fieldName} textarea
 */
import { WidgetCommon } from './WidgetCommon';
import { getPage } from '../common';

export class TextWidget {
  /**
   * Set value in a text field.
   *
   * @param {string} fieldName - Database column name (e.g., 'Name', 'Description')
   * @param {string} value - Text value to set
   * @param {Object} options - Optional settings
   * @param {boolean} options.clearFirst - Clear existing value first (default: true)
   * @param {boolean} options.triggerSave - Press Tab to trigger save (default: true)
   */
  static async setValue(fieldName, value, { clearFirst = true, triggerSave = true } = {}) {
    return await WidgetCommon.withStep(`TextWidget - Set ${fieldName} to "${value}"`, async () => {
      const page = getPage();

      // Try default container first
      let container = WidgetCommon.getFieldContainer(fieldName);
      let input = container.locator('input.input-field, input[type="text"]').first();
      let inputCount = await input.count();

      if (inputCount === 0) {
        // Try textarea for LongText fields
        input = container.locator('textarea').first();
        inputCount = await input.count();

        // If still not found, the field might be a LongText with ambiguous class (e.g., C_BPartner_Memo)
        // Try with specific widgetType selector
        if (inputCount === 0) {
          container = WidgetCommon.getFieldContainer(fieldName, 'LongText');
          input = container.locator('textarea').first();
          inputCount = await input.count();
        }
      }

      await input.waitFor({ state: 'visible', timeout: WidgetCommon.WIDGET_TIMEOUT });

      if (clearFirst) {
        await input.click();
        await page.keyboard.press('Control+a');
      }

      await input.fill(value);

      if (triggerSave) {
        await WidgetCommon.triggerBlur();
        await WidgetCommon.waitForSaveComplete();
      }
    });
  }

  /**
   * Get current value of a text field.
   *
   * @param {string} fieldName - Database column name
   * @returns {Promise<string>} Current text value
   */
  static async getValue(fieldName) {
    return await WidgetCommon.withStep(`TextWidget - Get ${fieldName} value`, async () => {
      // Try default container first
      let container = WidgetCommon.getFieldContainer(fieldName);
      let input = container.locator('input.input-field, input[type="text"]').first();
      let inputCount = await input.count();

      if (inputCount === 0) {
        input = container.locator('textarea').first();
        inputCount = await input.count();

        // If still not found, try with specific widgetType selector for LongText
        if (inputCount === 0) {
          container = WidgetCommon.getFieldContainer(fieldName, 'LongText');
          input = container.locator('textarea').first();
        }
      }

      return await input.inputValue();
    });
  }

  /**
   * Check if text field is empty.
   *
   * @param {string} fieldName - Database column name
   * @returns {Promise<boolean>} True if field is empty or contains placeholder
   */
  static async isEmpty(fieldName) {
    const value = await this.getValue(fieldName);
    return !value || value === 'none' || value.trim() === '';
  }

  /**
   * Clear a text field.
   *
   * @param {string} fieldName - Database column name
   * @param {boolean} triggerSave - Press Tab to trigger save (default: true)
   */
  static async clear(fieldName, triggerSave = true) {
    return await WidgetCommon.withStep(`TextWidget - Clear ${fieldName}`, async () => {
      const page = getPage();

      // Try default container first
      let container = WidgetCommon.getFieldContainer(fieldName);
      let input = container.locator('input.input-field, input[type="text"]').first();
      let inputCount = await input.count();

      if (inputCount === 0) {
        input = container.locator('textarea').first();
        inputCount = await input.count();

        // If still not found, try with specific widgetType selector for LongText
        if (inputCount === 0) {
          container = WidgetCommon.getFieldContainer(fieldName, 'LongText');
          input = container.locator('textarea').first();
        }
      }

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
   * Verify text field contains expected value.
   *
   * @param {string} fieldName - Database column name
   * @param {string} expectedValue - Expected text value
   * @returns {Promise<boolean>} True if values match
   */
  static async hasValue(fieldName, expectedValue) {
    const actualValue = await this.getValue(fieldName);
    return actualValue === expectedValue;
  }
}
