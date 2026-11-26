import { test } from '../../playwright.config';
import { expect } from '@playwright/test';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';
import { BUSINESS_PARTNER_WINDOW_ID, SALES_ORDER_WINDOW_ID } from '../utils/WindowIds';
import { getTabRows, WEBAPI_BASE_URL } from '../utils/WebAPIValidation';

/**
 * Pins (must pass on today's code): a list widget used by keyboard in a view filter and in a process
 * dialog. ArrowDown highlights an option (the first ArrowDown of a closed list only opens it), Enter picks
 * the highlighted one, Tab leaves the list. After a pick the cursor is recorded and must stay where it was
 * for 1000 ms.
 *
 * Real-life cases: (a) on the sales order list the user filters the document status by keyboard and
 * applies; (b) on a business partner the user starts "Add/Remove Credit Stop Status" and sets the credit
 * status by keyboard.
 */

const LIST_OPTION = '.input-dropdown-list-option[data-testid^="option-"]';
const HIGHLIGHTED = '.input-dropdown-list-option-key-on';
const BUSINESS_PARTNER_CREDIT_TAB_ID = 'AD_Tab-540739';

/** The element that holds the cursor: tag, id, the list field (form-field-<Column>) and the panel it is in. */
async function describeCursor(page) {
  return await page.evaluate(() => {
    const e = document.activeElement;
    if (!e) return 'none';
    const field = e.closest('[class*="form-field-"]');
    const column = field ? (field.className.match(/form-field-(\S+)/) || [])[1] : null;
    const panel = e.closest('.panel-modal')
      ? 'process dialog'
      : e.closest('.filter-menu')
      ? 'filter panel'
      : 'page';
    const testId = e.getAttribute('data-testid');
    return `${e.tagName}${e.id ? '#' + e.id : ''}${testId ? '[' + testId + ']' : ''}${column ? ' in field ' + column : ''} (${panel})`;
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

const twoFrames = (page) => page.evaluate(() => new Promise((r) => requestAnimationFrame(() => requestAnimationFrame(r))));

/**
 * The focused list: ArrowDown opens it, wait for its options, ArrowDown until the option with `wantedKey`
 * is highlighted, Enter picks it.
 */
async function pickKeyByKeyboard(page, wantedKey) {
  await page.keyboard.press('ArrowDown');
  await page.locator(LIST_OPTION).first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  const highlighted = page.locator(HIGHLIGHTED);
  // The open list may already highlight an option (in CI it highlighted the first one, DR): check it before moving,
  // else the first ArrowDown skips it and the loop runs to the end of the list.
  let key = (await highlighted.count()) === 1 ? (await highlighted.getAttribute('data-testid')).replace(/^option-/, '') : null;
  for (let i = 0; i < 20 && key !== wantedKey; i++) {
    await page.keyboard.press('ArrowDown');
    await expect(highlighted, 'ArrowDown highlights one option').toHaveCount(1);
    key = (await highlighted.getAttribute('data-testid')).replace(/^option-/, '');
  }
  expect(key, `ArrowDown reaches the option with key ${wantedKey}`).toBe(wantedKey);
  await page.keyboard.press('Enter');
  await expect(page.locator('.input-dropdown-list'), 'the dropdown closes after Enter').toHaveCount(0, {
    timeout: SLOW_ACTION_TIMEOUT,
  });
}

function tag(language, story) {
  allure.epic('E0193: User Interface');
  allure.tag(story.feature);
  allure.tag(story.featureId);
  allure.tag('F50000: Frontend WebUI');
  allure.tag('F50000');
  allure.story(story.name);
  allure.severity('critical');
  allure.parameter('Language', language);
  allure.tag(language);
}

const languages = [
  { language: 'en_US', label: 'English' },
  { language: 'de_DE', label: 'German' },
];

languages.forEach(({ language, label }) => {
  test.describe(`List widget by keyboard — filter and process dialog (${label})`, () => {
    const start = async () => {
      const masterdata = await Backend.createMasterdata({
        request: {
          login: { user: { language, firstname: 'LWFP', lastname: 'Test' } },
          bpartners: {
            CUSTOMER1: { isVendor: false, isCustomer: true, isSoPriceList: true, name: 'Customer' },
          },
        },
      });
      allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');
      await LoginPage.goto();
      await LoginPage.login(masterdata.login.user);
      await DashboardPage.expectVisible();
      return masterdata;
    };

    test(`view filter: the document status list is picked by keyboard and applied (${label})`, async ({ page }) => {
      tag(language, { feature: 'F15000: Window Filters', featureId: 'F15000', name: 'View filter list: keyboard pick' });
      test.setTimeout(240000);
      const masterdata = await start();

      // the test's own draft order (Alt+N + customer)
      await SalesOrderPage.goto();
      await SalesOrderPage.clickNew();
      const recordId = await SalesOrderPage.selectCustomer(masterdata.bpartners.CUSTOMER1.bpartnerCode);

      await page.goto(`${FRONTEND_BASE_URL}/window/${SALES_ORDER_WINDOW_ID}`);
      await page.locator('.document-list-wrapper').waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });

      await page.locator('.filters-not-frequent button.btn-filter').click();
      await page.locator('li.filter-option-default').click();
      const panel = page.locator('.filter-menu.filter-widget');
      await panel.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      const docStatusList = panel.locator('.form-field-DocStatus .input-dropdown-container');
      await docStatusList.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      // the panel opens with the cursor in C_BPartner_ID; the Tab count to DocStatus depends on the filter's field order
      // (AD configuration), so the list is focused directly. Tab arriving in a list is pinned in the attribute popup spec.
      await docStatusList.focus();
      await expect(docStatusList, 'the DocStatus list holds the cursor').toBeFocused();

      await pickKeyByKeyboard(page, 'DR');
      await twoFrames(page);
      // observed today (EN + DE): the cursor stays in the list; Tab then moves it to the next filter field
      expect(await recordAndHoldCursor(page, 'after the DocStatus pick'), 'after the pick the cursor stays in the DocStatus list').toBe(
        'DIV in field DocStatus (filter panel)'
      );
      await page.keyboard.press('Tab');
      await twoFrames(page);
      expect(await recordAndHoldCursor(page, 'after Tab from the DocStatus list'), 'Tab moves the cursor to the next filter field and it stays there').toBe(
        'INPUT in field CreatedBy (filter panel)'
      );

      await panel.getByTestId('filter-apply-button').click();
      await expect(panel, 'the filter panel closes on apply').toHaveCount(0, { timeout: SLOW_ACTION_TIMEOUT });

      await expect
        .poll(
          async () => {
            // applying a filter makes the list a new view: read the id the page has now
            const viewId = new URL(page.url()).searchParams.get('viewId');
            const response = await page.request.get(
              `${WEBAPI_BASE_URL}/documentView/${SALES_ORDER_WINDOW_ID}/${viewId}?firstRow=0&pageLength=500`
            );
            if (!response.ok()) return `HTTP ${response.status()}`;
            const rows = (await response.json()).result ?? [];
            const keys = rows.map((row) => row.fieldsByName?.DocStatus?.value?.key);
            const hasOwn = rows.some((row) => String(row.id) === String(recordId));
            return JSON.stringify({ allDraft: keys.length > 0 && keys.every((k) => k === 'DR'), hasOwn });
          },
          { timeout: SLOW_ACTION_TIMEOUT, message: 'the filtered view holds the test order and only drafts' }
        )
        .toBe(JSON.stringify({ allDraft: true, hasOwn: true }));
    });

    test(`process dialog: the credit status list is picked by keyboard and the process starts (${label})`, async ({ page }) => {
      tag(language, { feature: 'F00040: Process framework', featureId: 'F00040', name: 'Process parameter list: keyboard pick' });
      test.setTimeout(240000);
      const masterdata = await start();
      const bpartnerId = masterdata.bpartners.CUSTOMER1.id;

      await page.goto(`${FRONTEND_BASE_URL}/window/${BUSINESS_PARTNER_WINDOW_ID}/${bpartnerId}`);
      await page.locator('.panel-primary').first().waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
      await page.locator('body').click();
      await page.keyboard.press('Alt+1');
      await page.getByTestId('action-C_BPartner_AddRemoveCreditStopStatus').click();

      const modal = page.locator('.panel-modal');
      const creditList = modal.locator('.form-field-setCreditStatus .input-dropdown-container');
      await creditList.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await expect(creditList, 'the dialog opens with the cursor in the credit status list').toBeFocused();

      await pickKeyByKeyboard(page, 'S');
      await twoFrames(page);
      expect(await recordAndHoldCursor(page, 'after the credit status pick'), 'after the pick the cursor stays in the credit status list').toBe(
        'DIV in field setCreditStatus (process dialog)'
      );
      await page.keyboard.press('Tab');
      await twoFrames(page);
      expect(await recordAndHoldCursor(page, 'after Tab from the credit status list'), 'Tab leaves the list for the Cancel button of the dialog and the cursor stays there').toBe(
        'BUTTON[process-modal-cancel-button] (process dialog)'
      );

      await modal.getByTestId('process-modal-start-button').click();
      await expect(modal, 'the dialog closes after start').toHaveCount(0, { timeout: SLOW_ACTION_TIMEOUT });
      await expect
        .poll(
          async () => {
            const rows = (await getTabRows(BUSINESS_PARTNER_WINDOW_ID, bpartnerId, BUSINESS_PARTNER_CREDIT_TAB_ID)) ?? [];
            const value = rows[0]?.fieldsByName?.SOCreditStatus?.value;
            return value === undefined ? 'SOCreditStatus missing' : value?.key;
          },
          { timeout: SLOW_ACTION_TIMEOUT, message: 'the process set SOCreditStatus' }
        )
        .toBe('S');
    });
  });
});
