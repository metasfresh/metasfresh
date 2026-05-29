/**
 * Test Window Page Object (AD_Window_ID=127)
 *
 * The Test Window contains all metasfresh widget types and is used
 * for comprehensive widget testing.
 *
 * Widget Types Available:
 * - Text: Name, Description
 * - LongText: Help (Comment/Help), CharacterData, BPMemo
 * - Numeric: T_Qty, T_Amount, T_Number, T_Integer, Test_ID (readonly)
 * - Date: T_Date, T_DateTime, T_Time, Created (readonly), Updated (readonly)
 * - Boolean: IsActive (Switch), Processed (YesNo checkbox)
 * - Button: Processing (Process Now)
 * - Lookup: AD_Org_ID, C_BPartner_ID, C_Payment_ID, M_Product_ID, M_Locator_ID
 * - List: C_Currency_ID, C_UOM_ID, M_HU_PI_Item_Product_ID
 * - Address: C_Location_ID
 * - ProductAttributes: M_AttributeSetInstance_ID
 * - Image: BinaryData
 * - Composed: C_BPartner_Location_ID (BPartner + Location + Contact)
 */
import { test } from '../../../playwright.config';
import { getPage, FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT } from '../common';
import { TEST_WINDOW_ID } from '../WindowIds';

export class TestWindowPage {
  /**
   * Navigate to the Test Window list view.
   */
  static async goto() {
    return await test.step('TestWindowPage - Navigate to Test Window', async () => {
      const page = getPage();
      await page.goto(`${FRONTEND_BASE_URL}/window/${TEST_WINDOW_ID}`);
      await page.waitForLoadState('networkidle').catch(() => {});
    });
  }

  /**
   * Navigate to create a new Test record.
   * @returns {Promise<string>} The new record ID
   */
  static async createNew() {
    return await test.step('TestWindowPage - Create new Test record', async () => {
      const page = getPage();
      await page.goto(`${FRONTEND_BASE_URL}/window/${TEST_WINDOW_ID}/new`);

      // Wait for record to be created and URL to update with record ID
      await page.waitForURL(/\/window\/127\/\d+/, { timeout: SLOW_ACTION_TIMEOUT });

      // Wait for form to load
      await page.waitForTimeout(1000);

      return this.getRecordId();
    });
  }

  /**
   * Navigate to an existing Test record by ID.
   * @param {string|number} recordId - The record ID to navigate to
   */
  static async gotoRecord(recordId) {
    return await test.step(`TestWindowPage - Navigate to record ${recordId}`, async () => {
      const page = getPage();
      await page.goto(`${FRONTEND_BASE_URL}/window/${TEST_WINDOW_ID}/${recordId}`);
      await page.waitForLoadState('networkidle').catch(() => {});
      await page.waitForTimeout(500);
    });
  }

  /**
   * Get the current record ID from the URL.
   * @returns {string} The record ID
   */
  static getRecordId() {
    const page = getPage();
    const url = page.url();
    const match = url.match(/\/window\/127\/(\d+)/);
    return match ? match[1] : null;
  }

  /**
   * Reload the current page (press F5).
   * Used to verify that field values persist after reload.
   */
  static async reload() {
    return await test.step('TestWindowPage - Reload page (F5)', async () => {
      const page = getPage();
      await page.keyboard.press('F5');
      await page.waitForLoadState('networkidle').catch(() => {});
      await page.waitForTimeout(1000);
    });
  }

  /**
   * Wait for the form to be fully loaded.
   */
  static async waitForFormLoaded() {
    return await test.step('TestWindowPage - Wait for form loaded', async () => {
      const page = getPage();

      // Wait for any loading indicators to disappear
      await page
        .locator('.rotating, .spinner, .indicator-pending')
        .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
        .catch(() => {});

      // Wait for at least one form field to be visible
      await page
        .locator('.form-group, .form-field-Name')
        .first()
        .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    });
  }

  /**
   * Check if we are on the Test Window.
   * @returns {Promise<boolean>} True if on Test Window
   */
  static async isOnTestWindow() {
    const page = getPage();
    const url = page.url();
    return url.includes(`/window/${TEST_WINDOW_ID}`);
  }

  /**
   * Click the New button (Alt+N) to create a new record.
   */
  static async clickNew() {
    return await test.step('TestWindowPage - Click New (Alt+N)', async () => {
      const page = getPage();
      await page.keyboard.press('Alt+n');
      await page.waitForURL(/\/window\/127\/\d+/, { timeout: SLOW_ACTION_TIMEOUT });
      await page.waitForTimeout(500);
    });
  }
}
