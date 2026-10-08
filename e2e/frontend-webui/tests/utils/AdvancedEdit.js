/**
 * Advanced Edit (Alt+E) Utility
 *
 * Opens the Advanced Edit modal and provides methods to interact with fields
 * inside it. Fields marked as IsAdvancedField='Y' in the Application Dictionary
 * are only accessible through this modal.
 *
 * The modal is opened via Alt+E keyboard shortcut and scoped via .panel-modal-content.
 *
 * Usage:
 *   import { AdvancedEdit } from '../utils/AdvancedEdit';
 *
 *   await AdvancedEdit.open();
 *   await AdvancedEdit.setListField('C_PromotionCode_ID', 'PC_12345');
 *   const value = await AdvancedEdit.getListFieldValue('C_PromotionCode_ID');
 *   await AdvancedEdit.close();
 */
import { test } from '../../playwright.config';
import { getPage, SLOW_ACTION_TIMEOUT } from './common';
import { WidgetCommon } from './widgets/WidgetCommon';

export class AdvancedEdit {
  /**
   * Open the Advanced Edit modal via Alt+E.
   * Waits for the modal to be fully visible before returning.
   */
  static async open() {
    return await test.step('AdvancedEdit - Open modal (Alt+E)', async () => {
      const page = getPage();

      await page.keyboard.press('Alt+e');
      await page.locator('.panel-modal').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      // Wait for content to render inside the modal
      await page.locator('.panel-modal-content').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await page.waitForTimeout(500);
    });
  }

