import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';
import { SLOW_ACTION_TIMEOUT } from '../utils/common';
import { SALES_ORDER_WINDOW_ID } from '../utils/WindowIds';
import { getRecordData } from '../utils/WebAPIValidation';
import { pickCustomerByMouse, expectFocusOn } from '../utils/QuickInputFocus';

/**
 * Pins (must pass on today's code): in the sales order header a mouse click on a list field opens its
 * dropdown, a mouse pick keeps the value, a press on the dropdown scrollbar does not close it, and Escape
 * closes it without changing the value. (Escape blurs the field by design, so the cursor is not asserted.)
 */

// List widgets (select-dropdown) have no `#lookup_<Column>` id (only Lookup widgets do), so the fields are
// addressed by the structural class `form-field-<ColumnName>` of their widget wrapper.

const PAYMENT_RULE = '.form-field-PaymentRule .input-dropdown-container';
const PAYMENT_TERM = '.form-field-C_PaymentTerm_ID .input-dropdown-container';
const OPTION = '.input-dropdown-list-option[data-testid^="option-"]';

// Same format as describeActiveElement in QuickInputFocus.js (not exported there).
async function describeActiveElement(page) {
  return await page.evaluate(() => {
    const e = document.activeElement;
    if (!e) return 'none';
    const lookup = e.closest('[id^="lookup_"]');
    return `${e.tagName}${e.id ? '#' + e.id : ''}${lookup ? ' in ' + lookup.id : ''}${
      e.closest('.quick-input-container') ? ' (quick input)' : ''
    }`;
  });
}

async function savedKey(recordId, column) {
  const f = (await getRecordData(SALES_ORDER_WINDOW_ID, recordId)).fieldsByName;
  const key = f?.[column]?.value?.key;
  return key === undefined || key === null ? null : String(key);
}

/** The option key from the data-testid of an option element. */
async function optionKey(option) {
  const testId = await option.getAttribute('data-testid');
  return testId.replace(/^option-/, '');
}

test.use({
  launchOptions: {
    args: [
      '--no-sandbox',
      '--unsafely-treat-insecure-origin-as-secure=http://webapi-test:8080',
      '--disable-features=StrictOriginPolicy,HttpsOnlyMode,BlockInsecurePrivateNetworkRequests',
      '--disable-site-isolation-trials',
      '--disable-web-security',
      '--ignore-certificate-errors',
      '--allow-insecure-localhost',
      '--allow-running-insecure-content',
    ],
    ignoreDefaultArgs: ['--hide-scrollbars'],
  },
});

const testCases = [
  { language: 'en_US', label: 'English' },
  { language: 'de_DE', label: 'German' },
];

