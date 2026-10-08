import { test } from '../../playwright.config';
import { expect } from '@playwright/test';
import { allure } from 'allure-playwright';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SLOW_ACTION_TIMEOUT } from '../utils/common';
import { SALES_ORDER_WINDOW_ID } from '../utils/WindowIds';
import { waitForRecordSaved, getFieldData } from '../utils/WebAPIValidation';
import { createMasterdata } from '../utils/OrderLineHarness';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';

/**
 * A text field of a single-row form (the sales order header): Enter saves the value and keeps the
 * focus on that field; typing into a filled field edits its value in place.
 *
 * Features tested:
 * - F5010: Order Lines Grid
 */
const HEADER_TEXT_FIELD = 'POReference';

test.describe('Single-row form text field', () => {
  test('Enter saves the value and keeps the focus on the field; typing into a filled field edits it in place', async ({
    page,
  }) => {
    allure.epic('E0500: Sales Orders');
    allure.tag('F5010: Order Lines Grid');
    allure.tag('F5010');
    allure.story('Single-row form text field: Enter and typing');
    allure.severity('critical');
    allure.description(`
1. Open a new sales order (a single-row form).
2. Type into the empty "${HEADER_TEXT_FIELD}" field and press Enter: the value is saved and the focus stays on the field.
3. Type into the now filled field: the text is appended, not replacing the value; Enter saves it.
    `);

    test.setTimeout(120000);

    const masterdata = await createMasterdata();
    allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    await SalesOrderPage.goto();
    await SalesOrderPage.clickNew();
    // the customer fills the remaining mandatory header fields, so the order can be saved
    const recordId = await SalesOrderPage.selectCustomer(masterdata.bpartners.CUSTOMER1.bpartnerCode);

    const headerField = page.locator(`.form-field-${HEADER_TEXT_FIELD} input.input-field`).first();
    await headerField.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

    const firstValue = `POREF-${Date.now()}`;

    await test.step('Typing into an empty field and pressing Enter commits it, focus stays on the same field', async () => {
      await headerField.click();
      await headerField.fill(firstValue);

      const patchResponse = page.waitForResponse(
        (response) =>
          response.url().includes(`/window/${SALES_ORDER_WINDOW_ID}/${recordId}`) &&
          response.request().method() === 'PATCH',
        { timeout: SLOW_ACTION_TIMEOUT }
      );
      await page.keyboard.press('Enter');
      await patchResponse;

      const activeElementInfo = await page.evaluate(() => {
        const el = document.activeElement;
        return { tag: el?.tagName, cls: el?.className };
      });
      const isSameFieldFocused = await headerField.evaluate(
        (el, info) => el.tagName === info.tag && el.className === info.cls,
        activeElementInfo
      );
      console.log(`[INFO] active element after Enter: ${JSON.stringify(activeElementInfo)}`);

      expect(
        isSameFieldFocused,
        'Enter must keep the focus on the same field'
      ).toBe(true);

      await waitForRecordSaved(SALES_ORDER_WINDOW_ID, recordId, { maxRetries: 20, retryDelayMs: 1000 });
      const fieldData = await getFieldData(SALES_ORDER_WINDOW_ID, recordId, HEADER_TEXT_FIELD);
      expect(fieldData.value, 'the typed value must be committed to the backend, unchanged').toBe(firstValue);
    });

    await test.step('Typing into the already-filled field edits in place (append), not a full replace', async () => {
      await headerField.click();
      await page.keyboard.press('End');
      await page.keyboard.type('-APPEND');

      const domValue = await headerField.inputValue();
      console.log(`[INFO] field value after typing into a filled field: "${domValue}"`);

      expect(
        domValue,
        'typing into an already-filled field must edit in place (append), not clear/replace it'
      ).toBe(`${firstValue}-APPEND`);

      const patchResponse = page.waitForResponse(
        (response) =>
          response.url().includes(`/window/${SALES_ORDER_WINDOW_ID}/${recordId}`) &&
          response.request().method() === 'PATCH',
        { timeout: SLOW_ACTION_TIMEOUT }
      );
      await page.keyboard.press('Enter');
      await patchResponse;

      await waitForRecordSaved(SALES_ORDER_WINDOW_ID, recordId, { maxRetries: 20, retryDelayMs: 1000 });
      const fieldData = await getFieldData(SALES_ORDER_WINDOW_ID, recordId, HEADER_TEXT_FIELD);
      expect(fieldData.value, 'the appended value must be committed to the backend').toBe(`${firstValue}-APPEND`);
    });
  });
});
