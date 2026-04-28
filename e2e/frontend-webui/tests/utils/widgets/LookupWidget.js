/**
 * Lookup Widget Utility
 *
 * Handles Lookup widget type - dropdown with typeahead search.
 * Used for fields that reference other records (e.g., C_BPartner_ID, M_Product_ID).
 *
 * Features:
 * - Typeahead search
 * - Search for more option (advanced search)
 * - New record creation
 * - ZoomInto linked record
 *
 * Selectors:
 * - Container: #lookup_{fieldName}
 * - Input: #lookup_{fieldName} input.input-field
 * - Dropdown: .input-dropdown-list
 * - Options: .input-dropdown-list-option
 */
import { WidgetCommon } from './WidgetCommon';
import { getPage } from '../common';

export class LookupWidget {
  /**
   * Set value in a lookup field by searching and selecting from dropdown.
   *
   * @param {string} fieldName - Database column name (e.g., 'C_BPartner_ID', 'M_Product_ID')
   * @param {string} searchText - Text to search for
   * @param {Object} options - Optional settings
   * @param {boolean} options.exactMatch - Require exact text match (default: false)
   * @param {boolean} options.triggerSave - Press Tab to trigger save (default: true)
   * @param {number} options.waitAfterSelect - Wait time after selection in ms (default: 500)
   */
  static async setValue(fieldName, searchText, { exactMatch = false, triggerSave = true, waitAfterSelect = 500 } = {}) {
    return await WidgetCommon.withStep(`LookupWidget - Set ${fieldName} to "${searchText}"`, async () => {
      const page = getPage();
      const container = WidgetCommon.getFieldContainer(fieldName);

      // Get the input field
      const input = container.locator('input.input-field, input[type="text"]').first();
      await input.waitFor({ state: 'visible', timeout: WidgetCommon.WIDGET_TIMEOUT });

      // Check if field is readonly
      const isDisabled = await input.isDisabled().catch(() => false);
      if (isDisabled) {
        console.log(`LookupWidget - ${fieldName} is readonly, skipping`);
        return;
      }

      // Click to focus and open dropdown
      await input.click();

      // Wait for initial loading spinner to disappear
      await WidgetCommon.waitForFieldLoaded(fieldName);

      // Small delay to ensure dropdown is ready
      await page.waitForTimeout(300);

      // Fill the search text
      await input.fill(searchText);

      // Wait for debounce/input processing
      await page.waitForTimeout(WidgetCommon.DEBOUNCE_DELAY);

      // Wait for search spinner to disappear
      await WidgetCommon.waitForFieldLoaded(fieldName);

      // Wait for dropdown to populate
      await page.waitForTimeout(300);

      // Find and click the matching option
      const dropdown = page.locator('.input-dropdown-list');
      await dropdown.waitFor({ state: 'visible', timeout: WidgetCommon.WIDGET_TIMEOUT });

      // Select option - prefer exact match if requested, otherwise first match
      if (exactMatch) {
        await dropdown.locator('.input-dropdown-list-option').getByText(searchText, { exact: true }).first().click();
      } else {
        await dropdown.locator('.input-dropdown-list-option').getByText(searchText).first().click();
      }

      // Wait for dropdown to close
      await WidgetCommon.waitForDropdownClosed();

      // Wait for selection to be processed
      await page.waitForTimeout(waitAfterSelect);

      if (triggerSave) {
        await WidgetCommon.triggerBlur();
        await WidgetCommon.waitForSaveComplete();
      }
    });
  }

  /**
   * Get current displayed value of a lookup field.
   *
   * @param {string} fieldName - Database column name
   * @returns {Promise<string>} Current displayed value
   */
  static async getValue(fieldName) {
    return await WidgetCommon.withStep(`LookupWidget - Get ${fieldName} value`, async () => {
      const container = WidgetCommon.getFieldContainer(fieldName);
      const input = container.locator('input.input-field, input[type="text"]').first();
      return await input.inputValue();
    });
  }

  /**
   * Clear a lookup field using the clear button.
   *
   * @param {string} fieldName - Database column name
   * @param {boolean} triggerSave - Press Tab to trigger save (default: true)
   */
  static async clear(fieldName, triggerSave = true) {
    return await WidgetCommon.withStep(`LookupWidget - Clear ${fieldName}`, async () => {
      const page = getPage();
      const container = WidgetCommon.getFieldContainer(fieldName);

      // Try to find and click clear button (x icon)
      const clearButton = container.locator('[class*="clear"], [class*="close"], .meta-icon-close-alt').first();
      const clearButtonExists = await clearButton.count() > 0;

      if (clearButtonExists && await clearButton.isVisible()) {
        await clearButton.click();
      } else {
        // Fallback: select all and delete
        const input = container.locator('input.input-field, input[type="text"]').first();
        await input.click();
        await page.keyboard.press('Control+a');
        await page.keyboard.press('Delete');
      }

      if (triggerSave) {
        await WidgetCommon.triggerBlur();
        await WidgetCommon.waitForSaveComplete();
      }
    });
  }

  /**
   * Check if lookup field is empty.
   *
   * @param {string} fieldName - Database column name
   * @returns {Promise<boolean>} True if field is empty
   */
  static async isEmpty(fieldName) {
    const value = await this.getValue(fieldName);
    return !value || value === 'none' || value.trim() === '';
  }

