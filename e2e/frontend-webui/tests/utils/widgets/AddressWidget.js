/**
 * Address Widget Utility
 *
 * Handles Address widget type - expands an INLINE editor for editing location/address.
 * Used for C_Location_ID fields.
 *
 * Features:
 * - Toggle inline address editor (click button to expand/collapse)
 * - Fill address components (street, city, postal code, country, etc.)
 * - Get formatted address display
 *
 * Note: metasfresh uses an INLINE editor pattern, not a modal popup.
 * When clicking the address button, it expands fields below the button.
 *
 * Selectors:
 * - Container: .form-field-{fieldName} or #lookup_{fieldName}
 * - Toggle button: container button
 * - Inline fields appear as siblings after the button when expanded
 */
import { WidgetCommon } from './WidgetCommon';
import { getPage } from '../common';

export class AddressWidget {
  /**
   * Check if the inline editor is currently expanded.
   *
   * @param {string} fieldName - Database column name (e.g., 'C_Location_ID')
   * @returns {Promise<boolean>} True if editor is expanded
   */
  static async isEditorExpanded(fieldName) {
    const container = WidgetCommon.getFieldContainer(fieldName);

    // When expanded, the container contains the Address1 field (Street)
    // Use database column name pattern which is language-independent
    const streetField = container.locator('.form-field-Address1');
    return (await streetField.count()) > 0;
  }

  /**
   * Open the inline address editor by clicking the button.
   * If already open, this is a no-op.
   *
   * @param {string} fieldName - Database column name (e.g., 'C_Location_ID')
   */
  static async openEditor(fieldName) {
    return await WidgetCommon.withStep(`AddressWidget - Open editor for ${fieldName}`, async () => {
      const container = WidgetCommon.getFieldContainer(fieldName);

      // Check if already expanded
      if (await this.isEditorExpanded(fieldName)) {
        return; // Already open
      }

      // Click the toggle button to expand
      const toggleButton = container.locator('button').first();
      await toggleButton.waitFor({ state: 'visible', timeout: WidgetCommon.WIDGET_TIMEOUT });
      await toggleButton.click();

      // Wait for inline fields to appear
      const page = getPage();
      await page.waitForTimeout(500); // Allow expansion animation
    });
  }

  /**
   * Close the inline address editor by clicking the button.
   * If already closed, this is a no-op.
   *
   * @param {string} fieldName - Database column name (e.g., 'C_Location_ID')
   */
  static async closeEditor(fieldName) {
    return await WidgetCommon.withStep(`AddressWidget - Close editor for ${fieldName}`, async () => {
      const page = getPage();
      const container = WidgetCommon.getFieldContainer(fieldName);

      // Check if already collapsed
      if (!(await this.isEditorExpanded(fieldName))) {
        return; // Already closed
      }

      // Click the toggle button to collapse - use force due to overlapping elements
      const toggleButton = container.locator('button').first();
      await toggleButton.scrollIntoViewIfNeeded();
      await toggleButton.click({ force: true });

      // Wait for collapse
      await page.waitForTimeout(300);
    });
  }

