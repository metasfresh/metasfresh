import { test } from '../../playwright.config';
import { expect } from '@playwright/test';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';
import { SLOW_ACTION_TIMEOUT } from '../utils/common';

/**
 * Pins (must pass on today's code): the attribute popup ("Merkmale") opened from an order-line grid
 * cell is filled by keyboard only: first list ArrowDown + Enter, Tab, second list ArrowDown + Enter,
 * Tab, text typed into the text attribute. The popup stays open throughout, Alt+Enter closes it, and
 * the line keeps both list values and the text.
 *
 * Real-life case: on an order line of a product with two list attributes and a text attribute, the user
 * opens the popup and fills it without the mouse.
 *
 * Features tested:
 * - F67000: Attributes
 * - F50000: Frontend WebUI
 */

const ATTRIBUTE_COLUMN = 'M_AttributeSetInstance_ID';
// Observed on today's code (EN + DE): the popup's fields in order; the cursor sits on the list container (DIV) or the text input
const FIRST_LIST = 'DIV in popup field 0';
const SECOND_LIST = 'DIV in popup field 1';
const TEXT_FIELD = 'INPUT in popup field 2';
const LIST_OPTION = '.input-dropdown-list-option[data-testid^="option-"]';

/** The element that holds the cursor: tag, its position among the popup's fields, or where it is otherwise. */
async function describeCursor(page) {
  return await page.evaluate(() => {
    const e = document.activeElement;
    if (!e) return 'none';
    const popup = e.closest('.attributes-dropdown');
    if (!popup) return `${e.tagName} outside the popup`;
    const groups = Array.from(popup.querySelectorAll('.form-group'));
    const group = e.closest('.form-group');
    return `${e.tagName} in popup field ${group ? groups.indexOf(group) : '?'}`;
  });
}

/** Record where the cursor is, then assert the same element still holds it 1000 ms later. */
async function recordAndHoldCursor(page, what) {
  const now = await describeCursor(page);
  console.log(`OBSERVED cursor ${what}: ${now}`);
  await page.waitForTimeout(1000);
  expect(await describeCursor(page), `${what}: the cursor stays where it was (was ${now})`).toBe(now);
  return now;
}

const languages = [
  { language: 'en_US', label: 'English' },
  { language: 'de_DE', label: 'German' },
];