testCases.forEach(({ language, label }) => {
  test.describe(`List widget mouse and Escape (${label})`, () => {
    const tag = (story) => {
      allure.epic('E0100: Sales');
      allure.tag('F50000: Frontend WebUI');
      allure.tag('F50000');
      allure.tag('F00100: Sales Order Window');
      allure.tag('F00100');
      allure.story(story);
      allure.severity('critical');
      allure.parameter('Language', language);
      allure.tag(language);
    };

    const start = async (page) => {
      const masterdata = await Backend.createMasterdata({
        request: {
          login: { user: { language, firstname: 'LWME', lastname: 'Test' } },
          bpartners: { CUST: { isVendor: false, isCustomer: true, isSoPriceList: true, name: 'Customer' } },
        },
      });
      allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');
      await LoginPage.goto();
      await LoginPage.login(masterdata.login.user);
      await DashboardPage.expectVisible();
      await Backend.resetWebApiCaches();
      await SalesOrderPage.goto();
      await SalesOrderPage.clickNew();
      const bp = masterdata.bpartners.CUST;
      const recordId = await pickCustomerByMouse({ searchText: bp.bpartnerCode, bpartnerCode: bp.bpartnerCode });
      await expect(page.locator(PAYMENT_RULE)).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
      return recordId;
    };

    test(`mouse pick in PaymentRule then Tab (${label})`, async ({ page }) => {
      tag('Header list: one click opens, a mouse pick keeps the value, Tab moves on');
      test.setTimeout(240000);
      const recordId = await start(page);

      await page.locator(PAYMENT_RULE).click();
      await expect(page.locator(PAYMENT_RULE), 'one click opens PaymentRule').toHaveClass(/opened/, {
        timeout: SLOW_ACTION_TIMEOUT,
      });
      const option = page.locator(OPTION).nth(1);
      await option.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      const key = await optionKey(option);
      await option.click();
      await expect(page.locator(PAYMENT_RULE), 'dropdown closed after the pick').not.toHaveClass(/opened/);

      await page.keyboard.press('Tab');
      await expect
        .poll(
          async () =>
            await page.evaluate(() => {
              const e = document.activeElement;
              return !!e && e !== document.body && !e.closest('.form-field-PaymentRule');
            }),
          { timeout: SLOW_ACTION_TIMEOUT, message: 'after Tab: cursor is on the next field, not on body or in PaymentRule' }
        )
        .toBe(true);
      console.log(`Active element after Tab (${language}): ${await describeActiveElement(page)}`);

      await expect
        .poll(() => savedKey(recordId, 'PaymentRule'), { timeout: SLOW_ACTION_TIMEOUT, message: `saved PaymentRule equals ${key}` })
        .toBe(key);
    });

    test(`click-open and scrollbar in C_PaymentTerm_ID (${label})`, async ({ page }) => {
      tag('Header list: one click opens, the scrollbar keeps it open, a mouse pick keeps the value');
      test.setTimeout(240000);
      const recordId = await start(page);

      await page.locator(PAYMENT_TERM).click();
      await expect(page.locator(PAYMENT_TERM), 'one click opens C_PaymentTerm_ID').toHaveClass(/opened/, {
        timeout: SLOW_ACTION_TIMEOUT,
      });
      const list = page.locator('.input-dropdown-list').first();
      await list.locator(OPTION).first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      const before = await savedKey(recordId, 'C_PaymentTerm_ID');
      const box = await list.boundingBox();
      const x = box.x + box.width - 4;
      const y = box.y + box.height / 2;
      const geo = await list.evaluate((l, [px, py]) => {
        const cs = getComputedStyle(l);
        const hit = document.elementFromPoint(px, py);
        return {
          scrollable: l.scrollHeight > l.clientHeight,
          scrollbarWidth: l.offsetWidth - l.clientWidth - parseFloat(cs.borderLeftWidth) - parseFloat(cs.borderRightWidth),
          hitIsList: hit === l,
        };
      }, [x, y]);
      expect(geo.scrollable, 'the payment term list is scrollable').toBe(true);
      expect(geo.scrollbarWidth, 'the list draws a classic scrollbar').toBeGreaterThan(0);
      expect(geo.hitIsList, 'the press point is on the list scrollbar, not on an option').toBe(true);
      await page.mouse.move(x, y);
      await page.mouse.down();
      await page.mouse.up();
      await expect(page.locator(PAYMENT_TERM), 'dropdown still open after a scrollbar press').toHaveClass(/opened/);
      await expect(list, 'option list still visible after a scrollbar press').toBeVisible();
      expect(await savedKey(recordId, 'C_PaymentTerm_ID'), 'scrollbar press selects nothing').toBe(before);

      const option = page.locator(OPTION).nth(1);
      await option.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      const key = await optionKey(option);
      await option.click();
      await expect
        .poll(() => savedKey(recordId, 'C_PaymentTerm_ID'), {
          timeout: SLOW_ACTION_TIMEOUT,
          message: `saved C_PaymentTerm_ID equals ${key}`,
        })
        .toBe(key);
    });

    test(`Escape closes PaymentRule (${label})`, async ({ page }) => {
      tag('Header list: ArrowDown opens, Escape closes it and keeps the value');
      test.setTimeout(240000);
      const recordId = await start(page);

      const before = await savedKey(recordId, 'PaymentRule');
      // the partner pick advances location -> contact; let that hop land before taking the cursor
      await page.waitForFunction(() => !!document.activeElement?.closest('#lookup_AD_User_ID'), null, { timeout: SLOW_ACTION_TIMEOUT });
      await page.locator(PAYMENT_RULE).focus();
      await expectFocusOn(page.locator(PAYMENT_RULE), 'PaymentRule focused before ArrowDown');
      await page.keyboard.press('ArrowDown');
      await expect(page.locator(PAYMENT_RULE), 'ArrowDown opens PaymentRule').toHaveClass(/opened/, {
        timeout: SLOW_ACTION_TIMEOUT,
      });
      // the options are loaded and the dropdown (which owns the Escape handler) is rendered
      await page.locator(OPTION).first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await page.keyboard.press('Escape');
      await expect(page.locator(PAYMENT_RULE), 'Escape closes PaymentRule').not.toHaveClass(/opened/, {
        timeout: SLOW_ACTION_TIMEOUT,
      });
      await expect(page.locator('.input-dropdown-list'), 'no dropdown list after Escape').toHaveCount(0);
      await page.waitForTimeout(1000);
      await expect(page.locator(PAYMENT_RULE), 'still closed 1 s after Escape').not.toHaveClass(/opened/);
      expect(await savedKey(recordId, 'PaymentRule'), 'PaymentRule value unchanged by Escape').toBe(before);
    });
  });
});