  /**
   * Close the Advanced Edit modal.
   * Clicks the Done/Apply button or presses Escape.
   */
  static async close() {
    return await test.step('AdvancedEdit - Close modal', async () => {
      const page = getPage();
      const modal = page.locator('.panel-modal');

      // Try the Done/Apply button first (language-independent: uses data-testid or class)
      const doneButton = modal.locator(
        'button[data-testid="done"], .btn-meta-primary, .btn-meta-success'
      ).first();
      const doneVisible = await doneButton.isVisible().catch(() => false);

      if (doneVisible) {
        await doneButton.click();
      } else {
        // Fallback: press Escape
        await page.keyboard.press('Escape');
      }

      await modal.waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT }).catch(() => {
        // Modal may just become hidden rather than detached
      });
      await page.waitForTimeout(500);
    });
  }

  /**
   * Get the modal content container locator.
   * All field selectors inside Advanced Edit should be scoped to this.
   *
   * @returns {import('@playwright/test').Locator}
   */
  static _getModalContent() {
    const page = getPage();
    return page.locator('.panel-modal-content');
  }

  /**
   * Get a field container inside the Advanced Edit modal.
   *
   * @param {string} fieldName - Database column name (e.g., 'C_PromotionCode_ID')
   * @returns {import('@playwright/test').Locator}
   */
  static _getFieldContainer(fieldName) {
    const modal = this._getModalContent();
    return modal.locator(`#lookup_${fieldName}, .form-field-${fieldName}`).first();
  }

  /**
   * Set a lookup field value inside the Advanced Edit modal.
   * Types the search text, waits for the dropdown, and selects the matching option.
   *
   * @param {string} fieldName - Database column name (e.g., 'C_PromotionCode_ID')
   * @param {string} searchText - Text to type for typeahead search
   * @param {Object} options - Optional settings
   * @param {boolean} options.exactMatch - Require exact text match (default: false)
   */
  static async setLookupField(fieldName, searchText, { exactMatch = false } = {}) {
    return await test.step(`AdvancedEdit - Set lookup ${fieldName} to "${searchText}"`, async () => {
      const page = getPage();
      const container = this._getFieldContainer(fieldName);

      const input = container.locator('input.input-field, input[type="text"]').first();
      await input.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      // Check if readonly
      const isDisabled = await input.isDisabled().catch(() => false);
      if (isDisabled) {
        throw new Error(`AdvancedEdit - Field ${fieldName} is readonly/disabled`);
      }

      await input.click();
      await page.waitForTimeout(300);

      await input.fill(searchText);
      await page.waitForTimeout(WidgetCommon.DEBOUNCE_DELAY);

      // Wait for dropdown
      const dropdown = page.locator('.input-dropdown-list');
      await dropdown.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      // Select option
      if (exactMatch) {
        await dropdown.locator('.input-dropdown-list-option').getByText(searchText, { exact: true }).first().click();
      } else {
        await dropdown.locator('.input-dropdown-list-option').getByText(searchText).first().click();
      }

      // Wait for dropdown to close and value to be set
      await WidgetCommon.waitForDropdownClosed();
      await page.waitForTimeout(500);
    });
  }

  /**
   * Get the displayed value of a lookup field inside the Advanced Edit modal.
   *
   * @param {string} fieldName - Database column name
   * @returns {Promise<string>} The displayed text value
   */
  static async getLookupFieldValue(fieldName) {
    return await test.step(`AdvancedEdit - Get lookup ${fieldName} value`, async () => {
      const container = this._getFieldContainer(fieldName);
      const input = container.locator('input.input-field, input[type="text"]').first();
      await input.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      return await input.inputValue();
    });
  }

  /**
   * Set a text field value inside the Advanced Edit modal.
   *
   * @param {string} fieldName - Database column name
   * @param {string} value - Text to set
   */
  static async setTextField(fieldName, value) {
    return await test.step(`AdvancedEdit - Set text ${fieldName} to "${value}"`, async () => {
      const page = getPage();
      const container = this._getFieldContainer(fieldName);

      let input = container.locator('input.input-field, input[type="text"]').first();
      let inputCount = await input.count();
      if (inputCount === 0) {
        input = container.locator('textarea').first();
      }

      await input.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await input.click();
      await page.keyboard.press('Control+a');
      await input.fill(value);
      await page.waitForTimeout(300);
    });
  }

  /**
   * Get a text field value inside the Advanced Edit modal.
   *
   * @param {string} fieldName - Database column name
   * @returns {Promise<string>}
   */
  static async getTextFieldValue(fieldName) {
    return await test.step(`AdvancedEdit - Get text ${fieldName} value`, async () => {
      const container = this._getFieldContainer(fieldName);
      let input = container.locator('input.input-field, input[type="text"]').first();
      let inputCount = await input.count();
      if (inputCount === 0) {
        input = container.locator('textarea').first();
      }
      return await input.inputValue();
    });
  }

  /**
   * Check if a field is visible inside the Advanced Edit modal.
   *
   * @param {string} fieldName - Database column name
   * @returns {Promise<boolean>}
   */
  static async isFieldVisible(fieldName) {
    return await test.step(`AdvancedEdit - Check if ${fieldName} is visible`, async () => {
      const container = this._getFieldContainer(fieldName);
      return await container.isVisible().catch(() => false);
    });
  }

  /**
   * Set a list/dropdown field value inside the Advanced Edit modal.
   * Clicks the dropdown trigger, waits for options, and selects the matching one.
   *
   * @param {string} fieldName - Database column name (e.g., 'C_PromotionCode_ID')
   * @param {string} optionText - Text of the option to select
   * @param {Object} options - Optional settings
   * @param {boolean} options.exactMatch - Require exact text match (default: false)
   */
  static async setListField(fieldName, optionText, { exactMatch = false } = {}) {
    return await test.step(`AdvancedEdit - Set list ${fieldName} to "${optionText}"`, async () => {
      const page = getPage();
      const container = this._getFieldContainer(fieldName);

      // Click the dropdown trigger (the container or chevron icon)
      const dropdownTrigger = container.locator('.input-dropdown, input, [class*="dropdown"]').first();
      await dropdownTrigger.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await dropdownTrigger.click();

      // Wait for dropdown to appear
      const dropdown = page.locator('.input-dropdown-list');
      await dropdown.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      // Select the matching option
      if (exactMatch) {
        await dropdown.locator('.input-dropdown-list-option').getByText(optionText, { exact: true }).first().click();
      } else {
        await dropdown.locator('.input-dropdown-list-option').getByText(optionText).first().click();
      }

      // Wait for dropdown to close
      await WidgetCommon.waitForDropdownClosed();
      await page.waitForTimeout(500);
    });
  }

  /**
   * Get the displayed value of a list/dropdown field inside the Advanced Edit modal.
   *
   * @param {string} fieldName - Database column name
   * @returns {Promise<string>} The displayed text value
   */
  static async getListFieldValue(fieldName) {
    return await test.step(`AdvancedEdit - Get list ${fieldName} value`, async () => {
      const container = this._getFieldContainer(fieldName);
      const input = container.locator('input').first();
      await input.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      return await input.inputValue();
    });
  }

  /**
   * Check if a lookup field is readonly inside the Advanced Edit modal.
   *
   * @param {string} fieldName - Database column name
   * @returns {Promise<boolean>}
   */
  static async isFieldReadonly(fieldName) {
    return await test.step(`AdvancedEdit - Check if ${fieldName} is readonly`, async () => {
      const container = this._getFieldContainer(fieldName);
      const input = container.locator('input.input-field, input[type="text"]').first();
      return await input.isDisabled().catch(() => true);
    });
  }
}