languages.forEach(({ language, label }) => {
  test.describe(`Sales order-line grid — attribute popup by keyboard (${label})`, () => {
    test(`two list attributes and a text attribute are filled by keyboard, Alt+Enter closes (${label})`, async ({ page }) => {
      allure.epic('E0100: Sales');
      allure.tag('F67000: Attributes');
      allure.tag('F67000');
      allure.tag('F50000: Frontend WebUI');
      allure.tag('F50000');
      allure.story('Attribute popup: keyboard fill');
      allure.severity('critical');
      allure.parameter('Language', language);
      allure.tag(language);
      test.setTimeout(240000);

      const runId = Date.now();
      const masterdata = await Backend.createMasterdata({
        request: {
          login: { user: { language, firstname: 'E2E', lastname: 'Tester' } },
          bpartners: {
            CUSTOMER1: { isVendor: false, isCustomer: true, isSoPriceList: true, name: 'Customer' },
          },
          productCategories: {
            CAT1: { attributeSetName: 'KBD_SET' },
          },
          attributes: {
            LIST_A: {
              value: `E2EKBDA${runId}`,
              name: `E2E Kbd List A ${runId}`,
              attributeValueType: 'LIST',
              isInstanceAttribute: true,
              listValues: [
                { value: 'A1', name: 'Alpha one' },
                { value: 'A2', name: 'Alpha two' },
              ],
              attributeSetNames: ['KBD_SET'],
            },
            LIST_B: {
              value: `E2EKBDB${runId}`,
              name: `E2E Kbd List B ${runId}`,
              attributeValueType: 'LIST',
              isInstanceAttribute: true,
              listValues: [
                { value: 'B1', name: 'Beta one' },
                { value: 'B2', name: 'Beta two' },
              ],
              attributeSetNames: ['KBD_SET'],
            },
            TEXT_ATTR: {
              value: `E2EKBDT${runId}`,
              name: `E2E Kbd Text ${runId}`,
              attributeValueType: 'STRING',
              isInstanceAttribute: true,
              attributeSetNames: ['KBD_SET'],
            },
          },
          products: {
            Product1: {
              name: 'KBDPROD',
              type: 'Item',
              productCategory: 'CAT1',
              prices: [{ price: 12.5, currencyCode: 'EUR' }],
            },
          },
        },
      });
      allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');

      await LoginPage.goto();
      await LoginPage.login(masterdata.login.user);
      await DashboardPage.expectVisible();

      await SalesOrderPage.goto();
      await SalesOrderPage.clickNew();
      const recordId = await SalesOrderPage.selectCustomer(masterdata.bpartners.CUSTOMER1.bpartnerCode);
      await SalesOrderPage.addOrderLine({
        product: masterdata.products.Product1.productCode,
        quantity: 1,
        recordId,
      });

      const cell = page.locator(`[data-cy="cell-${ATTRIBUTE_COLUMN}"]`).first();
      await cell.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      const popup = page.locator('.attributes-dropdown');
      const highlighted = page.locator('.input-dropdown-list-option-key-on');
      const typedText = 'TXT1';

      await cell.dblclick();
      await cell.locator('.attributes-in-table button').click();
      await popup.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await expect(popup.locator('.input-dropdown-container'), 'the popup shows both list attributes').toHaveCount(2, {
        timeout: SLOW_ACTION_TIMEOUT,
      });
      expect(await recordAndHoldCursor(page, 'popup just opened'), 'the popup opens with the first list').toBe(FIRST_LIST);

      /** ArrowDown opens the focused list, ArrowDown highlights an option, Enter picks it; returns the picked key. */
      const pickByKeyboard = async () => {
        await page.keyboard.press('ArrowDown');
        await page.locator(LIST_OPTION).first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
        await page.keyboard.press('ArrowDown');
        await expect(highlighted, 'ArrowDown highlights an option').toHaveCount(1);
        const key = (await highlighted.getAttribute('data-testid')).replace(/^option-/, '');
        await page.keyboard.press('Enter');
        await expect(page.locator('.input-dropdown-list'), 'the dropdown closes after Enter').toHaveCount(0, {
          timeout: SLOW_ACTION_TIMEOUT,
        });
        return key;
      };

      const keyA = await pickByKeyboard();
      await page.evaluate(() => new Promise((r) => requestAnimationFrame(() => requestAnimationFrame(r))));
      await expect(popup, 'popup open after the first pick').toBeVisible();
      expect(await recordAndHoldCursor(page, 'after the first list pick'), 'after the first pick the cursor stays in the first list').toBe(FIRST_LIST);

      await page.keyboard.press('Tab');
      await page.evaluate(() => new Promise((r) => requestAnimationFrame(() => requestAnimationFrame(r))));
      await expect(popup, 'popup open after Tab').toBeVisible();
      expect(await recordAndHoldCursor(page, 'after Tab from the first list'), 'Tab moves the cursor to the second list').toBe(SECOND_LIST);

      const keyB = await pickByKeyboard();
      await page.evaluate(() => new Promise((r) => requestAnimationFrame(() => requestAnimationFrame(r))));
      await expect(popup, 'popup open after the second pick').toBeVisible();
      expect(await recordAndHoldCursor(page, 'after the second list pick'), 'after the second pick the cursor stays in the second list').toBe(SECOND_LIST);

      await page.keyboard.press('Tab');
      await page.evaluate(() => new Promise((r) => requestAnimationFrame(() => requestAnimationFrame(r))));
      await expect(popup, 'popup open after the second Tab').toBeVisible();
      expect(await recordAndHoldCursor(page, 'after Tab from the second list'), 'Tab moves the cursor to the text attribute').toBe(TEXT_FIELD);

      await page.keyboard.type(typedText);
      await expect(popup, 'popup open after typing').toBeVisible();
      expect(await recordAndHoldCursor(page, 'after typing the text'), 'typing keeps the cursor in the text attribute').toBe(TEXT_FIELD);
      const completed = page.waitForRequest(
        (request) => request.method() === 'POST' && /\/pattribute\/[^/]+\/complete$/.test(new URL(request.url()).pathname),
        { timeout: SLOW_ACTION_TIMEOUT }
      );
      const lineReRead = page.waitForResponse(
        (response) =>
          response.request().method() === 'GET' &&
          response.url().includes(`/${recordId}/AD_Tab-`) &&
          response.url().includes('ids='),
        { timeout: SLOW_ACTION_TIMEOUT }
      );
      await page.keyboard.press('Alt+Enter');
      await popup.waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
      const completeBody = (await completed).postData();
      const saved = Object.fromEntries(JSON.parse(completeBody).events.map((e) => [e.path, e.value]));
      expect(saved[`E2EKBDA${runId}`]?.key, 'saved key of the first list attribute').toBe(keyA);
      expect(saved[`E2EKBDB${runId}`]?.key, 'saved key of the second list attribute').toBe(keyB);
      expect(saved[`E2EKBDT${runId}`], 'saved text of the text attribute').toBe(typedText);
      await lineReRead;
      await expect(cell, 'the re-read line shows the typed text').toContainText(typedText, { timeout: SLOW_ACTION_TIMEOUT });
      // the list values' names are test data (the same in every language)
      await expect(cell, 'the re-read line shows both list values').toContainText('Alpha one');
      await expect(cell, 'the re-read line shows both list values').toContainText('Beta one');
    });
  });
});
