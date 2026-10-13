/**
 * Attributes Widget Utility
 *
 * Handles ProductAttributes widget type - attribute set instance editor.
 * Used for M_AttributeSetInstance_ID fields.
 *
 * Features:
 * - Open attribute editor dropdown (inline, not modal)
 * - Set attribute values
 * - Get current attribute string
 *
 * Selectors:
 * - Container: .form-field-{fieldName} or .widgetType-Attributes
 * - Edit button: .attributes button
 * - Dropdown: .attributes-dropdown
 */
import { WidgetCommon } from './WidgetCommon';
import { getPage } from '../common';

export class AttributesWidget {
  /**
   * Open the attributes editor dropdown.
   * Note: This is an inline dropdown, not a modal dialog.
   *
   * @param {string} fieldName - Database column name (e.g., 'M_AttributeSetInstance_ID')
   */
  static async openEditor(fieldName) {
    return await WidgetCommon.withStep(`AttributesWidget - Open editor for ${fieldName}`, async () => {
      const page = getPage();
      const container = WidgetCommon.getFieldContainer(fieldName);

      // Click the button (shows "Edit" or attribute caption)
      const editButton = container.locator('.attributes button, button.tag').first();
      await editButton.waitFor({ state: 'visible', timeout: WidgetCommon.WIDGET_TIMEOUT });
      await editButton.click();

      // Wait for dropdown to appear (inline, not modal)
      // The attributes-dropdown is rendered inside the .attributes div when isDropdownOpen=true
      await page.locator('.attributes-dropdown').waitFor({
        state: 'visible',
        timeout: WidgetCommon.WIDGET_TIMEOUT,
      });
    });
  }

  /**
   * Set an attribute value in the editor dropdown.
   * Must call openEditor() first.
   *
   * @param {string} attributeName - Name of the attribute
   * @param {string} value - Value to set
   */
  static async setAttribute(attributeName, value) {
    return await WidgetCommon.withStep(`AttributesWidget - Set ${attributeName} to ${value}`, async () => {
      const page = getPage();

      // Find the attribute row by name in the dropdown
      // Attributes are rendered as WidgetWrapper components with caption labels
      const row = page.locator(`.attributes-dropdown [class*="form-group"]:has-text("${attributeName}"), .attributes-dropdown .form-control-label:has-text("${attributeName}")`).first();

      if (await row.count() === 0) {
        // Try finding by looking for a parent that contains the label
        const container = page.locator(`.attributes-dropdown`).locator(`xpath=.//*[contains(text(),"${attributeName}")]/ancestor::*[contains(@class,"form-group")]`).first();
        if (await container.count() === 0) {
          console.log(`AttributesWidget - Attribute "${attributeName}" not found`);
          return;
        }
        const input = container.locator('input, select').first();
        await setAttributeInput(input, value);
        return;
      }

      const input = row.locator('input, select').first();
      await setAttributeInput(input, value);
    });
  }

  /**
   * Save and close the attributes editor dropdown.
   * Completion is triggered by clicking outside or pressing Alt+Enter/Escape.
   */
  static async saveAndClose() {
    return await WidgetCommon.withStep(`AttributesWidget - Save and close`, async () => {
      const page = getPage();

      // Press Alt+Enter to complete/save and close
      await page.keyboard.press('Alt+Enter');

      // Wait for dropdown to close
      await page.locator('.attributes-dropdown').waitFor({
        state: 'detached',
        timeout: WidgetCommon.WIDGET_TIMEOUT,
      }).catch(() => {});

      await WidgetCommon.waitForSaveComplete();
    });
  }

  /**
   * Cancel and close the attributes editor dropdown.
   */
  static async cancel() {
    return await WidgetCommon.withStep(`AttributesWidget - Cancel`, async () => {
      const page = getPage();
      const dropdown = page.locator('.attributes-dropdown');

      // Try Escape first
      await page.keyboard.press('Escape');
      await page.waitForTimeout(300);

      // If dropdown is still visible, try clicking outside
      if (await dropdown.isVisible().catch(() => false)) {
        console.log('AttributesWidget - Dropdown still visible after Escape, clicking outside');
        await page.locator('body').click({ position: { x: 10, y: 10 }, force: true });
        await page.waitForTimeout(300);
      }

      // Wait for dropdown to close, with retry
      for (let i = 0; i < 3; i++) {
        if (!await dropdown.isVisible().catch(() => false)) {
          break;
        }
        await page.keyboard.press('Escape');
        await page.waitForTimeout(300);
      }
    });
  }

  /**
   * Close any open attributes dropdown (utility function).
   */
  static async closeAnyOpenDropdown() {
    const page = getPage();
    const dropdown = page.locator('.attributes-dropdown');

    if (await dropdown.isVisible().catch(() => false)) {
      console.log('AttributesWidget - Closing open attributes dropdown');
      await page.keyboard.press('Escape');
      await page.waitForTimeout(300);
      // Click body to ensure blur
      await page.locator('body').click({ position: { x: 10, y: 10 }, force: true });
      await page.waitForTimeout(300);
    }
  }

  /**
   * Get the displayed attribute string.
   *
   * @param {string} fieldName - Database column name
   * @returns {Promise<string>} Attribute string (e.g., "Lot=ABC; Exp=2026-12")
   */
  static async getValue(fieldName) {
    return await WidgetCommon.withStep(`AttributesWidget - Get ${fieldName} value`, async () => {
      const container = WidgetCommon.getFieldContainer(fieldName);
      const button = container.locator('button').first();
      const text = await button.textContent();
      return text?.trim() || '';
    });
  }

  /**
   * Check if attributes field is empty (shows "---").
   *
   * @param {string} fieldName - Database column name
   * @returns {Promise<boolean>} True if no attributes are set
   */
  static async isEmpty(fieldName) {
    const value = await this.getValue(fieldName);
    return !value || value === '---' || value === 'none' || value.trim() === '';
  }

  /**
   * Clear all attributes.
   *
   * @param {string} fieldName - Database column name
   */
  static async clear(fieldName) {
    return await WidgetCommon.withStep(`AttributesWidget - Clear ${fieldName}`, async () => {
      await this.openEditor(fieldName);

      const page = getPage();

      // Look for clear/reset button in dropdown
      const clearButton = page.locator('.attributes-dropdown button:has-text("Clear"), .attributes-dropdown button:has-text("Reset")').first();

      if (await clearButton.count() > 0) {
        await clearButton.click();
      }

      await this.saveAndClose();
    });
  }
}

/**
 * Helper to set input value based on element type.
 * @param {import('@playwright/test').Locator} input
 * @param {string} value
 */
async function setAttributeInput(input, value) {
  const page = getPage();
  const tagName = await input.evaluate(el => el.tagName.toLowerCase());

  if (tagName === 'select') {
    await input.selectOption({ label: value });
  } else {
    await input.fill(value);
    // Trigger blur to save
    await page.keyboard.press('Tab');
  }
}