  /**
   * Set address fields in the inline editor.
   * Opens the editor automatically if not already open.
   *
   * @param {string} fieldName - Database column name (e.g., 'C_Location_ID')
   * @param {Object} address - Address components
   * @param {string} address.street - Street address
   * @param {string} address.city - City name
   * @param {string} address.postalCode - Postal/ZIP code
   * @param {string} address.country - Country name
   * @param {string} address.region - State/region (optional)
   */
  static async setAddress(fieldName, { street, city, postalCode, country, region }) {
    return await WidgetCommon.withStep(`AddressWidget - Set address for ${fieldName}`, async () => {
      const page = getPage();
      const container = WidgetCommon.getFieldContainer(fieldName);

      // Make sure editor is open
      await this.openEditor(fieldName);

      // Wait for inline editor to fully expand
      await page.waitForTimeout(500);

      // Fill street address using form-field class (Address1 is the column name)
      if (street) {
        const streetInput = container.locator('.form-field-Address1 input').first();
        await streetInput.waitFor({ state: 'visible', timeout: 5000 }).catch(() => {});
        if ((await streetInput.count()) > 0) {
          await streetInput.fill(street);
          await page.waitForTimeout(WidgetCommon.DEBOUNCE_DELAY);
        }
      }

      // Fill postal code (Postal is the column name)
      if (postalCode) {
        const postalInput = container.locator('.form-field-Postal input').first();
        if ((await postalInput.count()) > 0) {
          await postalInput.fill(postalCode);
          await page.waitForTimeout(WidgetCommon.DEBOUNCE_DELAY);
        }
      }

      // Fill city (City is the column name)
      if (city) {
        const cityInput = container.locator('.form-field-City input').first();
        if ((await cityInput.count()) > 0) {
          await cityInput.fill(city);
          await page.waitForTimeout(WidgetCommon.DEBOUNCE_DELAY);
        }
      }

      // Fill country (C_Country_ID is the column name)
      if (country) {
        const countryInput = container.locator('#lookup_C_Country_ID input').first();
        if ((await countryInput.count()) > 0) {
          await countryInput.click();
          await page.waitForTimeout(300);
          await countryInput.fill(country);
          await page.waitForTimeout(500);
          // Select from dropdown using data-testid pattern
          const option = page.locator('[data-testid^="option-"]').filter({ hasText: country }).first();
          if ((await option.count()) > 0) {
            await option.click();
            await page.waitForTimeout(WidgetCommon.DEBOUNCE_DELAY);
          }
        }
      }

      // Fill region/state if provided (C_Region_ID is the column name)
      if (region) {
        const regionInput = container.locator('#lookup_C_Region_ID input').first();
        if ((await regionInput.count()) > 0) {
          await regionInput.click();
          await page.waitForTimeout(300);
          await regionInput.fill(region);
          await page.waitForTimeout(500);
          // Select from dropdown
          const option = page.locator('[data-testid^="option-"]').filter({ hasText: region }).first();
          if ((await option.count()) > 0) {
            await option.click();
            await page.waitForTimeout(WidgetCommon.DEBOUNCE_DELAY);
          }
        }
      }

      // Click elsewhere to close the editor and trigger save
      await page.locator('body').click({ position: { x: 10, y: 10 } });
      await WidgetCommon.waitForSaveComplete();
    });
  }

  /**
   * Close the inline editor (collapses the address fields).
   * Alias for closeEditor for backwards compatibility.
   *
   * @param {string} fieldName - Database column name (e.g., 'C_Location_ID')
   */
  static async saveAndClose(fieldName = 'C_Location_ID') {
    return await WidgetCommon.withStep(`AddressWidget - Save and close`, async () => {
      await this.closeEditor(fieldName);
      await WidgetCommon.waitForSaveComplete();
    });
  }

  /**
   * Cancel editing - just closes the editor.
   * Since changes are auto-saved, this just collapses the editor.
   *
   * @param {string} fieldName - Database column name (e.g., 'C_Location_ID')
   */
  static async cancel(fieldName = 'C_Location_ID') {
    return await WidgetCommon.withStep(`AddressWidget - Cancel`, async () => {
      await this.closeEditor(fieldName);
    });
  }

  /**
   * Get the displayed address text (from the toggle button).
   * The button shows the formatted address when collapsed, or "Edit" if empty.
   *
   * @param {string} fieldName - Database column name
   * @returns {Promise<string>} Formatted address text on the button
   */
  static async getValue(fieldName) {
    return await WidgetCommon.withStep(`AddressWidget - Get ${fieldName} value`, async () => {
      const container = WidgetCommon.getFieldContainer(fieldName);

      // Get the button text - that's the formatted address
      const button = container.locator('button').first();
      await button.waitFor({ state: 'visible', timeout: WidgetCommon.WIDGET_TIMEOUT });
      const text = await button.textContent();
      return text?.trim() || '';
    });
  }

  /**
   * Check if address field is empty.
   *
   * @param {string} fieldName - Database column name
   * @returns {Promise<boolean>} True if field is empty
   */
  static async isEmpty(fieldName) {
    const value = await this.getValue(fieldName);
    return !value || value === 'none' || value.trim() === '' || value === 'Edit' || value === '---';
  }
}
