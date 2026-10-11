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
import { expect } from '@playwright/test';
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
    const match = url.match(new RegExp(`/window/${TEST_WINDOW_ID}/(\\d+)`));
    return match ? match[1] : null;
  }

  /**
   * Reload the current record from the backend and wait until its data is shown.
   * Used to verify that field values persist after reload.
   *
   * Uses page.reload(): page.keyboard.press('F5') did not reload the page in the frontend-webui e2e runs
   * (e.g. https://github.com/metasfresh/metasfresh/actions/runs/38111487029, shard 4: the trace shows no request
   * after the key press and the page kept its in-memory state), so it could not prove that anything was persisted.
   * Waits for the record's data to be fetched again by the reloaded page (a response that arrives before the main
   * frame navigated does not count), so a reload the page cancels (e.g. its unsaved-changes "leave page?" prompt,
   * which Playwright dismisses) fails here instead of the checks reading the old page. Then waits until the record
   * is shown (its read-only "Test ID" field shows the record ID); the field values come with that same response.
   */
  static async reload() {
    return await test.step('TestWindowPage - Reload page', async () => {
      const page = getPage();
      const recordId = this.getRecordId();
      if (!recordId) {
        throw new Error(`TestWindowPage.reload: no Test record open (URL: ${page.url()})`);
      }

      let navigated = false;
      const onNavigated = (frame) => {
        if (frame === page.mainFrame()) {
          navigated = true;
        }
      };
      page.on('framenavigated', onNavigated);
      try {
        const recordPath = new RegExp(`/window/${TEST_WINDOW_ID}/${recordId}/?$`);
        const recordFetched = page.waitForResponse(
          (response) =>
            navigated && response.request().method() === 'GET' && recordPath.test(new URL(response.url()).pathname),
          { timeout: SLOW_ACTION_TIMEOUT }
        );
        recordFetched.catch(() => {}); // avoid an unhandled rejection if the reload itself throws; awaited below
        await page.reload();
        await recordFetched;
      } finally {
        page.off('framenavigated', onNavigated);
      }
      await expect(page.locator('.form-field-Test_ID input')).toHaveValue(recordId, { timeout: SLOW_ACTION_TIMEOUT });
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
