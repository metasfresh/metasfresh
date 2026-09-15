/**
 * Date Widget Utility
 *
 * Handles date and time widget types:
 * - Date: Date only (e.g., T_Date)
 * - Time: Time only (e.g., T_Time)
 * - ZonedDateTime: Date and time with timezone (e.g., T_DateTime)
 * - Timestamp: System timestamps (usually readonly)
 *
 * Selectors:
 * - All date types: #lookup_{fieldName} input (with calendar icon button)
 */
import { WidgetCommon } from './WidgetCommon';
import { getPage } from '../common';

export class DateWidget {
  /**
   * Set value in a date field by typing directly.
   * Note: Format depends on user's locale setting.
   *
   * @param {string} fieldName - Database column name (e.g., 'T_Date', 'T_DateTime')
   * @param {string} value - Date string in locale format (e.g., '01/25/2026' for en_US)
   * @param {Object} options - Optional settings
   * @param {boolean} options.clearFirst - Clear existing value first (default: true)
   * @param {boolean} options.triggerSave - Press Tab to trigger save (default: true)
   */
  static async setValue(fieldName, value, { clearFirst = true, triggerSave = true } = {}) {
    return await WidgetCommon.withStep(`DateWidget - Set ${fieldName} to "${value}"`, async () => {
      const page = getPage();
      const container = WidgetCommon.getFieldContainer(fieldName);

      // Date fields have input inside a date picker wrapper
      const input = container.locator('input').first();
      await input.waitFor({ state: 'visible', timeout: WidgetCommon.WIDGET_TIMEOUT });

      // Check if field is readonly
      const isDisabled = await input.isDisabled().catch(() => false);
      if (isDisabled) {
        console.log(`DateWidget - ${fieldName} is readonly, skipping`);
        return;
      }

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
   * Set date using the calendar picker.
   *
   * @param {string} fieldName - Database column name
   * @param {Object} date - Date components
   * @param {number} date.day - Day of month (1-31)
   * @param {number} date.month - Month (1-12)
   * @param {number} date.year - Full year (e.g., 2026)
   */
  static async setDatePicker(fieldName, { day, month, year }) {
    return await WidgetCommon.withStep(`DateWidget - Set ${fieldName} via picker to ${year}-${month}-${day}`, async () => {
      const page = getPage();
      const container = WidgetCommon.getFieldContainer(fieldName);

      // Click calendar icon to open picker
      const calendarButton = container.locator('[class*="calendar"], [class*="icon-calendar"]').first();
      await calendarButton.click();

      // Wait for calendar popup
      await page.locator('.react-datepicker, .rdtPicker').waitFor({
        state: 'visible',
        timeout: WidgetCommon.WIDGET_TIMEOUT,
      });

      // Navigate to correct month/year (implementation depends on calendar component)
      // This is a simplified version - may need adjustment based on actual calendar UI

      // Select day
      await page.locator(`.react-datepicker__day--0${day.toString().padStart(2, '0')}, .rdtDay:has-text("${day}")`).first().click();

      await WidgetCommon.waitForSaveComplete();
    });
  }

  /**
   * Get current value of a date field.
   *
   * @param {string} fieldName - Database column name
   * @returns {Promise<string>} Current date string in locale format
   */
  static async getValue(fieldName) {
    return await WidgetCommon.withStep(`DateWidget - Get ${fieldName} value`, async () => {
      const container = WidgetCommon.getFieldContainer(fieldName);
      const input = container.locator('input').first();
      return await input.inputValue();
    });
  }

  /**
   * Clear a date field.
   *
   * @param {string} fieldName - Database column name
   * @param {boolean} triggerSave - Press Tab to trigger save (default: true)
   */
  static async clear(fieldName, triggerSave = true) {
    return await WidgetCommon.withStep(`DateWidget - Clear ${fieldName}`, async () => {
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
   * Check if date field is empty.
   *
   * @param {string} fieldName - Database column name
   * @returns {Promise<boolean>} True if field is empty
   */
  static async isEmpty(fieldName) {
    const value = await this.getValue(fieldName);
    return !value || value === 'none' || value.trim() === '';
  }

  /**
   * Set current date (today).
   *
   * @param {string} fieldName - Database column name
   * @param {string} locale - Locale for date format (default: 'en-US')
   */
  static async setToday(fieldName, locale = 'en-US') {
    const today = new Date();
    const formattedDate = today.toLocaleDateString(locale, {
      month: '2-digit',
      day: '2-digit',
      year: 'numeric',
    });
    await this.setValue(fieldName, formattedDate);
  }
}