  /**
   * Open advanced search modal for a lookup field.
   *
   * @param {string} fieldName - Database column name
   */
  static async openAdvancedSearch(fieldName) {
    return await WidgetCommon.withStep(`LookupWidget - Open advanced search for ${fieldName}`, async () => {
      const page = getPage();
      const container = WidgetCommon.getFieldContainer(fieldName);

      // Click the search button (magnifying glass icon)
      const searchButton = container.locator('[class*="search"], .meta-icon-preview').first();
      await searchButton.click();

      // Wait for search modal to appear
      await page.locator('.modal-content, .modal, .panel-modal').waitFor({
        state: 'visible',
        timeout: WidgetCommon.WIDGET_TIMEOUT,
      });
    });
  }

  /**
   * Set value in a composed widget sub-field (e.g., Location or Contact within BPartner composed widget).
   * The parent field must be set first (e.g., C_BPartner_ID) before sub-fields become enabled.
   *
   * @param {string} fieldName - Database column name of the sub-field (e.g., 'C_BPartner_Location_ID', 'AD_User_ID')
   * @param {string} searchText - Text to search for
   * @param {Object} options - Optional settings (same as setValue)
   */
  static async setComposedSubField(fieldName, searchText, options = {}) {
    return await WidgetCommon.withStep(`LookupWidget - Set composed sub-field ${fieldName} to "${searchText}"`, async () => {
      const page = getPage();

      // Sub-fields in composed widgets use #lookup_{fieldName} directly
      const container = page.locator(`#lookup_${fieldName}`);
      await container.waitFor({ state: 'visible', timeout: WidgetCommon.WIDGET_TIMEOUT });

      // Get the input field within the sub-field container
      const input = container.locator('input.input-field, input[type="text"]').first();
      await input.waitFor({ state: 'visible', timeout: WidgetCommon.WIDGET_TIMEOUT });

      // Check if field is enabled (sub-fields are disabled until parent is set)
      const isDisabled = await input.isDisabled().catch(() => false);
      if (isDisabled) {
        console.log(`LookupWidget - Composed sub-field ${fieldName} is disabled (parent field not set?)`);
        return;
      }

      // Click to focus and open dropdown
      await input.click();
      await page.waitForTimeout(300);

      // Fill the search text
      await input.fill(searchText);
      await page.waitForTimeout(WidgetCommon.DEBOUNCE_DELAY);

      // Wait for dropdown
      const dropdown = page.locator('.input-dropdown-list');
      await dropdown.waitFor({ state: 'visible', timeout: WidgetCommon.WIDGET_TIMEOUT });

      // Select first matching option using mousedown (metasfresh requires this)
      const option = dropdown.locator('.input-dropdown-list-option').getByText(searchText).first();
      await option.dispatchEvent('mousedown');

      await WidgetCommon.waitForDropdownClosed();
      await page.waitForTimeout(options.waitAfterSelect || 500);

      if (options.triggerSave !== false) {
        await WidgetCommon.triggerBlur();
        await WidgetCommon.waitForSaveComplete();
      }
    });
  }

  /**
   * Zoom into the linked record (open in new tab/window).
   *
   * @param {string} fieldName - Database column name
   */
  static async zoomInto(fieldName) {
    return await WidgetCommon.withStep(`LookupWidget - Zoom into ${fieldName}`, async () => {
      const container = WidgetCommon.getFieldContainer(fieldName);

      // Click the zoom button if available
      const zoomButton = container.locator('[class*="zoom"], .meta-icon-zoom').first();
      const exists = await zoomButton.count() > 0;

      if (exists) {
        await zoomButton.click();
      } else {
        // Fallback: double-click the field
        const input = container.locator('input.input-field, input[type="text"]').first();
        await input.dblclick();
      }
    });
  }

  /**
   * Get available dropdown options without selecting.
   *
   * @param {string} fieldName - Database column name
   * @param {string} searchText - Optional search text to filter
   * @returns {Promise<string[]>} Array of option texts
   */
  static async getOptions(fieldName, searchText = '') {
    return await WidgetCommon.withStep(`LookupWidget - Get options for ${fieldName}`, async () => {
      const page = getPage();
      const container = WidgetCommon.getFieldContainer(fieldName);
      const input = container.locator('input.input-field, input[type="text"]').first();

      await input.click();
      await WidgetCommon.waitForFieldLoaded(fieldName);

      if (searchText) {
        await input.fill(searchText);
        await page.waitForTimeout(WidgetCommon.DEBOUNCE_DELAY);
        await WidgetCommon.waitForFieldLoaded(fieldName);
      }

      // Wait for dropdown
      const dropdown = page.locator('.input-dropdown-list');
      await dropdown.waitFor({ state: 'visible', timeout: WidgetCommon.WIDGET_TIMEOUT });

      // Get all option texts
      const options = await dropdown.locator('.input-dropdown-list-option').allTextContents();

      // Close dropdown without selecting
      await page.keyboard.press('Escape');
      await WidgetCommon.waitForDropdownClosed();

      return options;
    });
  }
}
