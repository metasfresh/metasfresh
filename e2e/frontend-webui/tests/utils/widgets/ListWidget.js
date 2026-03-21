/**
 * List Widget Utility
 *
 * Handles List widget type - dropdown with predefined options.
 * Used for fields with fixed set of values (e.g., C_Currency_ID, C_UOM_ID, AD_Client_ID).
 *
 * Difference from Lookup:
 * - List: Predefined options, no search
 * - Lookup: Dynamic search against database
 *
 * Selectors:
 * - Container: #lookup_{fieldName}
 * - Input: #lookup_{fieldName} input
 * - Dropdown trigger: #lookup_{fieldName} .input-dropdown (click to open)
 * - Options: .input-dropdown-list-option
 */
import { WidgetCommon } from './WidgetCommon';
import { getPage } from '../common';

export class ListWidget {
  /**
   * Set value in a list field by selecting from dropdown.
   *
   * @param {string} fieldName - Database column name (e.g., 'C_Currency_ID', 'C_UOM_ID')
   * @param {string} optionText - Text of the option to select
   * @param {Object} options - Optional settings
   * @param {boolean} options.exactMatch - Require exact text match (default: false)
   * @param {boolean} options.triggerSave - Press Tab to trigger save (default: true)
   */
  static async setValue(fieldName, optionText, { exactMatch = false, triggerSave = true } = {}) {
    return await WidgetCommon.withStep(`ListWidget - Set ${fieldName} to "${optionText}"`, async () => {
      const page = getPage();
      const container = WidgetCommon.getFieldContainer(fieldName);

      // Click the container to open dropdown (list widgets often use the whole container as trigger)
      const dropdownTrigger = container.locator('.input-dropdown, input, [class*="dropdown"]').first();
      await dropdownTrigger.waitFor({ state: 'visible', timeout: WidgetCommon.WIDGET_TIMEOUT });

      // Check if field is readonly
      const input = container.locator('input').first();
      const isDisabled = await input.isDisabled().catch(() => false);
      if (isDisabled) {
        console.log(`ListWidget - ${fieldName} is readonly, skipping`);
        return;
      }

      await dropdownTrigger.click();

      // Wait for dropdown to appear
      await WidgetCommon.waitForDropdown();

      // Find and click the matching option
      const dropdown = page.locator('.input-dropdown-list');

      if (exactMatch) {
        await dropdown.locator('.input-dropdown-list-option').getByText(optionText, { exact: true }).first().click();
      } else {
        await dropdown.locator('.input-dropdown-list-option').getByText(optionText).first().click();
      }

      // Wait for dropdown to close
      await WidgetCommon.waitForDropdownClosed();

      if (triggerSave) {
        await WidgetCommon.triggerBlur();
        await WidgetCommon.waitForSaveComplete();
      }
    });
  }

  /**
   * Set value by option index (0-based).
   *
   * @param {string} fieldName - Database column name
   * @param {number} index - Index of the option to select (0-based)
   * @param {boolean} triggerSave - Press Tab to trigger save (default: true)
   */
  static async setByIndex(fieldName, index, triggerSave = true) {
    return await WidgetCommon.withStep(`ListWidget - Set ${fieldName} by index ${index}`, async () => {
      const page = getPage();
      const container = WidgetCommon.getFieldContainer(fieldName);

      const dropdownTrigger = container.locator('.input-dropdown, input, [class*="dropdown"]').first();
      await dropdownTrigger.click();

      await WidgetCommon.waitForDropdown();

      const dropdown = page.locator('.input-dropdown-list');
      const options = dropdown.locator('.input-dropdown-list-option');

      await options.nth(index).click();

      await WidgetCommon.waitForDropdownClosed();

      if (triggerSave) {
        await WidgetCommon.triggerBlur();
        await WidgetCommon.waitForSaveComplete();
      }
    });
  }

  /**
   * Get current displayed value of a list field.
   *
   * @param {string} fieldName - Database column name
   * @returns {Promise<string>} Current displayed value
   */
  static async getValue(fieldName) {
    return await WidgetCommon.withStep(`ListWidget - Get ${fieldName} value`, async () => {
      const container = WidgetCommon.getFieldContainer(fieldName);
      const input = container.locator('input').first();
      return await input.inputValue();
    });
  }

  /**
   * Clear a list field using the clear button.
   *
   * @param {string} fieldName - Database column name
   * @param {boolean} triggerSave - Press Tab to trigger save (default: true)
   */
  static async clear(fieldName, triggerSave = true) {
    return await WidgetCommon.withStep(`ListWidget - Clear ${fieldName}`, async () => {
      const page = getPage();
      const container = WidgetCommon.getFieldContainer(fieldName);

      // Try to find and click clear button (x icon)
      const clearButton = container.locator('[class*="clear"], [class*="close"], .meta-icon-close-alt').first();
      const clearButtonExists = await clearButton.count() > 0;

      if (clearButtonExists && await clearButton.isVisible()) {
        await clearButton.click();
      } else {
        // Some list fields may not have a clear button
        // Try selecting an empty/null option if available
        console.log(`ListWidget - ${fieldName} has no clear button`);
      }

      if (triggerSave) {
        await WidgetCommon.triggerBlur();
        await WidgetCommon.waitForSaveComplete();
      }
    });
  }

  /**
   * Check if list field is empty.
   *
   * @param {string} fieldName - Database column name
   * @returns {Promise<boolean>} True if field is empty
   */
  static async isEmpty(fieldName) {
    const value = await this.getValue(fieldName);
    return !value || value === 'none' || value.trim() === '';
  }

  /**
   * Get all available options from the dropdown.
   *
   * @param {string} fieldName - Database column name
   * @returns {Promise<string[]>} Array of option texts
   */
  static async getOptions(fieldName) {
    return await WidgetCommon.withStep(`ListWidget - Get options for ${fieldName}`, async () => {
      const page = getPage();
      const container = WidgetCommon.getFieldContainer(fieldName);

      const dropdownTrigger = container.locator('.input-dropdown, input, [class*="dropdown"]').first();
      await dropdownTrigger.click();

      await WidgetCommon.waitForDropdown();

      const dropdown = page.locator('.input-dropdown-list');
      const options = await dropdown.locator('.input-dropdown-list-option').allTextContents();

      // Close dropdown without selecting
      await page.keyboard.press('Escape');
      await WidgetCommon.waitForDropdownClosed();

      return options;
    });
  }

  /**
   * Get the number of available options.
   *
   * @param {string} fieldName - Database column name
   * @returns {Promise<number>} Number of options
   */
  static async getOptionCount(fieldName) {
    const options = await this.getOptions(fieldName);
    return options.length;
  }
}
