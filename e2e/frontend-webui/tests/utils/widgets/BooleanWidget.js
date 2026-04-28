/**
 * Boolean Widget Utility
 *
 * Handles boolean widget types:
 * - Switch: Toggle switch (e.g., IsActive)
 * - YesNo: Checkbox (e.g., Processed)
 *
 * Selectors:
 * - Switch: #lookup_{fieldName} .input-slider
 * - YesNo: #lookup_{fieldName} input[type="checkbox"]
 */
import { WidgetCommon } from './WidgetCommon';
import { getPage } from '../common';

export class BooleanWidget {
  /**
   * Set a boolean field to true (checked/on).
   *
   * @param {string} fieldName - Database column name (e.g., 'IsActive', 'Processed')
   * @param {boolean} triggerSave - Wait for save to complete (default: true)
   */
  static async setTrue(fieldName, triggerSave = true) {
    return await WidgetCommon.withStep(`BooleanWidget - Set ${fieldName} to true`, async () => {
      const isChecked = await this.getValue(fieldName);
      if (!isChecked) {
        await this.toggle(fieldName, triggerSave);
      }
    });
  }

  /**
   * Set a boolean field to false (unchecked/off).
   *
   * @param {string} fieldName - Database column name
   * @param {boolean} triggerSave - Wait for save to complete (default: true)
   */
  static async setFalse(fieldName, triggerSave = true) {
    return await WidgetCommon.withStep(`BooleanWidget - Set ${fieldName} to false`, async () => {
      const isChecked = await this.getValue(fieldName);
      if (isChecked) {
        await this.toggle(fieldName, triggerSave);
      }
    });
  }

  /**
   * Set a boolean field to a specific value.
   *
   * @param {string} fieldName - Database column name
   * @param {boolean} value - True or false
   * @param {boolean} triggerSave - Wait for save to complete (default: true)
   */
  static async setValue(fieldName, value, triggerSave = true) {
    if (value) {
      await this.setTrue(fieldName, triggerSave);
    } else {
      await this.setFalse(fieldName, triggerSave);
    }
  }

  /**
   * Toggle a boolean field (switch between true and false).
   *
   * metasfresh boolean widgets:
   * - Switch (widgetType-Switch): .input-switch > input[type="checkbox"] + .input-slider
   * - YesNo (widgetType-YesNo): .input-checkbox > input[type="checkbox"] + .input-checkbox-tick
   *
   * The actual <input> has tabindex="-1" and is visually hidden behind the styled
   * slider/tick, so Playwright's click on wrappers doesn't toggle the checkbox.
   * We must use JavaScript el.click() on the actual checkbox input.
   *
   * @param {string} fieldName - Database column name
   * @param {boolean} triggerSave - Wait for save to complete (default: true)
   */
  static async toggle(fieldName, triggerSave = true) {
    return await WidgetCommon.withStep(`BooleanWidget - Toggle ${fieldName}`, async () => {
      const container = WidgetCommon.getFieldContainer(fieldName);

      // Both Switch and YesNo widgets have an input[type="checkbox"] inside
      // Use JavaScript click because Playwright click on wrapper doesn't work
      const checkbox = container.locator('input[type="checkbox"]').first();
      await checkbox.evaluate((el) => el.click());

      if (triggerSave) {
        await WidgetCommon.waitForSaveComplete();
      }
    });
  }

  /**
   * Get current value of a boolean field.
   *
   * Both Switch and YesNo widgets have an <input type="checkbox"> inside.
   * We read the checked state from that input.
   *
   * @param {string} fieldName - Database column name
   * @returns {Promise<boolean>} Current state (true = checked/on, false = unchecked/off)
   */
  static async getValue(fieldName) {
    return await WidgetCommon.withStep(`BooleanWidget - Get ${fieldName} value`, async () => {
      const container = WidgetCommon.getFieldContainer(fieldName);

      // Both Switch and YesNo widgets contain an input[type="checkbox"]
      const checkbox = container.locator('input[type="checkbox"]').first();
      const checkboxCount = await checkbox.count();

      if (checkboxCount > 0) {
        return await checkbox.isChecked();
      }

      // Fallback: check for checked class on wrapper
      const wrapper = container.locator('.input-switch, .input-checkbox').first();
      const isChecked = await wrapper.evaluate((el) => {
        const input = el.querySelector('input[type="checkbox"]');
        return input ? input.checked : false;
      }).catch(() => false);

      return isChecked;
    });
  }

  /**
   * Check if boolean field is true (checked/on).
   *
   * @param {string} fieldName - Database column name
   * @returns {Promise<boolean>} True if checked/on
   */
  static async isTrue(fieldName) {
    return await this.getValue(fieldName);
  }

  /**
   * Check if boolean field is false (unchecked/off).
   *
   * @param {string} fieldName - Database column name
   * @returns {Promise<boolean>} True if unchecked/off
   */
  static async isFalse(fieldName) {
    return !(await this.getValue(fieldName));
  }
}
